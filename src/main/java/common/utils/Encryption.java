package common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {


    public static String GetMd5(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5= MessageDigest.getInstance("MD5");
        byte[] encryptPwd = md5.digest(plaintext.getBytes("utf-8"));
        String result="";
        for (int i=0; i<encryptPwd.length;i++){
            result+=Integer.toHexString((0x000000ff & encryptPwd[i]) | 0xffffff00).substring(6);
        }

        return result;
    }
}
