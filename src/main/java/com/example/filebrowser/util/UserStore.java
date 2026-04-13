package com.example.filebrowser.util;

import com.example.filebrowser.model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserStore {

    private final Path storageFile;
    private final Map<String, User> users = new HashMap<>();

    public UserStore(Path storageFile) {
        this.storageFile = storageFile;
        load();
    }

    public static UserStore defaultStore() {
        Path baseDir = Paths.get(System.getProperty("user.home"), "filemanager-data");
        return new UserStore(baseDir.resolve("users.txt"));
    }

    public synchronized Optional<User> findByLogin(String login) {
        return Optional.ofNullable(users.get(login));
    }

    public synchronized boolean loginExists(String login) {
        return users.containsKey(login);
    }

    public synchronized User register(String login, String rawPassword, String email, String homeDirectory) {
        if (users.containsKey(login)) {
            throw new IllegalArgumentException("Пользователь уже существует");
        }

        User user = new User(login, PasswordUtil.hash(rawPassword), email, homeDirectory);
        users.put(login, user);
        save();
        return user;
    }

    public synchronized User authenticate(String login, String rawPassword) {
        User user = users.get(login);
        if (user == null) {
            return null;
        }
        if (!PasswordUtil.matches(rawPassword, user.getPasswordHash())) {
            return null;
        }
        return user;
    }

    private void load() {
        users.clear();

        try {
            if (!Files.exists(storageFile)) {
                Files.createDirectories(storageFile.getParent());
                Files.createFile(storageFile);
                return;
            }

            List<String> lines = Files.readAllLines(storageFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length != 4) {
                    continue;
                }

                User user = new User(parts[0], parts[1], parts[2], parts[3]);
                users.put(parts[0], user);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить пользователей", e);
        }
    }

    private void save() {
        try {
            Files.createDirectories(storageFile.getParent());
            List<String> lines = new ArrayList<>();
            for (User user : users.values()) {
                lines.add(String.join("|",
                        user.getLogin(),
                        user.getPasswordHash(),
                        user.getEmail(),
                        user.getHomeDirectory()));
            }
            Files.write(storageFile, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить пользователей", e);
        }
    }
}
