package com.cococompany.android.aq.models;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String title;
    private String imageUrl;

    public ImageItem() {
    }

    public ImageItem(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ImageItem(Bitmap image, String title, String imageUrl) {
        super();
        this.image = image;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
