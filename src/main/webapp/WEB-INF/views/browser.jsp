<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.example.filebrowser.model.FileItem" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    String currentDirectory = (String) request.getAttribute("currentDirectory");
    String parentPath = (String) request.getAttribute("parentPath");
    List<FileItem> items = (List<FileItem>) request.getAttribute("items");
    Date generatedAt = (Date) request.getAttribute("generatedAt");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>File Browser</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background: #f5f5f5;
        }

        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            margin-top: 0;
        }

        .info {
            margin-bottom: 15px;
            padding: 10px;
            background: #eef5ff;
            border-left: 4px solid #3b82f6;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background: #f0f0f0;
        }

        a {
            text-decoration: none;
            color: #0b66c3;
        }

        a:hover {
            text-decoration: underline;
        }

        .folder {
            font-weight: bold;
        }

        .top-line {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 10px;
        }
    </style>
</head>

<body>
<div class="container">

    <div class="top-line">
        <div>
            <h1>Файловый браузер</h1>
            <div>Время генерации: <%= sdf.format(generatedAt) %></div>
        </div>
    </div>

    <div class="info">
        <div><b>Текущая директория:</b> <%= currentDirectory %></div>
        <div>
            <b>Переход:</b>
            <a href="<%= request.getContextPath() %>/browse">Домой</a>
            <% if (parentPath != null) { %>
                |
                <a href="<%= request.getContextPath() %>/browse?path=<%= URLEncoder.encode(parentPath, "UTF-8") %>">Вверх</a>
            <% } %>
        </div>
    </div>

    <table>
        <thead>
        <tr>
            <th>Имя</th>
            <th>Тип</th>
            <th>Размер</th>
            <th>Дата изменения</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
        <% if (items == null || items.isEmpty()) { %>
            <tr>
                <td colspan="5">Папка пуста</td>
            </tr>
        <% } else { %>
            <% for (FileItem item : items) { %>
                <tr>
                    <td>
                        <% if (item.isDirectory()) { %>
                            <span class="folder">📁 <%= item.getName() %></span>
                        <% } else { %>
                            📄 <%= item.getName() %>
                        <% } %>
                    </td>
                    <td><%= item.isDirectory() ? "Папка" : "Файл" %></td>
                    <td><%= item.getFormattedSize() %></td>
                    <td><%= item.getFormattedDate() %></td>
                    <td>
                        <% if (item.isDirectory()) { %>
                            <a href="<%= request.getContextPath() %>/browse?path=<%= URLEncoder.encode(item.getPath(), "UTF-8") %>">Открыть</a>
                        <% } else { %>
                            <a href="<%= request.getContextPath() %>/download?path=<%= URLEncoder.encode(item.getPath(), "UTF-8") %>">Скачать</a>
                        <% } %>
                    </td>
                </tr>
            <% } %>
        <% } %>
        </tbody>
    </table>

</div>
</body>
</html>