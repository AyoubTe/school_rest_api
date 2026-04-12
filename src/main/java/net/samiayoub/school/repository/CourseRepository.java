package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
