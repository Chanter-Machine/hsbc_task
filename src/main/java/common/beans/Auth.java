package common.beans;

import java.util.Timer;

public class Auth {
    String token;
    Long expire;

    public Auth(String token, long expire) {
        this.token= token;
        this.expire = expire;
    }

    public Auth() {

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
