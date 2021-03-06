package com.proteam.propcms.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.proteam.propcms.Adapters.CtnrListAdapter;
import com.proteam.propcms.Models.Approvalmodel;
import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.VctDeleteResponse;
import com.proteam.propcms.Response.approvalresponse.ApprovalList;
import com.proteam.propcms.Utils.OnClick;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CostTransferNoteRequestApprovalActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener, OnClick, PickiTCallbacks {
    ImageView mToolbar;
    EditText edt_from,edt_search;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_ctnra;
    RecyclerView rv_ctrn_Data_list;
    TextView tv_raise_indent_total_item;
    ProgressDialog progressDialog;
    ArrayList<Approvalmodel> arrayList = new ArrayList<Approvalmodel>();
    ArrayList<CtrnDataModel> arrayList2 = new ArrayList<CtrnDataModel>();
    Map map = new HashMap();
    Map projectmap = new HashMap();
    List projectList = new ArrayList();
    ImageView iv_clear_ctrn;
    Button btn_approve,btn_reject,btn_ctrn_search;
    ArrayList<CtrnDataModel> temp = new ArrayList();
    ArrayList<CtrnDataModel> filterarraylist = new ArrayList<CtrnDataModel>();
    CheckBox ch_action_ctrn;
    File originalFile;
    String billId;
    PickiT pickiT;
    LinearLayout ll_no_data_ctnr;
    SharedPreferences.Editor editor;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_transfer_note_request_approval);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());
        pickiT = new PickiT(this, this, this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user = sharedPreferences.getString("userid", null);


        initialize();
        sp_all_project_ctnra.setOnItemSelectedListener(OnCatSpinnerCL);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CostTransferNoteRequestApprovalActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {
        ll_no_data_ctnr=findViewById(R.id.ll_no_data_ctnr);

        btn_ctrn_search = findViewById(R.id.btn_ctrn_search);
        btn_ctrn_search.setOnClickListener(this);

        btn_reject = findViewById(R.id.ctrn_reject_btn);
        btn_reject.setOnClickListener(this);
        btn_approve = findViewById(R.id.ctrn_approve_btn);
        btn_approve.setOnClickListener(this);
        ch_action_ctrn = findViewById(R.id.ch_action_ctrn);
        iv_clear_ctrn = findViewById(R.id.iv_clear_ctrn);
        iv_clear_ctrn.setOnClickListener(this);
        edt_from=findViewById(R.id.edt_from1);
        edt_search=findViewById(R.id.edt_search2);
        tv_raise_indent_total_item = findViewById(R.id.tv_raise_indent_total_item);
        edt_from.setOnClickListener(this);
        sp_all_project_ctnra=findViewById(R.id.sp_all_project_ctnra);
        rv_ctrn_Data_list=findViewById(R.id.rv_ctrn_Data_list);
        callapprovalApi();

        ch_action_ctrn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    adaptorclass(true);
                }else {
                    adaptorclass(false);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.edt_from1:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(CostTransferNoteRequestApprovalActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        mDay = dayOfMonth;
                        mMonth = month+1;
                        mYear = year;
                        edt_from.setText(mYear+"-"+"0"+mMonth);

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
                // monthDatePickerDialog.getDatePicker().setMaxDate(mcurrentDate.getTimeInMillis());
                // monthDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                break;

            case R.id.ctrn_approve_btn:
                callapproveApi();
                break;

            case R.id.ctrn_reject_btn:
                callRejectApi();
                break;

            case R.id.btn_ctrn_search:



                    if(!edt_from.getText().toString().equals("")){
                        Searchlist();
                    }else {
                        // Toast.makeText(this, "Please Select month", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select month", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }


                break;

            case R.id.iv_clear_ctrn:
                finish();
                startActivity(getIntent());
                break;


        }
    }


    /////////////////////////////////APis calling /////////////////////////

    private void callapprovalApi() {

        progressDialog = new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(CostTransferNoteRequestApprovalActivity.this);
                webServices.invoiceapprovalapi(WebServices.ApiType.invoicemod);
            } else {

            }
        }
    }

    private void callapproveApi() {

        ArrayList list = new ArrayList(map.values());
        if (list.size()==0){
            Toast.makeText(this, "Invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(CostTransferNoteRequestApprovalActivity.this);
                    webServices.approvectrncall(WebServices.ApiType.approve, invApproverequest);
                }
            }
        }

    }

    private void callProjectListApi() {

        ProjectListModel projectListModel = new ProjectListModel("21");
        WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(CostTransferNoteRequestApprovalActivity.this);
        webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);

        /*progressDialog = new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);

        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                ProjectListModel projectListModel = new ProjectListModel("21");
                WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(CostTransferNoteRequestApprovalActivity.this);
                webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);
            }
        }*/

    }

    private void  callRejectApi() {

        ArrayList list = new ArrayList(map.values());

        if (list.size()==0){
            Toast.makeText(this, "Invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(CostTransferNoteRequestApprovalActivity.this);
                    webServices.rejectctrncall(WebServices.ApiType.approve,invApproverequest);
                }

            }
        }
    }

    private void callapproveindividualApi(String id) {

        ArrayList list = new ArrayList();
        list.add(id);
        if (list.size()==0){
            Toast.makeText(this, "Invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(CostTransferNoteRequestApprovalActivity.this);
                    webServices.approvectrncall(WebServices.ApiType.approve, invApproverequest);
                }
            }
        }

    }

    private void  callRejectindividualApi(String id) {

        ArrayList list = new ArrayList();
        list.add(id);

        if (list.size()==0){
            Toast.makeText(this, "invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(CostTransferNoteRequestApprovalActivity.this);
                    webServices.rejectctrncall(WebServices.ApiType.approve,invApproverequest);
                }

            }
        }
    }

    private void callupdatefileapi() {

        progressDialog=new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);

        if(progressDialog!=null)
        {
            if(!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();


                WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(CostTransferNoteRequestApprovalActivity.this);
                webServices.fileupload( WebServices.ApiType.pdfupload, originalFile,user,billId);


            }
        }
    }


    ///////////////////////Api respone ////////////////////////////////////

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {

        switch (URL) {

            case invoicemod:
               // swipeRefreshLayout.setRefreshing(false);
                if(progressDialog!=null)
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if(response!=null){
                        ApprovalList approvalList = (ApprovalList) response;
                        List list = new ArrayList();
                        list = approvalList.getList();
                        tv_raise_indent_total_item.setText(String.valueOf(list.size()));
                        arrayList.clear();
                        for (int i=0;i<list.size();i++){

                            /*arrayList.add(new Approvalmodel(approvalList.getList().get(i).getId(),
                                    approvalList.getList().get(i).getCtn_no(),
                                    approvalList.getList().get(i).getFinancial_year(),
                                    approvalList.getList().get(i).getMonth(),
                                    approvalList.getList().get(i).getFrom_pc_code(),
                                    approvalList.getList().get(i).getTo_pc_code(),
                                    approvalList.getList().get(i).getExpense_category(),
                                    approvalList.getList().get(i).getExpense_type(),
                                    approvalList.getList().get(i).getAmount(),
                                    approvalList.getList().get(i).getRemarks(),
                                    approvalList.getList().get(i).getVerfication_status(),
                                    approvalList.getList().get(i).getUser_id(),
                                    approvalList.getList().get(i).getUpload_user_id(),
                                    approvalList.getList().get(i).getInvoice_approver(),
                                    approvalList.getList().get(i).getManager_approve(),
                                    approvalList.getList().get(i).getLatest_uploaded(),
                                    approvalList.getList().get(i).getLatest_update_status(),
                                    approvalList.getList().get(i).getCreated_on(),
                                    approvalList.getList().get(i).getUpdated_on(),
                                    approvalList.getList().get(i).getFlag(),
                                    approvalList.getList().get(i).getPc_code(),
                                    approvalList.getList().get(i).getExpense_type_name(),
                                    approvalList.getList().get(i).getFrom_pc_code_name(),
                                    approvalList.getList().get(i).getTo_pc_code_name()));*/

                            arrayList2.add( new CtrnDataModel(approvalList.getList().get(i).getCtn_no(),
                                            approvalList.getList().get(i).getMonth(),
                                            approvalList.getList().get(i).getExpense_type_name(),
                                            approvalList.getList().get(i).getFrom_pc_code_name(),
                                            approvalList.getList().get(i).getTo_pc_code_name(),
                                            approvalList.getList().get(i).getAmount(),
                                            approvalList.getList().get(i).getRemarks(),
                                            approvalList.getList().get(i).getId())
                                    );
                        };

                        if(arrayList2.size()==0){
                            ll_no_data_ctnr.setVisibility(View.VISIBLE);
                        }else {
                            ll_no_data_ctnr.setVisibility(View.GONE);

                        }
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_ctrn_Data_list);
                        CtnrListAdapter adapter = new CtnrListAdapter(arrayList2,this,false);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(adapter);

                        callProjectListApi();

                    }else{
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case approve:

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

                        List list = new ArrayList();
                        ProjectListResponse projectListResponse = (ProjectListResponse) response;

                        list = projectListResponse.getProject_list();

                        for (int i = 0; i < list.size(); i++) {

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name()+" ( "+projectListResponse.getProject_list().get(i).getPc_code()+" )",projectListResponse.getProject_list().get(i).getPc_code());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name()+" ( "+projectListResponse.getProject_list().get(i).getPc_code()+" )");

                   }

                        ArrayAdapter adapter = new ArrayAdapter(CostTransferNoteRequestApprovalActivity.this, android.R.layout.simple_list_item_1, projectList);
                        sp_all_project_ctnra.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case pdfupload:


                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {


                        VctDeleteResponse generealResponse = (VctDeleteResponse) response;

                        Toast.makeText(this, generealResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());

                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

        }

        }

    private void filter(String text) {

        if(text.equals("")){
            adaptorclass(false);
        }else {
           int count = arrayList2.size();
            temp.clear();
            for (int i=0;i<arrayList2.size();i++){

                if(arrayList2.get(i).getCtnrFromPcCode().toLowerCase().contains(text.toLowerCase()) || arrayList2.get(i).getCtnrCtn().toLowerCase().contains(text.toLowerCase()) || arrayList2.get(i).getCtnrExpenseType().toLowerCase().contains(text.toLowerCase())){

                    temp.add(new CtrnDataModel(arrayList2.get(i).getCtnrCtn(),
                            arrayList2.get(i).getCtnrMonth(),
                            arrayList2.get(i).getCtnrExpenseType(),
                            arrayList2.get(i).getCtnrFromPcCode(),
                            arrayList2.get(i).getCtnrToPcCode(),
                            arrayList2.get(i).getCtnrTransferCost(),
                            arrayList2.get(i).getCtnrRemarks(),
                            arrayList2.get(i).getId()

                    ));
                }
            }

            if(temp.size()==0){
                ll_no_data_ctnr.setVisibility(View.VISIBLE);
            }else {
                ll_no_data_ctnr.setVisibility(View.GONE);

            }

                tv_raise_indent_total_item.setText(String.valueOf(temp.size()));
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_ctrn_Data_list);
                CtnrListAdapter adapter = new CtnrListAdapter(temp, this, false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);

        }

    }

    private void opengcadminDialog(String position,String id) {
        final Dialog dialog = new Dialog(this);

        System.out.println("id print"+ id);
        dialog.setContentView(R.layout.dialoge_ctnr_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout upload = dialog.findViewById(R.id.ll_ctrn_uploaddock);
        ImageView iv_dia_edit = dialog.findViewById(R.id.iv_dia_edit);
        ImageView iv_dia_delete = dialog.findViewById(R.id.iv_dia_delete);
        ImageView iv_dia_upload = dialog.findViewById(R.id.iv_dia_upload);
        ImageView iv_dia_status = dialog.findViewById(R.id.iv_dia_status);
        ImageView back_toolbar = dialog.findViewById(R.id.back_toolbar2);
        TextView tv_dia_ctn = dialog.findViewById(R.id.tv_dia_ctn);
        TextView tv_dia_month = dialog.findViewById(R.id.tv_dia_month);
        TextView tv_dia_directExpense = dialog.findViewById(R.id.tv_dia_directExpense);
        TextView tv_dia_fromPcCode = dialog.findViewById(R.id.tv_dia_fromPcCode);
        TextView tv_dia_ToPcCode = dialog.findViewById(R.id.tv_dia_TpPcCode);
        TextView tv_dia_transferCost = dialog.findViewById(R.id.tv_dia_transferCost);
        TextView tv_dia_Remarks = dialog.findViewById(R.id.tv_dia_Remarks);
        Button btn_dia_approve = dialog.findViewById(R.id.btn_dia_approve);
        Button btn_dia_reject = dialog.findViewById(R.id.btn_dia_reject);

        billId = arrayList2.get(Integer.parseInt(position)).getId();
        tv_dia_ctn.setText(arrayList2.get(Integer.parseInt(position)).getCtnrCtn());
        tv_dia_month.setText(arrayList2.get(Integer.parseInt(position)).getCtnrMonth());
        tv_dia_directExpense.setText(arrayList2.get(Integer.parseInt(position)).getCtnrExpenseType());
        tv_dia_fromPcCode.setText(arrayList2.get(Integer.parseInt(position)).getCtnrFromPcCode());
        tv_dia_ToPcCode.setText(arrayList2.get(Integer.parseInt(position)).getCtnrToPcCode());

        float amount = Float.parseFloat(arrayList2.get(Integer.parseInt(position)).getCtnrTransferCost());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);

        tv_dia_transferCost.setText(moneyString);

        tv_dia_Remarks.setText(arrayList2.get(Integer.parseInt(position)).getCtnrRemarks());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean permition =  checkPermission();

                if(permition){
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), 1);

                }else {
                    requestPermission();
                }

            }
        });

        back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        iv_dia_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openeditDialog(Integer.parseInt(position));
            }
        });

        btn_dia_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogeApprove(id);
            }
        });

        btn_dia_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogeReject(id);

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {

            if (data != null) {
                Uri uri = data.getData();
                String ffs = uri.getPath();

                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);

            }
        }
    }

    private void adaptorclass(Boolean check) {

        if(arrayList2.size()==0){
            ll_no_data_ctnr.setVisibility(View.VISIBLE);
        }else {
            ll_no_data_ctnr.setVisibility(View.GONE);

        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_ctrn_Data_list);
        CtnrListAdapter adapter = new CtnrListAdapter(arrayList2,this, check);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void openeditDialog(int position) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_edit_verifycost_transfer);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    private void Searchlist() {

        String project_id ="";

        if(sp_all_project_ctnra.getSelectedItem()!=null){

            project_id = (String) projectmap.get(sp_all_project_ctnra.getSelectedItem().toString());

        }

        for (int i=0;i<arrayList2.size();i++){

            // String project_id = "365";

            String daydate = edt_from.getText().toString().trim();

            if(arrayList2.get(i).getId().equalsIgnoreCase(project_id) || arrayList2.get(i).getCtnrMonth().equalsIgnoreCase(daydate)){

                filterarraylist.add(new CtrnDataModel(arrayList2.get(i).getCtnrCtn(),
                        arrayList2.get(i).getCtnrMonth(),
                        arrayList2.get(i).getCtnrExpenseType(),
                        arrayList2.get(i).getCtnrFromPcCode(),
                        arrayList2.get(i).getCtnrToPcCode(),
                        arrayList2.get(i).getCtnrTransferCost(),
                        arrayList2.get(i).getCtnrRemarks(),
                        arrayList2.get(i).getId()

                ));
            }

            if(filterarraylist.size()==0){
                ll_no_data_ctnr.setVisibility(View.VISIBLE);
            }else {
                ll_no_data_ctnr.setVisibility(View.GONE);

            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_ctrn_Data_list);
            CtnrListAdapter adapter = new CtnrListAdapter(filterarraylist,this,false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
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

    @Override
    public void onClickitem(String position, int item, String id) {

        if(item==1) {
            opengcadminDialog(position,id);
        }else if(item==2){
            openeditDialog(Integer.parseInt(position));
        }else if(item==3){
            map.put(position,id);
        }else if(item==4){
            map.remove(position);
        }
    }

    public void openDialogeApprove(String re) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CostTransferNoteRequestApprovalActivity.this);
        builder.setTitle("Alert");
        builder.setMessage("Are You Sure Want to Approve?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                callapproveindividualApi(re);
                dialog.cancel();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    public void openDialogeReject(String re) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CostTransferNoteRequestApprovalActivity.this);
        builder.setTitle("Alert");
        builder.setMessage("Are You Sure Want to Reject?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                callRejectindividualApi(re);
                dialog.dismiss();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {

        // originalFile = path;
        originalFile = new File(path);
        callupdatefileapi();
        System.out.println("paths----"+path);

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
            // requestPermission();
        } else {
            int result = ContextCompat.checkSelfPermission(CostTransferNoteRequestApprovalActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(CostTransferNoteRequestApprovalActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(CostTransferNoteRequestApprovalActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, 123);
        }
    }
}