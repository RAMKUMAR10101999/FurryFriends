package com.example.furryfriends;
public class User {
    private String username;
    private String email;
    private String contactNumber;
    private String location;
    private String profileImageUrl;

    // Empty constructor required for Firebase
    public User() {
    }

    // Constructor with parameters
    public User(String username, String email, String contactNumber, String location, String profileImageUrl) {
        this.username = username;
        this.email = email;
        this.contactNumber = contactNumber;
        this.location = location;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and setters for the fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String petName) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
