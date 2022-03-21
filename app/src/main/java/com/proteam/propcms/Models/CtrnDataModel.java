package com.proteam.propcms.Models;

public class CtrnDataModel {



    private String ctnrCtn,ctnrMonth,ctnrExpenseType,ctnrFromPcCode,ctnrToPcCode,ctnrTransferCost,ctnrRemarks,id;

    public CtrnDataModel(String ctnrCtn, String ctnrMonth, String ctnrExpenseType, String ctnrFromPcCode, String ctnrToPcCode, String ctnrTransferCost, String ctnrRemarks, String id) {
        this.ctnrCtn = ctnrCtn;
        this.ctnrMonth = ctnrMonth;
        this.ctnrExpenseType = ctnrExpenseType;
        this.ctnrFromPcCode = ctnrFromPcCode;
        this.ctnrToPcCode = ctnrToPcCode;
        this.ctnrTransferCost = ctnrTransferCost;
        this.ctnrRemarks = ctnrRemarks;
        this.id = id;
    }

    public String getCtnrCtn() {
        return ctnrCtn;
    }

    public void setCtnrCtn(String ctnrCtn) {
        this.ctnrCtn = ctnrCtn;
    }

    public String getCtnrMonth() {
        return ctnrMonth;
    }

    public void setCtnrMonth(String ctnrMonth) {
        this.ctnrMonth = ctnrMonth;
    }

    public String getCtnrExpenseType() {
        return ctnrExpenseType;
    }

    public void setCtnrExpenseType(String ctnrExpenseType) {
        this.ctnrExpenseType = ctnrExpenseType;
    }

    public String getCtnrFromPcCode() {
        return ctnrFromPcCode;
    }

    public void setCtnrFromPcCode(String ctnrFromPcCode) {
        this.ctnrFromPcCode = ctnrFromPcCode;
    }

    public String getCtnrToPcCode() {
        return ctnrToPcCode;
    }

    public void setCtnrToPcCode(String ctnrToPcCode) {
        this.ctnrToPcCode = ctnrToPcCode;
    }

    public String getCtnrTransferCost() {
        return ctnrTransferCost;
    }

    public void setCtnrTransferCost(String ctnrTransferCost) {
        this.ctnrTransferCost = ctnrTransferCost;
    }

    public String getCtnrRemarks() {
        return ctnrRemarks;
    }

    public void setCtnrRemarks(String ctnrRemarks) {
        this.ctnrRemarks = ctnrRemarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
