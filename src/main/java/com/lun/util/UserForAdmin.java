package com.lun.util;

import com.lun.model.Tracking;

import java.util.Date;
import java.util.List;

public class UserForAdmin {
    private int status;
    private List<Tracking> trackings;
    private long comein;
    private long away;
    private String firstName;
    private String lastName;
    private String login;

//    public UserForAdmin(int status, List<Tracking> trackings) {
//        this.status = status;
//        this.trackings = trackings;
//    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getComein() {
        for (Tracking tracking : trackings) {
            if (tracking.getAction().getType().equals("comein")) {
                long comein = tracking.getDate().getTime();
                return comein;
            }
        }
        return 0;
    }

    public long getAway() {
        for (Tracking tracking : trackings) {
            if (tracking.getAction().getType().equals("gone")) {
                long gone = tracking.getDate().getTime();
                return gone;
            }
        }
        return 0;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Tracking> getTrackings() {
        return trackings;
    }

    public void setTrackings(List<Tracking> trackings) {
        this.trackings = trackings;
    }
}
