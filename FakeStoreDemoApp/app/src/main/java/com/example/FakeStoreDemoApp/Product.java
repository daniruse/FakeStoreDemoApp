package com.example.FakeStoreDemoApp;

import android.os.Parcel;
import android.os.Parcelable;


public class Product implements Parcelable {

    String image;
    String id;
    String title;
    String price;
    String description;
    String category;
    String ratingCount;
    String rating;

    public Product(String image, String id, String title, String price, String description, String category, String ratingCount, String rating) {
        this.image = image;
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.ratingCount = ratingCount;
        this.rating = rating;
    }

    protected Product(Parcel in) {
        image = in.readString();
        id = in.readString();
        title = in.readString();
        price = in.readString();
        description = in.readString();
        category = in.readString();
        ratingCount = in.readString();
        rating = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeString(category);
        parcel.writeString(ratingCount);
        parcel.writeString(rating);
    }
}
