package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VerifyBillingInstructionListResponse {

    @SerializedName("list")
    @Expose
    private ArrayList<VerifyBillingInstructionResponse> list = null;

    public ArrayList<VerifyBillingInstructionResponse> getList() {
        return list;
    }

    public void setList(ArrayList<VerifyBillingInstructionResponse> list) {
        this.list = list;
    }
}
