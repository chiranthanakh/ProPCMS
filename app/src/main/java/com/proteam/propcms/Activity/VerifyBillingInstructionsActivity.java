package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proteam.propcms.Adapters.IrfmListAdapter;
import com.proteam.propcms.Adapters.VerifyBillingInstructionAdapter;
import com.proteam.propcms.Adapters.VerifyCostTransferAdapter;
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.Models.VerifyBillingInstructionModel;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.RequestForModificationListResponse;
import com.proteam.propcms.Response.VerifyBillingInstructionListResponse;
import com.proteam.propcms.Response.VerifyBillingInstructionResponse;
import com.proteam.propcms.Utils.OnClick;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyBillingInstructionsActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener, OnClick {
    ImageView mToolbar;
    Spinner sp_all_project_verify_bi;
    int mMonth,mDay,mYear;
    EditText edt_from_verify_BI;
    RecyclerView rv_verify_BI_Data_list;
    ImageView temp_btn_Bi;
    LinearLayout ll_no_data_BI;
    ProgressDialog progressDialog;
    TextView tv_count_vbi;
    AppCompatButton btn_verifySubmitBI;

    Map projectmap = new HashMap();
    List projectList = new ArrayList();
    Map map = new HashMap();

    ArrayList<VerifyBillingInstructionModel> arrayList = new ArrayList<VerifyBillingInstructionModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_billing_instructions);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());
        initialize();
        sp_all_project_verify_bi.setOnItemSelectedListener(OnCatSpinnerCL);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VerifyBillingInstructionsActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {
        btn_verifySubmitBI=findViewById(R.id.btn_verifySubmitBI);
        btn_verifySubmitBI.setOnClickListener(this);
        tv_count_vbi=findViewById(R.id.tv_count_vbi);
        rv_verify_BI_Data_list=findViewById(R.id.rv_verify_BI_Data_list);
        ll_no_data_BI=findViewById(R.id.ll_no_data_BI);

        edt_from_verify_BI=findViewById(R.id.edt_from_verify_BI);
        edt_from_verify_BI.setOnClickListener(this);
        sp_all_project_verify_bi=findViewById(R.id.sp_all_project_verify_bi);

        callProjectListApi();
        callBIlistApi();

    }


    /////////////////////Calling API//////////////////////

    private void callBIlistApi() {

        progressDialog = new ProgressDialog(VerifyBillingInstructionsActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                UserIdRequest userIdRequest = new UserIdRequest("14");
                WebServices<VerifyBillingInstructionResponse> webServices = new WebServices<VerifyBillingInstructionResponse>(VerifyBillingInstructionsActivity.this);
                webServices.VerifyBIDataList(WebServices.ApiType.verifyBi,userIdRequest);
            } else {

            }
        }
    }

    private void callProjectListApi() {

        ProjectListModel projectListModel = new ProjectListModel("14");
        WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(VerifyBillingInstructionsActivity.this);
        webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);

        progressDialog = new ProgressDialog(VerifyBillingInstructionsActivity.this);

        /*if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                ProjectListModel projectListModel = new ProjectListModel("21");
                WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(InvoiceRequestForCancellationsActivity.this);
                webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);
            }
        }*/

    }

    private void callsubmitBIapi() {

        ArrayList list = new ArrayList(map.values());
        if (list.size()==0){
            Toast.makeText(this, "Item not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(VerifyBillingInstructionsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(VerifyBillingInstructionsActivity.this);
                    webServices.verifyandSubmitBI(WebServices.ApiType.submitBI, invApproverequest);
                }
            }
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code)
    {
        switch (URL)
        {

            case submitBI:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {
                        GenerealResponse generealResponse = (GenerealResponse) response;

                        Toast.makeText(this, generealResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case projectlist:
                //  swipeRefreshLayout.setRefreshing(false);

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {

                        List list1 = new ArrayList();
                        ProjectListResponse projectListResponse = (ProjectListResponse) response;

                        list1 = projectListResponse.getProject_list();

                        for (int i = 0; i < list1.size(); i++) {

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name()+" ("+projectListResponse.getProject_list().get(i).getPc_code()+")",projectListResponse.getProject_list().get(i).getPc_code());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name()+" ( "+projectListResponse.getProject_list().get(i).getPc_code()+" )");
                        }

                        ArrayAdapter adapter = new ArrayAdapter(VerifyBillingInstructionsActivity.this, android.R.layout.simple_list_item_1, projectList);
                        sp_all_project_verify_bi.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case verifyBi:

                if(progressDialog!=null)
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if(response!=null){
                        VerifyBillingInstructionListResponse verifyBillingInstructionListResponse = (VerifyBillingInstructionListResponse) response;
                        List list = new ArrayList();
                        list = verifyBillingInstructionListResponse.getList();
                        tv_count_vbi.setText(String.valueOf(list.size()));
                        arrayList.clear();
                        for (int i=0;i<list.size();i++){

                            arrayList.add(new VerifyBillingInstructionModel(
                                    verifyBillingInstructionListResponse.getList().get(i).getPc_code(),
                                    verifyBillingInstructionListResponse.getList().get(i).getGroup_name(),
                                    verifyBillingInstructionListResponse.getList().get(i).getAssignment(),
                                    verifyBillingInstructionListResponse.getList().get(i).getBill_to(),
                                    verifyBillingInstructionListResponse.getList().get(i).getBilling_address(),
                                    verifyBillingInstructionListResponse.getList().get(i).getReference_no(),
                                    verifyBillingInstructionListResponse.getList().get(i).getKind_attention(),
                                    verifyBillingInstructionListResponse.getList().get(i).getRegion(),
                                    verifyBillingInstructionListResponse.getList().get(i).getPlace(),
                                    verifyBillingInstructionListResponse.getList().get(i).getGstin_no(),
                                    verifyBillingInstructionListResponse.getList().get(i).getPan_no_customer(),
                                    verifyBillingInstructionListResponse.getList().get(i).getAmount(),
                                    verifyBillingInstructionListResponse.getList().get(i).getGst_percentage(),
                                    verifyBillingInstructionListResponse.getList().get(i).getMonth(),
                                    verifyBillingInstructionListResponse.getList().get(i).getDescription(),
                                    verifyBillingInstructionListResponse.getList().get(i).getHSN_SAC(),
                                    verifyBillingInstructionListResponse.getList().get(i).getParticular(),
                                    verifyBillingInstructionListResponse.getList().get(i).getState_supply(),
                                    verifyBillingInstructionListResponse.getList().get(i).getTransaction_type(),
                                    verifyBillingInstructionListResponse.getList().get(i).getId()));

                        };

                        if(arrayList.size()==0){
                            ll_no_data_BI.setVisibility(View.VISIBLE);
                        }else {
                            ll_no_data_BI.setVisibility(View.GONE);

                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_verify_BI_Data_list);
                        VerifyBillingInstructionAdapter adapter = new VerifyBillingInstructionAdapter(arrayList,this,false);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(adapter);

                    }else{
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.edt_from_verify_BI:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(VerifyBillingInstructionsActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from_verify_BI.setText(sdf.format(mcurrentDate.getTime()));
                        mDay = dayOfMonth;
                        mMonth = month;
                        mYear = year;

                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DATE)){
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
                    }
                };
                monthDatePickerDialog.setTitle("Select Month And Year");
                monthDatePickerDialog.show();
                break;
            case R.id.btn_verifySubmitBI:
                callsubmitBIapi();
                break;

        }
    }

    @Override
    public void onClickitem(String position, int item, String id)
    {

        if(item==1){
            openBIallDataDialog(position,id);
        }else if(item==2){
            map.put(position,id);
        }else if(item==3){
            map.remove(position);
        }else if(item==4){

        }
    }


    private void openBIallDataDialog(String position,String id) {
        final Dialog dialog = new Dialog(this);

        System.out.println("id print"+ id);
        dialog.setContentView(R.layout.dialoge_division_bi_verification);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView BI_back_toolbar = dialog.findViewById(R.id.BI_back_toolbar);
        ImageView iv_dia_BI_edit = dialog.findViewById(R.id.iv_dia_BI_edit);
        ImageView tv_dia_BI_delete = dialog.findViewById(R.id.tv_dia_BI_delete);
        ImageView iv_dia_BI_upload = dialog.findViewById(R.id.iv_dia_BI_upload);
        ImageView iv_dia_BI_status = dialog.findViewById(R.id.iv_dia_BI_status);

        TextView tv_dia_BI_pcCode = dialog.findViewById(R.id.tv_dia_BI_pcCode);
        TextView tv_dia_BI_group = dialog.findViewById(R.id.tv_dia_BI_group);
        TextView tv_dia_BI_assignment = dialog.findViewById(R.id.tv_dia_BI_assignment);
        TextView tv_dia_BI_billTo = dialog.findViewById(R.id.tv_dia_BI_billTo);
        TextView tv_dia_BI_billingAddress = dialog.findViewById(R.id.tv_dia_BI_billingAddress);
        TextView tv_dia_BI_referenceNum = dialog.findViewById(R.id.tv_dia_BI_referenceNum);
        TextView tv_dia_BI_kindAttention = dialog.findViewById(R.id.tv_dia_BI_kindAttention);
        TextView tv_dia_BI_region = dialog.findViewById(R.id.tv_dia_BI_region);
        TextView tv_dia_BI_place = dialog.findViewById(R.id.tv_dia_BI_place);
        TextView tv_dia_BI_gstinNO = dialog.findViewById(R.id.tv_dia_BI_gstinNO);
        TextView tv_dia_BI_panOfCustomer = dialog.findViewById(R.id.tv_dia_BI_panOfCustomer);
        TextView tv_dia_BI_taxableAmount = dialog.findViewById(R.id.tv_dia_BI_taxableAmount);
        TextView tv_dia_BI_gstRate = dialog.findViewById(R.id.tv_dia_BI_gstRate);
        TextView tv_dia_BI_forMonth = dialog.findViewById(R.id.tv_dia_BI_forMonth);
        TextView tv_dia_BI_description = dialog.findViewById(R.id.tv_dia_BI_description);
        TextView tv_dia_BI_hsnSac = dialog.findViewById(R.id.tv_dia_BI_hsnSac);
        TextView tv_dia_BI_particulars = dialog.findViewById(R.id.tv_dia_BI_particulars);
        TextView tv_dia_BI_stateOfSupply = dialog.findViewById(R.id.tv_dia_BI_stateOfSupply);
        TextView tv_dia_BI_transactionType = dialog.findViewById(R.id.tv_dia_BI_transactionType);
        AppCompatButton btn_dia_BI_submitBI=dialog.findViewById(R.id.btn_dia_BI_submitBI);

        VerifyBillingInstructionModel verifyBillingInstructionModel = arrayList.get(Integer.parseInt(position));
        tv_dia_BI_pcCode.setText(verifyBillingInstructionModel.getBIPcCode());
        tv_dia_BI_group.setText(verifyBillingInstructionModel.getBIgroup());
        tv_dia_BI_assignment.setText(verifyBillingInstructionModel.getBIassigmnent());
        tv_dia_BI_billTo.setText(verifyBillingInstructionModel.getBIbillTO());
        tv_dia_BI_billingAddress.setText(verifyBillingInstructionModel.getBIbillingAdress());
        tv_dia_BI_referenceNum.setText(verifyBillingInstructionModel.getBIrefrenceNumber());
        tv_dia_BI_kindAttention.setText(verifyBillingInstructionModel.getBIkindAttention());
        tv_dia_BI_region.setText(verifyBillingInstructionModel.getBIregion());
        tv_dia_BI_place.setText(verifyBillingInstructionModel.getBIplace());
        tv_dia_BI_gstinNO.setText(verifyBillingInstructionModel.getBIgstinNo());
        tv_dia_BI_panOfCustomer.setText(verifyBillingInstructionModel.getBIpanOfCustomer());
        tv_dia_BI_taxableAmount.setText(verifyBillingInstructionModel.getBItaxableAmount());
        tv_dia_BI_gstRate.setText(verifyBillingInstructionModel.getBIgstRate());
        tv_dia_BI_forMonth.setText(verifyBillingInstructionModel.getBIforMonth());
        tv_dia_BI_description.setText(verifyBillingInstructionModel.getBIdescription());
        tv_dia_BI_hsnSac.setText(verifyBillingInstructionModel.getBIhsnSac());
        tv_dia_BI_particulars.setText(verifyBillingInstructionModel.getBIparticulars());
        tv_dia_BI_stateOfSupply.setText(verifyBillingInstructionModel.getBIstateOfSupplyCode());
        tv_dia_BI_transactionType.setText(verifyBillingInstructionModel.getBItransactionType());

        BI_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        iv_dia_BI_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditBIDialog();
            }
        });

        btn_dia_BI_submitBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  callsubmitBIapi(id);
            }
        });

    }

    private void openEditBIDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_bi_edit);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        ImageView BI_edit_back_toolbar =dialog.findViewById(R.id.BI_edit_back_toolbar);
        Spinner sp_Bi_edit_ProjectCode = dialog.findViewById(R.id.sp_Bi_edit_ProjectCode);
        Spinner sp_Bi_edit_division = dialog.findViewById(R.id.sp_Bi_edit_division);
        Spinner sp_Bi_edit_region = dialog.findViewById(R.id.sp_Bi_edit_region);

        EditText ed_Bi_edit_invoiceDate = dialog.findViewById(R.id.ed_Bi_edit_invoiceDate);
        EditText ed_Bi_edit_group = dialog.findViewById(R.id.ed_Bi_edit_group);
        EditText ed_Bi_edit_billTo = dialog.findViewById(R.id.ed_Bi_edit_billTo);
        EditText ed_Bi_edit_assignment = dialog.findViewById(R.id.ed_Bi_edit_assignment);
        EditText ed_Bi_edit_referenceNo = dialog.findViewById(R.id.ed_Bi_edit_referenceNo);
        EditText ed_Bi_edit_kindAttention = dialog.findViewById(R.id.ed_Bi_edit_kindAttention);
        EditText ed_Bi_edit_gstinNo = dialog.findViewById(R.id.ed_Bi_edit_gstinNo);
        EditText ed_Bi_edit_panOfCustomer = dialog.findViewById(R.id.ed_Bi_edit_panOfCustomer);
        EditText ed_Bi_edit_place = dialog.findViewById(R.id.ed_Bi_edit_place);
        EditText ed_Bi_edit_forMonth = dialog.findViewById(R.id.ed_Bi_edit_forMonth);
        EditText ed_Bi_edit_supplyCode = dialog.findViewById(R.id.ed_Bi_edit_supplyCode);
        EditText ed_Bi_edit_transactionType = dialog.findViewById(R.id.ed_Bi_edit_transactionType);
        EditText ed_Bi_edit_amount = dialog.findViewById(R.id.ed_Bi_edit_amount);
        EditText ed_Bi_edit_gstPercentage = dialog.findViewById(R.id.ed_Bi_edit_gstPercentage);
        EditText ed_Bi_edit_hsnSac = dialog.findViewById(R.id.ed_Bi_edit_hsnSac);
        EditText ed_Bi_edit_particulars = dialog.findViewById(R.id.ed_Bi_edit_particulars);
        EditText ed_Bi_edit_billingAddress = dialog.findViewById(R.id.ed_Bi_edit_billingAddress);
        EditText ed_Bi_edit_description = dialog.findViewById(R.id.ed_Bi_edit_description);

        BI_edit_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);

        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);
        }
    };



}