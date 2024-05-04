package com.example.furryfriends;

import android.os.Parcel;
import android.os.Parcelable;

public class PetProduct implements Parcelable {
    private String productId; // Add productId field
    private String productName;
    private String productDescription;
    private String location;
    private String imageUrl;
    private boolean isFavorite;

    // Default constructor
    public PetProduct() {
        // Required empty public constructor
    }

    // Constructor with parameters
    public PetProduct(String productId, String productName, String productDescription, String location, String imageUrl, boolean isFavorite) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.location = location;
        this.imageUrl = imageUrl;
        this.isFavorite = isFavorite;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    // Parcelable implementation
    protected PetProduct(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productDescription = in.readString();
        location = in.readString();
        imageUrl = in.readString();
        isFavorite = in.readByte() != 0; // Convert byte to boolean
    }

    public static final Creator<PetProduct> CREATOR = new Creator<PetProduct>() {
        @Override
        public PetProduct createFromParcel(Parcel in) {
            return new PetProduct(in);
        }

        @Override
        public PetProduct[] newArray(int size) {
            return new PetProduct[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(productDescription);
        dest.writeString(location);
        dest.writeString(imageUrl);
        dest.writeByte((byte) (isFavorite ? 1 : 0)); // Convert boolean to byte
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
