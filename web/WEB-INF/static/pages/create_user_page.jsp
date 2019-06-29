<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<h1>Зарегистрируйте новый аккаунт</h1>
<form:form method="post" action="/register" modelAttribute="user">
    <form:label path="login">Login</form:label>
    <p></p>
    <form:input path="username"/>
    <p></p>
    <form:label path="password">Password</form:label>
    <p></p>
    <form:password path="password"/>
    <p></p>
    <input type="submit" value="Sign up"/>
    <p></p>
</form:form>
</body>
</html>
