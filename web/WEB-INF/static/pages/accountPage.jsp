<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Your account, ${user.login}</title>
</head>
<body>
<h1>Welcome, ${user.login}</h1>

<form:form method="POST" action="/getTransactions">
    <input type="submit" value="список переводов"/>
</form:form>
<form:form method="POST" action="/doTransaction/${user.bankAccountNumber}">
    <input type="submit" value="сделать перевод"/>
</form:form>

</body>
</html>
