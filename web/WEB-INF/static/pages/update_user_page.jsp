<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Update user information</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css"%>
    <%@include file="/WEB-INF/resources/css/buttonStyle.css"%>
</style>

<body>
<div style="margin-top: 20px; margin-left: 20px">
    <form:form name="getUserForm" method="post" action="/updateUser">
        <select name="users" class="form-control col-1">
            <c:forEach items="${usersList}" var="user">
                <option value="${user.id}">${user.username}</option>
            </c:forEach>
        </select>
        <p></p>
        <input type="submit" class="btn btn-outline-primary" value="OK">
    </form:form>

    <p></p>

    <c:if test="${userToUpdate.id > 0}">
        <form:form name="doUpdate" method="post" action="/doUpdate" modelAttribute="userToUpdate">
            <label>ID</label>
            <p></p>
            <form:input path="id"/> <p></p>
            <label>Username</label>
            <p></p>
            <form:input path="username"/> <p></p>
            <label>Password</label>
            <p></p>
            <form:input path="password"/> <p></p>
            <p></p>
            <div class="custom-control custom-checkbox">
                <form:checkbox path="admin" cssClass="custom-control-input" id="defaultUnchecked"/>
                <label class="custom-control-label" for="defaultUnchecked">Admin?</label>
            </div>
            <p></p>
            <label>Money count</label>
            <p></p>
            <form:input path="bankAccounts"/> <p></p>
            <input type="submit" class="btn btn-outline-primary" value="Submit"/>
        </form:form>
    </c:if>
</div>

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
