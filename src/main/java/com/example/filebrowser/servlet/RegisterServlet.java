package com.example.filebrowser.servlet;

import com.example.filebrowser.model.User;
import com.example.filebrowser.util.UserStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserStore userStore;

    @Override
    public void init() {
        this.userStore = UserStore.defaultStore();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (login == null || login.isBlank() || password == null || password.isBlank() || email == null || email.isBlank()) {
            request.setAttribute("errorMessage", "Заполни все поля");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (userStore.loginExists(login)) {
            request.setAttribute("errorMessage", "Такой логин уже занят");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        Path baseRoot = Paths.get(System.getProperty("user.home"), "filemanager");
        Path homeDir = baseRoot.resolve(login).normalize();
        Files.createDirectories(homeDir);

        User user = userStore.register(login, password, email, homeDir.toAbsolutePath().toString());

        HttpSession session = request.getSession(true);
        session.setAttribute("authUser", user);

        response.sendRedirect(request.getContextPath() + "/browse");
    }
}
