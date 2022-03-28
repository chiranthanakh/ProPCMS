package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardFilterDetailsResponse {

    @SerializedName("total_outstanding")
    @Expose
    private String total_outstanding;

    @SerializedName("total_collection")
    @Expose
    private String total_collection;

    @SerializedName("total_revenue")
    @Expose
    private String total_revenue;

    @SerializedName("new_pc_code")
    @Expose
    private String new_pc_code;

    public String getTotal_outstanding() {
        return total_outstanding;
    }

    public void setTotal_outstanding(String total_outstanding) {
        this.total_outstanding = total_outstanding;
    }

    public String getTotal_collection() {
        return total_collection;
    }

    public void setTotal_collection(String total_collection) {
        this.total_collection = total_collection;
    }

    public String getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(String total_revenue) {
        this.total_revenue = total_revenue;
    }

    public String getNew_pc_code() {
        return new_pc_code;
    }

    public void setNew_pc_code(String new_pc_code) {
        this.new_pc_code = new_pc_code;
    }
}
