package net.samiayoub.school.mapper;

import net.samiayoub.school.dto.requets.GradeDetailsRequest;
import net.samiayoub.school.dto.responses.GradeDetailsResponse;
import net.samiayoub.school.entity.GradeDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GradeDetailsMapper {
    @Mapping(target = "id", ignore = true)
    GradeDetails toEntity(GradeDetailsRequest gradeDetailsRequest);
    GradeDetailsResponse toDto(GradeDetails grade);
    List<GradeDetailsResponse> toDtoList(List<GradeDetails> grades);
}
