package com.proteam.propcms.Request;

public class VctUpdateRequest {

    String logged_user_id,id,month,from_pc_code,to_pc_code,expense_type,amount,remarks;

    public VctUpdateRequest(String logged_user_id, String id, String month, String from_pc_code, String to_pc_code, String expense_type, String amount, String remarks) {
        this.logged_user_id = logged_user_id;
        this.id = id;
        this.month = month;
        this.from_pc_code = from_pc_code;
        this.to_pc_code = to_pc_code;
        this.expense_type = expense_type;
        this.amount = amount;
        this.remarks = remarks;
    }
}
