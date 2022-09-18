package service;

import org.eclipse.jetty.util.StringUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static service.ServiceData.roleUsers;
import static service.ServiceData.userRoles;

public class RoleService {
    private static ConcurrentHashMap.KeySetView<String, Boolean> roles = ConcurrentHashMap.newKeySet();

    protected static boolean checkRoleExistence(String role){
        return roles.contains(role);
    }

    /***
     * 添加一个新的角色
     * @param role 角色名称
     * @throws Exception
     */
    public static void addRole(String role) throws Exception {
        if(StringUtil.isBlank(role)) {
            throw new Exception("role should not be empty");
        }
        if (!roles.add(role)) {
            throw new Exception("role has already exists");
        }
    }

    /***
     * 删除一个role
     * @param role 角色名称
     * @throws Exception
     */
    public static void deleteRole(String role) throws Exception {
        if(StringUtil.isBlank(role)) {
            throw new Exception("role should not be empty string");
        }
        if(!roles.remove(role)){
            throw new Exception("role is not exist");
        }

        synchronized (userRoles) {
            for(Map.Entry<String, HashSet<String>> entry : userRoles.entrySet()) {
                HashSet<String> roleSet = entry.getValue();
                if(roleSet != null) {
                    roleSet.remove(role);
                }
            }

            roleUsers.remove(role);
        }


    }

}
