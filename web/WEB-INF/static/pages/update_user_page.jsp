<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <title>Update user information</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css"%>
    <%@include file="/WEB-INF/resources/css/buttonStyle.css"%>
</style>

<body>
<%@include file="navbar.jsp" %>

<div style="margin-top: 100px;">
    <div class="card mx-auto" style="width: 18rem;">
        <div class="card-body">
            <form:form name="getUserForm" method="post" action="/admin/updateUser">
                <h5 class="card-title">Select user to update</h5>
                <p class="card-text" style="margin-top: 20px">
                    <label style="color: black">Username</label>
                    <br>
                    <select name="users" class="form-control col-auto">
                        <c:forEach items="${usersList}" var="user">
                            <option value="${user.id}">${user.username}</option>
                        </c:forEach>
                    </select>
                </p>
                <input type="submit" class="btn btn-primary" value="Find">
            </form:form>
        </div>
    </div>
</div>

<p></p>

<c:if test="${userToUpdate.id > 0}">
    <form:form name="doUpdate" method="post" action="/admin/doUpdate">
        <div class="card mx-auto" style="width: 18rem;">
            <div class="card-body">
                <h5 class="card-title">Select new username and password</h5>
                <p class="card-text" style="margin-top: 20px">
                    <label style="color: black">ID</label>
                    <input name="id" class="form-control col-16" value="${userToUpdate.id}" readonly/>
                    <br>
                    <label style="color: black">New username</label>
                    <br>
                    <input name="username" class="form-control col-16" value="${userToUpdate.username}" required/>
                    <br>
                    <label style="color: black">New password</label>
                    <br>
                    <input name="password" class="form-control col-16" type="password" placeholder="********" required>
                </p>
                <input type="submit" class="btn btn-primary" value="Update"/>
            </div>
        </div>
    </form:form>
</c:if>

<%--Bootstrap JS classes--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
