package com.example.ssoheyli.ticketing_newdesign.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_Get_Basket_Confirm {

    @SerializedName("Result")
    @Expose
    int result;

    @SerializedName("Msg")
    @Expose
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
