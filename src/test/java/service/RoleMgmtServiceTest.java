package service;

import common.beans.Auth;
import common.beans.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class RoleMgmtServiceTest {
    @Test
    @DisplayName("Test add role to user")
    void assertAddRoleToUser() {
        try {
            RoleMgmtService.addRoleToUser("", "123");
        } catch (Exception e) {
            assertEquals("user/role is should not be empty", e.getMessage());
        }

        try {
            RoleMgmtService.addRoleToUser("zhangsan", "test");
        } catch (Exception e) {
            assertEquals("user/role is not exists", e.getMessage());
        }

        User user = new User(1, "zhangsan");
        RegisterUsers.addUser("zhangsan", user);
        String role = "test";
        try {
            RoleService.addRole("test");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        try {
            RoleMgmtService.addRoleToUser("zhangsan", "test");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(ServiceData.userRoles.size(), 1);
        assertEquals(ServiceData.roleUsers.size(), 1);
        assertEquals(true, ServiceData.roleUsers.get("test").contains("zhangsan"));
        assertEquals(true, ServiceData.userRoles.get("zhangsan").contains("test"));
    }


    @Test
    @DisplayName("Test checking User Role")
    void assertRoleMgmtService() {
        boolean checkRes = RoleMgmtService.checkUserRole("", "");
        assertEquals(false, checkRes);

        checkRes = RoleMgmtService.checkUserRole("123", "hhh");
        assertEquals(false, checkRes);

        AuthService.auth2User.put("123", "zhangsan");
        AuthService.authMgmt.put("zhangsan", new Auth("123", System.currentTimeMillis()));
        checkRes = RoleMgmtService.checkUserRole("123", "hhh");
        assertEquals(false, checkRes);

        HashSet<String> roleSet = new HashSet<>();
        roleSet.add("hhh");
        ServiceData.userRoles.put("zhangsan", roleSet);

        AuthService.authMgmt.put("zhangsan", new Auth("123", System.currentTimeMillis()+100000L));

        checkRes = RoleMgmtService.checkUserRole("123", "hhh");
        assertEquals(true, checkRes);

    }

    @Test
    @DisplayName("Test getting all user roles")
    void assertGetAllUserRoles() {
        boolean checkRes = false;
        try {
            RoleMgmtService.getAllRoles("");
        } catch (Exception e) {
            assertEquals("invalid token format", e.getMessage());
        }



        AuthService.auth2User.put("123", "zhangsan");
        try {
            RoleMgmtService.getAllRoles("1234");
        } catch (Exception e) {
            assertEquals("invalid token", e.getMessage());
        }


        AuthService.authMgmt.put("zhangsan", new Auth("123", System.currentTimeMillis()));
        try {
            RoleMgmtService.getAllRoles("123");
        } catch (Exception e) {
            assertEquals("invalid token", e.getMessage());
        }

        HashSet<String> roleSet = new HashSet<>();
        roleSet.add("hhh");
        ServiceData.userRoles.put("zhangsan", roleSet);
        AuthService.authMgmt.put("zhangsan", new Auth("123", System.currentTimeMillis()+100000L));

        try {
            HashSet<String> rSet =  RoleMgmtService.getAllRoles("123");
            for(String ele : rSet) {
                assertEquals(true, roleSet.contains(ele));
            }
        } catch (Exception e) {
            assertEquals(null, e);
        }
    }
}