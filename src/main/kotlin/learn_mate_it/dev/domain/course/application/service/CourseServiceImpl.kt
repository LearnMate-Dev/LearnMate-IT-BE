package learn_mate_it.dev.domain.course.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.course.application.dto.response.CourseDto
import learn_mate_it.dev.domain.course.application.dto.response.CourseListDto
import learn_mate_it.dev.domain.course.application.dto.response.StepDto
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.domain.enums.CourseStatus
import learn_mate_it.dev.domain.course.domain.enums.CourseType
import learn_mate_it.dev.domain.course.domain.enums.StepStatus
import learn_mate_it.dev.domain.course.domain.enums.StepType
import learn_mate_it.dev.domain.course.domain.model.UserStepProgress
import learn_mate_it.dev.domain.course.domain.repository.UserStepProgressRepository
import learn_mate_it.dev.domain.user.domain.model.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    private val stepProgressRepository: UserStepProgressRepository,
) : CourseService {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Start User's New Step Course
     *
     * @param courseLv level of course to start (1 ~ 3)
     * @param stepLv level of step to start (1 ~ 3)
     * @return StepInitDto id of step progress, info of step, info of all quizes
     */
    @Transactional
    override fun startStep(courseLv: Int, stepLv: Int): StepInitDto {
        val user = getUser()

        // valid step info and get step, course type
        val course = CourseType.from(courseLv)
        val step = StepType.from(course, stepLv)
        validIsStepInOrder(step, user.userId)

        // save step progress
        val stepProgress = stepProgressRepository.save(
            UserStepProgress(
                stepType = step,
                userId = user.userId
            )
        )

        return StepInitDto.toStepInitDto(
            stepProgressId = stepProgress.stepProgressId,
            courseLv = courseLv,
            stepLv = stepLv,
            step = step
        )
    }

    private fun validIsStepInOrder(step: StepType, userId: Long) {
        if (step.isFirstStep()) return

        validArePreviousStepsCompleted(step, userId)
        validIsStepAlreadyStarted(step, userId)
    }

    private fun validArePreviousStepsCompleted(step: StepType, userId: Long) {
        val previousStepList = step.getPreviousStep()
        val completedStepSet = getCompletedStepTypeSet(previousStepList, userId)

        val isAllCompleted = previousStepList.all { completedStepSet.contains(it) }
        require(isAllCompleted) { throw GeneralException(ErrorStatus.INVALID_STEP_ORDER) }
    }

    private fun getCompletedStepTypeSet(stepTypeList: List<StepType>, userId: Long): Set<StepType> {
        return stepProgressRepository
            .findByStepTypeInAndUserIdAndCompletedAtIsNotNull(stepTypeList, userId)
            .map { it.stepType }
            .toSet()
    }

    private fun validIsStepAlreadyStarted(step: StepType, userId: Long) {
        val isStepAlreadyStarted = stepProgressRepository.existsByStepTypeAndUserIdAndCompletedAtIsNull(step, userId)
        require(!isStepAlreadyStarted) { throw GeneralException(ErrorStatus.ALREADY_ON_STEP) }
    }

    /**
     * Set Complete User's Step Progress
     *
     * @param stepProgressId id of step progress
     */
    @Transactional
    override fun endStep(stepProgressId: Long) {
        val user = getUser()
        val stepProgress = getStepProgress(stepProgressId, user.userId)
        validIsStepAlreadyCompleted(stepProgress)

        stepProgress.completeStep()
    }

    /**
     * Delete User's Step Progress
     *
     * @param stepProgressId id of step progress
     */
    @Transactional
    override fun deleteStep(stepProgressId: Long) {
        val user = getUser()
        val stepProgress = getStepProgress(stepProgressId, user.userId)
        validIsStepAlreadyCompleted(stepProgress)

        stepProgressRepository.delete(stepProgress)
    }

    private fun validIsStepAlreadyCompleted(stepProgress: UserStepProgress) {
        require(!stepProgress.isCompleted()){ throw GeneralException(ErrorStatus.ALREADY_COMPLETED_STEP) }
    }

    /**
     * Get Each Course's Information
     * Progress of Course, List of Step
     *
     * @param courseLv level of course (1 ~ 3)
     * @return CourseDto Info of course, each step and status
     */
    override fun getCourseInfo(): CourseListDto {
        val user = getUser()
        val courseList = CourseType.getAllCourseList()
        val completedStepSet = getAllCompletedStepTypeSet(user.userId)

        val courseDtoList = courseList.map {

            val courseStatus = getCourseStatus(it, completedStepSet)
            val stepDtoList = getStepDtoList(it, courseStatus, completedStepSet)
            val progress = getCourseProgress(stepDtoList)

            CourseDto.toCourseDto(
                course = it,
                stepList = stepDtoList,
                progress = progress,
                courseStatus = courseStatus
            )
        }

        return CourseListDto.toCourseListDto(courseDtoList)
    }

    private fun getCourseStatus(course: CourseType, completedStepSet: Set<StepType>): CourseStatus {
        if (course.isFirstCourse()) return CourseStatus.UNLOCK

        // get all previous step list
        val previousCourseList = course.getPreviousCourse()
        val previousStepList = previousCourseList.flatMap { StepType.getStepList(it.level) }

        // check is all previous step are completed
        val isAllCompleted = previousStepList.all { completedStepSet.contains(it) }
        return if (isAllCompleted) CourseStatus.UNLOCK else CourseStatus.LOCK
    }

    private fun getStepDtoList(course: CourseType, courseStatus: CourseStatus, completedStepSet: Set<StepType>): List<StepDto> {
        val stepList = StepType.getStepList(course.level)

        // if course is locked, return LOCK step list
        if (courseStatus.isLocked()) {
            return stepList.map { StepDto.toStepDto(it, StepStatus.LOCK) }
        }

        return stepList
            .map {
                val status = if (it in completedStepSet) StepStatus.SOLVED else StepStatus.UNSOLVED
                StepDto.toStepDto(it, status)
            }
    }

    private fun getCourseProgress(stepList: List<StepDto>): Int {
        if (stepList.isEmpty()) return 0

        val progress = stepList.count { it.stepStatus == StepStatus.SOLVED }
        return ((progress.toDouble() / stepList.size) * 100).toInt()
    }

    private fun getStepProgress(stepProgressId: Long, userId: Long): UserStepProgress {
        return stepProgressRepository.findByStepProgressIdAndUserId(stepProgressId, userId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_STEP_PROGRESS)
    }

    private fun getAllCompletedStepTypeSet(userId: Long): Set<StepType> {
        return stepProgressRepository
            .findByUserIdAndCompletedAtIsNotNull(userId)
            .map { it.stepType }
            .toSet()
    }

    private fun getUser(): User {
        // TODO: get user info
        return User(username = "username")
    }

}