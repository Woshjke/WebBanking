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
    <title>Read users</title>
</head>

<script>
    <%@include file="/WEB-INF/resources/js/getUsers.js"%>
</script>

<style>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css"%>
</style>

<body>
<%@include file="navbar.jsp"%>

<div style="margin-top: 100px;">
    <div class="card mx-auto" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">Find user by specific username or get all users</h5>
            <p class="card-text" style="margin-top: 20px">
                <label style="color: black">Username</label>
                <br>
                <input id="username" name="username" class="form-control col-16"/>
            </p>
            <input id="getAllUsers" type="button" onclick="" class="btn btn-primary" value="Find">
        </div>
    </div>
</div>

<table class="table table-dark table-striped" id="table" style="margin-top: 50px"></table>


<%--Bootstrap JS classes--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>

