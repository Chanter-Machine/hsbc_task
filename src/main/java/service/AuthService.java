package service;

import common.beans.Auth;
import common.beans.User;
import common.utils.Encryption;
import common.utils.UserValidator;
import org.eclipse.jetty.util.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class AuthService {

    private static final String salt ="chaos_str_for_token!";
    // 2hours
    private static final Long timeSpan = 2*60*60*1000L;

    // key userName, value Auth object
    public static final ConcurrentHashMap<String, Auth> authMgmt = new ConcurrentHashMap<>();
    protected static ConcurrentHashMap<String, String> auth2User = new ConcurrentHashMap<>();

    /***
     * 生成token
     *
     * @param user 用户对象
     * @param time 当前系统时间
     * @return : 生成的token
     * @throws IOException
     */
    private static String genToken(User user, Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Random rand = new Random();
        int num = rand.nextInt(Integer.MAX_VALUE);
        String tokenParam1 = UserValidator.genTokenParam(user.getId(), user.getName(), user.getPassword(), salt, num);
        String tokenParam2 = Encryption.GetMd5(tokenParam1);
        String tokenParam3 = tokenParam2+time;
        String tokenBase64 = Base64.getEncoder().encodeToString(tokenParam3.getBytes("utf-8"));
        return Encryption.GetMd5(tokenBase64);
    }

    /***
     * 获取token
     * @param name 用户名
     * @param password 用户密码
     * @return : 生成的token
     * @throws IOException
     */
    public static String getToken(String name, String password) throws Exception {
        Long now = System.currentTimeMillis();

        if(StringUtil.isBlank(name) || StringUtil.isBlank(password)) {
            throw  new Exception("username/password should be empty");
        }

        User user = RegisterUsers.getUserByName(name);
        if(user == null) {
            throw  new Exception("User is not exists");
        }

        // get user id
        Integer userId = user.getId();

        // encrypt user password
        String encryptyPwd = Encryption.GetMd5(UserValidator.genEncryptPwdParam(userId, name, password));
        if(!user.getPassword().equals(encryptyPwd)) {
            throw new Exception("Username/Password is not match");
        }

        //get previous token
        Auth auth = authMgmt.getOrDefault(name, new Auth());
        if(auth.getExpire() !=null && auth.getExpire() > now) {
            return auth.getToken();
        }
        synchronized (authMgmt) {
            // double check token
            auth = authMgmt.getOrDefault(name, new Auth());
            if(auth.getExpire()!=null && auth.getExpire() > now) {
                return auth.getToken();
            }

            String token = genToken(user, now);
            auth.setExpire(now + timeSpan);
            auth.setToken(token);
            authMgmt.put(name, auth);
            auth2User.put(token, user.getName());
        }

        return auth.getToken();
    }

    public static void invalidToken(String token) throws Exception {
        if(StringUtil.isBlank(token)) {
            throw new Exception("token should not be empty");
        }

        synchronized (authMgmt) {
            if(!auth2User.keySet().contains(token)) {
                throw new Exception("No valid token exists");
            }

            String name = auth2User.get(token);
            auth2User.remove(token);
            authMgmt.remove(name);
        }
    }

    public static ConcurrentHashMap<String, Auth> getAuthMgmt() {
        return authMgmt;
    }

    public static ConcurrentHashMap<String, String> getAuth2User() {
        return auth2User;
    }
}
