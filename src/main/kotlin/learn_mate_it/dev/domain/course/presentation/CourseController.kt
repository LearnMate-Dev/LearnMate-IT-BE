package learn_mate_it.dev.domain.course.presentation

import learn_mate_it.dev.domain.course.application.CourseService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/courses")
class CourseController (
    private val courseService: CourseService
){
}