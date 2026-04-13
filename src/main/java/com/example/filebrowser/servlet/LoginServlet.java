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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    public void init() {
        synchronized (getServletContext()) {
            if (getServletContext().getAttribute("userStore") == null) {
                getServletContext().setAttribute("userStore", UserStore.defaultStore());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserStore userStore = (UserStore) getServletContext().getAttribute("userStore");
        User user = userStore.authenticate(login, password);
        if (user == null) {
            request.setAttribute("errorMessage", "Неверный логин или пароль");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("authUser", user);

        response.sendRedirect(request.getContextPath() + "/browse");
    }
}
