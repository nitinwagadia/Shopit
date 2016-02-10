package com.cloudcomputing.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Nitin on 12/12/2015.
 */

@JsonObject
public class Deals {


    @JsonField
    private String description;
    @JsonField
    private String title;
    @JsonField
    private String short_title;
    @JsonField
    private boolean online;
    @JsonField
    private int price;
    @JsonField
    private int value;
    @JsonField
    private String url;
    @JsonField
    private String untracked_url;
    @JsonField
    private String expires_at;
    @JsonField
    private String image_url;
    @JsonField
    private String category_name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String decription) {
        this.description = decription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUntracked_url() {
        return untracked_url;
    }

    public void setUntracked_url(String untracked_url) {
        this.untracked_url = untracked_url;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
