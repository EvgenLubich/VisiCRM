<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin</title>
</head>
<body>
<c:forEach items="${usersStatusMap}" var="usersStatusMap">
    ${usersStatusMap.key} - ${usersStatusMap.value}
</c:forEach>
</body>
</html>

