package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseListResponse {

    @SerializedName("expense_type_id")
    @Expose
    private String expense_type_id;

    @SerializedName("expense_type_name")
    @Expose
    private String expense_type_name;

    public String getExpense_type_id() {
        return expense_type_id;
    }

    public void setExpense_type_id(String expense_type_id) {
        this.expense_type_id = expense_type_id;
    }

    public String getExpense_type_name() {
        return expense_type_name;
    }

    public void setExpense_type_name(String expense_type_name) {
        this.expense_type_name = expense_type_name;
    }
}
