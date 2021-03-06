<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <script src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>User details</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/buttonStyle.css"%>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css"%>
</style>

<body>
<%@include file="navbar.jsp" %>

<div style="margin-top: 100px;">
    <form:form action="${pageContext.request.contextPath}/userDetails" method="post"
               enctype="multipart/form-data">
        <div class="card mx-auto" style="width: 19rem;">
            <div class="card-body">
                <h5 class="card-title">Add user information</h5>
                <p class="card-text" style="margin-top: 10px">
                    <input value="${user.id}" name="userId" hidden>
                    <label style="color: black">Username</label>
                    <br>
                    <input type="text" class="form-control col-16" name="firstName" value="${user.username}" readonly>
                    <br>
                    <label style="color: black">First name</label>
                    <br>
                    <input type="text" class="form-control col-16" name="firstName">
                    <br>
                    <label style="color: black">Last name</label>
                    <br>
                    <input type="text" class="form-control col-16" name="lastName">
                    <br>
                    <label style="color: black">Phone number</label>
                    <br>
                    <input type="text" class="form-control col-16" name="phoneNumber">
                    <br>
                    <label style="color: black">Date of birthday</label>
                    <br>
                    <input type="date" class="form-control col-16" name="dob">
                    <br>
                    <label style="color: black">Passport ID</label>
                    <br>
                    <input type="text" class="form-control col-16" name="passId">
                    <br>
                    <label style="color: black">Profile image</label>
                    <br>
                    <input type="file" class="form-control-file" name="profileImage" accept="image/x-png,image/jpeg">
                </p>
                <input type="submit" class="btn btn-primary" value="Sign up"/>
            </div>
        </div>
    </form:form>
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
