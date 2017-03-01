package com.lun.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by lubich on 08.02.17.
 */

@Entity
@Table(name = "Roles")
public class UserRole {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty
    @SuppressWarnings("unused")
    @Column(unique = true, nullable = false, length = 50)
    private String roleName;

    public UserRole() {}

    public UserRole(String roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
