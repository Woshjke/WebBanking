<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
    <form:form name="createUserForm" method="post" action="/createUser">
        <input type="submit" value="Create user"/>
    </form:form>

<form:form name="updateUserForm" method="post" action="/updateUser">
    <input type="submit" value="Update user">
</form:form>

    <form:form name="deleteUserForm" method="post" action="/deleteUser">
        <input type="submit" value="Update user">
    </form:form>
</body>
</html>
