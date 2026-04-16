package net.samiayoub.school.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import net.samiayoub.school.entity.enums.Role;

@Entity
@Table(name = "admins")
@Getter
@Setter
public class Admin extends User {

    @Column(name = "mission", nullable = true)
    private String mission;

    public Admin() {
        super(Role.ADMIN);
    }
    public Admin(Long id, String username, String firstname, String lastname, String email, String password, String mission) {
        super(id, username, firstname, lastname, email, password, Role.ADMIN);
        this.mission = mission;
    }
}
