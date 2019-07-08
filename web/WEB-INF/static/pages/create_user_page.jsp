<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<h1>Зарегистрируйте новый аккаунт</h1>
<form:form method="post" action="/register">
    <label>Login</label>
    <p></p>
    <input name="username"/>
    <p></p>
    <label>Password</label>
    <p></p>
    <input name="password" type="password"/>
    <p></p>
    <input type="submit" value="Sign up"/>
    <p></p>

</form:form>
</body>
</html>
