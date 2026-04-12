package net.samiayoub.school.repository;

import net.samiayoub.school.entity.StudentCourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCourseDetailsRepository extends JpaRepository<StudentCourseDetails, Long> {
    List<StudentCourseDetails> findByStudentId(Long studentId);
}
