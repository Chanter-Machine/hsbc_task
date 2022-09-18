package service;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceData {

    // 用户到角色映射
    public static ConcurrentHashMap<String, HashSet<String>> userRoles = new ConcurrentHashMap<>(); // user with specified roles
    // 角色到用户映射
    public static ConcurrentHashMap<String, HashSet<String>> roleUsers = new ConcurrentHashMap<>(); // role with specified users

}
