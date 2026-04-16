package net.samiayoub.school.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import net.samiayoub.school.entity.enums.Role;

import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher extends User {

    @Column(name = "discipline")
    private String discipline;


    @ManyToOne
    @JoinColumn(name = "school_id")
    School school;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    public Teacher() {
        super(Role.TEACHER);
    }

    public Teacher(Long id, String username, String firstname, String lastname, String email, String password, String discipline) {
        super(id, username, firstname, lastname, email, password, Role.TEACHER);
        this.discipline = discipline;
    }
}
