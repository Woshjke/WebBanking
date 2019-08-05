<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Activate User</title>
</head>
<body>

<form:form action="/admin/doActivateUser" method="post">
    <c:forEach items="${usersToActivate}" var="user">
        <select name="users" class="form-control col-1">
            <option value="${user.id}">${user.username}</option>
        </select>
    </c:forEach>

    <button type="submit">Activate</button>
</form:form>
<%--<form:form action="/admin/doDelete" method="post">--%>
<%--    <button type="submit">Delete</button>--%>
<%--</form:form>--%>
<%--\\--%>

</body>
</html>
