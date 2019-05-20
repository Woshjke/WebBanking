<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>WebBank</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<form:form method="POST"--%>
<%--           action="/name" >--%>
<%--    <h1>${welcomingMessage}</h1>--%>
<%--    <p>Имя пользователя</p>--%>
<%--    <label>--%>
<%--&lt;%&ndash;        <input title="Имя пользователя" , type="text" , name="name">&ndash;%&gt;--%>
<%--        <form:input path="name"/>--%>
<%--    </label>--%>
<%--    <p>Пароль</p>--%>
<%--    <label>--%>
<%--&lt;%&ndash;        <input title="Пароль" , type="password" , name="password">&ndash;%&gt;--%>
<%--        <form:input path="password"/>--%>
<%--    </label>--%>
<%--    <p></p>--%>


<%--    <button type="submit" , title="Зайти" , name="signInButton">Зайти</button>--%>
<%--    <p></p>--%>
<%--    <button type="button" , title="Зарегистрироваться" , name="signUpButton">Зарегистрироваться</button>--%>
<%--</form:form>--%>
<%--</body>--%>
<%--</html>--%>

<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
    <title>Spring MVC Form Handling</title>
</head>

<body>
<h2>Student Information</h2>
<form:form method = "POST" action = "/login" modelAttribute="user">
    <table>
        <tr>
            <%--<td><form:label path = "name">Name</form:label></td>--%>
            <td><form:input path = "name" /></td>
        </tr>
        <tr>
<%--            <td><form:label path = "password">Password</form:label></td>--%>
            <td><form:input path = "password" /></td>
        </tr>
        <tr>
            <td colspan = "2">
                <input type = "submit" value = "Submit"/>
            </td>
        </tr>
    </table>
</form:form>
</body>

</html>

