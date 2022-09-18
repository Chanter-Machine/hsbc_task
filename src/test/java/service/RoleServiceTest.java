package service;

import common.beans.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTest {
    @Test
    @DisplayName("Test add role")
    void assertAddRole() {
        try {
            RoleService.addRole("");
        } catch (Exception e) {
            assertEquals("role should not be empty", e.getMessage());
        }

        try {
            RoleService.addRole("test");
        } catch (Exception e) {
            assertEquals(null, e);
        }

        try {
            RoleService.addRole("test");
        } catch (Exception e) {
            assertEquals("role has already exists", e.getMessage());
        }


    }

    @Test
    @DisplayName("Test delete role")
    void assertDeleteRole() {
        try {
            RoleService.deleteRole("");
        } catch (Exception e) {
            assertEquals("role should not be empty string", e.getMessage());
        }

        try {
            RoleService.deleteRole("role is not exist");
        } catch (Exception e) {
            assertEquals("role is not exist", e.getMessage());
        }

        String role = "role";
        try {
            RoleService.addRole(role);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User u = new User(1, "zhangsan");
        HashSet<String> userset = new HashSet<>();
        userset.add(u.getName());
        ServiceData.roleUsers.put(role, userset);
        HashSet<String> roleSet = new HashSet<>();
        roleSet.add(role);
        ServiceData.userRoles.put(u.getName(), roleSet);

        try {
            RoleService.deleteRole(role);
        } catch (Exception e) {
            assertEquals("role should not be empty", e.getMessage());
        }
    }
}