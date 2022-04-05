package com.proteam.propcms.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.customview.widget.Openable;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.proteam.propcms.Adapters.CtnrListAdapter;
import com.proteam.propcms.Adapters.IrfcListAdapter;
import com.proteam.propcms.Adapters.IrfmListAdapter;
import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.Models.IrfcDataModel;
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.invoicereject.RejectList;
import com.proteam.propcms.Utils.OnClick;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InvoiceRequestForCancellationsActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener, OnClick, PickiTCallbacks {
    ImageView mToolbar,iv_refresh_irfc;
    EditText edt_from_irfc,edt_search_irfc;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_irfc;
    TextView temp_btn_irfc,tv_irfc_count,tv_pc_code_sort,tv_invoiceno_sort;
    ProgressDialog progressDialog;
    Button btn_irfc_rejact,btn_irfc_approve,btn_search_Irfc;
    List projectList = new ArrayList();
    Map map = new HashMap();
    CheckBox ch_action_irfc;
    RecyclerView rv_irfc_Data_list;
    ArrayList<IrfcDataModel> arrayList = new ArrayList<IrfcDataModel>();
    Map projectmap = new HashMap();
    ArrayList<IrfcDataModel> filterarraylist = new ArrayList<IrfcDataModel>();
    ArrayList<IrfcDataModel> temp = new ArrayList();
    SharedPreferences.Editor editor;
    String user;
    LinearLayout ll_no_data_irfc;
    File originalFile;
    String billId;
    PickiT pickiT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_request_for_cancellations);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        pickiT = new PickiT(this, this, this);
        SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user = sharedPreferences.getString("userid", null);

        initialize();
        sp_all_project_irfc.setOnItemSelectedListener(OnCatSpinnerCL);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InvoiceRequestForCancellationsActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {
        ll_no_data_irfc=findViewById(R.id.ll_no_data_irfc);
        tv_invoiceno_sort=findViewById(R.id.tv_invoiceno_sort);
        tv_pc_code_sort=findViewById(R.id.tv_pc_code_sort);
        tv_irfc_count=findViewById(R.id.tv_irfc_count);
        rv_irfc_Data_list=findViewById(R.id.rv_irfc_Data_list);
       // temp_btn_irfc=findViewById(R.id.temp_btn_irfc);
        //temp_btn_irfc.setOnClickListener(this);
        edt_from_irfc=findViewById(R.id.edt_from_irfc);
        iv_refresh_irfc=findViewById(R.id.iv_refresh_irfc);
        iv_refresh_irfc.setOnClickListener(this);
        edt_from_irfc.setOnClickListener(this);
        edt_search_irfc =findViewById(R.id.edt_search_irfc);
        sp_all_project_irfc=findViewById(R.id.sp_all_project_irfc);
        btn_irfc_rejact = findViewById(R.id.btn_irfc_rejact);
        ch_action_irfc = findViewById(R.id.ch_action_irfc);
        btn_irfc_approve=findViewById(R.id.btn_irfc_approve);
        btn_search_Irfc = findViewById(R.id.btn_search_Irfc);
        btn_search_Irfc.setOnClickListener(this);
        btn_irfc_rejact.setOnClickListener(this);
        btn_irfc_approve.setOnClickListener(this);

        callmodificationApi();

        ch_action_irfc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    adaptorclass(true);
                }else {
                    adaptorclass(false);
                }

            }
        });

        tv_invoiceno_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort(1);
            }

        });

        tv_pc_code_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort(2);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        edt_search_irfc.addTextChangedListener(new TextWatcher() {
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

    ////////////////////////////////////////////Api calling ///////////////////////////////////////////////////////

    private void callmodificationApi() {

        progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                UserIdRequest userIdRequest = new UserIdRequest(user);

                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForCancellationsActivity.this);
                webServices.invoicancelationapi(WebServices.ApiType.invoicemod,userIdRequest);
            } else {

            }
        }
    }

    private void callapproveApi() {

        ArrayList list = new ArrayList(map.values());
        if (list.size()==0){
            Toast.makeText(this, "Invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForCancellationsActivity.this);
                    webServices.approveirfccall(WebServices.ApiType.approve, invApproverequest);
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

            progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForCancellationsActivity.this);
                    webServices.approveirfccall(WebServices.ApiType.approve, invApproverequest);
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

            progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForCancellationsActivity.this);
                    webServices.rejectirfccall(WebServices.ApiType.approve,invApproverequest);
                }
            }
        }
    }

    private void  callRejectApi() {

        ArrayList list = new ArrayList(map.values());

        if (list.size()==0){
            Toast.makeText(this, "Invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(InvoiceRequestForCancellationsActivity.this);
                    webServices.rejectirfccall(WebServices.ApiType.approve,invApproverequest);
                }

            }
        }
    }

    private void callProjectListApi() {

        ProjectListModel projectListModel = new ProjectListModel(user);
        WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(InvoiceRequestForCancellationsActivity.this);
        webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);

        progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);

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

        progressDialog=new ProgressDialog(InvoiceRequestForCancellationsActivity.this);

        if(progressDialog!=null)
        {
            if(!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();


                WebServices<GenerealResponse> webServices = new WebServices<GenerealResponse>(InvoiceRequestForCancellationsActivity.this);
                webServices.fileupload( WebServices.ApiType.pdfupload, originalFile,user,billId);


            }
        }
    }


    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {

        switch(URL){

            case general:
                break;
            case login:
                break;
            case profile:
                break;
            case profileupdate:
                break;
            case invoicemod:

                List list = new ArrayList();
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {
                        RejectList rejectList = (RejectList) response;

                        list = rejectList.getList();

                        tv_irfc_count.setText(String.valueOf(list.size()));
                        arrayList.clear();

                        for (int i=0;i<list.size();i++){

                            arrayList.add( new IrfcDataModel(rejectList.getList().get(i).getPc_code(),
                                    rejectList.getList().get(i).getInvoice_number(),
                                    rejectList.getList().get(i).getInvoice_date(),
                                    rejectList.getList().get(i).getGroup_name(),
                                    rejectList.getList().get(i).getAssignment(),
                                    rejectList.getList().get(i).getBill_to(),
                                    rejectList.getList().get(i).getBilling_address(),
                                    rejectList.getList().get(i).getReference_no(),
                                    rejectList.getList().get(i).getKind_attention(),
                                    rejectList.getList().get(i).getRegion_name(),
                                    rejectList.getList().get(i).getPlace(),
                                    rejectList.getList().get(i).getGstin_no(),
                                    rejectList.getList().get(i).getPan_no_customer(),
                                    rejectList.getList().get(i).getAmount(),
                                    rejectList.getList().get(i).getGst_percentage()+"%",
                                    rejectList.getList().get(i).getMonth(),
                                    rejectList.getList().get(i).getDescription(),
                                    rejectList.getList().get(i).getHSN_SAC(),
                                    rejectList.getList().get(i).getParticular(),
                                    rejectList.getList().get(i).getState_supply(),
                                    rejectList.getList().get(i).getTransaction_type(),
                                    rejectList.getList().get(i).getInvoice_approver_name(),
                                    rejectList.getList().get(i).getId()));

                        }

                        if(arrayList.size()==0){
                            ll_no_data_irfc.setVisibility(View.VISIBLE);
                        }else {
                            ll_no_data_irfc.setVisibility(View.GONE);

                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfc_Data_list);
                        IrfcListAdapter adapter = new IrfcListAdapter(arrayList,this,false);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(adapter);
                        callProjectListApi();

                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
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

                        List list1 = new ArrayList();
                        ProjectListResponse projectListResponse = (ProjectListResponse) response;

                        list1 = projectListResponse.getProject_list();

                        for (int i = 0; i < list1.size(); i++) {

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name()+" ("+projectListResponse.getProject_list().get(i).getPc_code()+")",projectListResponse.getProject_list().get(i).getPc_code());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name()+" ("+projectListResponse.getProject_list().get(i).getPc_code()+")");
                        }

                        ArrayAdapter adapter = new ArrayAdapter(InvoiceRequestForCancellationsActivity.this, android.R.layout.simple_list_item_1, projectList);
                        sp_all_project_irfc.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;
            case companylist:
                break;
            case headlist:
                break;
            case divisionlist:
                break;
            case countitem:
                break;
            case client:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.edt_from_irfc:
                datepicker();
                break;

            case R.id.btn_irfc_rejact:
                callRejectApi();
                break;

            case R.id.btn_irfc_approve:
                callapproveApi();
                break;

            case R.id.iv_refresh_irfc:
                finish();
                startActivity(getIntent());
                break;

            case R.id.btn_search_Irfc:
                if(sp_all_project_irfc.getSelectedItem()!=null){

                    if(!edt_from_irfc.getText().toString().isEmpty()){
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

    private void sort(int id) {

        Collections.sort(arrayList, new Comparator<IrfcDataModel>() {
            @Override
            public int compare(IrfcDataModel item1, IrfcDataModel item2) {
                if(id==1){
                    return item1.getIrfcPcCode().compareToIgnoreCase(item2.getIrfcPcCode());
                }else{
                    return item1.getIrfcPcCode().compareToIgnoreCase(item2.getIrfcPcCode());
                }


            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfc_Data_list);
        IrfcListAdapter adapter = new IrfcListAdapter(arrayList,this,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void filter(String text) {

        if(text.equals("")){
            adaptorclass(false);
        }else {

            temp.clear();

            for (int i=0;i<arrayList.size();i++){

                if(arrayList.get(i).getIrfcGroup().toLowerCase().contains(text.toLowerCase()) ||
                        arrayList.get(i).getIrfcInvoiceNo().toLowerCase().contains(text.toLowerCase()) ||
                        arrayList.get(i).getIrfcKindAttention().toLowerCase().contains(text.toLowerCase()) ||
                                arrayList.get(i).getIrfcPcCode().toLowerCase().contains(text.toLowerCase())){

                    temp.add( new IrfcDataModel(arrayList.get(i).getIrfcPcCode(),
                            arrayList.get(i).getIrfcInvoiceNo(),
                            arrayList.get(i).getIrfcInvoiceDate(),
                            arrayList.get(i).getIrfcGroup(),
                            arrayList.get(i).getIrfcAssignment(),
                            arrayList.get(i).getIrfcBillTo(),
                            arrayList.get(i).getIrfcBillingAddress(),
                            arrayList.get(i).getIrfcReferenceNumber(),
                            arrayList.get(i).getIrfcKindAttention(),
                            arrayList.get(i).getIrfcRegion(),
                            arrayList.get(i).getIrfcPlace(),
                            arrayList.get(i).getIrfcGstinNo(),
                            arrayList.get(i).getIrfcPanOfCustomer(),
                            arrayList.get(i).getIrfcTaxableAmount(),
                            arrayList.get(i).getIrfcGstRate()+"%",
                            arrayList.get(i).getIrfcForMonth(),
                            arrayList.get(i).getIrfcDescription(),
                            arrayList.get(i).getIrfcHsnSac(),
                            arrayList.get(i).getIrfcParticulars(),
                            arrayList.get(i).getIrfcStateOfSupplyCode(),
                            arrayList.get(i).getIrfcTransactionType(),
                            arrayList.get(i).getIrfcInvoiceWithWhom(),
                            arrayList.get(i).getId()));

                }
                if(temp.size()==0){
                    ll_no_data_irfc.setVisibility(View.VISIBLE);
                }else {
                    ll_no_data_irfc.setVisibility(View.GONE);

                }
            }
            tv_irfc_count.setText(String.valueOf(temp.size()));
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfc_Data_list);
            IrfcListAdapter adapter = new IrfcListAdapter(temp,this,false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    private void Searchlist() {

        String vddj = sp_all_project_irfc.getSelectedItem().toString();

        String project_id = String.valueOf(projectmap.get(sp_all_project_irfc.getSelectedItem().toString()));

        for (int i=0;i<arrayList.size();i++){

            // String project_id = "365";

            if(arrayList.get(i).getIrfcPcCode().equalsIgnoreCase(project_id)){

                filterarraylist.add( new IrfcDataModel(arrayList.get(i).getIrfcPcCode(),
                        arrayList.get(i).getIrfcInvoiceNo(),
                        arrayList.get(i).getIrfcInvoiceDate(),
                        arrayList.get(i).getIrfcGroup(),
                        arrayList.get(i).getIrfcAssignment(),
                        arrayList.get(i).getIrfcBillTo(),
                        arrayList.get(i).getIrfcBillingAddress(),
                        arrayList.get(i).getIrfcReferenceNumber(),
                        arrayList.get(i).getIrfcKindAttention(),
                        arrayList.get(i).getIrfcRegion(),
                        arrayList.get(i).getIrfcPlace(),
                        arrayList.get(i).getIrfcGstinNo(),
                        arrayList.get(i).getIrfcPanOfCustomer(),
                        arrayList.get(i).getIrfcTaxableAmount(),
                        arrayList.get(i).getIrfcGstRate()+"%",
                        arrayList.get(i).getIrfcForMonth(),
                        arrayList.get(i).getIrfcDescription(),
                        arrayList.get(i).getIrfcHsnSac(),
                        arrayList.get(i).getIrfcParticulars(),
                        arrayList.get(i).getIrfcStateOfSupplyCode(),
                        arrayList.get(i).getIrfcTransactionType(),
                        arrayList.get(i).getIrfcInvoiceWithWhom(),
                        arrayList.get(i).getId()));
            }

            if(filterarraylist.size()==0){
                ll_no_data_irfc.setVisibility(View.VISIBLE);
            }else {
                ll_no_data_irfc.setVisibility(View.GONE);

            }

        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfc_Data_list);
        IrfcListAdapter adapter = new IrfcListAdapter(filterarraylist,this,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void adaptorclass(boolean b)
    {
        if(arrayList.size()==0){
            ll_no_data_irfc.setVisibility(View.VISIBLE);
        }else {
            ll_no_data_irfc.setVisibility(View.GONE);

        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfc_Data_list);
        IrfcListAdapter adapter = new IrfcListAdapter(arrayList,this,b);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void opengcadminDialog(int position,int id) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_irfc_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();

        LinearLayout ic_d_irfc_viewInvoice = dialog.findViewById(R.id.ll_irfc);
        LinearLayout ll_irfm = dialog.findViewById(R.id.ll_irfm);
        LinearLayout ll_irfc_upload = dialog.findViewById(R.id.ll_irfc_upload);
        ImageView iv_d_irfc_upload = dialog.findViewById(R.id.iv_d_irfc_upload);
        ImageView iv_d_irfc_status = dialog.findViewById(R.id.iv_d_irfc_status);
        ImageView back_toolbar_dialog = dialog.findViewById(R.id.back_toolbar_dialog);

        TextView tv_d_irfc_pcCode = dialog.findViewById(R.id.tv_d_irfc_pcCode);
        TextView tv_d_irfc_InvoiceNo = dialog.findViewById(R.id.tv_d_irfc_InvoiceNo);
        TextView tv_d_irfc_InvoiceDate = dialog.findViewById(R.id.tv_d_irfc_InvoiceDate);
        TextView tv_d_irfc_group = dialog.findViewById(R.id.tv_d_irfc_group);
        TextView tv_d_irfc_assignment = dialog.findViewById(R.id.tv_d_irfc_assignment);
        TextView tv_d_irfc_billTo = dialog.findViewById(R.id.tv_d_irfc_billTo);
        TextView tv_d_irfc_BillingAddress = dialog.findViewById(R.id.tv_d_irfc_BillingAddress);
        TextView tv_d_irfc_referenceNo = dialog.findViewById(R.id.tv_d_irfc_referenceNo);
        TextView tv_d_irfc_kindAttention = dialog.findViewById(R.id.tv_d_irfc_kindAttention);
        TextView tv_d_irfc_region = dialog.findViewById(R.id.tv_d_irfc_region);
        TextView tv_d_irfc_place = dialog.findViewById(R.id.tv_d_irfc_place);
        TextView tv_d_irfc_gstNo = dialog.findViewById(R.id.tv_d_irfc_gstNo);
        TextView tv_d_irfc_panOfCustomer = dialog.findViewById(R.id.tv_d_irfc_panOfCustomer);
        TextView tv_d_irfc_taxableAmount = dialog.findViewById(R.id.tv_d_irfc_taxableAmount);
        TextView tv_d_irfc_gstRate = dialog.findViewById(R.id.tv_d_irfc_gstRate);
        TextView tv_d_irfc_forMonth = dialog.findViewById(R.id.tv_d_irfc_forMonth);
        TextView tv_d_irfc_description = dialog.findViewById(R.id.tv_d_irfc_description);
        TextView tv_d_irfc_hsn_sac = dialog.findViewById(R.id.tv_d_irfc_hsn_sac);
        TextView tv_d_irfc_Particular = dialog.findViewById(R.id.tv_d_irfc_Particular);
        TextView tv_d_irfc_stateOfSupply = dialog.findViewById(R.id.tv_d_irfc_stateOfSupply);
        TextView tv_d_irfc_transactionType = dialog.findViewById(R.id.tv_d_irfc_transactionType);
        TextView tv_d_irfc_InvoiceWithWhom = dialog.findViewById(R.id.tv_d_irfc_InvoiceWithWhom);

        Button btn_d_irfc_ctn_approve = dialog.findViewById(R.id.btn_d_irfc_ctn_approve);
        Button btn_d_irfc_ctn_reject = dialog.findViewById(R.id.btn_d_irfc_ctn_reject);

        tv_d_irfc_group.setText(arrayList.get(position).getIrfcGroup());
        tv_d_irfc_pcCode.setText(arrayList.get(position).getIrfcPcCode());
        tv_d_irfc_InvoiceNo.setText(arrayList.get(position).getIrfcInvoiceNo());
        tv_d_irfc_InvoiceDate.setText(arrayList.get(position).getIrfcInvoiceDate());
        tv_d_irfc_assignment.setText(arrayList.get(position).getIrfcAssignment());
        tv_d_irfc_billTo.setText(arrayList.get(position).getIrfcBillTo());
        tv_d_irfc_BillingAddress.setText(arrayList.get(position).getIrfcBillingAddress());
        tv_d_irfc_referenceNo.setText(arrayList.get(position).getIrfcReferenceNumber());
        tv_d_irfc_kindAttention.setText(arrayList.get(position).getIrfcKindAttention());
        tv_d_irfc_region.setText(arrayList.get(position).getIrfcRegion());
        tv_d_irfc_place.setText(arrayList.get(position).getIrfcPlace());

        tv_d_irfc_gstNo.setText(arrayList.get(position).getIrfcGstinNo());
        tv_d_irfc_panOfCustomer.setText(arrayList.get(position).getIrfcPanOfCustomer());

        float amount = Float.parseFloat(arrayList.get(position).getIrfcTaxableAmount());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String moneyString = formatter.format(amount);

        tv_d_irfc_taxableAmount.setText(moneyString);
        tv_d_irfc_gstRate.setText(arrayList.get(position).getIrfcGstRate());
        tv_d_irfc_forMonth.setText(arrayList.get(position).getIrfcForMonth());
        tv_d_irfc_description.setText(arrayList.get(position).getIrfcDescription());
        tv_d_irfc_hsn_sac.setText(arrayList.get(position).getIrfcHsnSac());
        tv_d_irfc_Particular.setText(arrayList.get(position).getIrfcParticulars());
        tv_d_irfc_stateOfSupply.setText(arrayList.get(position).getIrfcStateOfSupplyCode());
        tv_d_irfc_transactionType.setText(arrayList.get(position).getIrfcTransactionType());
        tv_d_irfc_InvoiceWithWhom.setText(StringUtils.capitalize(arrayList.get(position).getIrfcInvoiceWithWhom().toLowerCase().trim()));

        ll_irfc_upload.setOnClickListener(new View.OnClickListener() {
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
        ll_irfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openrequestdialog( String.valueOf(position));
            }
        });

        btn_d_irfc_ctn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openapproveDialog2(arrayList.get(position).getId());
                //dialog.dismiss();
            }
        });

        ic_d_irfc_viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String invoice=arrayList.get(position).getIrfcInvoiceNo().replace("/","_");

                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://pcmsdemo.proteam.co.in/upload/dsc_invoices/"+invoice+".pdf"));
                startActivity(viewIntent);

                System.out.println("url:  == https://pcmsdemo.proteam.co.in/upload/dsc_invoices/"+arrayList.get(position).getIrfcInvoiceNo()+".pdf");
            }
        });

        btn_d_irfc_ctn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openrejectDialog2(arrayList.get(position).getId());
               // dialog.dismiss();
            }
        });

        back_toolbar_dialog.setOnClickListener(new View.OnClickListener() {
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

            }
        }
    }

    public void openapproveDialog2(String it) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceRequestForCancellationsActivity.this);
        builder.setTitle("Alert");
        builder.setMessage("Are You Sure Want to Approve?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                callapproveindividualApi(it);
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

    public void openrejectDialog2(String it) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceRequestForCancellationsActivity.this);
        builder.setTitle("Alert");
        builder.setMessage("Are You Sure Want to Reject?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                callapproveindividualApi(it);
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

    private void openrequestdialog(String position) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_requesting_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText tv_remarks = dialog.findViewById(R.id.tv_clients_home);
        EditText edt_date_1 = dialog.findViewById(R.id.edt_date_1);
        EditText remarks = dialog.findViewById(R.id.et_remarks);
        ImageView back_press = dialog.findViewById(R.id.back_press);

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        IrfcDataModel irfcDataModel = arrayList.get(Integer.parseInt(position));

        //tv_remarks.setText(irfmDataModel.getRequest());
        tv_remarks.setText("Requesting for modification");
        edt_date_1.setText(irfcDataModel.getIrfcInvoiceDate());
        remarks.setText("Remarks");

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

    private void datepicker() {

        Calendar mcurrentDate = Calendar.getInstance();
        String myFormat = "MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        DatePickerDialog monthDatePickerDialog = new DatePickerDialog(InvoiceRequestForCancellationsActivity.this,
                AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mcurrentDate.set(Calendar.YEAR, year) ;
                mcurrentDate.set(Calendar.MONTH, month);

                edt_from_irfc.setText(sdf.format(mcurrentDate.getTime()));
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
        // monthDatePickerDialog.getDatePicker().setMaxDate(mcurrentDate.getTimeInMillis());
        // monthDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }


    @Override
    public void onClickitem(String value, int item, String id) {

        if(item==1){
            opengcadminDialog(Integer.parseInt(value),Integer.parseInt(id));
        }else if(item==2){
            openrequestdialog(value);
        }else if(item==3){
            map.put(value,id);
        }else if(item==4){
            map.remove(value);
        }

    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
            // requestPermission();
        } else {
            int result = ContextCompat.checkSelfPermission(InvoiceRequestForCancellationsActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(InvoiceRequestForCancellationsActivity.this, WRITE_EXTERNAL_STORAGE);
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
            ActivityCompat.requestPermissions(InvoiceRequestForCancellationsActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, 123);
        }
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