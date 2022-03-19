package com.proteam.propcms.Utils;

import com.proteam.propcms.Request.CompanyDetailsModel;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.Loginmodel;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.CompanyDetailsResponse;
import com.proteam.propcms.Response.CompanyListResponse;
import com.proteam.propcms.Response.DevisionHeadList;
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
    Call<CompanyDetailsResponse> companydetail(@Body CompanyDetailsModel companyDetailsModel);

    @POST("Pcms_apis/invoice_req_for_modification_approve")
    Call<GenerealResponse> aprovallist(@Body InvApproverequest invApproverequest);

    @POST("Pcms_apis/invoice_req_for_modification_reject")
    Call<GenerealResponse> rejectList(@Body InvApproverequest invApproverequest);

    @POST("Pcms_apis/company_name_list")
    Call<CompanyListResponse> companys();

    @POST("Pcms_apis/division_head_list")
    Call<DevisionHeadList> heads();

}
