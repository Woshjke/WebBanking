<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<form:form name="createUserPage" method="post" action="/createUser">
    <input type="submit" value="Create user"/>
</form:form>

<form:form name="getUserPage" method="post" action="/getUser">
    <input type="submit" value="Get user"/>
</form:form>

<form:form name="updateUserPage" method="post" action="/updateUser">
    <input type="submit" value="Update user">
</form:form>

<form:form name="deleteUserPage" method="post" action="/deleteUser">
    <input type="submit" value="Delete user">
</form:form>
</body>
</html>
