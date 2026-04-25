package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findByCode(String code);
}
