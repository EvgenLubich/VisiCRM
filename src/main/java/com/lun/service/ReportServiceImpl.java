package com.lun.service;

import com.lun.dao.*;
import com.lun.model.AppUser;
import com.lun.model.Tracking;
import com.lun.util.DataModel;
import com.lun.util.UserReport;
import com.lun.util.WorkingDay;
import com.lun.util.WorkingOff;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lubich on 14.04.17.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private TrackingDAO trackingDAO;
    @Autowired
    private ActionTypeDAO actionTypeDAO;
    @Autowired
    private CalendarDAO calendarDAO;

    @Autowired
    private TrackingService trackingService;

    public void generateReport(HttpServletRequest request, Date date){

        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Просто лист");

        // заполняем список какими-то данными
        //List<DataModel> dataList = fillData();
        List<UserReport> userReports = fillData(date);

        // счетчик для строк
        int rowNum = 0;

        int cellNum = 0;

        // получаем к-во дней в месяце
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int day = 1;

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(cellNum).setCellValue("Сотрудник");
        cellNum++;
        for (; maxDays>=day; day++, cellNum++){
            row.createCell(cellNum).setCellValue(day);
        }

        row.createCell(cellNum).setCellValue("");
        cellNum++;
        row.createCell(cellNum).setCellValue("Переработки");
        cellNum++;
        row.createCell(cellNum).setCellValue("Часы");
        cellNum++;
        row.createCell(cellNum).setCellValue("Отпуск");
        cellNum++;
        row.createCell(cellNum).setCellValue("Отпуск за год");

        // заполняем лист данными
        for (UserReport userReport : userReports) {
            createSheetHeader(sheet, ++rowNum, userReport);
        }

        // записываем созданный в памяти Excel документ в файл
        String phyPath = request.getSession().getServletContext().getRealPath("/");
        String filepath = phyPath + "resources/report.xls";
        try (FileOutputStream out = new FileOutputStream(new File(filepath))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");
    }

    private void createSheetHeader(HSSFSheet sheet, int rowNum, UserReport userReport) {

        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(userReport.getUserLogin());

        int cnt = userReport.getWorkingDays().size();
        int cellNum = 1;

        for (WorkingDay day: userReport.getWorkingDays()){
            if (day.isHospital()){
                row.createCell(cellNum).setCellValue("Б");
                cellNum++;
            } else if (day.isVacation()) {
                row.createCell(cellNum).setCellValue("О");
                cellNum++;
            } else if (day.isCommanding()) {
                row.createCell(cellNum).setCellValue("К");
                cellNum++;
            } else {
                double my = convTimeToDouble(day.getWorkDayString());
                if (my == 0.0) {
                    row.createCell(cellNum).setCellValue("");
                } else {
                    row.createCell(cellNum).setCellValue(my);
                }
               // row.createCell(cellNum).setCellValue(day.getWorkDayString());
                cellNum++;
            }
        }



        row.createCell(cellNum).setCellValue("");
        cellNum++;
        String pererabotka = userReport.getWorkingOff().getDiffWorkingOffTime();
        if (pererabotka.equals("0")){
            row.createCell(cellNum).setCellValue("");
        } else {
            row.createCell(cellNum).setCellValue(convTimeToDouble(pererabotka));
        }
        cellNum++;
        row.createCell(cellNum).setCellValue(convTimeToDouble(userReport.getWorkingOff().getCurrWorkingOffTime()));
        cellNum++;
        row.createCell(cellNum).setCellValue(userReport.getWeekendsCurrCount());
        cellNum++;
        row.createCell(cellNum).setCellValue(userReport.getWeekendsCount());


//        row.createCell(2).setCellValue(dataModel.getCity());
//        row.createCell(3).setCellValue(dataModel.getSalary());

    }

    private double convTimeToDouble(String timeStr) {
        String time = timeStr;
        String [] timeParts = time.split(":");
        if (timeParts.length == 2) {
            float intMin = Float.parseFloat(timeParts[1]);
            float drobMin = (intMin / 60);
            float longTime = Float.parseFloat(timeParts[0]) + drobMin;
            double dTime = Math.rint(100.0 * longTime) / 100;
            return dTime;
        } else {
            float intMin = Float.parseFloat(timeParts[1]);
            float drobMin = (intMin / 60);
            float intSec = Float.parseFloat(timeParts[2]);
            float drobSec = (intSec / 3600);
            float longTime = Float.parseFloat(timeParts[0]) + drobMin + drobSec;
            double dTime = Math.rint(100.0 * longTime) / 100;
            return dTime;
        }
    }

    // заполняем список рандомными данными
    // в реальных приложениях данные будут из БД или интернета
    private List<UserReport> fillData(Date date) {
        List<UserReport> userReport = new ArrayList<>();

        List<AppUser> users = appUserDAO.findAllUsers();

        Date dateStart = new Date();
        Date dateFin = new Date();
        Date dateFinCurrMonth = new Date();

        Calendar calStart = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();
        Calendar calFinCurrMonth = Calendar.getInstance();



        calFinCurrMonth.setTime(date);
        calFinCurrMonth.add(Calendar.MONTH, 1);

        calStart.setTime(date);
        int year = calStart.get(Calendar.YEAR);
        int month = calStart.get(Calendar.MONTH);
        calStart.set(Calendar.MONTH, 0);
        calFin.setTime(date);
        calFin.set(Calendar.MONTH, 11);
        calFin.set(Calendar.DAY_OF_MONTH, 31);
        calFin.set(Calendar.HOUR, 23);
        dateStart.setTime(calStart.getTimeInMillis());
        dateFin.setTime(calFin.getTimeInMillis());
        dateFinCurrMonth.setTime(calFinCurrMonth.getTimeInMillis());

        for (AppUser user: users){
            List<Tracking> weekend = trackingDAO.getDaysOffCount(user.getId(), dateStart, dateFin);
            int weekendsCount = weekend.size();
            List<Tracking> weekendCurr = trackingDAO.getDaysOffCount(user.getId(), date, dateFinCurrMonth);
            int weekendsCurrCount = weekendCurr.size();
            UserReport uRep = new UserReport(user.getSurname() + " " + user.getName(), date, weekendsCount, weekendsCurrCount);

            List<WorkingDay> workingDays = trackingService.getWorkingDayForSomeMonth(user.getLogin(), year, month);
            WorkingOff workingOff = trackingService.getWorkingOffHistory(workingDays,user.getLogin(),year,month);
            uRep.setWorkingDays(workingDays);
            uRep.setWorkingOff(workingOff);
            userReport.add(uRep);
        }
        Collections.sort(userReport);
        return userReport;
    }
}
