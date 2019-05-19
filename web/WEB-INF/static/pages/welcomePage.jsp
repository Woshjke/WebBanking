<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>WebBank</title>
</head>
<body>
    <h1>${welcomingMessage}</h1>
    <p>Имя пользователя</p>
    <label>
        <input title="Имя пользователя" , type="text" , name="login">
    </label>
    <p>Пароль</p>
    <label>
        <input title="Пароль" , type="password" , name="password">
    </label>
    <p></p>
    <button type="button" , title="Зайти" , name="signInButton">Зайти</button>
    <p></p>
    <button type="button" , title="Зарегистрироваться" , name="signUpButton">Зарегистрироваться</button>
</body>
</html>
