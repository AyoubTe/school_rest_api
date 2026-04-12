package net.samiayoub.school.repository;

import net.samiayoub.school.entity.GradeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeDetailsRepository extends JpaRepository<GradeDetails,Long> {
}
