package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proteam.propcms.Adapters.VerifyBillingInstructionAdapter;
import com.proteam.propcms.Adapters.VerifyCostTransferAdapter;
import com.proteam.propcms.Models.VerifyBillingInstructionModel;
import com.proteam.propcms.Models.VerifyCostTransferModel;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Request.VctDeleteRequest;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyCostTransferActivity extends AppCompatActivity implements View.OnClickListener , OnResponseListener, OnClick {
    ImageView mToolbar,iv_clear_vct;
    int mMonth,mDay,mYear;
    Spinner sp_all_project_vct;
    RecyclerView rv_vct_Data_list;
    EditText edt_from_vct;
    TextView tv_count_vct;

    ProgressDialog progressDialog;
    LinearLayout ll_no_data_VCT;
    AppCompatButton btn_verifySubmitCTN;
    CheckBox ch_ctn;
    Context context=this;

    Map projectmap = new HashMap();
    List projectList = new ArrayList();

    Map map = new HashMap();



    ArrayList<VerifyCostTransferModel> arrayList = new ArrayList<VerifyCostTransferModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_cost_transfer);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

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

                            projectmap.put(projectListResponse.getProject_list().get(i).getProject_name()+" ("+projectListResponse.getProject_list().get(i).getPc_code()+")",projectListResponse.getProject_list().get(i).getPc_code());
                            projectList.add(projectListResponse.getProject_list().get(i).getProject_name()+" ( "+projectListResponse.getProject_list().get(i).getPc_code()+" )");
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

    private void opengAllDataDialog(String position,String id) {
        final Dialog dialog = new Dialog(this);
        System.out.println("id print"+ id);
        dialog.setContentView(R.layout.dialoge_division_verifycost_transfer);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

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

        tv_dia_vct_ctn.setText(verifyCostTransferModel.getVctCtn());
        tv_dia_vct_month.setText(verifyCostTransferModel.getVctmonth());
        tv_dia_vct_directExpense.setText(verifyCostTransferModel.getVctexpenseTypeName());
        tv_dia_vct_fromPcCode.setText(verifyCostTransferModel.getVctfromPcCodeName());
        tv_dia_vct_TpPcCode.setText(verifyCostTransferModel.getVcttoPcCodeName());
        tv_dia_vct_transferCost.setText(verifyCostTransferModel.getVctamount());
        tv_dia_vct_Remarks.setText(verifyCostTransferModel.getVctremarks());

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
                callDialogeSubmitCTNapi(id);
            }
        });
        ll_vct_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  callDialogueDeleteCTNapi(id);
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

        VerifyCostTransferModel verifyCostTransferModel = arrayList.get(Integer.parseInt(position));

        edt_edit_ctn.setText(verifyCostTransferModel.getVctCtn());
        edt_edit_month.setText(verifyCostTransferModel.getVctmonth());
        edt_edit_transferCost.setText(verifyCostTransferModel.getVctamount());
        edt_edit_remarks.setText(verifyCostTransferModel.getVctremarks());

        back_toolbar_ctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

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



}