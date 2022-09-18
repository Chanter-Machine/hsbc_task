package common.utils;

public class UserValidator {

    public static String genEncryptPwdParam(Integer id, String name, String pwd) {
        return id+name+pwd;
    }

    public static String genTokenParam(Integer id, String name, String pwd, String extra, Integer randNum) {
        return id+name+pwd+extra+randNum;
    }
}
