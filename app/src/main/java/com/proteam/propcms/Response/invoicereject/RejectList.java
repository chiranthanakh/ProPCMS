package com.proteam.propcms.Response.invoicereject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.proteam.propcms.Response.approvalresponse.ApprovalItems;

import java.util.ArrayList;

public class RejectList {

    @SerializedName("list")
    @Expose
    private ArrayList<RejectItems> list = null;

    public ArrayList<RejectItems> getList() {
        return list;
    }

    public void setList(ArrayList<RejectItems> list) {
        this.list = list;
    }
}
