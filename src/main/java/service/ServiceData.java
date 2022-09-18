package service;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceData {

    public static ConcurrentHashMap<String, HashSet<String>> userRoles = new ConcurrentHashMap<>(); // user with specified roles
    public static ConcurrentHashMap<String, HashSet<String>> roleUsers = new ConcurrentHashMap<>(); // role with specified users

}
