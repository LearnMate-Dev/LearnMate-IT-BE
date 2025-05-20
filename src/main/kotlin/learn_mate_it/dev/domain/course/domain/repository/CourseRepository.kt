package learn_mate_it.dev.domain.course.domain.repository

import learn_mate_it.dev.domain.course.domain.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository: JpaRepository<Course, Long> {
}