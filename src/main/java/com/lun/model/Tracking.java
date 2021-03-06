package com.lun.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lubich on 14.02.17.
 */

@Entity
@Table(name = "Time_tracking")
public class Tracking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "action", nullable = false)
    private ActionType action;

    @Column(name = "working_status")
    private Integer workingStatus;

    @Column(name = "day")
    @Temporal(TemporalType.TIMESTAMP)
    private Date day;

    public Tracking() {
    }

    public Integer getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(Integer workingStatus) {
        this.workingStatus = workingStatus;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

}
