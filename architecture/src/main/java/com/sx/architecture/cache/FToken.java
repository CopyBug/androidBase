package com.sx.architecture.cache;


import java.io.Serializable;

/**
 * Created by lzj on 2017/11/15.
 */

public class FToken implements Serializable {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJwtTokenKey() {
        return jwtTokenKey;
    }

    public void setJwtTokenKey(String jwtTokenKey) {
        this.jwtTokenKey = jwtTokenKey;
    }

    public String token;
    public String jwtTokenKey;

}
