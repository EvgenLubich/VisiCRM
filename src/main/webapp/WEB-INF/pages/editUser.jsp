<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Edit Form</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
<div class="generic-container">


    <div class="well lead">User Edit Form</div>
    <c:url var="signupUrl" value="/edituser"/>
    <form action="${signupUrl}" method="POST" class="fsBig">

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="login">Login</label>
                <div class="col-md-7">
                    <input type="text" name="login" id="login" value="${user.getLogin()}" class="form-control input-sm"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="firstName">Имя</label>
                <div class="col-md-7">
                    <input type="text" name="firstName" id="firstName" value="${user.getName()}" class="form-control input-sm"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="lastName">Фамилия</label>
                <div class="col-md-7">
                    <input type="text" name="lastName" id="lastName" value="${user.getSurname()}" class="form-control input-sm" />

                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="password">Пароль</label>
                <div class="col-md-7">
                    <input type="text" name="password" id="password" class="form-control input-sm" />
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="role">Roles</label>
                <div class="col-md-7">
                    <select id="role" name="role" class="form-control input-sm">
                        <option value="ROLE_ADMIN">ADMIN</option>
                        <option value="ROLE_DBA">DBA</option>
                        <option value="ROLE_USER">USER</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-actions floatRight">
                <a href="<c:url value='/' />">Cancel</a>  or
                <input type="submit" value="Edit" class="btn btn-primary btn-sm"/>

            </div>
            <div class="floatLeft"> <a href="<c:url value='/delete-user-${user.getLogin()}' />" class="btn btn-danger custom-width">delete</a></div>
        </div>

    </form>
</div>
</body>
</html>