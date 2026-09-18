package student_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student_api.model.Course;
import student_api.model.Student;
import student_api.repository.CourseRepository;
import student_api.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setName(courseDetails.getName());
                    course.setCode(courseDetails.getCode());
                    course.setRoom(courseDetails.getRoom());
                    course.setTime(courseDetails.getTime());
                    course.setColor(courseDetails.getColor());
                    course.setBgColor(courseDetails.getBgColor());
                    return ResponseEntity.ok(courseRepository.save(course));
                }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(course -> {
                    courseRepository.delete(course);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentRepository.findByCourseId(id));
    }

    @PostMapping("/{id}/students")
    public ResponseEntity<Student> addStudentToCourse(@PathVariable Long id, @RequestBody Student student) {
        return courseRepository.findById(id).map(course -> {
            student.setCourse(course);
            Student savedStudent = studentRepository.save(student);
            
            // Increment the students count in Course
            course.setStudents(course.getStudents() + 1);
            courseRepository.save(course);
            
            return ResponseEntity.ok(savedStudent);
        }).orElse(ResponseEntity.notFound().build());
    }
}
