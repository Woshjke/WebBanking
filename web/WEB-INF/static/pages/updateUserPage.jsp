<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Update user information</title>
</head>
<body>
<form:form name="getUserForm" method="post" action="/updateUser">
    <input type="number" name="userId" value="0">
    <input type="submit" value="OK">
</form:form>

<p></p>

<c:if test="${userToUpdate.id > 0}">
    <form:form name="doUpdate" method="post" action="/doUpdate" modelAttribute="userToUpdate">
        <label>ID</label>
        <p></p>
        <form:input path="id"/> <p></p>
        <label>Username</label>
        <p></p>
        <form:input path="login"/> <p></p>
        <label>Password</label>
        <p></p>
        <form:input path="password"/> <p></p>
        <label>is Admin</label>
        <p></p>
        <form:checkbox path="admin"/> <p></p>
        <label>Money count</label>
        <p></p>
        <form:input path="money_count"/> <p></p>
        <input type="submit" value="Submit"/>
    </form:form>
</c:if>
</body>
</html>
