package com.proteam.propcms.Models;

public class VerifyCostTransferModel {

    String vctCtn,vctMonth,vctExpenseType,vctFromPcCode,vctToPcCode,vctTransferCost,vctRemarks;

    public VerifyCostTransferModel(String vctCtn, String vctMonth, String vctExpenseType, String vctFromPcCode, String vctToPcCode, String vctTransferCost, String vctRemarks) {
        this.vctCtn = vctCtn;
        this.vctMonth = vctMonth;
        this.vctExpenseType = vctExpenseType;
        this.vctFromPcCode = vctFromPcCode;
        this.vctToPcCode = vctToPcCode;
        this.vctTransferCost = vctTransferCost;
        this.vctRemarks = vctRemarks;
    }

    public String getVctCtn() {
        return vctCtn;
    }

    public void setVctCtn(String vctCtn) {
        this.vctCtn = vctCtn;
    }

    public String getVctMonth() {
        return vctMonth;
    }

    public void setVctMonth(String vctMonth) {
        this.vctMonth = vctMonth;
    }

    public String getVctExpenseType() {
        return vctExpenseType;
    }

    public void setVctExpenseType(String vctExpenseType) {
        this.vctExpenseType = vctExpenseType;
    }

    public String getVctFromPcCode() {
        return vctFromPcCode;
    }

    public void setVctFromPcCode(String vctFromPcCode) {
        this.vctFromPcCode = vctFromPcCode;
    }

    public String getVctToPcCode() {
        return vctToPcCode;
    }

    public void setVctToPcCode(String vctToPcCode) {
        this.vctToPcCode = vctToPcCode;
    }

    public String getVctTransferCost() {
        return vctTransferCost;
    }

    public void setVctTransferCost(String vctTransferCost) {
        this.vctTransferCost = vctTransferCost;
    }

    public String getVctRemarks() {
        return vctRemarks;
    }

    public void setVctRemarks(String vctRemarks) {
        this.vctRemarks = vctRemarks;
    }
}
