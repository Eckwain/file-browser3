<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Логин</title>
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
    <h2>Вход</h2>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/login">
        <label>Логин</label>
        <input type="text" name="login" required>

        <label>Пароль</label>
        <input type="password" name="password" required>

        <button type="submit">Войти</button>
    </form>

    <p>Нет аккаунта? <a href="<%= request.getContextPath() %>/register">Регистрация</a></p>
</div>
</body>
</html>
