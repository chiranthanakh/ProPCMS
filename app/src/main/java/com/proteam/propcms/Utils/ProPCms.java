package com.proteam.propcms.Utils;

import com.proteam.propcms.Request.BillingUpdaterequest;
import com.proteam.propcms.Request.Clientlistrequest;
import com.proteam.propcms.Request.CompanyDetailsModel;
import com.proteam.propcms.Request.DivisionListModel;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.Loginmodel;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.ClientList;
import com.proteam.propcms.Response.CompanyDetailsResponse;
import com.proteam.propcms.Response.CompanyListResponse;
import com.proteam.propcms.Response.Dashboardcountresponse;
import com.proteam.propcms.Response.DevisionHeadList;
import com.proteam.propcms.Response.DivisionListResponse;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProfileResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.RequestForModificationListResponse;
import com.proteam.propcms.Request.Updateuserrequest;
import com.proteam.propcms.Response.VerifyBillingInstructionListResponse;
import com.proteam.propcms.Response.approvalresponse.ApprovalList;
import com.proteam.propcms.Response.invoicereject.RejectItems;
import com.proteam.propcms.Response.invoicereject.RejectList;

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

    @POST("Pcms_apis/division_list")
    Call<DivisionListResponse> divisionnamelist(@Body DivisionListModel divisionListModel);

    @POST("Pcms_apis/cost_transfer_note_request_for_approval_list")
    Call<ApprovalList> invoiceapprove();

    @POST("Pcms_apis/cost_transfer_note_request_for_approval_approve")
    Call<GenerealResponse> aprovalctrnlist(@Body InvApproverequest invApproverequest);

    @POST("Pcms_apis/cost_transfer_note_request_for_approval_reject")
    Call<GenerealResponse> rejectctrnList(@Body InvApproverequest invApproverequest);

    @POST("Pcms_apis/invoice_request_for_cancellation_list")
    Call<RejectList> invoicecancelation(@Body UserIdRequest userIdRequest);

    @POST("Pcms_apis/invoice_request_for_cancellation_approve")
    Call<GenerealResponse> aprovalirfclist(@Body InvApproverequest invApproverequest);

    @POST("Pcms_apis/invoice_request_for_cancellation_reject")
    Call<GenerealResponse> rejectirfcList(@Body InvApproverequest invApproverequest);

    @POST("Pcms_apis/dashboard_count")
    Call<Dashboardcountresponse> count(@Body UserIdRequest userIdRequest);

    @POST("Pcms_apis/client_list")
    Call<ClientList> client(@Body Clientlistrequest clientlistrequest);


    @POST("Pcms_apis/verification_getBIListDivisionwise")
    Call<VerifyBillingInstructionListResponse> VerifyBIList(@Body UserIdRequest userIdRequest);

    @POST("Pcms_apis/verify_billing_instructions_with_status_list_update")
    Call<GenerealResponse> billupdate(@Body BillingUpdaterequest billingUpdaterequest);
}
