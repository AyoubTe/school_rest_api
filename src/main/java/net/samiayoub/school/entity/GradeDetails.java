package net.samiayoub.school.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grade_details")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_grade")
    private int gradeOne;

    @Column(name = "second_grade")
    private int gradeTwo;

    @Column(name = "third_grade")
    private int gradeThree;

    @OneToOne(mappedBy = "gradeDetails", cascade = CascadeType.ALL)
    private StudentCourseDetails studentCourseDetails;
}
