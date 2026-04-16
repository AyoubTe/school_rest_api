package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.SchoolResponse;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Course;
import net.samiayoub.school.entity.Teacher;
import net.samiayoub.school.exception.ResourceNotFoundException;
import net.samiayoub.school.mapper.CourseMapper;
import net.samiayoub.school.mapper.SchoolMapper;
import net.samiayoub.school.mapper.TeacherMapper;
import net.samiayoub.school.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final CourseMapper courseMapper;
    private final SchoolMapper schoolMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper, CourseMapper courseMapper, SchoolMapper schoolMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.courseMapper = courseMapper;
        this.schoolMapper = schoolMapper;
    }

    public TeacherResponse createTeacher(Teacher teacher) {
        return  teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherResponse updateTeacher(Teacher teacher) {
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public List<TeacherResponse> getAllTeachers() {
        return teacherMapper.toDtoList(teacherRepository.findAll());
    }

    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher with id " + id + " not found");
        }
        return teacherMapper.toDto(teacher);
    }

    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<CourseResponse> getCoursesByTeacherId(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher with id " + id + " not found");
        }
        List<Course> courses = teacher.getCourses();

        return courseMapper.toDtoList(courses);
    }

    public SchoolResponse getSchool(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if(teacher == null) {
            throw new ResourceNotFoundException("No teacher found with id" + id);
        }
        return schoolMapper.toDto(teacher.getSchool());
    }

    public boolean isTeacherExists(String emailOrUsername) {
        return (teacherRepository.findByUsername(emailOrUsername).orElse(null) != null) || (teacherRepository.findByEmail(emailOrUsername).orElse(null) != null);
    }

    public Teacher getTeacherByUsernameOrEmail(String usernameOrEmail) {
        return teacherRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);
    }

    public TeacherResponse getTeacherByUsername(String name) {
        Teacher teacher = getTeacherByUsernameOrEmail(name);
        if (teacher == null) {
            throw new ResourceNotFoundException("Student with name: " + name + " not found");
        }
        return teacherMapper.toDto(teacher);
    }

    public List<CourseResponse> getCoursesByTeacherUsername(String name) {
        Teacher teacher = getTeacherByUsernameOrEmail(name);
        if (teacher == null) {
            throw new ResourceNotFoundException("Student with name: " + name + " not found");
        }
        return courseMapper.toDtoList(teacher.getCourses());
    }

    public SchoolResponse getSchoolByUsername(String name) {
        Teacher teacher = getTeacherByUsernameOrEmail(name);
        if (teacher == null) {
            throw new ResourceNotFoundException("Student with name: " + name + " not found");
        }
        return schoolMapper.toDto(teacher.getSchool());
    }
}
