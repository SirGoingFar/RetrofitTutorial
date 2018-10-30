package com.eemf.sirgoingfar.retrofittutorial.model;

public class User {

    private Integer userId;
    private String name;
    private String password;
    private String email;
    private int age;
    private String[] topicOfInterest;
    private String accessToken;

    public User(String name, String password, String email, int age, String[] topicOfInterest) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.topicOfInterest = topicOfInterest;
    }

    public int getUserId(){
        return userId;
    }

    public String getAccessToken() {
        /*Access Token or Authentication Token*/
        return accessToken;
    }
}
