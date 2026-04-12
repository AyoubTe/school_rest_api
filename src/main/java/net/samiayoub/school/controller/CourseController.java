package net.samiayoub.school.controller;

import net.samiayoub.school.dto.requets.CourseRequest;
import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.StudentResponse;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.mapper.CourseMapper;
import net.samiayoub.school.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public List<CourseResponse> getCourses() {
        return courseService.findAllCourses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse addCourse(@RequestBody CourseRequest courseRequest) {
        return courseService.createCourse(courseMapper.toEntity(courseRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse updateCourse(@RequestBody CourseRequest courseRequest) {
        return courseService.updateCourse(courseMapper.toEntity(courseRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
    }

    @GetMapping("/{id}")
    public CourseResponse getCourse(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }

    @GetMapping("/{id}/students")
    public List<StudentResponse> getStudents(@PathVariable Long id) {
        return courseService.getEnrolledStudents(id);
    }

    @GetMapping("/{id}/teacher")
    public TeacherResponse getTeacher(@PathVariable Long id) {
        return courseService.getTeacher(id);
    }
}
