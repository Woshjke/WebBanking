<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Login page</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/login.css"%>
</style>

<body>
<div class="row h-100 justify-content-center align-items-center">
<form:form method="post" action="/process_login">
    <div class="form-group">
        <label>Login</label>
        <label>
            <input name="username" class="form-text text-muted"/>
        </label>
    </div>
    <div class="form-group">
        <label>Password</label>
        <label>
            <input name="password" type="password" class="form-text text-muted"/>
        </label>
        <small id="passwordHelp" class="form-text text-muted">We'll never share your password with anyone else.</small>
    </div>
    <button class="btn btn-outline-primary">Sign in</button>
    <c:if test="${errorMessage}">
    <div class="form-group">
        <label>${errorMessage}</label>
    </div>
    </c:if>
</form:form>


<%--Bootstrap JS classes--%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>

