<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete user</title>
</head>
<body>
<form:form name="deleteUserForm" method="post" action="/deleteUser">
    <input type="number" name="userId" value="0">
    <input type="submit" value="Delete">
</form:form>
</body>
</html>
