package com.example.ssoheyli.ticketing_newdesign.Language;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageModel {

    @SerializedName("LanguageId")
    @Expose
    private int languageId;

    @SerializedName("LCID")
    @Expose
    private int LCID;

    @SerializedName("Culture")
    @Expose
    private String Culture;

    public LanguageModel(int languageId, int LCID, String culture) {
        this.languageId = languageId;
        this.LCID = LCID;
        Culture = culture;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getLCID() {
        return LCID;
    }

    public void setLCID(int LCID) {
        this.LCID = LCID;
    }

    public String getCulture() {
        return Culture;
    }

    public void setCulture(String culture) {
        Culture = culture;
    }
}
