package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByUsernameOrEmail(String username, String email);
    Optional<Admin> findByEmail(String emailOrUsername);
}
