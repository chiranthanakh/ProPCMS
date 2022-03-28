package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCountDivisionResponse {

    @SerializedName("verify_billing_instructions_with_status_list")
    @Expose
    private String verify_billing_instructions_with_status_list;

    @SerializedName("verify_cost_transfer_note_with_status")
    @Expose
    private String verify_cost_transfer_note_with_status;

    public String getVerify_billing_instructions_with_status_list() {
        return verify_billing_instructions_with_status_list;
    }

    public void setVerify_billing_instructions_with_status_list(String verify_billing_instructions_with_status_list) {
        this.verify_billing_instructions_with_status_list = verify_billing_instructions_with_status_list;
    }

    public String getVerify_cost_transfer_note_with_status() {
        return verify_cost_transfer_note_with_status;
    }

    public void setVerify_cost_transfer_note_with_status(String verify_cost_transfer_note_with_status) {
        this.verify_cost_transfer_note_with_status = verify_cost_transfer_note_with_status;
    }
}
