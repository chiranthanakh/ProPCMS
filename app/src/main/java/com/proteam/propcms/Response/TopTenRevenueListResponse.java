package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopTenRevenueListResponse {

    @SerializedName("graph_data")
    @Expose
    private ArrayList<TopTenRevenueItemResponse> graph_data = null;

    public ArrayList<TopTenRevenueItemResponse> getGraph_data() {
        return graph_data;
    }

    public void setGraph_data(ArrayList<TopTenRevenueItemResponse> graph_data) {
        this.graph_data = graph_data;
    }
}
