package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DivisionListResponse {

    @SerializedName("division_list")
    @Expose
    private ArrayList<DivisionList> division_list = null;

    public ArrayList<DivisionList> getDivision_list() {
        return division_list;
    }

    public void setDivision_list(ArrayList<DivisionList> division_list) {
        this.division_list = division_list;
    }
}
