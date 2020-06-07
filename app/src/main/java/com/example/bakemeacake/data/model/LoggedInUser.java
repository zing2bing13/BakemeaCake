package com.example.bakemeacake.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private int userId;
    private String userName;
    private String password;

    public LoggedInUser(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return userName;
    }

    public String getPassword() {return password;}
}
