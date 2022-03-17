package com.proteam.propcms.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InvApproverequest {

    @SerializedName("ids")
    @Expose
    private ArrayList<String> ids;

    public InvApproverequest(ArrayList<String> ids) {
        this.ids = ids;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }
}
