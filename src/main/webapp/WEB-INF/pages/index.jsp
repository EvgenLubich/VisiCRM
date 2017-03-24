<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello page ${currentLogin}</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
<div class="generic-container">
    <div class="authbar">
        <span>Hello ${currentLogin}</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
    </div>
    <sec:authorize access="hasRole('ADMIN')">
    <div><a href="<c:url value="/adduser" />">Добавить пользователя</a></div>
    </sec:authorize>


    <c:if test="${status == 5}" >
        <a href="/comein">Пришел</a>
    </c:if>
    <c:if test="${status == 1 || status == 3}" >
        <a href="/away">Отошел</a> или <a href="/gone">Ушел</a>
    </c:if>
    <c:if test="${status == 2}" >
        <a href="/returned">Вернулся</a>
    </c:if>
    <c:if test="${status == 4}" >
        <p>Хорошего отдыха</p>
    </c:if>


    <br>
    <div>
         Статистика за:
    <c:forEach items="${workingYear}" var="workingYears">
        <a href="/statistic-${workingYears}">${workingYears}</a>
    </c:forEach>
    </div>

    <br>


    <table class="table table-striped"">
        <thead>
        <tr>
            <th><b>Дата</b></th>
            <th><b>День</b></th>
            <th width="60"><b>Пришел</b></th>
            <th width="60"><b>Ушел</b></th>
            <th width="60"><b>Отходил</b></th>
            <th width="60"><b>Отработка</b></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${workingDay}" var="workingDays">
            <jsp:useBean id="comein" class="java.util.Date"/>
            <jsp:useBean id="away" class="java.util.Date"/>
            <jsp:useBean id="epsent" class="java.util.Date"/>
            <jsp:useBean id="workDay" class="java.util.Date"/>
            <jsp:setProperty name="comein" property="time" value="${workingDays.comein}"/>
            <jsp:setProperty name="away" property="time" value="${workingDays.away}"/>
            <jsp:setProperty name="epsent" property="time" value="${workingDays.epsent}"/>
            <jsp:setProperty name="workDay" property="time" value="${workingDays.workDay}"/>
            <tr>

                <td>${workingDays.date}</td>
                <td>${workingDays.day}</td>
                <c:choose>
                    <c:when test="${comein == 'Thu Jan 01 03:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="/<fmt:formatDate value="${comein}" pattern="HH:mm:ss" timeZone="GMT+2"/>"><fmt:formatDate value="${comein}" pattern="HH:mm:ss" timeZone="GMT+2"/></a>  </td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${away == 'Thu Jan 01 03:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:formatDate value="${away}" pattern="HH:mm:ss" timeZone="GMT+2"/>  </td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${comein == 'Thu Jan 01 03:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:formatDate value="${epsent}" pattern="HH:mm:ss" timeZone="GMT"/>  </td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${comein == 'Thu Jan 01 03:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:formatDate value="${workDay}" pattern="HH:mm:ss" timeZone="GMT"/>  </td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </tbody>
    </table>


    ${currWorkingOff}
    ${workingOff}

</div>





</body>
</html>