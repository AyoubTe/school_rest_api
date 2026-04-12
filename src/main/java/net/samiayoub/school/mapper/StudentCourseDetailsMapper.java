package net.samiayoub.school.mapper;

import net.samiayoub.school.dto.requets.StudentCourseDetailsRequest;
import net.samiayoub.school.dto.responses.StudentCourseDetailsResponse;
import net.samiayoub.school.entity.StudentCourseDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentCourseDetailsMapper {
    @Mapping(target = "id", ignore = true)
    StudentCourseDetails toEntity(StudentCourseDetailsRequest studentCourseDetailsRequest);
    StudentCourseDetailsResponse toDto(StudentCourseDetails studentCourseDetails);
    List<StudentCourseDetailsResponse> toDtoList(List<StudentCourseDetails> studentCourseDetails);
}
