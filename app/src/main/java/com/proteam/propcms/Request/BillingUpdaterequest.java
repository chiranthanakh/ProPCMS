package com.proteam.propcms.Request;

public class BillingUpdaterequest {

    String user_id_logg,id,company_id,project_id,invoice_number,division_id,group_name,assignment,bill_to,billing_address,reference_no,
            region,kind_attention,gstin_no,pan_no_customer,place,month,amount,gst_percentage,total_amount,description,HSN_SAC,particular,
            invoice_no,invoice_date,gst_month,state_supply,transaction_type;

    public BillingUpdaterequest(String user_id_logg, String id, String company_id, String project_id, String invoice_number, String division_id, String group_name, String assignment, String bill_to, String billing_address, String reference_no, String region, String kind_attention, String gstin_no, String pan_no_customer, String place, String month, String amount, String gst_percentage, String total_amount, String description, String HSN_SAC, String particular, String invoice_no, String invoice_date, String gst_month, String state_supply, String transaction_type) {
        this.user_id_logg = user_id_logg;
        this.id = id;
        this.company_id = company_id;
        this.project_id = project_id;
        this.invoice_number = invoice_number;
        this.division_id = division_id;
        this.group_name = group_name;
        this.assignment = assignment;
        this.bill_to = bill_to;
        this.billing_address = billing_address;
        this.reference_no = reference_no;
        this.region = region;
        this.kind_attention = kind_attention;
        this.gstin_no = gstin_no;
        this.pan_no_customer = pan_no_customer;
        this.place = place;
        this.month = month;
        this.amount = amount;
        this.gst_percentage = gst_percentage;
        this.total_amount = total_amount;
        this.description = description;
        this.HSN_SAC = HSN_SAC;
        this.particular = particular;
        this.invoice_no = invoice_no;
        this.invoice_date = invoice_date;
        this.gst_month = gst_month;
        this.state_supply = state_supply;
        this.transaction_type = transaction_type;
    }
}
