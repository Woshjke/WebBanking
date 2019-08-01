<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User details</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/userDetails" method="post" enctype="multipart/form-data" >
        <input type="text" name="firstName">
        <p></p>
        <input type="text" name="lastName">
        <p></p>
        <input type="text" name="phoneNumber">
        <p></p>
        <input type="date" name="dob">
        <p></p>
        <input type="text" name="passId">
        <p></p>
        <input type="file" name="profileImage" accept="image/x-png,image/jpeg">
        <p></p>
        <input type="submit">

        <img src="data:image/jpg;base64,${myImage}" alt="" height="60" width="60">
    </form>
</body>
</html>
