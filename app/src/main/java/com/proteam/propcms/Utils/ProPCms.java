package com.proteam.propcms.Utils;

import com.proteam.propcms.Request.Loginmodel;
import com.proteam.propcms.Request.ProjectListModel;

import com.proteam.propcms.Response.GenerealResponse;

import com.proteam.propcms.Response.ProjectListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProPCms {



    @POST("Pcms_apis/project_list")
    Call<ProjectListResponse> projectnamelist(@Body ProjectListModel projectListModel);

}
