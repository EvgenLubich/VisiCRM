<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Calendar</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
<div class="generic-container">
    <sec:authorize access="hasRole('ADMIN')">
        <div><a href="<c:url value="/" />">Назад</a></div>
    </sec:authorize>

    <c:if test="${error == true}">
        <div class="alert alert-danger">
            <p>Эта дата уже существует!</p>
        </div>
    </c:if>
    <form role="form" class="form-inline" action="/addDate" method="post">
        <div class="form-group">
            <label for="date">Дата</label>
            <input type="date" class="form-control" name="date" id="date">
        </div>
        <div class="form-group">
            <label for="time">Рабочие часы</label>
            <input type="number" class="form-control" name="time" id="time">
        </div>
        <button type="submit" class="btn btn-success">Добавить день</button>
    </form>

    <table class="table table-striped" style="vertical-align:baseline;">
        <thead>
        <tr>
            <th><b>День</b></th>
            <th><b>Часы</b></th>
            <th><th/>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${calendarMap}" var="calendarMap">
            <tr>
                <td>${calendarMap.key}</td>
                <td>${calendarMap.value}</td>

                <td><a href="<c:url value='/calendar-delete-${calendarMap.key}' />" class="btn btn-danger custom-width">delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

