<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Login page</title>
</head>

<body>
<h2>${welcomingMessage}</h2>
<form:form method="POST" action="/login" modelAttribute="user">

    <form:label path="login">Login</form:label>
    <p></p>
    <form:input path="login"/>
    <p></p>
    <form:label path="password">Password</form:label>
    <p></p>
    <form:password path="password"/>
    <p></p>
    <input type="submit" value="Sign in"/>
    <p></p>
</form:form>

<form:form method="POST" action="/goToRegistrationPage">
    <input type="submit" value="Sign up"/>
</form:form>

</body>

</html>

