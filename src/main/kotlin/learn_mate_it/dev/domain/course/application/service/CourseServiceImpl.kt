package learn_mate_it.dev.domain.course.application.service

import jakarta.transaction.Transactional
import learn_mate_it.dev.common.exception.GeneralException
import learn_mate_it.dev.common.status.ErrorStatus
import learn_mate_it.dev.domain.course.application.dto.response.QuizAnswerDto
import learn_mate_it.dev.domain.course.application.dto.response.StepInitDto
import learn_mate_it.dev.domain.course.domain.enums.CourseType
import learn_mate_it.dev.domain.course.domain.enums.QuizType
import learn_mate_it.dev.domain.course.domain.enums.StepType
import learn_mate_it.dev.domain.course.domain.model.UserQuizAnswer
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
        val user: User = getUser()

        // valid step info and get step, course type
        val course: CourseType = CourseType.from(courseLv)
        val step: StepType = StepType.from(course, stepLv)
        validIsAlreadyOnStep(step, user.userId)

        // get first quiz type
        val firstQuiz: QuizType = QuizType.getQuiz(step, 1)

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

    /**
     * Solve Quiz of Step
     *
     * @param stepProgressId id of user's step progress
     * @param quizLv level of solving quiz
     * @param selectedIdx selected index of quiz options list
     * @return QuizAnswerDto answer of quiz, is step completed, is quiz correct, info of next quiz
     */
    @Transactional
    override fun solveQuiz(stepProgressId: Long, quizLv: Int, selectedIdx: Int): QuizAnswerDto {
        val user: User = getUser()

        // get and valid step progress and quiz
        val stepProgress: UserStepProgress = getStepProgress(stepProgressId)
        val quiz: QuizType = QuizType.getQuiz(stepProgress.stepType, quizLv)
        validIsQuizOnStep(stepProgress.stepProgressId, quiz)

        // check is quiz correct
        val isCorrect = quiz.correctIdx == selectedIdx
        val isCompleted = isCorrect && quizLv == 3
        val nextQuiz = if (isCorrect && !isCompleted) QuizType.getQuiz(stepProgress.stepType, quizLv + 1) else null

        // save userQuizAnswer
        quizAnswerRepository.save(
            UserQuizAnswer(
                stepProgressId = stepProgressId,
                quizType = quiz,
                selectedOptionIdx = selectedIdx,
                isCorrect = isCorrect
            )
        )

        // if the step is completed, set quiz info of step progress
        if (isCompleted) {
            stepProgress.completeStep()
        }

        return QuizAnswerDto.toQuizAnswerDto(
            isStepCompleted = isCompleted,
            isCorrect = isCorrect,
            description = quiz.options[selectedIdx].description,
            nextQuiz = nextQuiz
        )
    }

    private fun validIsQuizOnStep(stepProgressId: Long, quiz: QuizType) {
        if (quizAnswerRepository.existsByStepProgressIdAndQuizTypeAndIsCorrectIsTrue(stepProgressId, quiz)) {
            throw GeneralException(ErrorStatus.ALREADY_ON_QUIZ)
        }
    }

    private fun getStepProgress(stepProgressId: Long): UserStepProgress {
        return stepProgressRepository.findByStepProgressIdAndCompletedAtIsNull(stepProgressId)
            ?: throw GeneralException(ErrorStatus.NOT_FOUND_STEP_PROGRESS)
    }

    private fun getUser(): User {
        // TODO: get user info
        return User(username = "username")
    }

}