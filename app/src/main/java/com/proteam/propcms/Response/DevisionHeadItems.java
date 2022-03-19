package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DevisionHeadItems {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("head_name")
    @Expose
    private String head_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead_name() {
        return head_name;
    }

    public void setHead_name(String head_name) {
        this.head_name = head_name;
    }
}
