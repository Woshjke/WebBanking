<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${accountNumber}</title>
</head>
<body>
<form:form method="POST" action="/transaction/${accountNumber}" modelAttribute="transaction">
    <p>Куда</p>
    <form:input path="to"/>
    <p>Сколько:</p>
    <form:input path="value"/>
    <p></p>
    <input type="submit" value="Перевести"/>
</form:form>
</body>
</html>
