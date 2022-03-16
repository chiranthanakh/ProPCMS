package com.proteam.propcms.Models;

public class CtrnDataModel {


    private int ctnrAllDetails,ctnrActionEdit,ctnrActionDelete,ctnrUpload,ctnrStatusModification;
    private String ctnrCtn,ctnrMonth,ctnrExpenseType,ctnrFromPcCode,ctnrToPcCode,ctnrTransferCost,ctnrRemarks;

    public CtrnDataModel(int ctnrAllDetails, int ctnrActionEdit, int ctnrActionDelete, int ctnrUpload, int ctnrStatusModification, String ctnrCtn, String ctnrMonth, String ctnrExpenseType, String ctnrFromPcCode, String ctnrToPcCode, String ctnrTransferCost, String ctnrRemarks) {
        this.ctnrAllDetails = ctnrAllDetails;
        this.ctnrActionEdit = ctnrActionEdit;
        this.ctnrActionDelete = ctnrActionDelete;
        this.ctnrUpload = ctnrUpload;
        this.ctnrStatusModification = ctnrStatusModification;
        this.ctnrCtn = ctnrCtn;
        this.ctnrMonth = ctnrMonth;
        this.ctnrExpenseType = ctnrExpenseType;
        this.ctnrFromPcCode = ctnrFromPcCode;
        this.ctnrToPcCode = ctnrToPcCode;
        this.ctnrTransferCost = ctnrTransferCost;
        this.ctnrRemarks = ctnrRemarks;
    }

    public int getCtnrAllDetails() {
        return ctnrAllDetails;
    }

    public void setCtnrAllDetails(int ctnrAllDetails) {
        this.ctnrAllDetails = ctnrAllDetails;
    }

    public int getCtnrActionEdit() {
        return ctnrActionEdit;
    }

    public void setCtnrActionEdit(int ctnrActionEdit) {
        this.ctnrActionEdit = ctnrActionEdit;
    }

    public int getCtnrActionDelete() {
        return ctnrActionDelete;
    }

    public void setCtnrActionDelete(int ctnrActionDelete) {
        this.ctnrActionDelete = ctnrActionDelete;
    }

    public int getCtnrUpload() {
        return ctnrUpload;
    }

    public void setCtnrUpload(int ctnrUpload) {
        this.ctnrUpload = ctnrUpload;
    }

    public int getCtnrStatusModification() {
        return ctnrStatusModification;
    }

    public void setCtnrStatusModification(int ctnrStatusModification) {
        this.ctnrStatusModification = ctnrStatusModification;
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
}
