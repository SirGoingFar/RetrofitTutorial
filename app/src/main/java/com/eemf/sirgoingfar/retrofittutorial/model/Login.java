package com.eemf.sirgoingfar.retrofittutorial.model;

public class Login {

    private String username;

    private String encodedPassword;

    public String getUsername() {
        return username;
    }

    public Login(String username, String encodedPassword) {
        this.username = username;
        this.encodedPassword = encodedPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}
