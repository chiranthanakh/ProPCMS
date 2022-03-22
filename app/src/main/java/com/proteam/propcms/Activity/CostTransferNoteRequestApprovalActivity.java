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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proteam.propcms.Adapters.CtnrListAdapter;
import com.proteam.propcms.Models.Approvalmodel;
import com.proteam.propcms.Models.CtrnDataModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Response.GenerealResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.ProjectListResponse;
import com.proteam.propcms.Response.approvalresponse.ApprovalList;
import com.proteam.propcms.Utils.OnClick;
import com.proteam.propcms.Utils.OnResponseListener;
import com.proteam.propcms.Utils.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostTransferNoteRequestApprovalActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener, OnClick {
    ImageView mToolbar;
    EditText edt_from;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_ctnra;
    RecyclerView rv_ctrn_Data_list;
    TextView temp_btn_ctnr,tv_raise_indent_total_item;
    ProgressDialog progressDialog;
    ArrayList<Approvalmodel> arrayList = new ArrayList<Approvalmodel>();
    ArrayList<CtrnDataModel> arrayList2 = new ArrayList<CtrnDataModel>();
    Map map = new HashMap();
    Map projectmap = new HashMap();
    List projectList = new ArrayList();
    Button btn_approve,btn_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_transfer_note_request_approval);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());


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
        temp_btn_ctnr=findViewById(R.id.temp_btn_ctnr);
        temp_btn_ctnr.setOnClickListener(this);
        btn_reject = findViewById(R.id.ctrn_reject_btn);
        btn_reject.setOnClickListener(this);
        btn_approve = findViewById(R.id.ctrn_approve_btn);
        btn_approve.setOnClickListener(this);
        edt_from=findViewById(R.id.edt_from);
        tv_raise_indent_total_item = findViewById(R.id.tv_raise_indent_total_item);
        edt_from.setOnClickListener(this);
        sp_all_project_ctnra=findViewById(R.id.sp_all_project_ctnra);
        rv_ctrn_Data_list=findViewById(R.id.rv_ctrn_Data_list);
        callapprovalApi();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.temp_btn_ctnr:
              //  opengcadminDialog();
                break;
            case R.id.edt_from:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(CostTransferNoteRequestApprovalActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_from.setText(sdf.format(mcurrentDate.getTime()));
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

            case R.id.ctrn_approve_btn:
                callapproveApi();
                break;

            case R.id.ctrn_reject_btn:
                callRejectApi();
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

        progressDialog = new ProgressDialog(CostTransferNoteRequestApprovalActivity.this);

        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                ProjectListModel projectListModel = new ProjectListModel("21");
                WebServices<ProjectListResponse> webServices = new WebServices<ProjectListResponse>(CostTransferNoteRequestApprovalActivity.this);
                webServices.projectlist(WebServices.ApiType.projectlist,projectListModel);
            }
        }

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
                                            approvalList.getList().get(i).getFrom_pc_code(),
                                            approvalList.getList().get(i).getTo_pc_code(),
                                            approvalList.getList().get(i).getAmount(),
                                            approvalList.getList().get(i).getRemarks(),
                                            approvalList.getList().get(i).getId())
                                    );
                        };

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_ctrn_Data_list);
                        CtnrListAdapter adapter = new CtnrListAdapter(arrayList2,this);
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

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name(),projectListResponse.getProject_list().get(i).getProject_id());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name());
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

        }

        }

    private void opengcadminDialog(String position,String id) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_ctnr_all_details);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

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

        tv_dia_ctn.setText(arrayList2.get(Integer.parseInt(position)).getCtnrCtn());
        tv_dia_month.setText(arrayList2.get(Integer.parseInt(position)).getCtnrMonth());
        tv_dia_directExpense.setText(arrayList2.get(Integer.parseInt(position)).getCtnrExpenseType());
        tv_dia_fromPcCode.setText(arrayList2.get(Integer.parseInt(position)).getCtnrFromPcCode());
        tv_dia_ToPcCode.setText(arrayList2.get(Integer.parseInt(position)).getCtnrToPcCode());
        tv_dia_transferCost.setText(arrayList2.get(Integer.parseInt(position)).getCtnrTransferCost());
        tv_dia_Remarks.setText(arrayList2.get(Integer.parseInt(position)).getCtnrRemarks());
        back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        iv_dia_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openeditDialog(Integer.parseInt(position));
            }
        });

        btn_dia_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callapproveindividualApi( id);
            }
        });

        btn_dia_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRejectindividualApi(id );
            }
        });

    }

    private void openeditDialog(int position) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialoge_division_edit_verifycost_transfer);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

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
}