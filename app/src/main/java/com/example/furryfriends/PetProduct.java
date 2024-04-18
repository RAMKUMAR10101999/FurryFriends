package com.example.furryfriends;

public class PetProduct {
    private String productName;
    private String productDescription;
    private String username;
    private String location;
    private String contactDetails;
    private String imageUrl;

    public PetProduct() {
        // Required empty constructor for Firebase
    }

    public PetProduct(String productName, String productDescription, String username, String location, String contactDetails, String imageUrl) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.username = username;
        this.location = location;
        this.contactDetails = contactDetails;
        this.imageUrl = imageUrl;
    }

    // Getters and setters

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}