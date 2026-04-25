package net.samiayoub.school.repository;


import net.samiayoub.school.entity.School;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SchoolRepositoryTest {
    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    void shouldGetAllSchools() {
        // Act
        List<School> schools = schoolRepository.findAll();

        // Assert
        assertThat(schools).isNotNull();
    }

    @Test
    void shouldGetSchoolById() {
        // Act
        School school = schoolRepository.findById(1L).get();
        // Assert
        assertThat(school).isNotNull();
    }

    @Test
    void shouldCreateSchool() {
        // Arrange
        School school = new School();
        school.setName("ENSEEIHT School");
        school.setAddress("2 Rue Camichel, 31000, Toulouse, FR.");

        // Act
        School savedSchool = schoolRepository.save(school);

        // Assert
        Assertions.assertNotNull(savedSchool.getId());
    }

    @Test
    void shouldUpdateSchool() {
        // Arrange
        School school = new School();
        school.setName("ENSEEIHT School");
        school.setAddress("2 Rue Camichel, 31000, Toulouse, FR.");

        // Act
        School savedSchool = schoolRepository.save(school);
        savedSchool.setAddress("1 Rue Saunière");
        School updatedSchool = schoolRepository.save(savedSchool);

        // Assert
        Assertions.assertEquals("1 Rue Saunière",  updatedSchool.getAddress());
    }

    @Test
    void shouldDeleteSchool() {
        School school = schoolRepository.findById(1L).get();
        schoolRepository.delete(school);
        School checkSchool = schoolRepository.findById(1L).orElse(null);
        Assertions.assertNull(checkSchool);
    }

    @Test
    void shouldFindById() {
        School school = schoolRepository.findById(1L).orElse(null);

        Assertions.assertNotNull(school);
    }
}