package service;

import common.beans.Auth;
import common.beans.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    @Test
    @DisplayName("Test gen token")
    void assertGetToken() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        try {
            String token = AuthService.getToken("", "welasfj");
        } catch (Exception e) {
//            throw new RuntimeException(e);
            assertEquals("username/password should be empty", e.getMessage());
        }

        try {
            String token = AuthService.getToken("zhangsan", "123");
        } catch (Exception e) {
//            throw new RuntimeException(e);
            assertEquals("User is not exists", e.getMessage());
        }

        // incorrect pwd provided
        User zhangsan = new User(1, "zhangsan");
        zhangsan.setPassword("123jdfa");
        RegisterUsers.addUser("zhangsan", zhangsan);
        try {
            String token = AuthService.getToken("zhangsan", "1234");
        } catch (Exception e) {
            assertEquals("Username/Password is not match", e.getMessage());
        }



        //get token from cache
        zhangsan.setPassword("42c645e48f10ae67b286797fdf7bb1b1");
        AuthService.authMgmt.put("zhangsan", new Auth("123", System.currentTimeMillis() + 2*3600*1000));
        try {
            String token = AuthService.getToken("zhangsan", "1234");
            assertEquals(token, "123");

        } catch (Exception e) {
            assertEquals(null, e);
        }


        // test gen new token
        AuthService.authMgmt.remove("zhangsan");
        try {
            String token = AuthService.getToken("zhangsan", "1234");
            assertNotEquals(null, token);
        } catch (Exception e) {
        }

    }

    @Test
    @DisplayName("Test invalidate token")
    void assertInvalidateToken() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        User zhangsan = new User(1, "zhangsan");
        zhangsan.setPassword("334f864c16a6df585eb585d8845af190");
        RegisterUsers.addUser("zhangsan", zhangsan);

        AuthService.authMgmt.put("zhangsan", new Auth("334f864c16a6df585eb585d8845af190", System.currentTimeMillis() + 2*3600*1000));


        // test gen new token
        String token = null;
        try {
            token = AuthService.getToken("zhangsan", "123");
        } catch (Exception e) {
        }

        try {
            AuthService.invalidToken("");
        } catch (Exception e) {
            assertEquals("token should not be empty", e.getMessage());
        }

        try {
            AuthService.invalidToken(token);
        } catch (Exception e) {
            assertEquals("No valid token exists", e.getMessage());
        }

    }
}