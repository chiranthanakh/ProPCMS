package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectList {

    @SerializedName("project_id")
    @Expose
    private String project_id;

    @SerializedName("project_name")
    @Expose
    private String project_name;

    @SerializedName("pc_code")
    @Expose
    private String pc_code;

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
}
