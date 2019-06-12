<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Payment</title>
</head>
<body>

<form:form action="/doPayment" method="post">

    <select name="organisation">
        <c:forEach items="${orgs}" var="org">
            <option value="${org.id}">${org.name}</option>
        </c:forEach>
    </select>
    <p></p>
    <label>Money count</label>
    <p></p>
    <label>
        <input type="number" name="money_count">
    </label>
    <p></p>
    <input type="submit" name="submitPayment" value="Submit payment"/>
</form:form>

</body>
</html>
