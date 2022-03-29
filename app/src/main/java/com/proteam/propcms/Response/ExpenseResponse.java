package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExpenseResponse {

    @SerializedName("expense_type_list")
    @Expose
    private ArrayList<ExpenseListResponse> expense_type_list = null;

    public ArrayList<ExpenseListResponse> getExpense_type_list() {
        return expense_type_list;
    }

    public void setExpense_type_list(ArrayList<ExpenseListResponse> expense_type_list) {
        this.expense_type_list = expense_type_list;
    }
}
