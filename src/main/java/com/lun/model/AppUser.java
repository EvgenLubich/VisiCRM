package com.lun.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lubich on 08.02.17.
 */

@Entity
@Table(name = "Users")
public class AppUser {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty
    @Column(unique = true, nullable = false, length = 50)
    private String login;

    @NotEmpty
    @Column(nullable = false, length = 200)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<UserRole> userRoles;

    public List<Tracking> getTime_tracking() {
        return time_tracking;
    }

    public void setTime_tracking(List<Tracking> time_tracking) {
        this.time_tracking = time_tracking;
    }

    @OneToMany(mappedBy = "user"/*, cascade = CascadeType.ALL, fetch = FetchType.EAGER*/)
    private List<Tracking> time_tracking;

    public AppUser() {}

    public AppUser(String login, String password, Set<UserRole> userRoles) {
        this.login = login;
        this.password = password;
        this.userRoles = new HashSet<>(userRoles);
    }

    public AppUser(String login, String password) {
        this.login = login;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public Set<UserRole> getUserRoles() {
        return new HashSet<>(userRoles);
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
