package com.example.ssoheyli.ticketing_newdesign.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by s.soheyli on 11/15/2018.
 */

public class Model_Get_City
{
    @SerializedName("Id")
    @Expose
    int id;

    @SerializedName("Title")
    @Expose
    String title;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
