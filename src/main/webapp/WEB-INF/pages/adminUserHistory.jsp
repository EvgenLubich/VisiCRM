<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>History page ${currentLogin}</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
</head>
<body>
<div class="generic-container">
    <div class="authbar">
        <span>Статистика пользователя ${currentLogin}</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
    </div>
    <div><span><a href="<c:url value="/" />">Вернуться на главную</a></span></div>
    <br>

    <div>
        Статистика за:
        <c:forEach items="${workingYear}" var="workingYears">
            <a href="/statistic_${workingYears}_${currentLogin}">${workingYears}</a>
        </c:forEach>
    </div>
    <sec:authorize access="hasRole('ADMIN')">
    <h4>Больничный:</h4>
    <form style="float: left" role="form" class="form-inline" action="/add_hospital_${currentLogin}" method="post">
        <div class="form-group">
            <label for="dateStartB">Дата начала</label>
            <input type="date" class="form-control" name="dateStart" id="dateStartB">
        </div>
        <div class="form-group">
            <label for="dateEndB">Дата выхода на работу</label>
            <input type="date" class="form-control" name="dateEnd" id="dateEndB">
        </div>
        <button type="submit" class="btn btn-success">Добавить</button>
    </form>
    <br>
    <h4 style="clear: both">Отпуск:</h4>
    <form style="float: left" role="form" class="form-inline" action="/add_vacation_${currentLogin}" method="post">
        <div class="form-group">
            <label for="dateStart">Дата начала</label>
            <input type="date" class="form-control" name="dateStart" id="dateStart">
        </div>
        <div class="form-group">
            <label for="dateEnd">Дата выхода на работу</label>
            <input type="date" class="form-control" name="dateEnd" id="dateEnd">
        </div>
        <button type="submit" class="btn btn-success">Добавить</button>
    </form>
    <br>
    <h4 style="clear: both">Командировка:</h4>
    <form style="float: left" role="form" class="form-inline" action="/add_commanding_${currentLogin}" method="post">
        <div class="form-group">
            <label for="dateStartC">Дата начала</label>
            <input type="date" class="form-control" name="dateStart" id="dateStartC">
        </div>
        <div class="form-group">
            <label for="dateEndC">Дата выхода на работу</label>
            <input type="date" class="form-control" name="dateEnd" id="dateEndC">
        </div>
        <button type="submit" class="btn btn-success">Добавить</button>
    </form>
    </sec:authorize>
    <table class="table table-striped">
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
            <c:if test="${workingDays.isVacation() == true}" >
                <c:if test="${workingDays.isWeekend() == true}" >
                    <tr style="color: red">
                </c:if>
                <c:if test="${workingDays.isWeekend() == false}" >
                    <tr>
                </c:if>
                <td>${workingDays.date}</td>
                <td>${workingDays.day}</td>
                <td> <sec:authorize access="hasRole('ADMIN')"><a href="/delete_${currentLogin}_${workingDays.getFullDate()}"></sec:authorize>O<sec:authorize access="hasRole('ADMIN')"></a></sec:authorize> </td>
                <td> O </td>
                <td> O </td>
                <td> O </td>
                </tr>
            </c:if>
            <c:if test="${workingDays.isHospital() == true}" >
                <c:if test="${workingDays.isWeekend() == true}" >
                    <tr style="color: red">
                </c:if>
                <c:if test="${workingDays.isWeekend() == false}" >
                    <tr>
                </c:if>
                <td>${workingDays.date}</td>
                <td>${workingDays.day}</td>
                <td><sec:authorize access="hasRole('ADMIN')"><a href="/delete_${currentLogin}_${workingDays.getFullDate()}"></sec:authorize>Б<sec:authorize access="hasRole('ADMIN')"></a></sec:authorize></td>
                <td> Б </td>
                <td> Б </td>
                <td> Б </td>
                </tr>
            </c:if>
            <c:if test="${workingDays.isCommanding() == true}" >
                <c:if test="${workingDays.isWeekend() == true}" >
                    <tr style="color: red">
                </c:if>
                <c:if test="${workingDays.isWeekend() == false}" >
                    <tr>
                </c:if>
                <td>${workingDays.date}</td>
                <td>${workingDays.day}</td>
                <td><sec:authorize access="hasRole('ADMIN')"><a href="/delete_${currentLogin}_${workingDays.getFullDate()}"></sec:authorize>К<sec:authorize access="hasRole('ADMIN')"></a></sec:authorize></td>
                <td> К </td>
                <td> К </td>
                <td> К </td>
                </tr>
            </c:if>
            <c:if test="${workingDays.isVacation() == false && workingDays.isHospital() == false && workingDays.isCommanding() == false}" >
                <c:if test="${workingDays.isWeekend() == true}" >
                    <tr style="color: red">
                </c:if>
                <c:if test="${workingDays.isWeekend() == false}" >
                    <tr>
                </c:if>

                <td>${workingDays.date}</td>
                <td>${workingDays.day}</td>
                <c:choose>
                    <c:when test="${comein == 'Thu Jan 01 02:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><sec:authorize access="hasRole('ADMIN')"><a id="go" href="/update_${currentLogin}_${workingDays.getFullDate()}_<fmt:formatDate value="${comein}" pattern="HH:mm:ss" timeZone="GMT+3"/>"></sec:authorize><fmt:formatDate value="${comein}" pattern="HH:mm:ss" timeZone="GMT+3"/><sec:authorize access="hasRole('ADMIN')"></a></sec:authorize>  </td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${away == 'Thu Jan 01 02:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><sec:authorize access="hasRole('ADMIN')"><a id="go" href="/update_${currentLogin}_${workingDays.getFullDate()}_<fmt:formatDate value="${away}" pattern="HH:mm:ss" timeZone="GMT+3"/>"></sec:authorize><fmt:formatDate value="${away}" pattern="HH:mm:ss" timeZone="GMT+3"/><sec:authorize access="hasRole('ADMIN')"></a></sec:authorize>  </td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${comein == 'Thu Jan 01 02:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:formatDate value="${epsent}" pattern="HH:mm:ss" timeZone="GMT"/>  </td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${comein == 'Thu Jan 01 02:00:00 EET 1970'}">
                        <td> - </td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:formatDate value="${workDay}" pattern="HH:mm:ss" timeZone="GMT"/>  </td>
                    </c:otherwise>
                </c:choose>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
    <div> Отработанно <b>${currWorkingOff}</b> часов из <b>${workingOff}</b></div>
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