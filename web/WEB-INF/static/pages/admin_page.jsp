<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta charset="utf-8">
    <script src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Admin page</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/buttonStyle.css" %>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css" %>
</style>

<body>
<%@include file="navbar.jsp"%>

<div style="margin-top: 100px; margin-left: 30px">
    <form:form name="createUserPageButton" method="post" action="/register">
        <button type="submit" class="btn btn-primary">Create user</button>
    </form:form>

    <form:form name="getUsersPageButton" method="get" action="/admin/readUsers">
        <button type="submit" class="btn btn-primary">Read users</button>
    </form:form>

    <form:form name="updateUserPageButton" method="post" action="/admin/updateUser">
        <button type="submit" class="btn btn-primary">Update user</button>
    </form:form>

    <form:form name="deleteUserPageButton" method="post" action="/admin/deleteUser">
        <button type="submit" class="btn btn-primary">Delete user</button>
    </form:form>

    <form:form name="goToUserPageButton" method="get" action="/user/user_page">
        <button type="submit" class="btn btn-primary">User page</button>
    </form:form>

    <form:form name="setRoleForUser" method="get" action="/admin/setRole">
        <button type="submit" class="btn btn-primary">Set role for user</button>
    </form:form>

    <label style="color: red;">${resultMessage}</label>
</div>


<%--Bootstrap JS classes--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

</body>
</html>
