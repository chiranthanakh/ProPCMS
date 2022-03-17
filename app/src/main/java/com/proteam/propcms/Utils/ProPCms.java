package com.proteam.propcms.Utils;

import com.proteam.propcms.Request.CompanyDetailsModel;
import com.proteam.propcms.Request.Loginmodel;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.CompanyDetailsResponse;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProfileResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.RequestForModificationListResponse;
import com.proteam.propcms.Request.Updateuserrequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProPCms {


    @POST("Pcms_apis/login")
    Call<LoginResponse> validatelogin(@Body Loginmodel logininfo);

    @POST("Pcms_apis/user_details")
    Call<ProfileResponse> profile(@Body UserIdRequest Userid);

    @POST("Pcms_apis/update_user_details")
    Call<GenerealResponse> profileupdate(@Body Updateuserrequest Userid);

    @POST("Pcms_apis/project_list")
    Call<ProjectListResponse> projectnamelist(@Body ProjectListModel projectListModel);

    @POST("Pcms_apis/invoice_req_for_modification")
    Call<RequestForModificationListResponse> invoicemodify();


    @POST("Pcms_apis/company_details")
    Call<CompanyDetailsResponse> companydetail(CompanyDetailsModel companyDetailsModel);

}
