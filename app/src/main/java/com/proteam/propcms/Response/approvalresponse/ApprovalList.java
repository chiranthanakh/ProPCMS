package com.proteam.propcms.Response.approvalresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.proteam.propcms.Response.DevisionHeadItems;

import java.util.ArrayList;

public class ApprovalList {

    @SerializedName("list")
    @Expose
    private ArrayList<ApprovalItems> list = null;

    public ArrayList<ApprovalItems> getList() {
        return list;
    }

    public void setList(ArrayList<ApprovalItems> list) {
        this.list = list;
    }
}
