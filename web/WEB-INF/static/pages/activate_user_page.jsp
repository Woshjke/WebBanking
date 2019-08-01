<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Activate User</title>
</head>
<body>
<c:forEach items="${usersToActivate}" var="user">
    <select name="bankAccounts" class="form-control col-1">
        <option value="${user.id}">${user.username}</option>
    </select>

    <form:form action="/" method="post">
        <button type="submit">Activate</button>
    </form:form>
    <form:form action="/deleteDisabled" method="post">
        <button type="submit">Delete</button>
    </form:form>
</c:forEach>

</body>
</html>
