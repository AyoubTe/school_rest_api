package net.samiayoub.school.mapper;

import net.samiayoub.school.dto.requets.TeacherRequest;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(target = "id", ignore = true)
    Teacher toEntity(TeacherRequest teacherRequest);

    @Mapping(target = "courses.teacher", ignore = true)
    TeacherResponse toDto(Teacher teacher);
    List<TeacherResponse> toDtoList(List<Teacher> teacherList);
}
