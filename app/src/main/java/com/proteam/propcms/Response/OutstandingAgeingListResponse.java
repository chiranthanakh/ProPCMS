package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OutstandingAgeingListResponse {

    @SerializedName("graph_data")
    @Expose
    private ArrayList<OutstandingAgeingItemResponse> graph_data = null;

    public ArrayList<OutstandingAgeingItemResponse> getGraph_data() {
        return graph_data;
    }

    public void setGraph_data(ArrayList<OutstandingAgeingItemResponse> graph_data) {
        this.graph_data = graph_data;
    }
}
