package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.proteam.propcms.Request.BillingUpdaterequest;
import com.proteam.propcms.Request.DivisionListModel;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.DivisionListResponse;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
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
    List pccode = new ArrayList();
    List divisionList = new ArrayList();
    Map divisionmap = new HashMap();

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
        tv_count_vbi=findViewById(R.id.tv_count_vbi);
        rv_verify_BI_Data_list=findViewById(R.id.rv_verify_BI_Data_list);
        ll_no_data_BI=findViewById(R.id.ll_no_data_BI);

        edt_from_verify_BI=findViewById(R.id.edt_from_verify_BI);
        edt_from_verify_BI.setOnClickListener(this);
        sp_all_project_verify_bi=findViewById(R.id.sp_all_project_verify_bi);

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

    private void calldevisionapi() {

            //DivisionListModel divisionListModel = new DivisionListModel("14");
           // WebServices<DivisionListResponse> webServices = new WebServices<DivisionListResponse>(VerifyBillingInstructionsActivity.this);
           // webServices.divisionlist(WebServices.ApiType.divisionlist,divisionListModel);

        progressDialog = new ProgressDialog(VerifyBillingInstructionsActivity.this);

        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                DivisionListModel divisionListModel = new DivisionListModel("21");
                WebServices<DivisionListResponse> webServices = new WebServices<DivisionListResponse>(VerifyBillingInstructionsActivity.this);
                webServices.divisionlist(WebServices.ApiType.divisionlist,divisionListModel);
            }
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code)
    {
        switch (URL)
        {
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

                            pccode.add(verifyBillingInstructionListResponse.getList().get(i).getPc_code());

                            arrayList.add(new VerifyBillingInstructionModel(
                                    verifyBillingInstructionListResponse.getList().get(i).getInvoice_date(),
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
                                    verifyBillingInstructionListResponse.getList().get(i).getId(),
                                    verifyBillingInstructionListResponse.getList().get(i).getCompany_id(), verifyBillingInstructionListResponse.getList().get(i).getProject_id(),
                                    verifyBillingInstructionListResponse.getList().get(i).getInvoice_no(),
                                    verifyBillingInstructionListResponse.getList().get(i).getDivision_id(),
                                    verifyBillingInstructionListResponse.getList().get(i).getTotal_amount(),
                                    verifyBillingInstructionListResponse.getList().get(i).getGst_month()));

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

            case divisionlist:
                //  swipeRefreshLayout.setRefreshing(false);

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {

                        List list = new ArrayList();
                        DivisionListResponse divisionListResponse = (DivisionListResponse) response;

                        list = divisionListResponse.getDivision_list();

                        for (int i = 0; i < list.size(); i++) {


                            divisionList.add(divisionListResponse.getDivision_list().get(i).getDivision_name());
                            divisionmap.put(divisionListResponse.getDivision_list().get(i).getDivision_name(),divisionListResponse.getDivision_list().get(i).getDivision_id());
                        }


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case update:
                //  swipeRefreshLayout.setRefreshing(false);

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {


                        GenerealResponse generealResponse = (GenerealResponse) response;

                        Toast.makeText(this, generealResponse.getStatus(), Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
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
     /*       case R.id.temp_btn_Bi:
                openBIallDataDialog();
                break;*/

        }
    }

    @Override
    public void onClickitem(String value, int item, String id)
    {

        if(item==1){
            openBIallDataDialog(value);
        }else if(item==2){

        }else if(item==3){

        }else if(item==4){

        }
    }


    private void openBIallDataDialog(String position) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_bi_verification);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout ll_edit2 = dialog.findViewById(R.id.ll_edit2);
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

        ll_edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditBIDialog(position);
            }
        });
    }

    private void openEditBIDialog(String position) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_bi_edit);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();

        calldevisionapi();

        List regions= new ArrayList();
        regions.add("East");regions.add("West");regions.add("North");regions.add("South");

        Button btn_dia_BI_submitBI = dialog.findViewById(R.id.btn_dia_BI_submitBI);
        ImageView BI_edit_back_toolbar =dialog.findViewById(R.id.BI_edit_back_toolbar);
        Spinner sp_Bi_edit_ProjectCode = dialog.findViewById(R.id.sp_Bi_edit_ProjectCode);
        Spinner sp_Bi_edit_division = dialog.findViewById(R.id.sp_Bi_edit_division);
        Spinner sp_Bi_edit_region = dialog.findViewById(R.id.sp_Bi_edit_region);


        ArrayAdapter adapter = new ArrayAdapter(VerifyBillingInstructionsActivity.this, android.R.layout.simple_list_item_1, pccode);
        sp_Bi_edit_ProjectCode.setAdapter(adapter);

        sp_Bi_edit_ProjectCode.post(new Runnable() {
            public void run() {
                sp_Bi_edit_ProjectCode.setSelection(0);
            }
        });

        //sp_Bi_edit_ProjectCode.setSelection(1);
        sp_Bi_edit_ProjectCode.setSelection(0,true);
        ArrayAdapter adapter2 = new ArrayAdapter(VerifyBillingInstructionsActivity.this, android.R.layout.simple_list_item_1, regions);
        sp_Bi_edit_region.setAdapter(adapter2);
        sp_Bi_edit_region.setSelection(0);

        ArrayAdapter adapter3 = new ArrayAdapter(VerifyBillingInstructionsActivity.this, android.R.layout.simple_list_item_1, divisionList);
        sp_Bi_edit_division.setAdapter(adapter3);



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


        VerifyBillingInstructionModel verifyBillingInstructionModel = arrayList.get(Integer.parseInt(position));
        ed_Bi_edit_invoiceDate.setText(verifyBillingInstructionModel.getInvoicedate());
        ed_Bi_edit_group.setText(verifyBillingInstructionModel.getBIgroup());
        ed_Bi_edit_billTo.setText(verifyBillingInstructionModel.getBIbillTO());
        ed_Bi_edit_assignment.setText(verifyBillingInstructionModel.getBIassigmnent());
        ed_Bi_edit_referenceNo.setText(verifyBillingInstructionModel.getBIrefrenceNumber());
        ed_Bi_edit_kindAttention.setText(verifyBillingInstructionModel.getBIkindAttention());
        ed_Bi_edit_gstinNo.setText(verifyBillingInstructionModel.getBIgstinNo());
        ed_Bi_edit_panOfCustomer.setText(verifyBillingInstructionModel.getBIpanOfCustomer());
        ed_Bi_edit_place.setText(verifyBillingInstructionModel.getBIplace());
        ed_Bi_edit_forMonth.setText(verifyBillingInstructionModel.getBIforMonth());
        ed_Bi_edit_supplyCode.setText(verifyBillingInstructionModel.getBIstateOfSupplyCode());
        ed_Bi_edit_transactionType.setText(verifyBillingInstructionModel.getBItransactionType());
        ed_Bi_edit_amount.setText(verifyBillingInstructionModel.getBItaxableAmount());
        ed_Bi_edit_gstPercentage.setText(verifyBillingInstructionModel.getBIgstRate()+"%");
        ed_Bi_edit_hsnSac.setText(verifyBillingInstructionModel.getBIhsnSac());
        ed_Bi_edit_particulars.setText(verifyBillingInstructionModel.getBIparticulars());
        ed_Bi_edit_billingAddress.setText(verifyBillingInstructionModel.getBIbillingAdress());
        ed_Bi_edit_description.setText(verifyBillingInstructionModel.getBIdescription());

       //int posregion = adapter2.getPosition(verifyBillingInstructionModel.getBIregion().trim());
       sp_Bi_edit_region.setPrompt(verifyBillingInstructionModel.getBIregion().trim());

        btn_dia_BI_submitBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!ed_Bi_edit_invoiceDate.getText().toString().isEmpty() &&
                        !ed_Bi_edit_group.getText().toString().isEmpty() &&
                        !ed_Bi_edit_billTo.getText().toString().isEmpty() &&
                        !ed_Bi_edit_assignment.getText().toString().isEmpty() &&
                        !ed_Bi_edit_referenceNo.getText().toString().isEmpty() &&
                        !ed_Bi_edit_kindAttention.getText().toString().isEmpty() &&
                        !ed_Bi_edit_gstinNo.getText().toString().isEmpty() &&
                        !ed_Bi_edit_panOfCustomer.getText().toString().isEmpty() &&
                        !ed_Bi_edit_place.getText().toString().isEmpty() &&
                        !ed_Bi_edit_forMonth.getText().toString().isEmpty() &&
                        !ed_Bi_edit_supplyCode.getText().toString().isEmpty() &&
                        !ed_Bi_edit_transactionType.getText().toString().isEmpty() &&
                        !ed_Bi_edit_amount.getText().toString().isEmpty() &&
                        !ed_Bi_edit_gstPercentage.getText().toString().isEmpty() &&
                        !ed_Bi_edit_hsnSac.getText().toString().isEmpty() &&
                        !ed_Bi_edit_particulars.getText().toString().isEmpty() &&
                        !ed_Bi_edit_billingAddress.getText().toString().isEmpty() &&
                        !ed_Bi_edit_description.getText().toString().isEmpty()
                ){

                    progressDialog = new ProgressDialog(VerifyBillingInstructionsActivity.this);

                    if (progressDialog != null) {
                        if (!progressDialog.isShowing()) {
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            BillingUpdaterequest billingUpdaterequest = new BillingUpdaterequest(
                                    "14", verifyBillingInstructionModel.getId(),
                                    verifyBillingInstructionModel.getCompanyid(),
                                    verifyBillingInstructionModel.getProductid(),
                                    verifyBillingInstructionModel.getInvoicenumber(),
                                    verifyBillingInstructionModel.getDevisionid(),
                                    ed_Bi_edit_group.getText().toString(),
                                    ed_Bi_edit_assignment.getText().toString(),
                                    ed_Bi_edit_billTo.getText().toString(),
                                    ed_Bi_edit_billingAddress.getText().toString(),
                                    ed_Bi_edit_referenceNo.getText().toString(),
                                    "2",
                                    ed_Bi_edit_kindAttention.getText().toString(),
                                    ed_Bi_edit_gstinNo.getText().toString(),
                                    ed_Bi_edit_panOfCustomer.getText().toString(),
                                    ed_Bi_edit_place.getText().toString(),
                                    ed_Bi_edit_forMonth.getText().toString(),
                                    ed_Bi_edit_amount.getText().toString(),
                                    ed_Bi_edit_gstPercentage.getText().toString(),
                                    verifyBillingInstructionModel.getTotalamount(),
                                    ed_Bi_edit_description.getText().toString(),
                                    ed_Bi_edit_hsnSac.getText().toString(),
                                    ed_Bi_edit_particulars.getText().toString(),
                                    verifyBillingInstructionModel.getInvoicenumber(),
                                    verifyBillingInstructionModel.getInvoicedate(),
                                    verifyBillingInstructionModel.getGstmonth(),
                                    ed_Bi_edit_supplyCode.getText().toString(),
                                    ed_Bi_edit_transactionType.getText().toString());


                            WebServices<DivisionListResponse> webServices = new WebServices<DivisionListResponse>(VerifyBillingInstructionsActivity.this);
                            webServices.Updatebilling(WebServices.ApiType.update,billingUpdaterequest);
                        }
                    }


                }else {
                    Toast.makeText(VerifyBillingInstructionsActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }

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