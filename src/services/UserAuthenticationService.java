package services;

import models.User;
import java.util.*;

public class UserAuthenticationService {
    private Map<String, User> users;

    public UserAuthenticationService() {
        this.users = new HashMap<>();
    }

    public User registerUser(String userId, String username, String password, User.UserRole role) {
        if (users.containsKey(userId)) {
            System.out.println("✗ User ID already exists");
            return null;
        }

        User user = new User(userId, username, password, role);
        users.put(userId, user);
        System.out.println("✓ User registered: " + username + " (" + role + ")");
        return user;
    }

    public User loginUser(String userId, String password) {
        User user = users.get(userId);

        if (user == null) {
            System.out.println("✗ User not found");
            return null;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("✗ Invalid password");
            return null;
        }

        System.out.println("✓ Login successful: " + user.getUsername());
        return user;
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }

    public void displayAllUsers() {
        System.out.println("\n--- Registered Users ---");
        users.values().forEach(System.out::println);
    }

    public boolean isValidUser(String userId) {
        return users.containsKey(userId);
    }

    public boolean isAdmin(String userId) {
        User user = users.get(userId);
        return user != null && user.isAdmin();
    }
}
