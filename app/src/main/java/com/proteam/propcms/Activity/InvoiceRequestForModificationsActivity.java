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
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.proteam.propcms.Models.IrfmDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.CompanyDetailsModel;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.RequestForModificationListResponse;
import com.proteam.propcms.Utils.OnClick;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceRequestForModificationsActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener, OnClick {
    ImageView mToolbar;
    EditText edt_from_irfm,edt_search;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_irfm;
    TextView temp_btn,tv_count;
    RecyclerView rv_irfm_Data_list;
    ProgressDialog progressDialog;
    List projectList = new ArrayList();
    ArrayList<IrfmDataModel> arrayList = new ArrayList<IrfmDataModel>();
    ArrayList<IrfmDataModel> filterarraylist = new ArrayList<IrfmDataModel>();
    private OnClick mClick;
    Map map = new HashMap();
    Button approve,reject,btn_search_list;
    Map projectmap = new HashMap();
    ArrayList<IrfmDataModel> temp = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_request_for_modifications);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        initialize();
        sp_all_project_irfm.setOnItemSelectedListener(OnCatSpinnerCL);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InvoiceRequestForModificationsActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void initialize()
    {
        rv_irfm_Data_list=findViewById(R.id.rv_irfm_Data_list);
        temp_btn=findViewById(R.id.temp_btn);
        temp_btn.setOnClickListener(this);
        edt_from_irfm=findViewById(R.id.edt_from_irfm);
        edt_from_irfm.setOnClickListener(this);
        sp_all_project_irfm=findViewById(R.id.sp_all_project_irfm);
        tv_count = findViewById(R.id.tv_count);
        approve = findViewById(R.id.btn_approve_invoise);
        reject = findViewById(R.id.btn_reject_invoise);
        btn_search_list = findViewById(R.id.btn_search_list);
        edt_search = findViewById(R.id.edt_search_irfm);
        btn_search_list.setOnClickListener(this);
        approve.setOnClickListener(this);
        reject.setOnClickListener(this);

        callmodificationApi();
    }

    private void callmodificationApi() {

        progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForModificationsActivity.this);
                webServices.invoicemodificationapi(WebServices.ApiType.invoicemod);
            } else {

            }
        }
    }

    private void callProjectListApi() {

        progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);

        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                ProjectListModel projectListModel = new ProjectListModel("21");
                WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(InvoiceRequestForModificationsActivity.this);
                webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);
            }
        }

    }

    /*private void callcompanyApi() {

        progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                CompanyDetailsModel companyDetailsModel = new CompanyDetailsModel("1");
                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForModificationsActivity.this);
                webServices.companyname(WebServices.ApiType.invoicemod,companyDetailsModel);
            } else {

            }
        }
    }*/

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

    private void filter(String text) {

        if(text.equals("")){
            adaptorclass();
        }else {

            temp.clear();
            for (int i=0;i<arrayList.size();i++){

                if(arrayList.get(i).getIrfmPcCode().toLowerCase().contains(text.toLowerCase()) || arrayList.get(i).getIrfmGroup().toLowerCase().contains(text.toLowerCase())){

                    temp.add(new IrfmDataModel(arrayList.get(i).getIrfmPcCode(),
                            arrayList.get(i).getIrfmInvoiceNo(),
                            arrayList.get(i).getIrfmProcessOwner(),
                            arrayList.get(i).getIrfmRequestForChange(),
                            arrayList.get(i).getIrfmGroup(),
                            arrayList.get(i).getIrfmAssignment(),
                            arrayList.get(i).getIrfmRegion(),
                            arrayList.get(i).getIrfmPlace(),
                            arrayList.get(i).getIrfmGstinNo(),
                            arrayList.get(i).getIrfmPanOfCustomer(),
                            arrayList.get(i).getIrfmTaxableAmount(),
                            arrayList.get(i).getIrfmGstRate(),
                            arrayList.get(i).getIrfmForMonth(),
                            arrayList.get(i).getIrfmDescription(),
                            arrayList.get(i).getProcessowner(),
                            arrayList.get(i).getId(),
                            arrayList.get(i).getProjectid(),
                            arrayList.get(i).getRequest(),
                            arrayList.get(i).getRequestdate()
                    ));

                }

            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfm_Data_list);
            IrfmListAdapter adapter = new IrfmListAdapter(temp,this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {


            case R.id.edt_from_irfm:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(InvoiceRequestForModificationsActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from_irfm.setText(sdf.format(mcurrentDate.getTime()));
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
                break;

            case R.id.temp_btn:
                break;

            case R.id.btn_approve_invoise:
                callapproveApi();
                break;

            case R.id.btn_reject_invoise:
                callRejectApi();
                break;
            case R.id.btn_search_list:

                if(sp_all_project_irfm.getSelectedItem()!=null){

                    if(!edt_from_irfm.getText().toString().isEmpty()){
                        Searchlist();
                    }else {
                        Toast.makeText(this, "Please select month", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "Select project", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void callapproveApi() {

        ArrayList list = new ArrayList(map.values());
        if (list.size()==0){
            Toast.makeText(this, "invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
            WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForModificationsActivity.this);
            webServices.approvecall(WebServices.ApiType.approve, invApproverequest);
        }
            }
        }

    }

    private void  callRejectApi() {

                ArrayList list = new ArrayList(map.values());

                if (list.size()==0){
                    Toast.makeText(this, "invoice not selected ", Toast.LENGTH_SHORT).show();
                }else {

                    progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);
                    if (progressDialog != null) {
                        if (!progressDialog.isShowing()) {

                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForModificationsActivity.this);
                    webServices.rejectcall(WebServices.ApiType.approve,invApproverequest);
                }

            }
        }
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {

        switch (URL) {

            case invoicemod:
                if(progressDialog!=null)
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if(response!=null){
                    RequestForModificationListResponse invoiceres = (RequestForModificationListResponse) response;
                    List list = new ArrayList();
                    list = invoiceres.getList();
                    tv_count.setText(String.valueOf(list.size()));
                    for (int i=0;i<list.size();i++){

                       arrayList.add(new IrfmDataModel(invoiceres.getList().get(i).getPc_code(),
                               invoiceres.getList().get(i).getInvoice_number(),
                               invoiceres.getList().get(i).getUser_id(),
                               invoiceres.getList().get(i).getRequest_remarks(),
                               invoiceres.getList().get(i).getGroup_name(),
                               invoiceres.getList().get(i).getAssignment(),
                               invoiceres.getList().get(i).getRegion(),
                               invoiceres.getList().get(i).getPlace(),
                               invoiceres.getList().get(i).getGstin_no(),
                               invoiceres.getList().get(i).getPan_no_customer(),
                               invoiceres.getList().get(i).getAmount(),
                               invoiceres.getList().get(i).getGst_percentage(),
                               invoiceres.getList().get(i).getMonth(),
                               invoiceres.getList().get(i).getDescription(),
                               invoiceres.getList().get(i).getProcess_owner(),
                               invoiceres.getList().get(i).getId(),
                               invoiceres.getList().get(i).getProject_id(),
                               invoiceres.getList().get(i).getRequest(),
                               invoiceres.getList().get(i).getRequest_date()));

                    };

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfm_Data_list);
                        IrfmListAdapter adapter = new IrfmListAdapter(arrayList,this);
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

            case projectlist:

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

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name(),projectListResponse.getProject_list().get(i).getProject_id());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name());
                        }

                        ArrayAdapter adapter = new ArrayAdapter(InvoiceRequestForModificationsActivity.this, android.R.layout.simple_list_item_1, projectList);
                        sp_all_project_irfm.setAdapter(adapter);


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
                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private void Searchlist() {

        //String project_id = (String) projectmap.get(sp_all_project_irfm.getSelectedItem().toString());

        for (int i=0;i<arrayList.size();i++){

            String project_id = "365";

            if(arrayList.get(i).getProjectid().equalsIgnoreCase(project_id)){

                filterarraylist.add(new IrfmDataModel(arrayList.get(i).getIrfmPcCode(),
                        arrayList.get(i).getIrfmInvoiceNo(),
                        arrayList.get(i).getIrfmProcessOwner(),
                        arrayList.get(i).getIrfmRequestForChange(),
                        arrayList.get(i).getIrfmGroup(),
                        arrayList.get(i).getIrfmAssignment(),
                        arrayList.get(i).getIrfmRegion(),
                        arrayList.get(i).getIrfmPlace(),
                        arrayList.get(i).getIrfmGstinNo(),
                        arrayList.get(i).getIrfmPanOfCustomer(),
                        arrayList.get(i).getIrfmTaxableAmount(),
                        arrayList.get(i).getIrfmGstRate(),
                        arrayList.get(i).getIrfmForMonth(),
                        arrayList.get(i).getIrfmDescription(),
                        arrayList.get(i).getProcessowner(),
                        arrayList.get(i).getId(),
                        arrayList.get(i).getProjectid(),
                        arrayList.get(i).getRequest(),
                        arrayList.get(i).getRequestdate()
                        ));
            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfm_Data_list);
            IrfmListAdapter adapter = new IrfmListAdapter(filterarraylist,this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
    }


    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(14);
            adaptorclass();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(14);

            adaptorclass();
        }
    };

    private void adaptorclass() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfm_Data_list);
        IrfmListAdapter adapter = new IrfmListAdapter(arrayList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void callapproveindividualApi(String id) {

        ArrayList list = new ArrayList();
        list.add(id);
        if (list.size()==0){
            Toast.makeText(this, "invoice not selected ", Toast.LENGTH_SHORT).show();
        }else {

            progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForModificationsActivity.this);
                    webServices.approvecall(WebServices.ApiType.approve, invApproverequest);
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

            progressDialog = new ProgressDialog(InvoiceRequestForModificationsActivity.this);
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {

                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    InvApproverequest invApproverequest = new InvApproverequest(list);
                    WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(InvoiceRequestForModificationsActivity.this);
                    webServices.rejectcall(WebServices.ApiType.approve,invApproverequest);
                }

            }
        }
    }


    private void opengcadminDialog(String position) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_irfm_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout ll_popups = dialog.findViewById(R.id.ll_popups);
        LinearLayout ll_invoice = dialog.findViewById(R.id.ll_invoice);
        TextView tv_pccode = dialog.findViewById(R.id.tv_pccode);
        TextView invoice = dialog.findViewById(R.id.tv_invoise_no);
        TextView owner = dialog.findViewById(R.id.tv_process_owner);
        TextView change = dialog.findViewById(R.id.tv_requesting);
        TextView group = dialog.findViewById(R.id.tv_group);
        TextView assign = dialog.findViewById(R.id.tv_assignment);
        TextView region = dialog.findViewById(R.id.tv_region);
        TextView tv_place = dialog.findViewById(R.id.tv_place);
        TextView tv_gstin = dialog.findViewById(R.id.tv_gstin);
        TextView tv_pan_no = dialog.findViewById(R.id.tv_pan_no);
        TextView tv_tax = dialog.findViewById(R.id.tv_tax);
        TextView tv_gst_rate = dialog.findViewById(R.id.tv_gst_rate);
        TextView tv_month = dialog.findViewById(R.id.tv_month);
        TextView tv_discription = dialog.findViewById(R.id.tv_discription);
        Button approve = dialog.findViewById(R.id.btn_approve);
        Button reject = dialog.findViewById(R.id.btn_reject);
        ImageView back_toolbar = dialog.findViewById(R.id.back_toolbar);

        IrfmDataModel irfmDataModel = arrayList.get(Integer.parseInt(position));

        invoice.setText(irfmDataModel.getIrfmInvoiceNo());
        owner.setText(irfmDataModel.getIrfmProcessOwner());
        change.setText(irfmDataModel.getIrfmRequestForChange());
        group.setText(irfmDataModel.getIrfmGroup());
        assign.setText(irfmDataModel.getIrfmAssignment());
        region.setText(irfmDataModel.getIrfmRegion());
        tv_place.setText(irfmDataModel.getIrfmPlace());
        tv_gstin.setText(irfmDataModel.getIrfmGstinNo());
        tv_pan_no.setText(irfmDataModel.getIrfmPanOfCustomer());
        tv_tax.setText(irfmDataModel.getIrfmTaxableAmount());
        tv_gst_rate.setText(irfmDataModel.getIrfmGstRate());
        tv_month.setText(irfmDataModel.getIrfmForMonth());
        tv_discription.setText(irfmDataModel.getIrfmDescription());
        tv_pccode.setText(irfmDataModel.getIrfmPcCode());

        ll_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://pcmsdemo.proteam.co.in//upload/bi_docs/6868e91d7d7f14d22.pdf"));
                        startActivity(viewIntent);
            }
        });
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callapproveindividualApi(irfmDataModel.getId());
                dialog.dismiss();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callRejectindividualApi(irfmDataModel.getId());
                dialog.dismiss();
            }
        });
        back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ll_popups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openrequestdialog( position);
            }
        });

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

        IrfmDataModel irfmDataModel = arrayList.get(Integer.parseInt(position));

        //tv_remarks.setText(irfmDataModel.getRequest());
        tv_remarks.setText("Requesting for modification");
        edt_date_1.setText(irfmDataModel.getRequestdate());
        remarks.setText(irfmDataModel.getIrfmRequestForChange());

    }



    @Override
    public void onClickitem(String value,int item,String id) {

        if(item==1){
            opengcadminDialog(value);
        }else if(item==2){
            openrequestdialog(value);
        }else if(item==3){
            map.put(value,id);
        }else if(item==4){
            map.remove(value);
        }
    }
}