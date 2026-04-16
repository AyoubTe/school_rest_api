package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void shouldFindAdminById() {
        // Act
        Admin admin = adminRepository.findById(1L).get();
        // Assert
        assertEquals("admin.chief", admin.getUsername());
    }

    @Test
    void shouldCreateAdmin() {
        // Assert
        Admin admin = new Admin();
        admin.setUsername("admin.applicatif");
        admin.setPassword("AHashedPassword");
        admin.setFirstname("Ayoub");
        admin.setLastname("SAMI");
        admin.setEmail("ayoub.sami@school.com");
        admin.setMission("The chief of all chiefs");

        // Act
        Admin savedAdmin = adminRepository.save(admin);

        // Assert
        Assertions.assertNotNull(savedAdmin.getId());
    }

    @Test
    void shouldUpdateAdmin() {
        // Act
        Admin admin = adminRepository.findByUsername("admin.test").get();
        admin.setFirstname("Ayoub");
        adminRepository.save(admin);

        Admin updatedAdmin = adminRepository.findByUsername("admin.test").get();

        Assertions.assertEquals("Ayoub", updatedAdmin.getFirstname());
    }

    @Test
    void shouldDeleteAdmin() {
        Admin admin = adminRepository.findByUsername("admin.test").get();
        adminRepository.delete(admin);
        Admin checkAdmin = adminRepository.findByUsername("admin.test").orElse(null);

        Assertions.assertNull(checkAdmin);
    }

    @Test
    void shouldFindAllAdmins() {
        // Arrange

        // Act
        List<Admin> admins = adminRepository.findAll();

        // Assert
        assertEquals(11, admins.size());
    }

    @Test
    void shouldFindByUsername() {
        // Arrange : Prepare data

        // Act : Functions (methods calls)
        Admin admin = adminRepository.findByUsername("admin.test").orElse(null);

        // Assert : To verify the waited results
        Assertions.assertNotNull(admin);
    }

    @Test
    void shouldFindByUsernameOrEmail() {
        // Act
        Admin admin = adminRepository.findByUsernameOrEmail("admin", "admin.test@school.net").orElse(null);

        // Assert
        Assertions.assertNotNull(admin);
        assertEquals("admin.test", admin.getUsername());
    }

    @Test
    void shouldFindByEmail() {
        // Act
        Admin admin = adminRepository.findByEmail("admin@school.com").orElse(null);
        // Assert
        Assertions.assertNull(admin);
    }
}