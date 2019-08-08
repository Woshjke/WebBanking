<%@ page import="bank.RequestParameter" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Payment</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/user.css"%>
</style>

<body>
<%@include file="navbar.html" %>

<div style="margin-top: 100px;">
    <div class="form-group">
        <form:form action="/user/doPayment" method="post">
            <div class="card mx-auto" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Submit payment to organisation</h5>
                    <p class="card-text" style="margin-top: 10px">
                        <label style="color: black">Choose organisation:</label>
                        <br>
                        <select name="organisation" class="form-control col-16">
                            <c:forEach items="${orgs}" var="org">
                                <option value="${org.id}">${org.name}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <label style="color: black">How Much:</label>
                        <br>
                        <input type="number" class="form-control col-16" name="money_count" required>
                        <br>
                        <label style="color: black">Choose your bank card for payment:</label>
                        <br>
                        <select name="bankAccounts" class="form-control col-16">
                            <c:forEach items="${bankAccounts}" var="bankAccount">
                                <option value="${bankAccount.id}">${bankAccount.cardNumber}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <input type="submit" class="btn btn-primary" name="submitPayment" value="Submit"/>
                </div>
            </div>
        </form:form>
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
