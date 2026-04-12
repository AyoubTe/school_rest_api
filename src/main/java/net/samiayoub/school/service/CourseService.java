package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.StudentResponse;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Course;
import net.samiayoub.school.exception.ResourceNotFoundException;
import net.samiayoub.school.mapper.CourseMapper;
import net.samiayoub.school.mapper.StudentMapper;
import net.samiayoub.school.mapper.TeacherMapper;
import net.samiayoub.school.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    public CourseResponse createCourse(Course course) {
        return courseMapper.toDto(courseRepository.save(course));
    }

    public CourseResponse updateCourse(Course course) {
        return courseMapper.toDto(courseRepository.save(course));
    }

    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    public List<CourseResponse> findAllCourses() {
        return courseMapper.toDtoList(courseRepository.findAll());
    }

    public CourseResponse findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);

        if (course == null) {
            throw new ResourceNotFoundException("Course with id: " + id + " not found");
        };

        return courseMapper.toDto(course);
    }

    public List<StudentResponse> getEnrolledStudents(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new ResourceNotFoundException("Course with id: " + id + " not found");
        }
        return studentMapper.toDtoList(course.getStudents());
    }

    public TeacherResponse getTeacher(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new ResourceNotFoundException("Course with id: " + id + " not found");
        }
        return teacherMapper.toDto(course.getTeacher());
    }
}
