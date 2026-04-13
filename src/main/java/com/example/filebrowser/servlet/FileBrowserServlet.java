package com.example.filebrowser.servlet;

import com.example.filebrowser.model.FileItem;
import com.example.filebrowser.model.User;
import com.example.filebrowser.util.PathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "FileBrowserServlet", urlPatterns = {"/browse"})
public class FileBrowserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("authUser");

        File homeDirectory = new File(user.getHomeDirectory());
        String pathParam = request.getParameter("path");
        File currentDirectory = PathUtil.resolveWithinHome(homeDirectory, pathParam);

        File parentDirectory = currentDirectory.getParentFile();
        String parentPath = null;
        if (parentDirectory != null) {
            String homeCanonical = homeDirectory.getCanonicalPath();
            String parentCanonical = parentDirectory.getCanonicalPath();
            if (parentCanonical.startsWith(homeCanonical)) {
                parentPath = parentCanonical;
            }
        }

        List<FileItem> items = new ArrayList<>();
        File[] files = currentDirectory.listFiles();

        if (files != null) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    if (f1.isDirectory() && !f2.isDirectory()) {
                        return -1;
                    }
                    if (!f1.isDirectory() && f2.isDirectory()) {
                        return 1;
                    }
                    return f1.getName().compareToIgnoreCase(f2.getName());
                }
            });

            for (File file : files) {
                items.add(new FileItem(
                        file.getName(),
                        file.getCanonicalPath(),
                        file.isDirectory(),
                        file.isFile() ? file.length() : 0,
                        file.lastModified()
                ));
            }
        }

        request.setAttribute("currentDirectory", currentDirectory.getCanonicalPath());
        request.setAttribute("homeDirectory", homeDirectory.getCanonicalPath());
        request.setAttribute("parentPath", parentPath);
        request.setAttribute("items", items);
        request.setAttribute("generatedAt", new java.util.Date());
        request.setAttribute("currentUser", user);

        request.getRequestDispatcher("/WEB-INF/views/browser.jsp").forward(request, response);
    }
}
