package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VerifyCostTransferListResponse {

    @SerializedName("list")
    @Expose
    private ArrayList<VerifCostTransferResponse> list = null;

    public ArrayList<VerifCostTransferResponse> getList() {
        return list;
    }

    public void setList(ArrayList<VerifCostTransferResponse> list) {
        this.list = list;
    }
}
