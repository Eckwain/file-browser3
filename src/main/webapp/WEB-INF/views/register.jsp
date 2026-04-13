<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .box { width: 360px; margin: 80px auto; background: #fff; padding: 20px; border-radius: 8px; }
        input { width: 100%; padding: 10px; margin: 8px 0; box-sizing: border-box; }
        button { width: 100%; padding: 10px; }
        .error { color: red; margin-bottom: 10px; }
    </style>
</head>
<body>
<div class="box">
    <h2>Регистрация</h2>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/register">
        <label>Логин</label>
        <input type="text" name="login" required>

        <label>Пароль</label>
        <input type="password" name="password" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <button type="submit">Зарегистрироваться</button>
    </form>

    <p>Уже есть аккаунт? <a href="<%= request.getContextPath() %>/login">Войти</a></p>
</div>
</body>
</html>
