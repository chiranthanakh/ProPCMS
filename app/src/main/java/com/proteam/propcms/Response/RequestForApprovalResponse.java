package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestForApprovalResponse {

    @SerializedName("list")
    @Expose
    private ArrayList<RequestForApprovalListResponse> list = null;

    public ArrayList<RequestForApprovalListResponse> getList() {
        return list;
    }

    public void setList(ArrayList<RequestForApprovalListResponse> list) {
        this.list = list;
    }
}
