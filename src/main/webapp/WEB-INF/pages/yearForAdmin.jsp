<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lubich
  Date: 06.03.17
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach items="${workingMonthes}" var="workingMonthes">
    <a href="/admin_statfor_${login}/${year}_${workingMonthes}">${workingMonthes}</a>
</c:forEach>
</body>
</html>
