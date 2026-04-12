package net.samiayoub.school.mapper;

import net.samiayoub.school.dto.requets.AssignmentDetailsRequest;
import net.samiayoub.school.dto.responses.AssignmentDetailsResponse;
import net.samiayoub.school.entity.AssignmentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentDetailsMapper {
    @Mapping(target = "id", ignore = true)
    AssignmentDetails toEntity(AssignmentDetailsRequest request);
    AssignmentDetailsResponse toDto(AssignmentDetails assignmentDetails);
    List<AssignmentDetailsResponse> toDtoList(List<AssignmentDetails> assignmentDetails);
}
