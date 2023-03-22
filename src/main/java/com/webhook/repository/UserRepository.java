package com.webhook.repository;

import com.webhook.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserRepository {
    private final ConcurrentMap<Long, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public List<User> getUsersByName(String name) {
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getName().equals(name)) {
                result.add(user);
            }
        }
        return result;
    }

    public List<User> getUsersByLocation(String location) {
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getLocation().equals(location)) {
                result.add(user);
            }
        }
        return result;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}

