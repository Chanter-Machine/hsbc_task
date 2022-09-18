package service;

import common.beans.User;

import java.util.concurrent.ConcurrentHashMap;

public class RegisterUsers {

    private static ConcurrentHashMap<String, User> registerUsers = new ConcurrentHashMap<>();

    public static User getUserByName(String name) {
        return registerUsers.getOrDefault(name, null);
    }

    /***
     * 添加一个用户
     * @param name 用户名称
     * @param user 用户对象
     * @return : 之前已经存在的用户
     */
    public static User addUser(String name, User user) {
        return registerUsers.putIfAbsent(name, user);
    }

    /***
     * 删除一个用户
     * @param name 用户名称
     * @return : 删除之前已经存在的用户
     */

    public static User deleteUser(String name) {
        return registerUsers.remove(name);
    }
}
