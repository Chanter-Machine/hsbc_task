package service;

import common.beans.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUsersTest {
    @Test
    @DisplayName("Test getUserByName")
    void assertGetUserByName() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String name = "name";
        User user = RegisterUsers.getUserByName(name);
        assertEquals(null, user);
    }


    @Test
    @DisplayName("Test getUserByName")
    void assertGetMd5() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String name = "zhangsan";

        User user = new User(1, "zhangsan");
        User u = RegisterUsers.addUser("zhangsan", user);
        assertEquals(null, u);

        u = RegisterUsers.addUser("zhangsan", user);
        assertEquals(u, user);
    }

    @Test
    @DisplayName("Test delete user")
    void assertDeleteUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String name = "zhangsan";

        User user = new User(1, "zhangsan");
        RegisterUsers.addUser("zhangsan", user);
        User u = RegisterUsers.deleteUser("zhangsan");
        assertEquals(u, user);
    }
}