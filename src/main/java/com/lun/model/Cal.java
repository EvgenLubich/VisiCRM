package com.lun.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lubich on 14.03.17.
 */
@Entity
@Table(name = "cal")
public class Cal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "hours")
    private Integer hours;

    public Cal() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDay() {
        return date;
    }

    public void setDay(Date day) {
        this.date = day;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
