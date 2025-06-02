package learn_mate_it.dev.domain.course.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.domain.enums.CourseType
import learn_mate_it.dev.domain.course.domain.enums.QuizType
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
     * @return StepInitDto id of step progress, info of step, info of first quiz of step
     */
    @Transactional
    override fun startStep(courseLv: Int, stepLv: Int): StepInitDto {
        val user = getUser()

        // valid step info and get step, course type
        val course = CourseType.from(courseLv)
        val step = StepType.from(course, stepLv)
        validIsAlreadyOnStep(step, user.userId)

        // get first quiz type
        val firstQuiz = QuizType.getQuiz(step, 1)

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
            step = step,
            quiz = firstQuiz
        )
    }

    private fun validIsAlreadyOnStep(step: StepType, userId: Long) {
        if (stepProgressRepository.findByStepTypeAndUserIdAndCompletedAtIsNull(step, userId) != null) {
            throw GeneralException(ErrorStatus.ALREADY_ON_STEP)
        }
    }

    private fun getStepProgress(stepProgressId: Long, userId: Long): UserStepProgress {
        return stepProgressRepository.findByStepProgressIdAndUserIdAndCompletedAtIsNull(stepProgressId, userId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_STEP_PROGRESS)
    }

    private fun getUser(): User {
        // TODO: get user info
        return User(username = "username")
    }

}