package learn_mate_it.dev.domain.course.application

import jakarta.transaction.Transactional
import learn_mate_it.dev.domain.chat.presentation.dto.response.StepInitDto
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

    @Transactional
    override fun startStep(courseNum: Int, stepNum: Int): StepInitDto {
        // TODO:
        // get user info
        val user: User = User(
            username = "username"
        )

        // valid step info and get step, course type
        val course: CourseType = CourseType.from(courseNum)
        val step: StepType = StepType.from(course, stepNum)

        // save step progress
        val stepProgress = UserStepProgress(
            stepType = step,
            userId = user.userId
        )
        stepProgressRepository.save(stepProgress)

        // get first quiz type
        val firstQuiz: QuizType = QuizType.getQuiz(step, 1)

        return StepInitDto(
            stepProgressId = stepProgress.stepProgressId,
            courseNum = courseNum,
            stepNum = stepNum,
            stepTitle = step.title,
            stepDescription = step.description,
            stepSituation = step.situation,
            firstQuiz = firstQuiz.quiz,
            firstQuizOptions = firstQuiz.options.map { it.answer }
        )
    }

}