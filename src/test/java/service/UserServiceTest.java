package service;

import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {


    @Test
    @DisplayName("Test if create new user successfully.")
    void assertCreateNewUserService(){
        UserService userService = new UserService();
        try {
            userService.createUser("hehe", "123");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Test creating user repeatedly.")
    void assertCreateNewUserService2(){
        UserService userService = new UserService();
        try {
            userService.createUser("hehe", "123");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            userService.createUser("hehe", "123");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @DisplayName("Test delete empty user")
    void assertCreateNewUserService3(){
        UserService userService = new UserService();
        try {
            userService.deleteUser("hehe");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete user successfully")
    void assertCreateNewUserService4(){
        UserService userService = new UserService();

        try {
            userService.createUser("hehe", "123");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            userService.deleteUser("hehe");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("user is deleted");
    }
}