<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>admin</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
</head>
<body>
    <div class="generic-container">

        <div style="float: left"><a href="<c:url value="/" />">Назад</a></div>
        <div style="clear: both"></div>
        <form style="float: left" role="form" class="form-inline" action="/admin-another-date" method="post">
            <div class="form-group">
                <label for="date">Дата</label>
                <input type="date" class="form-control" name="date" id="date">
            </div>
            <button type="submit" class="btn btn-success">Показать</button>
        </form>

        <div style="float: right">Статус сотрудников за ${currDate}</div>

        <table class="table table-striped" style="vertical-align:baseline;">
            <thead>
            <tr>
                <th><b>Сотрудник</b></th>
                <c:if test="${nostatus != true}">
                    <th><b>Статус</b></th>
                </c:if>
                <th><b>пришел</b></th>
                <th><b>ушел</b></th>
                <th><th/>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${usersStatusMap}" var="usersStatusMap">
                    <jsp:useBean id="comein" class="java.util.Date"/>
                    <jsp:useBean id="away" class="java.util.Date"/>
                    <jsp:setProperty name="comein" property="time" value="${usersStatusMap.value.getComein()}"/>
                    <jsp:setProperty name="away" property="time" value="${usersStatusMap.value.getAway()}"/>
            <tr>
                <td><a href="/admin_statfor_${usersStatusMap.value.getLogin()}">${usersStatusMap.key}</a> </td>
                    <c:if test="${nostatus != true}">
                        <c:if test="${usersStatusMap.value.getStatus() == 5 || usersStatusMap.value.getStatus() == 4}" >
                            <td style="color: red">Отсутствует</td>
                        </c:if>
                        <c:if test="${usersStatusMap.value.getStatus() == 2}" >
                            <td style="color: gray">Отошел</td>
                        </c:if>
                        <c:if test="${usersStatusMap.value.getStatus() == 3 || usersStatusMap.value.getStatus() == 1}" >
                            <td style="color: green">В офисе</td>
                        </c:if>
                        <c:if test="${usersStatusMap.value.getStatus() != 1 && usersStatusMap.value.getStatus() != 2 && usersStatusMap.value.getStatus() != 3 && usersStatusMap.value.getStatus() != 4 && usersStatusMap.value.getStatus() != 5}" >
                            <td style="color: red">Отсутствует</td>
                        </c:if>
                    </c:if>
                    <c:choose>
                        <c:when test="${comein == 'Thu Jan 01 02:00:00 EET 1970'}">
                            <td> - </td>
                        </c:when>
                        <c:otherwise>
                            <td><sec:authorize access="hasRole('ADMIN')"><a id="go" href="/update_${usersStatusMap.value.getLogin()}_${currDate}_<fmt:formatDate value="${comein}" pattern="HH:mm:ss" timeZone="GMT+3"/>"></sec:authorize><fmt:formatDate value="${comein}" pattern="HH:mm:ss" timeZone="GMT+3"/><sec:authorize access="hasRole('ADMIN')"></a></sec:authorize>  </td>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${away == 'Thu Jan 01 02:00:00 EET 1970'}">
                            <td> - </td>
                        </c:when>
                        <c:otherwise>
                            <td><sec:authorize access="hasRole('ADMIN')"><a id="go" href="/update_${usersStatusMap.value.getLogin()}_${currDate}_<fmt:formatDate value="${away}" pattern="HH:mm:ss" timeZone="GMT+3"/>"></sec:authorize><fmt:formatDate value="${away}" pattern="HH:mm:ss" timeZone="GMT+3"/><sec:authorize access="hasRole('ADMIN')"></a></sec:authorize>  </td>
                        </c:otherwise>
                    </c:choose>
                    <!--<td><sec:authorize access="hasRole('ADMIN')"><a href="<c:url value='/delete-user-${usersStatusMap.value.getLogin()}' />" class="btn btn-danger custom-width">delete</a></sec:authorize></td>-->
                    <td><sec:authorize access="hasRole('ADMIN')"><a href="<c:url value='/edit-user-${usersStatusMap.value.getLogin()}' />" class="btn btn-danger custom-width">Edit</a></sec:authorize>
            </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
<!-- Модальное окно -->
<div id="modal_form">
    <span id="modal_close">X</span>
    <form role="form" class="form-inline" action="" method="post">
        <h3>Введите время</h3>
        <div class="form-group">
            <label for="time">Время</label>
            <input type="time" class="form-control" name="time" id="time">
        </div>
        <p style="text-align: center; padding-bottom: 10px;">
            <input type="submit" class="btn btn-success" value="Ok" />
        </p>
    </form>
</div>
<div id="overlay"></div>
<script type="text/javascript">
    $(document).ready(function() { // вся магия после загрузки страницы
        $('a#go').click( function(event){ // ловим клик по ссылки с id="go"
            event.preventDefault(); // выключаем стандартную роль элемента
            $('#overlay').fadeIn(400, // сначала плавно показываем темную подложку
                function(){ // после выполнения предъидущей анимации
                    $('#modal_form')
                        .css('display', 'block') // убираем у модального окна display: none;
                        .animate({opacity: 1, top: '50%'}, 200); // плавно прибавляем прозрачность одновременно со съезжанием вниз
                });
        });
        /* Закрытие модального окна, тут делаем то же самое но в обратном порядке */
        $('#modal_close, #overlay').click( function(){ // ловим клик по крестику или подложке
            $('#modal_form')
                .animate({opacity: 0, top: '45%'}, 200,  // плавно меняем прозрачность на 0 и одновременно двигаем окно вверх
                    function(){ // после анимации
                        $(this).css('display', 'none'); // делаем ему display: none;
                        $('#overlay').fadeOut(400); // скрываем подложку
                    }
                );
        });
    });

    $(document).ready(function(){
console.log($('a#go'));
        $('a#go').click(function(){

            var htmlStr = $(this).attr("href");
            $("form").attr("action", htmlStr);
        });
    });

</script>
</html>

