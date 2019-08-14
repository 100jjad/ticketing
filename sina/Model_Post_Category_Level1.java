package com.example.ssoheyli.ticketing_newdesign.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_Post_Category_Level1
{
    @SerializedName("CategoryId")
    @Expose
    int category_id;

    @SerializedName("LangID")
    @Expose
    int language_id;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }
}
