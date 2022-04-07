package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopTenRevenueItemResponse {

    @SerializedName("amt")
    @Expose
    private String amt;

    @SerializedName("client_id")
    @Expose
    private String client_id;

    @SerializedName("client_name")
    @Expose
    private String client_name;

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
