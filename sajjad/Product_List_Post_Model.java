package com.example.ssoheyli.ticketing_newdesign.Products.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_List_Post_Model {

    @SerializedName("CategoryId")
    @Expose
    private int CategoryId;

    @SerializedName("lcid")
    @Expose
    private int lcid;

    @SerializedName("skip")
    @Expose
    private int skip;

    @SerializedName("take")
    @Expose
    private int take;

    public Product_List_Post_Model(int categoryId, int lcid, int skip, int take) {
        CategoryId = categoryId;
        this.lcid = lcid;
        this.skip = skip;
        this.take = take;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getLcid() {
        return lcid;
    }

    public void setLcid(int lcid) {
        this.lcid = lcid;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }
}
