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
    <title>Userpage</title>
</head>
<style>
    <%@include file="/WEB-INF/resources/css/user.css"%>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css"%>
</style>

<body>

<nav class="navbar navbar-dark bg-dark navbar-expand-lg fixed-top">
    <a href="#" class="navbar-brand">Мой банк</a>
    <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Банковские операции
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/payment">Сделать платеж</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/transaction">Сделать перевод на счет</a>
                </div>
            </li>
                <a href="${pageContext.request.contextPath}/process_logout" class="nav-link">Выйти</a>
<%--                <a href="${pageContext.request.contextPath}/user/home_page" class="nav-link">${authUser.username}</a>--%>
                <a href="${pageContext.request.contextPath}/admin/admin_page" class="nav-link">Admin page</a>
        </ul>
    </div>
</nav>


<label style="margin-top: 100px;">Дата: </label>
<label>${usdRate.date}</label>

<p></p>
<p></p>

<label>Валюта: </label>
<label>${usdRate.name}</label>
<br>
<label>Курс к рублю: </label>
<label>${usdRate.rate}</label>

<p></p>
<p></p>

<label>Валюта: </label>
<label>${eurRate.name}</label>
<br>
<label>Курс к рублю: </label>
<label>${eurRate.rate}</label>

<p></p>
<p></p>

<label style="color: red;">${resultMessage}</label>




<%--Bootstrap JS classes--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
