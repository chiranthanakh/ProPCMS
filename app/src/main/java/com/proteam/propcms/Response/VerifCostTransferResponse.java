package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifCostTransferResponse {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("ctn_no")
    @Expose
    private String ctn_no;

    @SerializedName("financial_year")
    @Expose
    private String financial_year;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("from_pc_code")
    @Expose
    private String from_pc_code;

    @SerializedName("to_pc_code")
    @Expose
    private String to_pc_code;

    @SerializedName("expense_category")
    @Expose
    private String expense_category;

    @SerializedName("expense_type")
    @Expose
    private String expense_type;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("verfication_status")
    @Expose
    private String verfication_status;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("upload_user_id")
    @Expose
    private String upload_user_id;

    @SerializedName("invoice_approver")
    @Expose
    private String invoice_approver;

    @SerializedName("manager_approve")
    @Expose
    private String manager_approve;

    @SerializedName("latest_uploaded")
    @Expose
    private String latest_uploaded;

    @SerializedName("latest_update_status")
    @Expose
    private String latest_update_status;

    @SerializedName("created_on")
    @Expose
    private String created_on;

    @SerializedName("updated_on")
    @Expose
    private String updated_on;

    @SerializedName("flag")
    @Expose
    private String flag;

    @SerializedName("pc_code")
    @Expose
    private String pc_code;

    @SerializedName("from_pc_code_name")
    @Expose
    private String from_pc_code_name;

    @SerializedName("to_pc_code_name")
    @Expose
    private String to_pc_code_name;

    @SerializedName("expense_type_name")
    @Expose
    private String expense_type_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCtn_no() {
        return ctn_no;
    }

    public void setCtn_no(String ctn_no) {
        this.ctn_no = ctn_no;
    }

    public String getFinancial_year() {
        return financial_year;
    }

    public void setFinancial_year(String financial_year) {
        this.financial_year = financial_year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFrom_pc_code() {
        return from_pc_code;
    }

    public void setFrom_pc_code(String from_pc_code) {
        this.from_pc_code = from_pc_code;
    }

    public String getTo_pc_code() {
        return to_pc_code;
    }

    public void setTo_pc_code(String to_pc_code) {
        this.to_pc_code = to_pc_code;
    }

    public String getExpense_category() {
        return expense_category;
    }

    public void setExpense_category(String expense_category) {
        this.expense_category = expense_category;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVerfication_status() {
        return verfication_status;
    }

    public void setVerfication_status(String verfication_status) {
        this.verfication_status = verfication_status;
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

    public String getInvoice_approver() {
        return invoice_approver;
    }

    public void setInvoice_approver(String invoice_approver) {
        this.invoice_approver = invoice_approver;
    }

    public String getManager_approve() {
        return manager_approve;
    }

    public void setManager_approve(String manager_approve) {
        this.manager_approve = manager_approve;
    }

    public String getLatest_uploaded() {
        return latest_uploaded;
    }

    public void setLatest_uploaded(String latest_uploaded) {
        this.latest_uploaded = latest_uploaded;
    }

    public String getLatest_update_status() {
        return latest_update_status;
    }

    public void setLatest_update_status(String latest_update_status) {
        this.latest_update_status = latest_update_status;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPc_code() {
        return pc_code;
    }

    public void setPc_code(String pc_code) {
        this.pc_code = pc_code;
    }

    public String getFrom_pc_code_name() {
        return from_pc_code_name;
    }

    public void setFrom_pc_code_name(String from_pc_code_name) {
        this.from_pc_code_name = from_pc_code_name;
    }

    public String getTo_pc_code_name() {
        return to_pc_code_name;
    }

    public void setTo_pc_code_name(String to_pc_code_name) {
        this.to_pc_code_name = to_pc_code_name;
    }

    public String getExpense_type_name() {
        return expense_type_name;
    }

    public void setExpense_type_name(String expense_type_name) {
        this.expense_type_name = expense_type_name;
    }
}
