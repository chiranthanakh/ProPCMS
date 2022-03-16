package com.proteam.propcms.Utils;

import com.proteam.propcms.Request.Loginmodel;
import com.proteam.propcms.Response.GenerealResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProPCms {


    @POST("Clients_apis/client_login")
    Call<GenerealResponse> validatelogin(@Body Loginmodel logininfo);

}
