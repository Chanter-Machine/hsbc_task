package service;

import common.beans.Auth;
import org.eclipse.jetty.util.StringUtil;

import java.util.HashSet;

public class RoleMgmtService {

    public static void addRoleToUser(String userName, String role) throws Exception {

        if(StringUtil.isBlank(userName) || StringUtil.isBlank(role)) {
            throw new Exception("user/role is should not be empty");
        }

        // double checked lock
        if (RegisterUsers.getUserByName(userName)== null || !RoleService.checkRoleExistence(role)) {
            throw new Exception("user/role is not exists");
        }

        synchronized (ServiceData.userRoles) {
            if (RegisterUsers.getUserByName(userName)== null || !RoleService.checkRoleExistence(role)) {
                throw new Exception("user/role is not exists");
            }

            HashSet<String> roleSet = ServiceData.userRoles.getOrDefault(userName, new HashSet<>());
            roleSet.add(role);
            ServiceData.userRoles.put(userName, roleSet);

            HashSet<String> userSet = ServiceData.roleUsers.getOrDefault(role, new HashSet<>());
            userSet.add(userName);
            ServiceData.roleUsers.put(role, userSet);
        }
    }

    public static boolean checkUserRole(String token, String role) {
        if( StringUtil.isBlank(token) || StringUtil.isBlank(role)) {
            return false;
        }
        String username = AuthService.getAuth2User().get(token);
        if( username == null) {
            return false;
        }

        Auth auth = AuthService.getAuthMgmt().get(username);
        if (auth == null || System.currentTimeMillis() > auth.getExpire()) {
            return false;
        }

        HashSet<String > roleSet = ServiceData.userRoles.get(username);
        return roleSet != null && !roleSet.isEmpty() && roleSet.contains(role);
    }

    public static HashSet<String>getAllRoles(String token) throws Exception {
        if( StringUtil.isBlank(token)) {
            throw new Exception("invalid token format");
        }
        String username = AuthService.getAuth2User().get(token);
        if( username == null) {
            throw new Exception("invalid token");
        }

        Auth auth = AuthService.getAuthMgmt().get(username);
        if (auth == null || System.currentTimeMillis() > auth.getExpire()) {
            throw new Exception("invalid token");
        }

        HashSet<String > roleSet = ServiceData.userRoles.get(username);
        return roleSet;
    }
}
