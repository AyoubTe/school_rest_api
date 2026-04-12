package net.samiayoub.school.dto.responses;

public record GradeDetailsResponse(
        Long id,
        Integer gradeOne,
        Integer gradeTwo,
        Integer gradeThree,
        StudentCourseDetailsResponse studentCourseDetails
) { }
