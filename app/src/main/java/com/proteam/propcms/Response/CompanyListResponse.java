package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CompanyListResponse {


    @SerializedName("company_list")
    @Expose
    private ArrayList<CompanyItemsResponse> company_list = null;

    public ArrayList<CompanyItemsResponse> getCompany_list() {
        return company_list;
    }

    public void setCompany_list(ArrayList<CompanyItemsResponse> company_list) {
        this.company_list = company_list;
    }
}
