package com.lun.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.*;

/**
 * Created by lubich on 08.02.17.
 */

@Entity
@Table(name = "Users")
public class AppUser implements Comparable<AppUser> {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty
    @Column(unique = true, nullable = false, length = 50)
    private String login;

    @NotEmpty
    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, length = 10)
    private String patronymic;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date offerin;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date offerout;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tracking> time_tracking;

    public AppUser() {}

    public AppUser(String login, String firstName, String lastName, String password, Set<UserRole> userRoles, Date offerin) {
        this.login = login;
        this.password = password;
        this.userRoles = new HashSet<>(userRoles);
        this.offerin = offerin;
        this.name = firstName;
        this.surname = lastName;
    }

    public AppUser(String login, String password) {
        this.login = login;
        this.password = password;

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getOfferin() {
        return offerin;
    }

    public void setOfferin(Date offerin) {
        this.offerin = offerin;
    }

    public Date getOfferout() {
        return offerout;
    }

    public void setOfferout(Date offerout) {
        this.offerout = offerout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser user = (AppUser) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(offerin, user.offerin) &&
                Objects.equals(offerout, user.offerout) &&
                Objects.equals(userRoles, user.userRoles) &&
                Objects.equals(time_tracking, user.time_tracking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, surname, patronymic, offerin, offerout, userRoles, time_tracking);
    }


    @Override
    public int compareTo(AppUser appUser) {
        return appUser.getLogin().length() > login.length() ? 0 : 1;
    }
}
