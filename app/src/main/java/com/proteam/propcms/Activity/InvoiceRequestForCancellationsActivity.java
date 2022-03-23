package com.proteam.propcms.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.Openable;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.proteam.propcms.Adapters.CtnrListAdapter;
import com.proteam.propcms.Adapters.IrfcListAdapter;
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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InvoiceRequestForCancellationsActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener, OnClick {
    ImageView mToolbar;
    EditText edt_from_irfc;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_irfc;
    TextView temp_btn_irfc,tv_irfc_count;
    ProgressDialog progressDialog;
    Button btn_irfc_rejact,btn_irfc_approve;
    List projectList = new ArrayList();
    Map map = new HashMap();
    RecyclerView rv_irfc_Data_list;
    ArrayList<IrfcDataModel> arrayList = new ArrayList<IrfcDataModel>();
    Map projectmap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_request_for_cancellations);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

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
        tv_irfc_count=findViewById(R.id.tv_irfc_count);
        rv_irfc_Data_list=findViewById(R.id.rv_irfc_Data_list);
       // temp_btn_irfc=findViewById(R.id.temp_btn_irfc);
        //temp_btn_irfc.setOnClickListener(this);
        edt_from_irfc=findViewById(R.id.edt_from_irfc);
        edt_from_irfc.setOnClickListener(this);
        sp_all_project_irfc=findViewById(R.id.sp_all_project_irfc);
        btn_irfc_rejact = findViewById(R.id.btn_irfc_rejact);
        btn_irfc_approve=findViewById(R.id.btn_irfc_approve);
        btn_irfc_rejact.setOnClickListener(this);
        btn_irfc_approve.setOnClickListener(this);

        callmodificationApi();
    }



    ////////////////////////////////////////////Api calling ///////////////////////////////////////////////////////

    private void callmodificationApi() {

        progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                UserIdRequest userIdRequest = new UserIdRequest("21");

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

        progressDialog = new ProgressDialog(InvoiceRequestForCancellationsActivity.this);

        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                ProjectListModel projectListModel = new ProjectListModel("21");
                WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(InvoiceRequestForCancellationsActivity.this);
                webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);
            }
        }

    }


    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {

        switch(URL){

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
                                    rejectList.getList().get(i).getRegion(),
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

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_irfc_Data_list);
                        IrfcListAdapter adapter = new IrfcListAdapter(arrayList,this);
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

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name(),projectListResponse.getProject_list().get(i).getProject_id());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name());
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
        }
    }



    private void opengcadminDialog(int position,int id) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_irfc_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView ic_d_irfc_viewInvoice = dialog.findViewById(R.id.ic_d_irfc_viewInvoice);
        ImageView tv_d_irfc_requestDetails = dialog.findViewById(R.id.tv_d_irfc_requestDetails);
        ImageView iv_d_irfc_upload = dialog.findViewById(R.id.iv_d_irfc_upload);
        ImageView iv_d_irfc_status = dialog.findViewById(R.id.iv_d_irfc_status);

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
        tv_d_irfc_InvoiceWithWhom.setText(arrayList.get(position).getIrfcInvoiceWithWhom());


        btn_d_irfc_ctn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callapproveindividualApi(arrayList.get(position).getId());
            }
        });

        btn_d_irfc_ctn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRejectindividualApi(arrayList.get(position).getId());
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
}