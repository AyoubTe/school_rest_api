package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.SchoolResponse;
import net.samiayoub.school.entity.School;
import net.samiayoub.school.mapper.SchoolMapper;
import net.samiayoub.school.repository.SchoolRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SchoolMapper schoolMapper;

    @InjectMocks
    private SchoolService schoolService;

    @Test
    void shouldCreateSchool() {
        // Arrange
        School school = new School(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse", new ArrayList<>());
        when(schoolRepository.save(school)).thenReturn(school);

        SchoolResponse response = new SchoolResponse(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse");
        when(schoolMapper.toDto(school)).thenReturn(response);

        // Act
        SchoolResponse result = schoolService.createSchool(school);

        // Assert
        Assertions.assertEquals(response, result);
    }

    @Test
    void shouldUpdateSchool() {
        // Arrange
        School school = new School(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse", new ArrayList<>());
        when(schoolRepository.save(school)).thenReturn(school);

        SchoolResponse response = new SchoolResponse(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse");
        when(schoolMapper.toDto(school)).thenReturn(response);

        // Act
        SchoolResponse result = schoolService.updateSchool(school);

        // Assert
        Assertions.assertEquals(response, result);
    }

    @Test
    void shouldDeleteSchool() {
        School school = new School(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse", new ArrayList<>());

        schoolRepository.delete(school);

        verify(schoolRepository, times(1)).delete(school);
    }

    @Test
    void shouldDeleteSchoolById() {
        schoolService.deleteSchoolById(1L);
        verify(schoolRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetSchools() {
        // Arrange
        School school1 = new School(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse", new ArrayList<>());
        School school2 = new School(2L, "INSA", "Toulouse", new ArrayList<>());
        School school3 = new School(3L, "TBS", "Toulouse", new ArrayList<>());

        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school1, school2, school3));

        SchoolResponse response1 = new SchoolResponse(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse");
        SchoolResponse response2 = new SchoolResponse(2L, "INSA", "Toulouse");
        SchoolResponse response3 = new SchoolResponse(3L, "TBS", "Toulouse");

        when(schoolMapper.toDtoList(schoolRepository.findAll())).thenReturn(Arrays.asList(response1, response2, response3));

        // Act
        List<SchoolResponse> schools = schoolService.getSchools();

        // Assert
        Assertions.assertEquals(response1, schools.get(0));
    }

    @Test
    void shouldFindSchoolById() {
        // Arrange
        School school = new School(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse", new ArrayList<>());
        SchoolResponse response = new SchoolResponse(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse");

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(schoolMapper.toDto(school)).thenReturn(response);

        // Act
        SchoolResponse result = schoolService.findSchoolById(1L);

        // Assert
        Assertions.assertEquals(response, result);
    }
}