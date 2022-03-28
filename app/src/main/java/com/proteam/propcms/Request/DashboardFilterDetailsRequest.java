package com.proteam.propcms.Request;

public class DashboardFilterDetailsRequest {

    String user_id,month,client_id,division,company_id;

    public DashboardFilterDetailsRequest(String user_id, String month, String client_id, String division, String company_id) {
        this.user_id = user_id;
        this.month = month;
        this.client_id = client_id;
        this.division = division;
        this.company_id = company_id;
    }
}
