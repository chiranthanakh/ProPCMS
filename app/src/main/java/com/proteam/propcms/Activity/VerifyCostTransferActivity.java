package com.proteam.propcms.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import com.proteam.propcms.Adapters.VerifyBillingInstructionAdapter;
import com.proteam.propcms.Adapters.VerifyCostTransferAdapter;
import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.Models.VerifyBillingInstructionModel;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.BillingUpdaterequest;
import com.proteam.propcms.Request.ExpenseRequest;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Request.VctDeleteRequest;
import com.proteam.propcms.Request.VctUpdateRequest;
import com.proteam.propcms.Response.DivisionListResponse;
import com.proteam.propcms.Response.ExpenseResponse;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.VctDeleteResponse;
import com.proteam.propcms.Response.VerifCostTransferResponse;
import com.proteam.propcms.Response.VerifyBillingInstructionListResponse;
import com.proteam.propcms.Response.VerifyBillingInstructionResponse;
import com.proteam.propcms.Response.VerifyCostTransferListResponse;
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

public class VerifyCostTransferActivity extends AppCompatActivity implements View.OnClickListener , OnResponseListener, OnClick, PickiTCallbacks {
    ImageView mToolbar,iv_clear_vct;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_vct;
    RecyclerView rv_vct_Data_list;
    EditText edt_from_vct,edt_search_vct;
    TextView tv_count_vct;

    ProgressDialog progressDialog;
    LinearLayout ll_no_data_VCT;
    AppCompatButton btn_verifySubmitCTN,btn_search_vct;
    CheckBox ch_ctn;
    Context context=this;
    String from_pc,to_pc,expense_list;
    File originalFile;

    Map projectmap = new HashMap();
    List projectList = new ArrayList();
    List pccodelist = new ArrayList();
    Map pccodemap = new HashMap();
    List expenselist = new ArrayList();
    Map expensemap = new HashMap();
    Map map = new HashMap();
    SharedPreferences.Editor editor;
    String user;
    String billid;
    PickiT pickiT;

    ArrayList<VerifyCostTransferModel> filterarraylist = new ArrayList<VerifyCostTransferModel>();
    ArrayList<VerifyCostTransferModel> arrayList = new ArrayList<VerifyCostTransferModel>();
    ArrayList<VerifyCostTransferModel> temp = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_cost_transfer);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user = sharedPreferences.getString("userid", null);

        pickiT = new PickiT(this, this, this);
        initialize();
        sp_all_project_vct.setOnItemSelectedListener(OnCatSpinnerCL);

        };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VerifyCostTransferActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {
        btn_search_vct=findViewById(R.id.btn_search_vct);
        btn_search_vct.setOnClickListener(this);
        edt_search_vct=findViewById(R.id.edt_search_vct);
        ch_ctn=findViewById(R.id.ch_ctn);
        iv_clear_vct=findViewById(R.id.iv_clear_vct);
        iv_clear_vct.setOnClickListener(this);
        btn_verifySubmitCTN=findViewById(R.id.btn_verifySubmitCTN);
        btn_verifySubmitCTN.setOnClickListener(this);
        ll_no_data_VCT=findViewById(R.id.ll_no_data_VCT);
        tv_count_vct=findViewById(R.id.tv_count_vct);
        edt_from_vct=findViewById(R.id.edt_from_vct);
        edt_from_vct.setOnClickListener(this);
        rv_vct_Data_list=findViewById(R.id.rv_vct_Data_list);
        sp_all_project_vct=findViewById(R.id.sp_all_project_vct);
        callProjectListApi();
        callVCTlistApi();

        ch_ctn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    private void adaptorclass(Boolean check) {

        if(arrayList.size()==0){
            ll_no_data_VCT.setVisibility(View.VISIBLE);
        }else {
            ll_no_data_VCT.setVisibility(View.GONE);

        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_vct_Data_list);
        VerifyCostTransferAdapter adapter = new VerifyCostTransferAdapter(arrayList,this,check);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code)
    {
        switch (URL)
        {
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

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name()+" ( "+projectListResponse.getProject_list().get(i).getPc_code()+" )",projectListResponse.getProject_list().get(i).getPc_code());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name()+" ( "+projectListResponse.getProject_list().get(i).getPc_code()+" )");
                            pccodelist.add(projectListResponse.getProject_list().get(i).getPc_code());
                            pccodemap.put(projectListResponse.getProject_list().get(i).getPc_code(),projectListResponse.getProject_list().get(i).getProject_id());
                        }

                        ArrayAdapter adapter = new ArrayAdapter(VerifyCostTransferActivity.this, android.R.layout.simple_list_item_1, projectList);
                        sp_all_project_vct.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;
            case verifyVct:

                if(progressDialog!=null)
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if(response!=null){
                        VerifyCostTransferListResponse verifyCostTransferListResponse = (VerifyCostTransferListResponse) response;
                        List list = new ArrayList();
                        list = verifyCostTransferListResponse.getList();
                        tv_count_vct.setText(String.valueOf(list.size()));
                        arrayList.clear();
                        for (int i=0;i<list.size();i++){

                            arrayList.add(new VerifyCostTransferModel(
                                    verifyCostTransferListResponse.getList().get(i).getCtn_no(),
                                    verifyCostTransferListResponse.getList().get(i).getMonth(),
                                    verifyCostTransferListResponse.getList().get(i).getExpense_type_name(),
                                    verifyCostTransferListResponse.getList().get(i).getFrom_pc_code_name(),
                                    verifyCostTransferListResponse.getList().get(i).getTo_pc_code_name(),
                                    verifyCostTransferListResponse.getList().get(i).getAmount(),
                                    verifyCostTransferListResponse.getList().get(i).getRemarks(),
                                    verifyCostTransferListResponse.getList().get(i).getId(),
                                    verifyCostTransferListResponse.getList().get(i).getUser_id(),
                                    verifyCostTransferListResponse.getList().get(i).getUpload_user_id()));


                        };

                        if(arrayList.size()==0){
                            ll_no_data_VCT.setVisibility(View.VISIBLE);
                        }else {
                            ll_no_data_VCT.setVisibility(View.GONE);

                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_vct_Data_list);
                        VerifyCostTransferAdapter adapter = new VerifyCostTransferAdapter(arrayList,this,false);
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

            case SubmitCTN:

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

            case deletectndata:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {
                        VctDeleteResponse vctDeleteResponse = (VctDeleteResponse) response;

                      //  Toast.makeText(this, vctDeleteResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, vctDeleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case updatevct:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {
                        VctDeleteResponse vctDeleteResponse = (VctDeleteResponse) response;

                        //  Toast.makeText(this, vctDeleteResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, vctDeleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;
            case expense:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {

                        List ex = new ArrayList();
                        ExpenseResponse expenseResponse = (ExpenseResponse) response;
                        ex = expenseResponse.getExpense_type_list();

                        for (int i=0;i<ex.size();i++){

                            expenselist.add(expenseResponse.getExpense_type_list().get(i).getExpense_type_name());
                            expensemap.put(expenseResponse.getExpense_type_list().get(i).getExpense_type_name(),expenseResponse.getExpense_type_list().get(i).getExpense_type_id());

                        }
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


    @Override
    public void onClickitem(String position, int item, String id)
    {
        if(item==1){
            opengAllDataDialog(position,id);
        }else if(item==2){
            map.put(position,id);
        }else if(item==3){
            map.remove(position);
        }else if(item==4){

        }
    }

    /////////////////////Calling API//////////////////////


    private void callSubmitCTNapi() {

        ArrayList list = new ArrayList(map.values());
        if (list.size()==0){
            Toast.makeText(this, "Item not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(VerifyCostTransferActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(VerifyCostTransferActivity.this);
                    webServices.verifyandSubmitCTN(WebServices.ApiType.SubmitCTN, invApproverequest);
                }
            }
        }

    }

    private void callDialogeSubmitCTNapi(String id) {

        ArrayList list = new ArrayList();
        list.add(id);
        if (list.size()==0){
            Toast.makeText(this, "Item not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(VerifyCostTransferActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(VerifyCostTransferActivity.this);
                    webServices.verifyandSubmitCTN(WebServices.ApiType.SubmitCTN, invApproverequest);
                }
            }
        }

    }

    private void callDialogueDeleteCTNapi(String id) {

        ArrayList list = new ArrayList();
        list.add(id);


            progressDialog = new ProgressDialog(VerifyCostTransferActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    VctDeleteRequest vctDeleteRequest = new VctDeleteRequest(id);
                    WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(VerifyCostTransferActivity.this);
                    webServices.DeleteCtn(WebServices.ApiType.deletectndata, vctDeleteRequest);
                }
            }


    }

    private void callExpensetypeList(String id) {

        ExpenseRequest expenseRequest = new ExpenseRequest(id,user);
        WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(VerifyCostTransferActivity.this);
        webServices.ExpensetypeList(WebServices.ApiType.expense, expenseRequest);

       /* progressDialog = new ProgressDialog(VerifyCostTransferActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                ExpenseRequest expenseRequest = new ExpenseRequest("12",user);
                WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(VerifyCostTransferActivity.this);
                webServices.ExpensetypeList(WebServices.ApiType.expense, expenseRequest);
            }
        }*/


    }

    private void callVCTlistApi() {

        progressDialog = new ProgressDialog(VerifyCostTransferActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                UserIdRequest userIdRequest = new UserIdRequest("14");
                WebServices<VerifCostTransferResponse> webServices = new WebServices<VerifCostTransferResponse>(VerifyCostTransferActivity.this);
                webServices.VerifyVCTDataList(WebServices.ApiType.verifyVct,userIdRequest);
            } else {

            }
        }
    }

    private void callProjectListApi() {

        ProjectListModel projectListModel = new ProjectListModel("14");
        WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(VerifyCostTransferActivity.this);
        webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);

        progressDialog = new ProgressDialog(VerifyCostTransferActivity.this);

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

    private void callupdatefileapi() {

        progressDialog=new ProgressDialog(VerifyCostTransferActivity.this);

        if(progressDialog!=null)
        {
            if(!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();


                WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(VerifyCostTransferActivity.this);
                webServices.fileupload( WebServices.ApiType.pdfupload, originalFile,user,billid);


            }

        }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.edt_from_vct:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(VerifyCostTransferActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from_vct.setText(sdf.format(mcurrentDate.getTime()));
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

            case R.id.btn_verifySubmitCTN:
                callSubmitCTNapi();
                break;
            case R.id.iv_clear_vct:
                finish();
                startActivity(getIntent());
                break;
            case R.id.btn_search_vct:

                if(sp_all_project_vct.getSelectedItem()!=null){

                    if(!edt_from_vct.getText().toString().equals("")){
                        Searchlist();
                    }else {
                        // Toast.makeText(this, "Please Select month", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select month", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }else {
                    //Toast.makeText(this, "Please Select project", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select project", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }

                break;


        }
    }


    private void Searchlist() {

        String project_id = (String) projectmap.get(sp_all_project_vct.getSelectedItem().toString());

        for (int i=0;i<arrayList.size();i++){

            // String project_id = "365";

            if(arrayList.get(i).getVctfromPcCodeName().equalsIgnoreCase(project_id) || arrayList.get(i).getVcttoPcCodeName().equalsIgnoreCase(project_id)){

                filterarraylist.add(new VerifyCostTransferModel(
                        arrayList.get(i).getVctCtn(),
                        arrayList.get(i).getVctmonth(),
                        arrayList.get(i).getVctexpenseTypeName(),
                        arrayList.get(i).getVctfromPcCodeName(),
                        arrayList.get(i).getVcttoPcCodeName(),
                        arrayList.get(i).getVctamount(),
                        arrayList.get(i).getVctremarks(),
                        arrayList.get(i).getId(),
                        arrayList.get(i).getVctuserId(),
                        arrayList.get(i).getVctuploadUserId()

                ));
            }

            if(filterarraylist.size()==0){
                ll_no_data_VCT.setVisibility(View.VISIBLE);
            }else {
                ll_no_data_VCT.setVisibility(View.GONE);

            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_vct_Data_list);
            VerifyCostTransferAdapter adapter = new VerifyCostTransferAdapter(filterarraylist,this,false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        edt_search_vct.addTextChangedListener(new TextWatcher() {
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


    private void filter(String text) {

        if(text.equals("")){
            adaptorclass(false);
        }else {
            int count = arrayList.size();
            temp.clear();
            for (int i=0;i<arrayList.size();i++){

                if(arrayList.get(i).getVctCtn().toLowerCase().contains(text.toLowerCase()) || arrayList.get(i).getVctfromPcCodeName().toLowerCase().contains(text.toLowerCase()) || arrayList.get(i).getVctexpenseTypeName().toLowerCase().contains(text.toLowerCase()) || arrayList.get(i).getVctmonth().toLowerCase().contains(text.toLowerCase())){

                    temp.add(new VerifyCostTransferModel(
                            arrayList.get(i).getVctCtn(),
                            arrayList.get(i).getVctmonth(),
                            arrayList.get(i).getVctexpenseTypeName(),
                            arrayList.get(i).getVctfromPcCodeName(),
                            arrayList.get(i).getVcttoPcCodeName(),
                            arrayList.get(i).getVctamount(),
                            arrayList.get(i).getVctremarks(),
                            arrayList.get(i).getId(),
                            arrayList.get(i).getVctuserId(),
                            arrayList.get(i).getVctuploadUserId()

                    ));
                }
            }

            if(temp.size()==0){
                ll_no_data_VCT.setVisibility(View.VISIBLE);
            }else {
                ll_no_data_VCT.setVisibility(View.GONE);

            }

            tv_count_vct.setText(String.valueOf(temp.size()));
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_vct_Data_list);
            VerifyCostTransferAdapter adapter = new VerifyCostTransferAdapter(temp, this, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
    };


    /////dialog/////
    private void opengAllDataDialog(String position,String id) {
        final Dialog dialog = new Dialog(this);
        System.out.println("id print"+ id);
        dialog.setContentView(R.layout.dialoge_division_verifycost_transfer);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout ll_crnra_upload = dialog.findViewById(R.id.ll_crnra_upload);
        LinearLayout ll_ctn_edit = dialog.findViewById(R.id.ll_ctn_edit);
        LinearLayout ll_vct_delete=dialog.findViewById(R.id.ll_vct_delete);
        TextView tv_dia_vct_ctn = dialog.findViewById(R.id.tv_dia_vct_ctn);
        TextView tv_dia_vct_month = dialog.findViewById(R.id.tv_dia_vct_month);
        TextView tv_dia_vct_directExpense = dialog.findViewById(R.id.tv_dia_vct_directExpense);
        TextView tv_dia_vct_fromPcCode = dialog.findViewById(R.id.tv_dia_vct_fromPcCode);
        TextView tv_dia_vct_TpPcCode = dialog.findViewById(R.id.tv_dia_vct_TpPcCode);
        TextView tv_dia_vct_transferCost = dialog.findViewById(R.id.tv_dia_vct_transferCost);
        TextView tv_dia_vct_Remarks = dialog.findViewById(R.id.tv_dia_vct_Remarks);

        ImageView back_toolbar_vct = dialog.findViewById(R.id.back_toolbar_vct);
        ImageView iv_dia_vct_edit = dialog.findViewById(R.id.iv_dia_vct_edit);
        ImageView iv_dia_vct_delete = dialog.findViewById(R.id.iv_dia_vct_delete);
        ImageView iv_dia_vct_upload = dialog.findViewById(R.id.iv_dia_vct_upload);
        ImageView iv_dia_vct_status = dialog.findViewById(R.id.iv_dia_vct_status);
        AppCompatButton btn_dia_verify_submit_ctn = dialog.findViewById(R.id.btn_dia_verify_submit_ctn);

        VerifyCostTransferModel verifyCostTransferModel = arrayList.get(Integer.parseInt(position));

        billid = verifyCostTransferModel.getId();
        from_pc = verifyCostTransferModel.getVctfromPcCodeName();
        to_pc = verifyCostTransferModel.getVcttoPcCodeName();
        expense_list = verifyCostTransferModel.getVctexpenseTypeName();

        callExpensetypeList(verifyCostTransferModel.getId());

        tv_dia_vct_ctn.setText(verifyCostTransferModel.getVctCtn());
        tv_dia_vct_month.setText(verifyCostTransferModel.getVctmonth());
        tv_dia_vct_directExpense.setText(verifyCostTransferModel.getVctexpenseTypeName());
        tv_dia_vct_fromPcCode.setText(verifyCostTransferModel.getVctfromPcCodeName());
        tv_dia_vct_TpPcCode.setText(verifyCostTransferModel.getVcttoPcCodeName());

        float amount = Float.parseFloat(arrayList.get(Integer.parseInt(position)).getVctamount());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);

        tv_dia_vct_transferCost.setText(moneyString);
        tv_dia_vct_Remarks.setText(verifyCostTransferModel.getVctremarks());

        ll_crnra_upload.setOnClickListener(new View.OnClickListener() {
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

        back_toolbar_vct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ll_ctn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                openEditDialog(position);
            }
        });

        btn_dia_verify_submit_ctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogSubmitBI(id);
            }
        });
        ll_vct_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogReject(id);
            }
        });
    }


    private void openEditDialog(String position) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_edit_verifycost_transfer);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        ImageView back_toolbar_ctn =dialog.findViewById(R.id.back_toolbar_ctn);

        TextView edt_edit_ctn = dialog.findViewById(R.id.edt_edit_ctn);
        TextView edt_edit_month = dialog.findViewById(R.id.edt_edit_month);
        Spinner sp_edit_fromPcCode = dialog.findViewById(R.id.sp_edit_fromPcCode);
        Spinner sp_edit_ToPcCode = dialog.findViewById(R.id.sp_edit_ToPcCode);
        Spinner sp_edit_expenseType = dialog.findViewById(R.id.sp_edit_expenseType);
        TextView edt_edit_transferCost = dialog.findViewById(R.id.edt_edit_transferCost);
        TextView edt_edit_remarks = dialog.findViewById(R.id.edt_edit_remarks);
        Button btn_dia_vct_update = dialog.findViewById(R.id.btn_dia_vct_update);

        VerifyCostTransferModel verifyCostTransferModel = arrayList.get(Integer.parseInt(position));

        edt_edit_ctn.setText(verifyCostTransferModel.getVctCtn());
        edt_edit_month.setText(verifyCostTransferModel.getVctmonth());
        edt_edit_transferCost.setText(verifyCostTransferModel.getVctamount());
        edt_edit_remarks.setText(verifyCostTransferModel.getVctremarks());

        ArrayAdapter adapter = new ArrayAdapter(VerifyCostTransferActivity.this, android.R.layout.simple_list_item_1, pccodelist);
        sp_edit_fromPcCode.setAdapter(adapter);
        sp_edit_fromPcCode.setOnItemSelectedListener(OnCatSpinnerCL1);

        ArrayAdapter adapter1 = new ArrayAdapter(VerifyCostTransferActivity.this, android.R.layout.simple_list_item_1, pccodelist);
        sp_edit_ToPcCode.setAdapter(adapter1);
        sp_edit_ToPcCode.setOnItemSelectedListener(OnCatSpinnerCL2);

        ArrayAdapter adapter2 = new ArrayAdapter(VerifyCostTransferActivity.this, android.R.layout.simple_list_item_1, expenselist);
        sp_edit_expenseType.setAdapter(adapter2);
        sp_edit_expenseType.setOnItemSelectedListener(OnCatSpinnerCL3);

        sp_edit_fromPcCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setText(from_pc);
                }else {
                    from_pc = sp_edit_fromPcCode.getSelectedItem().toString();
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setText(from_pc);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_edit_ToPcCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setText(to_pc);
                }else {
                    to_pc = sp_edit_ToPcCode.getSelectedItem().toString();
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setText(to_pc);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_edit_expenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setText(expense_list);
                }else {
                    expense_list = String.valueOf(expensemap.get(sp_edit_expenseType.getSelectedItem().toString()));
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setText(sp_edit_expenseType.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_dia_vct_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!edt_edit_ctn.getText().toString().isEmpty() &&
                        !edt_edit_month.getText().toString().isEmpty() &&
                        !edt_edit_transferCost.getText().toString().isEmpty() &&
                        !edt_edit_remarks.getText().toString().isEmpty()

                ){

                    progressDialog = new ProgressDialog(VerifyCostTransferActivity.this);

                    if (progressDialog != null) {
                        if (!progressDialog.isShowing()) {
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            VctUpdateRequest  vctUpdateRequest = new VctUpdateRequest(
                                    user,
                                    verifyCostTransferModel.getId(),
                                    verifyCostTransferModel.getVctmonth(),
                                    String.valueOf(pccodemap.get(from_pc)),
                                    String.valueOf(pccodemap.get(to_pc)),
                                    expense_list,
                                    edt_edit_transferCost.getText().toString().trim() ,
                                    edt_edit_remarks.getText().toString().trim());

                            WebServices<VctDeleteResponse> webServices = new WebServices<VctDeleteResponse>(VerifyCostTransferActivity.this);
                            webServices.UpdateVct(WebServices.ApiType.updatevct,vctUpdateRequest);
                        }
                    }

                }else {
                    Toast.makeText(VerifyCostTransferActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back_toolbar_ctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                //String path = copyFileToInternalStorage(uri,"PCMS");

                //originalFile = new File(String.valueOf(originalFile));
                //originalFile = new File(getRealPathFromURI(uri));
                // originalFile = new File(String.valueOf("/Internal storage/Documents/pallavi_statement.pdf"));
                // originalFile = new File(FileUtils.getRealPath(this, uri));

                // System.out.println("path======" + getRealPathFromURI(uri));
                String path1 = data.getData().getPath();
                String path2 = path1.replace("/document/raw:", "");
                //callupdatefileapi();
                String filename=path1.substring(path1.lastIndexOf("/")+1);
                //tv_upload_group_photo.setText(filename);

            }
            //upload_attachment.setText(filePath);
        }
    }

////////permitions storage

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
            // requestPermission();
        } else {
            int result = ContextCompat.checkSelfPermission(VerifyCostTransferActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(VerifyCostTransferActivity.this, WRITE_EXTERNAL_STORAGE);
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
            ActivityCompat.requestPermissions(VerifyCostTransferActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, 123);
        }
    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL1 = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);

        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);
            ((TextView) parent.getChildAt(0)).setText(from_pc);
        }
    };

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL2 = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);
            ((TextView) parent.getChildAt(0)).setText(to_pc);
        }
    };

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL3 = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);

        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(12);
            ((TextView) parent.getChildAt(0)).setText(expense_list);
        }
    };

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

    public void openDialogSubmitBI(String vct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert");
        builder.setMessage("Are You Sure Want to Submit CTN's ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                callDialogeSubmitCTNapi(vct);
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

    public void openDialogReject(String re) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert");
        builder.setMessage("Are You Sure Want to Delete?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                callDialogueDeleteCTNapi(re);
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

        originalFile = new File(path);
        callupdatefileapi();
        System.out.println("paths----"+path);


    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }
}