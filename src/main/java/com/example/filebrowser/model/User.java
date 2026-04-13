package com.example.filebrowser.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String login;
    private final String passwordHash;
    private final String email;
    private final String homeDirectory;

    public User(String login, String passwordHash, String email, String homeDirectory) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.email = email;
        this.homeDirectory = homeDirectory;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }
}