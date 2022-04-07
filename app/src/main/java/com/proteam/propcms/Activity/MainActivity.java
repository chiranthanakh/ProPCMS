package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.proteam.propcms.R;
import com.proteam.propcms.Request.Clientlistrequest;
import com.proteam.propcms.Request.DashboardFilterDetailsRequest;
import com.proteam.propcms.Request.DivisionListModel;
import com.proteam.propcms.Request.MonthlyRevenueGraphrequest;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Response.ClientList;
import com.proteam.propcms.Response.CompanyListResponse;
import com.proteam.propcms.Response.DashboardCountDivisionResponse;
import com.proteam.propcms.Response.DashboardFilterDetailsResponse;
import com.proteam.propcms.Response.Dashboardcountresponse;
import com.proteam.propcms.Response.DevisionHeadList;
import com.proteam.propcms.Response.DivisionListResponse;
import com.proteam.propcms.Response.LoginResponse;
import com.proteam.propcms.Response.OutstandingAgeingListResponse;
import com.proteam.propcms.Response.RevenueChartResponse;
import com.proteam.propcms.Response.TopTenRevenueListResponse;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListener {
    DrawerLayout drawer_layout;
    ImageView iv_nav_view;
    int mMonth, mDay, mYear;
    TextView tvR, tvPython, tvCPP, tvJava, btn_nav_profile, tv_verifyBillingInstruction, tv_verifyCostTransfer, tv_nav_username;

    LinearLayout ll_crnra, ll_irfm, ll_irfc, ll_verify_BI, ll_Verify_CTN;
    Button btn_logout,btn_search_list_dash;
    TextView irfc, tv_irfm, tv_ctnra, tv_irfc_count, tv_irfm_count, tv_ctnr_count, tv_division_BI_count, tv_division_vct_count;
    EditText edt_home_month;
    Spinner sp_division_home, sp_clients_home, sp_division_head_home, sp_company_home;

    CardView cc_For_managerLogin, cc_For_divisionLogin;
    LinearLayout ll_select_data, ll_filter_data;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swiperefresh;
    Handler mHandler;

    TextView tv_filterDetails_revenue, tv_filterDetails_outStanding, tv_filterDetails_PcCode, tv_filterDetails_collection;
    List divisionList = new ArrayList();
    List revenueList = new ArrayList();
    List monthList = new ArrayList();

    List revenueList1 = new ArrayList();
    List monthList1 = new ArrayList();
    String[] values;
    String[] values2;
    ArrayList<Entry> yValues = new ArrayList<Entry>();
    ArrayList<Entry> yValuesaverage = new ArrayList<Entry>();
    LineChart lineChart_revenue_trend,lineChart_average_trend;

    List revenueClientlist = new ArrayList();
    List revenueamountlist = new ArrayList();

    // Vertical chart variable for our bar chart
    BarChart barChart;
    // Verticalchart variable for our bar data set.
    BarDataSet barDataSet1, barDataSet2;
    // Verticalchart array list for storing entries.
    ArrayList barEntries;
    // Verticalchart creating a string array for displaying days.
    String[] days = new String[]{"Sep-2021", "Oct-2021", "Nov-2021", "Dec-2021", "Jan-2022", "Feb-2022"};
    SharedPreferences.Editor editor;
    String role;
    String user;

    Map companymap = new HashMap();
    List companyList = new ArrayList();
    Map headmap = new HashMap();
    List headList = new ArrayList();
    Map clientmap = new HashMap();
    List clientList1 = new ArrayList();
    Map divisionmap = new HashMap();


    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries = new ArrayList<>();;
    ArrayList PieEntryLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user = sharedPreferences.getString("userid", null);
        role = sharedPreferences.getString("role", null);
        mHandler=new Handler();


        if (user == null) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        initialize();

        if (role.equalsIgnoreCase("manager")) {
            cc_For_divisionLogin.setVisibility(View.GONE);
            tv_verifyCostTransfer.setVisibility(View.GONE);
            tv_verifyBillingInstruction.setVisibility(View.GONE);
            tv_nav_username.setText("Jayaram DR");
        } else {
            cc_For_managerLogin.setVisibility(View.GONE);
            tv_ctnra.setVisibility(View.GONE);
            tv_irfm.setVisibility(View.GONE);
            irfc.setVisibility(View.GONE);
            sp_division_head_home.setVisibility(View.GONE);
            tv_nav_username.setText("Basavaraj");
        }





        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });

        CallTopTenRevenue();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //callMonthlyrevenue();
            }
        });
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
               // callMonthlyAverage();
            }
        });


        //////////////////////////////////// Top 10 Outstanding Client///////////////


        HorizontalBarChart topTen_outstanding = (HorizontalBarChart) findViewById(R.id.topTen_outstanding);

        topTen_outstanding.setDragEnabled(true);
        topTen_outstanding.setScaleEnabled(false);

        BarDataSet set3;
        set3 = new BarDataSet(getDataSet(), "0.00 M To 10.00 M");

        set3.setColors(Color.parseColor("#008FFB"));


        ArrayList<IBarDataSet> dataSets3 = new ArrayList<IBarDataSet>();
        dataSets3.add(set3);

        BarData data3 = new BarData(dataSets3);

        // hide Y-axis
        YAxis left = topTen_outstanding.getAxisLeft();

        left.removeAllLimitLines();
        left.setAxisMaximum(8f);

        left.setAxisMinimum(0f);
        // left.setDrawLabels(false);

        YAxis right = topTen_outstanding.getAxisRight();
        right.setDrawLabels(false);
        // custom X-axis labels

        String[] values3 = new String[] { "LARSEN & TOUBRO", "MARICI SOLAR INDIA", "AIR PLAZA RETAIL", "SANDVIK ASIA PRIVATE", "ABB GLOBAL INDUSTRIES","PUMA SPORTS INDIA","ABB INDIA LIMITED","SPML INFRA LIMITED","ABB POWER PRODUCTS","ABB POWER TECHNOLOGY"};

        XAxis xAxis3 = topTen_outstanding.getXAxis();
        xAxis3.setValueFormatter(new MyXAxisValueFormatter(values3));

        xAxis3.setGranularity(1f);
        xAxis3.setLabelCount(12);
        xAxis3.setAxisLineWidth(1);
        xAxis3.setTextSize(7);
        topTen_outstanding.setData(data3);

        // custom description
        Description descriptiont = new Description();
        descriptiont.setText("");
        topTen_outstanding.setDescription(descriptiont);

        topTen_outstanding.animateY(1000);
        topTen_outstanding.invalidate();


        ////////////////////////Outstanding Ageing Details//////////////////////////////////////////

        HorizontalBarChart graph_outstanding_ageing = (HorizontalBarChart) findViewById(R.id.graph_outstanding_ageing);

        graph_outstanding_ageing.setDragEnabled(true);
        graph_outstanding_ageing.setScaleEnabled(false);

        BarDataSet set4;
        set4 = new BarDataSet(getDataSet4(), "0.00 M To 10.00 M");

        set4.setColors(Color.parseColor("#008FFB"));


        ArrayList<IBarDataSet> dataSets4 = new ArrayList<IBarDataSet>();
        dataSets4.add(set4);

        BarData data = new BarData(dataSets4);


        // hide Y-axis
        YAxis left4 = graph_outstanding_ageing.getAxisLeft();
        // left.setDrawLabels(false);
        left4.setAxisMaximum(25f);
        left4.setAxisMinimum(0f);

        YAxis right4 = graph_outstanding_ageing.getAxisRight();
        right4.setDrawLabels(false);
        // custom X-axis labels


        String[] values4 = new String[] { ">181", "151 To 180", "121 To 150", "91 To 120", "61 To 90","31 To 60","0 To 30"};

        XAxis xAxis4 = graph_outstanding_ageing.getXAxis();
        xAxis4.setValueFormatter(new MyXAxisValueFormatter(values4));

        xAxis4.setGranularity(1f);
        xAxis4.setLabelCount(12);
        xAxis4.setAxisLineWidth(1);
        xAxis4.setTextSize(10);


        graph_outstanding_ageing.setData(data);

        // custom description
        Description description4 = new Description();
        description4.setText("");
        graph_outstanding_ageing.setDescription(description4);


        graph_outstanding_ageing.animateY(1000);
        graph_outstanding_ageing.invalidate();


    }

    private void initialize() {
        btn_search_list_dash = findViewById(R.id.btn_search_list_dash);
        btn_search_list_dash.setOnClickListener(this);
        tv_filterDetails_revenue = findViewById(R.id.tv_filterDetails_revenue);
        tv_filterDetails_outStanding = findViewById(R.id.tv_filterDetails_outStanding);
        tv_filterDetails_PcCode = findViewById(R.id.tv_filterDetails_PcCode);
        tv_filterDetails_collection = findViewById(R.id.tv_filterDetails_collection);

        tv_division_vct_count = findViewById(R.id.tv_division_vct_count);
        tv_division_BI_count = findViewById(R.id.tv_division_BI_count);
        swiperefresh = findViewById(R.id.swiperefresh);
        tv_irfc_count = findViewById(R.id.tv_irfc_count1);
        tv_irfm_count = findViewById(R.id.tv_irfm_count1);
        tv_ctnr_count = findViewById(R.id.tv_ctnr_count1);
        cc_For_divisionLogin = findViewById(R.id.cc_For_divisionLogin);
        cc_For_managerLogin = findViewById(R.id.cc_For_managerLogin);
        ll_Verify_CTN = findViewById(R.id.ll_Verify_CTN);
        ll_Verify_CTN.setOnClickListener(this);
        ll_verify_BI = findViewById(R.id.ll_verify_BI);
        tv_nav_username = findViewById(R.id.tv_nav_username);
        ll_verify_BI.setOnClickListener(this);
        tv_verifyBillingInstruction = findViewById(R.id.tv_verifyBillingInstruction);
        tv_verifyBillingInstruction.setOnClickListener(this);
        tv_verifyCostTransfer = findViewById(R.id.tv_verifyCostTransfer);
        tv_verifyCostTransfer.setOnClickListener(this);
        ll_select_data = findViewById(R.id.ll_select_data);
        ll_select_data.setVisibility(View.GONE);
        ll_filter_data = findViewById(R.id.ll_filter_data);
        ll_filter_data.setOnClickListener(this);
        ll_irfc = findViewById(R.id.ll_irfc);
        ll_irfc.setOnClickListener(this);
        ll_crnra = findViewById(R.id.ll_crnra);
        ll_crnra.setOnClickListener(this);
        ll_irfm = findViewById(R.id.ll_irfm);
        ll_irfm.setOnClickListener(this);
        btn_logout = findViewById(R.id.btn_nav_logout);
        btn_logout.setOnClickListener(this);
        btn_nav_profile = findViewById(R.id.btn_nav_profile);
        btn_nav_profile.setOnClickListener(this);
        sp_company_home = findViewById(R.id.sp_company_home);
        sp_division_head_home = findViewById(R.id.sp_division_head_home);
        sp_clients_home = findViewById(R.id.sp_clients_home);
        sp_division_home = findViewById(R.id.sp_division_home);
        edt_home_month = findViewById(R.id.edt_home_month);
        edt_home_month.setOnClickListener(this);

        Calendar mcurrentDate = Calendar.getInstance();
        String myFormat = "MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        //mcurrentDate.set(Calendar.YEAR, year);
        //mcurrentDate.set(Calendar.MONTH, month);

        edt_home_month.setText(sdf.format(mcurrentDate.get(mMonth)));

        tvR = findViewById(R.id.tvR);
        tvPython = findViewById(R.id.tvPython);
        tvCPP = findViewById(R.id.tvCPP);
        tvJava = findViewById(R.id.tvJava);
        pieChart = findViewById(R.id.piechart);


        irfc = findViewById(R.id.irfc);
        irfc.setOnClickListener(this);
        tv_irfm = findViewById(R.id.tv_irfm);
        tv_irfm.setOnClickListener(this);
        tv_ctnra = findViewById(R.id.tv_ctnra);
        tv_ctnra.setOnClickListener(this);
        drawer_layout = findViewById(R.id.drawer_layout_main);
        iv_nav_view = findViewById(R.id.iv_nav_view);
        iv_nav_view.setOnClickListener(this);
        //callcompanyapi();
        // callDheadapi();
        // callDivisionListApi();
        calldashboardcount();
        callDashboardFilterDetails();


    }


    ////////////////////Top 10 Out Standing Client //////////////////////////


    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }

    }

    private ArrayList<BarEntry> getDataSet() {

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();


        BarEntry v1e1 = new BarEntry(0, 1.47f);
        valueSet1.add(v1e1);

        BarEntry v1e2 = new BarEntry(1, 1.53f);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(2, 1.57f);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3, 1.61f);
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(4, 1.82f);
        valueSet1.add(v1e5);

        BarEntry v1e6 = new BarEntry(5, 1.86f);
        valueSet1.add(v1e6);

        BarEntry v1e7 = new BarEntry(6, 4.75f);
        valueSet1.add(v1e7);

        BarEntry v1e8 = new BarEntry(7, 5.48f);
        valueSet1.add(v1e8);

        BarEntry v1e9 = new BarEntry(8, 6.05f);
        valueSet1.add(v1e9);

        BarEntry v1e10 = new BarEntry(9, 6.47f);
        valueSet1.add(v1e10);

        return valueSet1;



    }


    /////////////////////////////Outstanding Ageing Details////////////////////


    private ArrayList<BarEntry> getDataSet4() {

        ArrayList<BarEntry> valueSet4 = new ArrayList<>();


        BarEntry v1e1 = new BarEntry(0, 20.44f);
        valueSet4.add(v1e1);

        BarEntry v1e2 = new BarEntry(1, 16.46f);
        valueSet4.add(v1e2);
        BarEntry v1e3 = new BarEntry(2, 6.48f);
        valueSet4.add(v1e3);
        BarEntry v1e4 = new BarEntry(3, 0.0f);
        valueSet4.add(v1e4);
        BarEntry v1e5 = new BarEntry(4, 0.0f);
        valueSet4.add(v1e5);

        BarEntry v1e6 = new BarEntry(5, 0.0f);
        valueSet4.add(v1e6);

        BarEntry v1e7 = new BarEntry(6, 0.0f);
        valueSet4.add(v1e7);

        return valueSet4;


    }



    //////////////////////// Revenue Trends by Month Chart////////////////

    public class MyAxisValueFormatter implements IAxisValueFormatter
    {

        private String[] mValues;
        public MyAxisValueFormatter(String[] values)
        {
            this.mValues = values;

        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_filter_data:


                if (ll_select_data.getVisibility() == View.VISIBLE) {
                    ll_select_data.setVisibility(View.GONE);
                } else {
                    ll_select_data.setVisibility(View.VISIBLE);

                    callcompanyapi();
                    callDivisionListApi();
                    callDheadapi();
                    callclientapi();
                }
                break;

            case R.id.edt_home_month:
                Calendar mcurrentDate = Calendar.getInstance();
                String myFormat = "MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                DatePickerDialog monthDatePickerDialog = new DatePickerDialog(MainActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mcurrentDate.set(Calendar.YEAR, year);
                        mcurrentDate.set(Calendar.MONTH, month);
                        edt_home_month.setText(sdf.format(mcurrentDate.getTime()));
                        mDay = dayOfMonth;
                        mMonth = month;
                        mYear = year;

                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DATE)) {
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                    }
                };
                monthDatePickerDialog.setTitle("Select Month And Year");
                monthDatePickerDialog.show();
                break;

            case R.id.ll_irfc:
            case R.id.irfc:
                Intent intent_irfc = new Intent(MainActivity.this, InvoiceRequestForCancellationsActivity.class);
                startActivity(intent_irfc);
                break;
            case R.id.tv_irfm:
            case R.id.ll_irfm:
                Intent intent_irfm = new Intent(MainActivity.this, InvoiceRequestForModificationsActivity.class);
                startActivity(intent_irfm);
                break;
            case R.id.tv_ctnra:
            case R.id.ll_crnra:
                Intent intent_ctnra = new Intent(MainActivity.this, CostTransferNoteRequestApprovalActivity.class);
                startActivity(intent_ctnra);
                break;
            case R.id.iv_nav_view:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_nav_profile:
                Intent intent_profile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent_profile);
                break;


            case R.id.tv_verifyBillingInstruction:
                Intent intent_vbi1 = new Intent(MainActivity.this, VerifyBillingInstructionsActivity.class);
                startActivity(intent_vbi1);
                break;

            case R.id.btn_search_list_dash:

                if(sp_clients_home.getSelectedItem()!=null){

                    if(sp_division_home.getSelectedItem()!=null){

                        if(sp_company_home.getSelectedItem()!=null){

                            callDashboardFilterDetails2();

                        }else {

                            Toast.makeText(this, "Select Company", Toast.LENGTH_SHORT).show();
                        }

                    }else {

                        Toast.makeText(this, "Select Division", Toast.LENGTH_SHORT).show();
                    }


                }else {

                    Toast.makeText(this, "Select client", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.ll_verify_BI:
                Intent intent_vbi = new Intent(MainActivity.this, VerifyBillingInstructionsActivity.class);
                startActivity(intent_vbi);
                break;

            case R.id.tv_verifyCostTransfer:
                Intent intent_vcy1 = new Intent(MainActivity.this, VerifyCostTransferActivity.class);
                startActivity(intent_vcy1);
                break;

            case R.id.ll_Verify_CTN:
                Intent intent_vcy = new Intent(MainActivity.this, VerifyCostTransferActivity.class);
                startActivity(intent_vcy);
                break;

            case R.id.btn_nav_logout:
                editor.clear();
                editor.apply();
                editor.commit();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;

        }
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {

        switch (URL) {

            case countitem:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if (response != null) {

                        Dashboardcountresponse dashboardcountresponse = (Dashboardcountresponse) response;

                        tv_irfm_count.setText(dashboardcountresponse.getInvoice_req_for_modification());
                        tv_irfc_count.setText(dashboardcountresponse.getInvoice_request_for_cancellation());
                        tv_ctnr_count.setText(dashboardcountresponse.getCost_transfer_note_request_for_approval());


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case companylist:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    CompanyListResponse companyListResponse = (CompanyListResponse) response;
                    List list = new ArrayList();
                    list = companyListResponse.getCompany_list();

                    if (response != null) {

                        for (int i = 0; i < list.size(); i++) {

                            companyList.add(companyListResponse.getCompany_list().get(i).getCompany_name());
                            companymap.put(companyListResponse.getCompany_list().get(i).getCompany_name(), companyListResponse.getCompany_list().get(i).getCompany_id());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, companyList);
                        sp_company_home.setAdapter(adapter);

                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }

                break;

            case headlist:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    DevisionHeadList devisionHeadList = (DevisionHeadList) response;
                    List list = new ArrayList();
                    list = devisionHeadList.getHead_list();

                    if (response != null) {

                        for (int i = 0; i < list.size(); i++) {

                            headList.add(devisionHeadList.getHead_list().get(i).getHead_name());
                            headmap.put(devisionHeadList.getHead_list().get(i).getHead_name(), devisionHeadList.getHead_list().get(i).getId());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, headList);
                        sp_division_head_home.setAdapter(adapter);

                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
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

                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, divisionList);
                        sp_division_home.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;


            case client:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (isSucces) {
                    if (response != null) {

                        List listwe = new ArrayList();
                        ClientList clientList = (ClientList) response;

                        listwe = clientList.getClient_list();

                        for (int i = 0; i < listwe.size(); i++) {

                            clientList1.add(clientList.getClient_list().get(i).getClient_code());
                            clientmap.put(clientList.getClient_list().get(i).getClient_code(),clientList.getClient_list().get(i).getClient_id());
                        }

                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, clientList1);
                        sp_clients_home.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }


                break;


            case divisioncountdashboard:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if (response != null) {

                        DashboardCountDivisionResponse dashboardCountDivisionResponse = (DashboardCountDivisionResponse) response;

                        tv_division_BI_count.setText(dashboardCountDivisionResponse.getVerify_billing_instructions_with_status_list());
                        tv_division_vct_count.setText(dashboardCountDivisionResponse.getVerify_cost_transfer_note_with_status());


                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case dashboardfilterdetails:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if (response != null) {

                        DashboardFilterDetailsResponse dashboardFilterDetailsResponse = (DashboardFilterDetailsResponse) response;

                        if(dashboardFilterDetailsResponse.getTotal_revenue()!=null){
                            float amount = Float.parseFloat(dashboardFilterDetailsResponse.getTotal_revenue());
                            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
                            String moneyString = formatter.format(amount);
                            tv_filterDetails_revenue.setText(moneyString);
                        }

                        if(dashboardFilterDetailsResponse.getTotal_outstanding()!=null){
                            float amount2 = Float.parseFloat(dashboardFilterDetailsResponse.getTotal_outstanding());
                            NumberFormat formatter2 = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
                            String moneyString2 = formatter2.format(amount2);
                            tv_filterDetails_outStanding.setText(moneyString2);
                        }

                        if(dashboardFilterDetailsResponse.getTotal_collection()!=null){
                            float amount3 = Float.parseFloat(dashboardFilterDetailsResponse.getTotal_collection());
                            NumberFormat formatter3 = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
                            String moneyString3 = formatter3.format(amount3);
                            tv_filterDetails_PcCode.setText(dashboardFilterDetailsResponse.getNew_pc_code());
                            tv_filterDetails_collection.setText(moneyString3);
                        }

                        //callMonthlyrevenue();

                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case mRevenue:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if (response != null) {

                        revenueList.clear();
                        monthList.clear();
                        yValues.clear();

                        RevenueChartResponse revenueChartResponse = (RevenueChartResponse) response;

                        revenueList = revenueChartResponse.getData();
                        monthList = revenueChartResponse.getMonth();
                        values = (String[]) monthList.toArray(new String[0]);
                        for(int i=1;i<=revenueList.size();i++){
                            //  String vacsd = formatNumber(Long.parseLong(revenueChartResponse.getData().get(i)));
                            yValues.add(new Entry(i,Float.parseFloat(revenueChartResponse.getData().get(i-1))));
                        }


                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                monthlyrevenuegraphs();
                            }
                        });
                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case mAveragerevenu:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if (response != null) {

                        revenueList1.clear();
                        monthList1.clear();
                        yValuesaverage.clear();

                        RevenueChartResponse revenueChartResponse = (RevenueChartResponse) response;

                         revenueList1 = revenueChartResponse.getData();
                         monthList1 = revenueChartResponse.getMonth();

                         values2 = (String[]) monthList.toArray(new String[0]);
                       // yValuesaverage.add(new Entry(0,Float.parseFloat("0")));

                        for(int i=0;i<revenueList1.size();i++){
                           // float dfvav = Float.parseFloat(truncateNumber(Float.parseFloat(revenueChartResponse.getData().get(i))));
                          // String vacsd = formatNumber(Long.parseLong(revenueChartResponse.getData().get(i)));
                            yValuesaverage.add(new Entry(i,Math.round(Float.parseFloat(revenueChartResponse.getData().get(i)))));
                        }


                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                averagerevenuegraphs();
                            }
                        });
                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }
                break;

            case mTopTenRevenue:

                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                if (isSucces) {

                    if (response != null) {

                        List list = new ArrayList();

                        TopTenRevenueListResponse topTenRevenueListResponse = (TopTenRevenueListResponse) response;

                        list = topTenRevenueListResponse.getGraph_data();

                        for (int i=0; i<list.size();i++){

                            revenueClientlist.add(topTenRevenueListResponse.getGraph_data().get(i).getClient_name());
                            revenueamountlist.add(topTenRevenueListResponse.getGraph_data().get(i).getAmt());

                            pieEntries.add(new PieEntry(Float.parseFloat(topTenRevenueListResponse.getGraph_data().get(i).getAmt()), i));
                            //pieEntries.add(new PieEntry(15.0f, i));

                        }

                        setpiechart();

                    } else {
                        Toast.makeText(this, "Server busy", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }

                break;
        }

    }



    ////////////////////////+++++++++++++++++++++API calling +++++++++++++++++++++++++=///////////////////////////


    private void CallOutStandingAgeing() {

        MonthlyRevenueGraphrequest monthlyRevenueGraphrequest = new MonthlyRevenueGraphrequest(user,"2022-03","","","","");
        WebServices<OutstandingAgeingListResponse> webServices = new WebServices<OutstandingAgeingListResponse>(MainActivity.this);
        webServices.OutstandingAgeingList(WebServices.ApiType.OutstandingAgeing, monthlyRevenueGraphrequest);


    }

    private void CallTopTenOutStanding() {

        MonthlyRevenueGraphrequest monthlyRevenueGraphrequest = new MonthlyRevenueGraphrequest(user,"2022-03","","","","");
        WebServices<TopTenRevenueListResponse> webServices = new WebServices<TopTenRevenueListResponse>(MainActivity.this);
        webServices.TopTenOutstandingList(WebServices.ApiType.mTopTenOutstanding, monthlyRevenueGraphrequest);


    }

    private void CallTopTenRevenue() {

        MonthlyRevenueGraphrequest monthlyRevenueGraphrequest = new MonthlyRevenueGraphrequest(user,"2022-03","","","","");
        WebServices<TopTenRevenueListResponse> webServices = new WebServices<TopTenRevenueListResponse>(MainActivity.this);
        webServices.TopTenRevenueList(WebServices.ApiType.mTopTenRevenue, monthlyRevenueGraphrequest);


    }

    private void callMonthlyrevenue() {

        MonthlyRevenueGraphrequest monthlyRevenueGraphrequest = new MonthlyRevenueGraphrequest(user,"2022-03","","","","");
        WebServices<DashboardFilterDetailsResponse> webServices = new WebServices<DashboardFilterDetailsResponse>(MainActivity.this);
        webServices.MonthlyRevenue(WebServices.ApiType.mRevenue, monthlyRevenueGraphrequest);


    }

    private void callMonthlyAverage() {

        MonthlyRevenueGraphrequest monthlyRevenueGraphrequest = new MonthlyRevenueGraphrequest(user,"2022-03","","","","");
        WebServices<DashboardFilterDetailsResponse> webServices = new WebServices<DashboardFilterDetailsResponse>(MainActivity.this);
        webServices.AverageMonthChart(WebServices.ApiType.mAveragerevenu, monthlyRevenueGraphrequest);


    }
    private void callDashboardFilterDetails() {

        DashboardFilterDetailsRequest dashboardFilterDetailsRequest = new DashboardFilterDetailsRequest(user, "2022-03",
             "","","");
        WebServices<DashboardFilterDetailsResponse> webServices = new WebServices<DashboardFilterDetailsResponse>(MainActivity.this);
        webServices.dashboardFilter(WebServices.ApiType.dashboardfilterdetails, dashboardFilterDetailsRequest);


    }

    private void callDashboardFilterDetails2() {

        progressDialog = new ProgressDialog(MainActivity.this);

         if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();


                DashboardFilterDetailsRequest dashboardFilterDetailsRequest = new DashboardFilterDetailsRequest(user, "2021-07",
                        String.valueOf(clientmap.get(sp_clients_home.getSelectedItem().toString())), String.valueOf(divisionmap.get(sp_division_home.getSelectedItem().toString())),
                        String.valueOf(companymap.get(sp_company_home.getSelectedItem().toString()))
                );
                WebServices<DashboardFilterDetailsResponse> webServices = new WebServices<DashboardFilterDetailsResponse>(MainActivity.this);
                webServices.dashboardFilter(WebServices.ApiType.dashboardfilterdetails, dashboardFilterDetailsRequest);

            }
        }

    }

    private void calldashboardcount() {

        progressDialog = new ProgressDialog(MainActivity.this);

        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                UserIdRequest userIdRequest = new UserIdRequest(user);
                if (role.equalsIgnoreCase("manager")) {
                    WebServices<Dashboardcountresponse> webServices = new WebServices<Dashboardcountresponse>(MainActivity.this);
                    webServices.dashboardcount(WebServices.ApiType.countitem, userIdRequest);

                } else {

                    WebServices<DashboardCountDivisionResponse> webServices = new WebServices<DashboardCountDivisionResponse>(MainActivity.this);
                    webServices.dashboardcountDivision(WebServices.ApiType.divisioncountdashboard, userIdRequest);

                }

            }
        }


    }

    private void callDivisionListApi() {

        DivisionListModel divisionListModel = new DivisionListModel(user);
        WebServices<DivisionListResponse> webServices = new WebServices<DivisionListResponse>(MainActivity.this);
        webServices.divisionlist(WebServices.ApiType.divisionlist, divisionListModel);

    }

    private void callcompanyapi() {


        progressDialog = new ProgressDialog(MainActivity.this);
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(MainActivity.this);
                webServices.companylist(WebServices.ApiType.companylist);
            } else {

            }
        }
    }

    private void callclientapi() {


        Clientlistrequest clientlistrequest = new Clientlistrequest(user, "");
        WebServices<ClientList> webServices = new WebServices<ClientList>(MainActivity.this);
        webServices.ClientList(WebServices.ApiType.client, clientlistrequest);

       /* progressDialog=new ProgressDialog(MainActivity.this);
        if(progressDialog!=null)
        {
            if(!progressDialog.isShowing())
            {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(MainActivity.this);
                webServices.headlist( WebServices.ApiType.headlist );
            }
            else {

            }
        }*/
    }

    private void callDheadapi() {

        WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(MainActivity.this);
        webServices.headlist(WebServices.ApiType.headlist);

       /* progressDialog=new ProgressDialog(MainActivity.this);
        if(progressDialog!=null)
        {
            if(!progressDialog.isShowing())
            {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                WebServices<LoginResponse> webServices = new WebServices<LoginResponse>(MainActivity.this);
                webServices.headlist( WebServices.ApiType.headlist );
            }
            else {

            }
        }*/
    }




    ///////////////////////// Chart Section ///////////////////////////////////


    private void setpiechart(){

        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        //pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setColors(Color.parseColor("#81c784"),
                Color.parseColor("#e57373"),
                Color.parseColor("#feb019"),
                Color.parseColor("#ff4560"),
                Color.parseColor("#b3e5fc"),
                Color.parseColor("#008ffb"),
                Color.parseColor("#e08e0b"),
                Color.parseColor("#008744"),
                Color.parseColor("#ff0000"),
                Color.parseColor("#234288"));
        pieDataSet.setSliceSpace(0f);
        pieDataSet.setValueFormatter(new LargeValueFormatter());
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(5f);
        pieDataSet.setSliceSpace(0f);


        sp_division_home.setOnItemSelectedListener(OnCatSpinnerCL);
        sp_clients_home.setOnItemSelectedListener(OnCatSpinnerCL);
        sp_division_head_home.setOnItemSelectedListener(OnCatSpinnerCL);
        sp_company_home.setOnItemSelectedListener(OnCatSpinnerCL);
    }

    private void monthlyrevenuegraphs() {

        //////////////////////// Revenue Trends by Month Chart////////////////
        lineChart_revenue_trend = findViewById(R.id.lineChart_revenue_trend);

        lineChart_revenue_trend.setDragEnabled(true);
        lineChart_revenue_trend.setScaleEnabled(false);

        YAxis leftAxis = lineChart_revenue_trend.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setAxisMaximum(40000000);
        // leftAxis.setAxisMinimum(0f);
        YAxis rightAxis = lineChart_revenue_trend.getAxisRight();
        rightAxis.setAxisMaximum(2500000);
        rightAxis.setDrawLabels(false);
        LineDataSet set1 =new LineDataSet(yValues,"Revenue Trends by Month");

        // set1.setFillAlpha(110);
        set1.setColor(Color.parseColor("#3498db"));
        set1.setLineWidth(6f);
        set1.setValueTextSize(14f);
        //  set1.enableDashedLine(10f,10f,0f);
        set1.setValueFormatter(new LargeValueFormatter());
        set1.setValueTextColor(Color.parseColor("#000000"));
        set1.setCircleColor(Color.GREEN);
        set1.setCircleColorHole(Color.GREEN);
        set1.setCircleRadius(3f);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        lineChart_revenue_trend.setData(data);

       //String[] values = new String[]{"Oct-2021","Nov-2021","Dec-2021","Jan-2022","Feb-2022","Mar-2022"};

        Description description = new Description();
        description.setText("");
        lineChart_revenue_trend.setDescription(description);

        XAxis xAxis = lineChart_revenue_trend.getXAxis();
        xAxis.setValueFormatter(new LargeValueFormatter());
        xAxis.setValueFormatter(new MyAxisValueFormatter(values));
        xAxis.setTextSize(9f);
        // xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart_revenue_trend.invalidate();
        lineChart_revenue_trend.refreshDrawableState();
    }

    private void averagerevenuegraphs() {

        lineChart_average_trend = findViewById(R.id.lineChart_average_trend);

        lineChart_average_trend.setDragEnabled(true);
        lineChart_average_trend.setScaleEnabled(false);

        YAxis leftAxis1 = lineChart_average_trend.getAxisLeft();
        leftAxis1.removeAllLimitLines();
        leftAxis1.setValueFormatter(new LargeValueFormatter());
        leftAxis1.setAxisMaximum(6164482);
        leftAxis1.setAxisMinimum(1574321);


        YAxis rightAxis1 = lineChart_average_trend.getAxisRight();
        rightAxis1.setAxisMaximum(6164482);
        rightAxis1.setAxisMinimum(1574321);
        rightAxis1.setDrawLabels(false);
        LineDataSet set2 =new LineDataSet(yValuesaverage,"Average Trends By Month");

        // set1.setFillAlpha(110);
        set2.setColor(Color.parseColor("#3498db"));
        set2.setLineWidth(6f);
        set2.setValueTextSize(8f);
        set2.enableDashedLine(10f,8f,0f);

        set2.setValueFormatter(new LargeValueFormatter());
        set2.setValueTextColor(Color.parseColor("#000000"));
        set2.setCircleColor(Color.GREEN);
        set2.setCircleColorHole(Color.GREEN);
        set2.setCircleRadius(3f);


        ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
        dataSets1.add(set2);

        LineData data1 = new LineData(dataSets1);
        lineChart_average_trend.setData(data1);


        Description description1 = new Description();
        description1.setText("");
        lineChart_average_trend.setDescription(description1);

        XAxis xAxis1 = lineChart_average_trend.getXAxis();
        xAxis1.setValueFormatter(new LargeValueFormatter());
        xAxis1.setValueFormatter(new MyAxisValueFormatter(values2));
        xAxis1.setTextSize(9f);
        xAxis1.setGranularityEnabled(true);
        // xAxis.setGranularity(1);
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart_average_trend.invalidate();
        lineChart_average_trend.refreshDrawableState();

    }

    //////////////////Top Ten Revenue Graph///////////////////

    private void getEntries() {
       /* pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(2f, 0));
        pieEntries.add(new PieEntry(4f, 1));
        pieEntries.add(new PieEntry(6f, 2));
        pieEntries.add(new PieEntry(8f, 3));
        pieEntries.add(new PieEntry(7f, 4));
        pieEntries.add(new PieEntry(3f, 5));*/
    }





    ////////////Third Graph////////////

    public static String formatNumber(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c", count / Math.pow(1000, exp),"kMGTPE".charAt(exp-1));
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


    public String truncateNumber(float floatNumber) {
        long million = 1000000L;
        long billion = 1000000000L;
        long trillion = 1000000000000L;
        long number = Math.round(floatNumber);
        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return String.valueOf(fraction);
        } else if ((number >= billion) && (number < trillion)) {
            float fraction = calculateFraction(number, billion);
            return String.valueOf(fraction);
        }
        return Long.toString(number);
    }

    public float calculateFraction(long number, long divisor) {
        long truncate = (number * 10L + (divisor / 2L)) / divisor;
        float fraction = (float) truncate * 0.10F;
        return fraction;
    }


}