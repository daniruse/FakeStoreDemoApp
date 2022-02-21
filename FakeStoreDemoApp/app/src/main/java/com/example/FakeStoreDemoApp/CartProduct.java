package com.example.FakeStoreDemoApp;

import android.graphics.Bitmap;

public class CartProduct {

    Bitmap image;
    String name;
    String quantity;
    String price;

    public CartProduct(Bitmap image, String name, String quantity, String price) {
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
