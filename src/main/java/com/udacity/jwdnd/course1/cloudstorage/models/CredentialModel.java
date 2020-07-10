package com.udacity.jwdnd.course1.cloudstorage.models;

public class CredentialModel {

    private String url;

    private String username;

    private String password;


    public CredentialModel(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
