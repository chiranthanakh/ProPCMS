package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.proteam.propcms.Request.Loginmodel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class GenerealResponse {


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reason")
    @Expose
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
