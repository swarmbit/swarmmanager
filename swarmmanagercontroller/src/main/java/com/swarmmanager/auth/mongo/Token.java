package com.swarmmanager.auth.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "invalidTokens")
public class Token {

    private String token;

    private Date expiration;

    public Token(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
