<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>admin</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
    <div class="generic-container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><b>Сотрудник</b></th>
                <th><b>Статус</b></th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${usersStatusMap}" var="usersStatusMap">
            <tr>
                <td>${usersStatusMap.key}</td>

                    <c:if test="${usersStatusMap.value == 5 || usersStatusMap.value == 4}" >
                        <td style="color: red">Отсутствует</td>
                    </c:if>
                    <c:if test="${usersStatusMap.value == 2}" >
                        <td style="color: gray">Отошел</td>
                    </c:if>
                    <c:if test="${usersStatusMap.value == 3 || usersStatusMap.value == 1}" >
                        <td style="color: green">В офисе</td>
                    </c:if>

            </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

