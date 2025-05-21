package learn_mate_it.dev.domain.course.application

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.course.presentation.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.domain.enums.CourseType
import learn_mate_it.dev.domain.course.domain.enums.QuizType
import learn_mate_it.dev.domain.course.domain.enums.StepType
import learn_mate_it.dev.domain.course.domain.model.UserStepProgress
import learn_mate_it.dev.domain.course.domain.repository.UserQuizAnswerRepository
import learn_mate_it.dev.domain.course.domain.repository.UserStepProgressRepository
import learn_mate_it.dev.domain.user.domain.model.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    private val stepProgressRepository: UserStepProgressRepository,
    private val quizAnswerRepository: UserQuizAnswerRepository
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
        // TODO: get user info
        val user: User = User(
            username = "username"
        )

        // valid step info and get step, course type
        val course: CourseType = CourseType.from(courseLv)
        val step: StepType = StepType.from(course, stepLv)
        validIsAlreadyOnStep(step, user.userId)

        // get first quiz type
        val firstQuiz: QuizType = QuizType.getQuiz(step, 1)

        // save step progress
        val stepProgress = UserStepProgress(
            stepType = step,
            userId = user.userId
        ).let { stepProgressRepository.save(it) }

        return StepInitDto(
            stepProgressId = stepProgress.stepProgressId,
            courseLv = courseLv,
            stepLv = stepLv,
            stepTitle = step.title,
            stepDescription = step.description,
            stepSituation = step.situation,
            firstQuiz = firstQuiz.quiz,
            firstQuizOptions = firstQuiz.options.map { it.answer }
        )
    }

    private fun validIsAlreadyOnStep(step: StepType, userId: Long) {
        if (stepProgressRepository.findByStepTypeAndUserIdAndCompletedAtIsNull(step, userId) != null) {
            throw GeneralException(ErrorStatus.ALREADY_ON_STEP)
        }
    }

}