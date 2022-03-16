package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestForModificationListResponse {

    @SerializedName("list")
    @Expose
    private ArrayList<RequestForModificationResponse> list = null;

    public ArrayList<RequestForModificationResponse> getList() {
        return list;
    }

    public void setList(ArrayList<RequestForModificationResponse> list) {
        this.list = list;
    }
}
