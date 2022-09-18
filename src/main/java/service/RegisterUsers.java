package service;

import common.beans.User;

import java.util.concurrent.ConcurrentHashMap;

public class RegisterUsers {

    private static ConcurrentHashMap<String, User> registerUsers = new ConcurrentHashMap<>();

    public static User getUserByName(String name) {
        return registerUsers.getOrDefault(name, null);
    }

    public static User addUser(String name, User user) {
        return registerUsers.putIfAbsent(name, user);
    }

    public static User deleteUser(String name) {
        return registerUsers.remove(name);
    }
}
