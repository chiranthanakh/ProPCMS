package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DevisionHeadList {

    @SerializedName("head_list")
    @Expose
    private ArrayList<DevisionHeadItems> head_list = null;

    public ArrayList<DevisionHeadItems> getHead_list() {
        return head_list;
    }

    public void setHead_list(ArrayList<DevisionHeadItems> head_list) {
        this.head_list = head_list;
    }
}
