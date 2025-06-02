package learn_mate_it.dev.domain.course.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.domain.enums.CourseType
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
        if (step.isFirstStep()) {
            return
        }

        // check previous step's complete
        val previousStepList = step.getPreviousStep()
        val completedStepList = stepProgressRepository.findByStepTypeInAndUserIdAndCompletedAtIsNotNull(previousStepList, userId)
        val completedStepTypes = completedStepList.map { it.stepType }.toSet()

        val isAllCompleted = previousStepList.all { previousStep ->
            completedStepTypes.contains(previousStep)
        }
        if (!isAllCompleted) {
            throw GeneralException(ErrorStatus.INVALID_STEP_ORDER)
        }

        // check if this step is not completed
        val isStepAlreadyStarted = stepProgressRepository.existsByStepTypeAndUserIdAndCompletedAtIsNull(step, userId)
        if (isStepAlreadyStarted) {
            throw GeneralException(ErrorStatus.ALREADY_ON_STEP)
        }
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

        if (stepProgress.isCompleted()) {
            throw GeneralException(ErrorStatus.ALREADY_COMPLETED_STEP)
        }

        stepProgress.completeStep()
    }

    /**
     * Delete User's Step Progress
     *
     * @param stepProgressId if of step progress
     */
    @Transactional
    override fun deleteStep(stepProgressId: Long) {
        val user = getUser()
        val stepProgress = getStepProgress(stepProgressId, user.userId)

        if (stepProgress.isCompleted()) {
            throw GeneralException(ErrorStatus.ALREADY_COMPLETED_STEP)
        }

        stepProgressRepository.delete(stepProgress)
    }

    private fun getStepProgress(stepProgressId: Long, userId: Long): UserStepProgress {
        return stepProgressRepository.findByStepProgressIdAndUserId(stepProgressId, userId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_STEP_PROGRESS)
    }

    private fun getUser(): User {
        // TODO: get user info
        return User(username = "username")
    }

}