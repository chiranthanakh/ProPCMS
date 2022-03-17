package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestForModificationResponse {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("company_id")
    @Expose
    private String company_id;

    @SerializedName("project_id")
    @Expose
    private String project_id;

    @SerializedName("invoice_number")
    @Expose
    private String invoice_number;

    @SerializedName("division_id")
    @Expose
    private String division_id;

    @SerializedName("group_name")
    @Expose
    private String group_name;

    @SerializedName("assignment")
    @Expose
    private String assignment;

    @SerializedName("region")
    @Expose
    private String region;

    @SerializedName("gstin_no")
    @Expose
    private String gstin_no;

    @SerializedName("pan_no_customer")
    @Expose
    private String pan_no_customer;

    @SerializedName("place")
    @Expose
    private String place;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("gst_percentage")
    @Expose
    private String gst_percentage;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("pc_code")
    @Expose
    private String pc_code;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("upload_user_id")
    @Expose
    private String upload_user_id;

    @SerializedName("request_remarks")
    @Expose
    private String request_remarks;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("process_owner")
    @Expose
    private String process_owner;

    public String getProcess_owner() {
        return process_owner;
    }

    public void setProcess_owner(String process_owner) {
        this.process_owner = process_owner;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRequest_remarks() {
        return request_remarks;
    }

    public void setRequest_remarks(String request_remarks) {
        this.request_remarks = request_remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getDivision_id() {
        return division_id;
    }

    public void setDivision_id(String division_id) {
        this.division_id = division_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGstin_no() {
        return gstin_no;
    }

    public void setGstin_no(String gstin_no) {
        this.gstin_no = gstin_no;
    }

    public String getPan_no_customer() {
        return pan_no_customer;
    }

    public void setPan_no_customer(String pan_no_customer) {
        this.pan_no_customer = pan_no_customer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getGst_percentage() {
        return gst_percentage;
    }

    public void setGst_percentage(String gst_percentage) {
        this.gst_percentage = gst_percentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPc_code() {
        return pc_code;
    }

    public void setPc_code(String pc_code) {
        this.pc_code = pc_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUpload_user_id() {
        return upload_user_id;
    }

    public void setUpload_user_id(String upload_user_id) {
        this.upload_user_id = upload_user_id;
    }
}
