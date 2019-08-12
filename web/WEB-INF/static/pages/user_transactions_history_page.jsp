<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <script src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous">
    </script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Transactions history</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/buttonStyle.css"%>
    <%@include file="/WEB-INF/resources/css/bodyStyle.css"%>
</style>

<body>
<%@include file="navbar.jsp" %>

<table class="table table-dark table-striped" id="table" style="margin-top: 150px">
    <thead>
    <tr>
        <th scope="col">Source</th>
        <th scope="col">Destination</th>
        <th scope="col">Money value</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${transactionsFromUser}" var="fromUser">
        <c:if test="${fromUser.destination.organisation.name != null}">
            <tr>
                <th scope="row">You (card number: ${fromUser.source.cardNumber})</th>
                <td>${fromUser.destination.organisation.name}</td>
                <td>${fromUser.value}</td>
            </tr>
        </c:if>
        <c:if test="${fromUser.destination.organisation.name == null}">
            <tr>
                <th scope="row">You (card number: ${fromUser.source.cardNumber})</th>
                <td>${fromUser.destination.cardNumber}</td>
                <td>${fromUser.value}</td>
            </tr>
        </c:if>
    </c:forEach>
    <c:forEach items="${transactionsToUser}" var="toUser">
        <c:if test="${toUser.source.organisation.name != null}">
            <tr>
                <th scope="row">${toUser.source.organisation.name}</th>
                <td>You (card number: ${toUser.destination.cardNumber})</td>
                <td>${toUser.value}</td>
            </tr>
        </c:if>
        <c:if test="${toUser.source.organisation.name == null}">
            <tr>
                <th scope="row">${toUser.source.cardNumber}</th>
                <td>You (card number: ${toUser.destination.cardNumber})</td>
                <td>${toUser.value}</td>
            </tr>
        </c:if>
    </c:forEach>
    </tbody>
</table>

<%--Bootstrap JS classes--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
