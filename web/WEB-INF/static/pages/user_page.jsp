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
    <title>Userpage</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/user.css"%>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css"%>
</style>

<script type="text/javascript" charset="UTF-8">
    <%@include file="/WEB-INF/resources/js/getCurrency.js"%>
</script>

<script>
    <%@include file="/WEB-INF/resources/js/getBankAccounts.js"%>
</script>

<body style="background: url(/img/star-sky.jpg) #475d62;">
<%@include file="navbar.html" %>
<div style="margin-top: 100px">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div class="card mx-auto" style="width: 18rem; margin-left: 18px;">
                    <div class="card-body">
                        <h5 class="card-title">Currency rate</h5>
                        <select id="currencyList" class="form-control col-4">
                            <option value="USD">USD</option>
                            <option value="EUR">EUR</option>
                        </select>
                        <p class="card-text" style="margin-top: 10px">
                            <label id="date" style="color: black;"></label>
                            <br>
                            <label id="currencyName" style="color: black;"></label>
                            <br>
                            <label id="currencyRate" style="color: black;"></label>
                        </p>
                        <a href="#" class="btn btn-primary" id="getCurrencyButton">Get currency rate</a>
                    </div>
                </div>

                <p></p>

                <label style="color: red;">${resultMessage}</label>
            </div>
            <div class="col-md-6">
                <div class="card mx-auto" style="width: 18rem; margin-left: 18px;">
                    <div class="card-body">
                        <h5 class="card-title">Your bank accounts</h5>
                        <select id="bankAccountsList" class="form-control col-16">
                            <c:forEach items="${bankAccounts}" var="bankAccount">
                                <option value="${bankAccount.id}">${bankAccount.cardNumber}</option>
                            </c:forEach>
                        </select>
                        <p class="card-text" style="margin-top: 10px">
                            <label id="money_value" style="color: black;"></label>
                        </p>
                        <a href="#" class="btn btn-primary" id="getBankAccountInfo">Check bank account</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
