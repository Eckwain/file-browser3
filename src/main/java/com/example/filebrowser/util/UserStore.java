package com.example.filebrowser.util;

import com.example.filebrowser.model.User;

import java.sql.*;

public class UserStore {

    public boolean register(User user) {
        String sql = "INSERT INTO users (login, password_hash, email, home_directory) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getHomeDirectory());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("login"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("home_directory")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean loginExists(String login) {
        return findByLogin(login) != null;
    }
}