package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDetailsResponse {

    @SerializedName("project_id")
    @Expose
    private String project_id;

    @SerializedName("project_name")
    @Expose
    private String project_name;

    @SerializedName("pc_code")
    @Expose
    private String pc_code;

    @SerializedName("company_id")
    @Expose
    private String company_id;

    @SerializedName("assignment_type")
    @Expose
    private String assignment_type;

    @SerializedName("division_id")
    @Expose
    private String division_id;

    @SerializedName("division_head")
    @Expose
    private String division_head;

    @SerializedName("client_id")
    @Expose
    private String client_id;

    @SerializedName("project_description")
    @Expose
    private String project_description;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getPc_code() {
        return pc_code;
    }

    public void setPc_code(String pc_code) {
        this.pc_code = pc_code;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getAssignment_type() {
        return assignment_type;
    }

    public void setAssignment_type(String assignment_type) {
        this.assignment_type = assignment_type;
    }

    public String getDivision_id() {
        return division_id;
    }

    public void setDivision_id(String division_id) {
        this.division_id = division_id;
    }

    public String getDivision_head() {
        return division_head;
    }

    public void setDivision_head(String division_head) {
        this.division_head = division_head;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }
}
