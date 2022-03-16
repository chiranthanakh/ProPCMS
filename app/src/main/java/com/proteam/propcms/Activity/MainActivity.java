package com.proteam.propcms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.proteam.propcms.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawer_layout;
    ImageView iv_nav_view;
    int mMonth,mDay,mYear;
    TextView tvR, tvPython, tvCPP, tvJava,btn_nav_profile;
    PieChart pieChart;
    LinearLayout ll_crnra,ll_irfm,ll_irfc;

    TextView irfc,tv_irfm,tv_ctnra;
    EditText edt_home_month;
    Spinner sp_division_home,sp_clients_home,sp_division_head_home,sp_company_home;

    LinearLayout ll_select_data,ll_filter_data;


    // horizontalchart variable for our bar chart
    BarChart barChart;

    // horizontalchart variable for our bar data set.
    BarDataSet barDataSet1, barDataSet2;

    // horizontalchart array list for storing entries.
    ArrayList barEntries;

    // horizontalchart creating a string array for displaying days.
    String[] days = new String[]{"Sep-2021", "Oct-2021","Nov-2021", "Dec-2021", "Jan-2022", "Feb-2022"};
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String user = sharedPreferences.getString("userid", null);

        if (user == null) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }*/

        initialize();
        setData();
        sp_division_home.setOnItemSelectedListener(OnCatSpinnerCL);
        sp_clients_home.setOnItemSelectedListener(OnCatSpinnerCL);
        sp_division_head_home.setOnItemSelectedListener(OnCatSpinnerCL);
        sp_company_home.setOnItemSelectedListener(OnCatSpinnerCL);

        horizontalchart();



    }



    private void horizontalchart()
    {
        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);

        // creating a new bar data set.
        barDataSet1 = new BarDataSet(getBarEntriesOne(), "First Set");
        barDataSet1.setColor(getApplicationContext().getResources().getColor(R.color.purple_200));
        barDataSet2 = new BarDataSet(getBarEntriesTwo(), "Second Set");
        barDataSet2.setColor(Color.BLUE);

        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1, barDataSet2);

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);

        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true);

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1);

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true);

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(3);

        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;

        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.5f;

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.15f);

        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);

        // below line is to
        // animate our chart.
        barChart.animate();

        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0, groupSpace, barSpace);

        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();
    }

    // horizontalChart array list for first set
    private ArrayList<BarEntry> getBarEntriesOne() {

        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 4));
        barEntries.add(new BarEntry(2f, 6));
        barEntries.add(new BarEntry(3f, 8));
        barEntries.add(new BarEntry(4f, 2));
        barEntries.add(new BarEntry(5f, 4));
        barEntries.add(new BarEntry(6f, 1));

        return barEntries;
    }

    //horizontalChart array list for second set.
    private ArrayList<BarEntry> getBarEntriesTwo() {

        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 8));
        barEntries.add(new BarEntry(2f, 12));
        barEntries.add(new BarEntry(3f, 4));
        barEntries.add(new BarEntry(4f, 1));
        barEntries.add(new BarEntry(5f, 7));
        barEntries.add(new BarEntry(6f, 3));

        return barEntries;
    }





    private void initialize()
    {




        ll_select_data=findViewById(R.id.ll_select_data);
        ll_filter_data=findViewById(R.id.ll_filter_data);
        ll_filter_data.setOnClickListener(this);
        ll_irfc=findViewById(R.id.ll_irfc);
        ll_irfc.setOnClickListener(this);
        ll_crnra=findViewById(R.id.ll_crnra);
        ll_crnra.setOnClickListener(this);
        ll_irfm=findViewById(R.id.ll_irfm);
        ll_irfm.setOnClickListener(this);

        btn_nav_profile=findViewById(R.id.btn_nav_profile);
        btn_nav_profile.setOnClickListener(this);
        sp_company_home=findViewById(R.id.sp_company_home);
        sp_division_head_home=findViewById(R.id.sp_division_head_home);
        sp_clients_home=findViewById(R.id.sp_clients_home);
        sp_division_home=findViewById(R.id.sp_division_home);
        edt_home_month=findViewById(R.id.edt_home_month);
        edt_home_month.setOnClickListener(this);

        tvR = findViewById(R.id.tvR);
        tvPython = findViewById(R.id.tvPython);
        tvCPP = findViewById(R.id.tvCPP);
        tvJava = findViewById(R.id.tvJava);
        pieChart = findViewById(R.id.piechart);
        pieChart = findViewById(R.id.piechart);

        irfc=findViewById(R.id.irfc);
        irfc.setOnClickListener(this);
        tv_irfm=findViewById(R.id.tv_irfm);
        tv_irfm.setOnClickListener(this);
        tv_ctnra=findViewById(R.id.tv_ctnra);
        tv_ctnra.setOnClickListener(this);
        drawer_layout = findViewById(R.id.drawer_layout_main);
        iv_nav_view=findViewById(R.id.iv_nav_view);
        iv_nav_view.setOnClickListener(this);
    }


    
    private void setData()
    {

        // Set the percentage of language used
        tvR.setText(Integer.toString(40));
        tvPython.setText(Integer.toString(30));
        tvCPP.setText(Integer.toString(5));
        tvJava.setText(Integer.toString(25));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt(tvR.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt(tvPython.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        Integer.parseInt(tvCPP.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        Integer.parseInt(tvJava.getText().toString()),
                        Color.parseColor("#29B6F6")));

        // To animate the pie chart
        pieChart.startAnimation();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_filter_data:


                if(ll_select_data.getVisibility() == View.VISIBLE){
                    ll_select_data.setVisibility(View.GONE);
                }else {
                    ll_select_data.setVisibility(View.VISIBLE);

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
                        mcurrentDate.set(Calendar.YEAR, year) ;
                        mcurrentDate.set(Calendar.MONTH, month);

                        edt_home_month.setText(sdf.format(mcurrentDate.getTime()));
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
            case R.id.irfc:
            case R.id.ll_irfc:
                Intent intent_irfc= new Intent(MainActivity.this, InvoiceRequestForCancellationsActivity.class);
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
                Intent intent_profile = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent_profile);
                break;

        }
    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(14);

        }

        public void onNothingSelected(AdapterView<?> parent) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            ((TextView) parent.getChildAt(0)).setTextSize(14);
        }
    };
}