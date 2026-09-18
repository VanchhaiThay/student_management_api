package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student_api.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
