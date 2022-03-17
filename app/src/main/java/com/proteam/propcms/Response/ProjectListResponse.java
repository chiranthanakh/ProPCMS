package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProjectListResponse {

    @SerializedName("project_list")
    @Expose
    private ArrayList<ProjectList> project_list = null;

    public ArrayList<ProjectList> getProject_list() {
        return project_list;
    }

    public void setProject_list(ArrayList<ProjectList> project_list) {
        this.project_list = project_list;
    }
}
