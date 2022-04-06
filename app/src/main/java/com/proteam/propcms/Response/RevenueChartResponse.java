package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RevenueChartResponse {

    @SerializedName("month")
    @Expose
    private ArrayList<String> month;

    @SerializedName("data")
    @Expose
    private ArrayList<String> data;

    public ArrayList<String> getMonth() {
        return month;
    }

    public void setMonth(ArrayList<String> month) {
        this.month = month;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
