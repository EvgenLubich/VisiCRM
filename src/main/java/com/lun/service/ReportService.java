package com.lun.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by lubich on 14.04.17.
 */
public interface ReportService {

    void generateReport(HttpServletRequest request, Date date);

}
