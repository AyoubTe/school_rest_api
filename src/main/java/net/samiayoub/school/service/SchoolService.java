package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.SchoolResponse;
import net.samiayoub.school.entity.School;
import net.samiayoub.school.mapper.SchoolMapper;
import net.samiayoub.school.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    public SchoolResponse createSchool(School school) {
        return schoolMapper.toDto(schoolRepository.save(school));
    }

    public SchoolResponse updateSchool(School school) {
        return schoolMapper.toDto(schoolRepository.save(school));
    }

    public void deleteSchool(School school) {
        schoolRepository.delete(school);
    }

    public void deleteSchoolById(Long id) {
        schoolRepository.deleteById(id);
    }

    public List<SchoolResponse> getSchools() {
        List<School> schools = schoolRepository.findAll();
        return schoolMapper.toDtoList(schools);
    }

    public SchoolResponse findSchoolById(Long id) {
        return schoolMapper.toDto(schoolRepository.findById(id).orElse(null));
    }
}
