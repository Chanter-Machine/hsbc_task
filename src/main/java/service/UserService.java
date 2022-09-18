package service;

import common.beans.Auth;
import common.beans.User;
import common.utils.Encryption;
import common.utils.StringUtil;
import common.utils.UserValidator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import static service.ServiceData.roleUsers;
import static service.ServiceData.userRoles;

public class UserService {


    /***
     * 添加一个用户
     * @param name 用户名
     * @param password 用户密码
     * @throws Exception
     */
    public static void createUser(String name, String password) throws Exception {
        // check user name
        if(StringUtil.isEmpty(name) || StringUtil.isEmpty(password) ) {
            throw new Exception("Username/password should not be empty.");
        }

        // query user from db
        User user = RegisterUsers.getUserByName(name);
        if(user != null) {
            throw new Exception("User has already exists");
        }

        // gen a new user id
        Integer userId = UserIdService.genId();
        user = new User(userId, name);

        // encrypt user password
        String encryptyPwd = Encryption.GetMd5(UserValidator.genEncryptPwdParam(userId, name, password));
        user.setPassword(encryptyPwd);

        // store user into db..
        User storedUser = RegisterUsers.addUser(name, user);
        if(storedUser != null) {
            throw new Exception("User has already exists");
        }
    }


    /***
     * 删除一个用户
     * @param name 用户名
     * @return : 生成的token
     * @throws Exception
     */
    public static void deleteUser(String name) throws Exception {
        // check user name
        if(StringUtil.isEmpty(name)) {
            throw new Exception("User name should not be empty.");
        }

        User deletedUser = RegisterUsers.deleteUser(name);
        if(deletedUser == null) {
            throw new Exception("User is not exists");
        }

        synchronized (userRoles) {
            for(Map.Entry<String, HashSet<String>> entry : roleUsers.entrySet()) {
                HashSet<String> userSet = entry.getValue();
                if(userSet != null) {
                    userSet.remove(name);
                }
            }

            userRoles.remove(name);

            Auth auth = AuthService.authMgmt.get(name);
            if(auth!= null && !StringUtil.isEmpty(auth.getToken())) {
                AuthService.auth2User.remove(auth.getToken());
            }
            AuthService.authMgmt.remove(name);
        }
    }



}
