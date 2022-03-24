package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashboardcountresponse {

    @SerializedName("invoice_request_for_cancellation")
    @Expose
    private String invoice_request_for_cancellation;

    @SerializedName("invoice_req_for_modification")
    @Expose
    private String invoice_req_for_modification;

    @SerializedName("cost_transfer_note_request_for_approval")
    @Expose
    private String cost_transfer_note_request_for_approval;

    public String getInvoice_request_for_cancellation() {
        return invoice_request_for_cancellation;
    }

    public void setInvoice_request_for_cancellation(String invoice_request_for_cancellation) {
        this.invoice_request_for_cancellation = invoice_request_for_cancellation;
    }

    public String getInvoice_req_for_modification() {
        return invoice_req_for_modification;
    }

    public void setInvoice_req_for_modification(String invoice_req_for_modification) {
        this.invoice_req_for_modification = invoice_req_for_modification;
    }

    public String getCost_transfer_note_request_for_approval() {
        return cost_transfer_note_request_for_approval;
    }

    public void setCost_transfer_note_request_for_approval(String cost_transfer_note_request_for_approval) {
        this.cost_transfer_note_request_for_approval = cost_transfer_note_request_for_approval;
    }
}
