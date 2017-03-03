package com.lun.util;

import com.lun.model.AppUser;

import java.util.*;

/**
 * Created by lubich on 03.03.17.
 */
public class WorkingYear {

    private AppUser offerIn;
    private List<Integer> years = new LinkedList();

    public WorkingYear(AppUser offerIn){
        this.offerIn = offerIn;
    }

    public List<Integer> getYears() {

        Calendar c = new GregorianCalendar();
        int yearEnd = c.get(c.YEAR);
        Calendar c2 = new GregorianCalendar();
        c2.setTime(offerIn.getOfferin());
        int yearStart = c2.get(c2.YEAR);
        int delta = yearEnd - yearStart;

        for (int i = 0; i <= delta; i++) {
            years.add(yearStart + i);
        }

        return years;
    }
}
