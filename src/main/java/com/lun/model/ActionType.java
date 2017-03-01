package com.lun.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Evgen on 15.02.2017.
 */

@Entity
@Table(name = "action")
public class ActionType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(unique = true, nullable = false, length = 10)
    private String type;

    @OneToMany(mappedBy = "action")
    private List<Tracking> time_tracking;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Tracking> getTime_tracking() {
        return time_tracking;
    }

    public void setTime_tracking(List<Tracking> time_tracking) {
        this.time_tracking = time_tracking;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ActionType() {
    }
}
