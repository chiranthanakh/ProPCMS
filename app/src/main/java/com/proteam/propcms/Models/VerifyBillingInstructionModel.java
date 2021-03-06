package com.proteam.propcms.Models;

public class VerifyBillingInstructionModel {

    String BIPcCode,BIgroup,BIassigmnent,BIbillTO,BIbillingAdress,BIrefrenceNumber,BIkindAttention,BIregion,BIplace
            ,BIgstinNo,BIpanOfCustomer,BItaxableAmount,BIgstRate,BIforMonth,BIdescription,BIhsnSac,BIparticulars,
            BIstateOfSupplyCode,BItransactionType,invoicedate,id,companyid,productid,invoicenumber,devisionid,totalamount,gstmonth;

    public VerifyBillingInstructionModel(String invoicedate,String BIPcCode, String BIgroup, String BIassigmnent, String BIbillTO, String BIbillingAdress, String BIrefrenceNumber, String BIkindAttention, String BIregion, String BIplace, String BIgstinNo, String BIpanOfCustomer, String BItaxableAmount, String BIgstRate, String BIforMonth, String BIdescription, String BIhsnSac, String BIparticulars, String BIstateOfSupplyCode, String BItransactionType,String id,
                                         String companyid, String productid, String invoicenumber,String devisionid,String totalamount,String gstmonth) {
        this.invoicedate = invoicedate;
        this.BIPcCode = BIPcCode;
        this.BIgroup = BIgroup;
        this.BIassigmnent = BIassigmnent;
        this.BIbillTO = BIbillTO;
        this.BIbillingAdress = BIbillingAdress;
        this.BIrefrenceNumber = BIrefrenceNumber;
        this.BIkindAttention = BIkindAttention;
        this.BIregion = BIregion;
        this.BIplace = BIplace;
        this.BIgstinNo = BIgstinNo;
        this.BIpanOfCustomer = BIpanOfCustomer;
        this.BItaxableAmount = BItaxableAmount;
        this.BIgstRate = BIgstRate;
        this.BIforMonth = BIforMonth;
        this.BIdescription = BIdescription;
        this.BIhsnSac = BIhsnSac;
        this.BIparticulars = BIparticulars;
        this.BIstateOfSupplyCode = BIstateOfSupplyCode;
        this.BItransactionType = BItransactionType;
        this.id = id;
        this.companyid = companyid;
        this.productid = productid;
        this.invoicenumber = invoicenumber;
        this.devisionid = devisionid;
        this.totalamount = totalamount;
        this.gstmonth = gstmonth;
    }

    public String getGstmonth() {
        return gstmonth;
    }

    public void setGstmonth(String gstmonth) {
        this.gstmonth = gstmonth;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getDevisionid() {
        return devisionid;
    }

    public void setDevisionid(String devisionid) {
        this.devisionid = devisionid;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getBIPcCode() {
        return BIPcCode;
    }

    public void setBIPcCode(String BIPcCode) {
        this.BIPcCode = BIPcCode;
    }

    public String getBIgroup() {
        return BIgroup;
    }

    public void setBIgroup(String BIgroup) {
        this.BIgroup = BIgroup;
    }

    public String getBIassigmnent() {
        return BIassigmnent;
    }

    public void setBIassigmnent(String BIassigmnent) {
        this.BIassigmnent = BIassigmnent;
    }

    public String getBIbillTO() {
        return BIbillTO;
    }

    public void setBIbillTO(String BIbillTO) {
        this.BIbillTO = BIbillTO;
    }

    public String getBIbillingAdress() {
        return BIbillingAdress;
    }

    public void setBIbillingAdress(String BIbillingAdress) {
        this.BIbillingAdress = BIbillingAdress;
    }

    public String getBIrefrenceNumber() {
        return BIrefrenceNumber;
    }

    public void setBIrefrenceNumber(String BIrefrenceNumber) {
        this.BIrefrenceNumber = BIrefrenceNumber;
    }

    public String getBIkindAttention() {
        return BIkindAttention;
    }

    public void setBIkindAttention(String BIkindAttention) {
        this.BIkindAttention = BIkindAttention;
    }

    public String getBIregion() {
        return BIregion;
    }

    public void setBIregion(String BIregion) {
        this.BIregion = BIregion;
    }

    public String getBIplace() {
        return BIplace;
    }

    public void setBIplace(String BIplace) {
        this.BIplace = BIplace;
    }

    public String getBIgstinNo() {
        return BIgstinNo;
    }

    public void setBIgstinNo(String BIgstinNo) {
        this.BIgstinNo = BIgstinNo;
    }

    public String getBIpanOfCustomer() {
        return BIpanOfCustomer;
    }

    public void setBIpanOfCustomer(String BIpanOfCustomer) {
        this.BIpanOfCustomer = BIpanOfCustomer;
    }

    public String getBItaxableAmount() {
        return BItaxableAmount;
    }

    public void setBItaxableAmount(String BItaxableAmount) {
        this.BItaxableAmount = BItaxableAmount;
    }

    public String getBIgstRate() {
        return BIgstRate;
    }

    public void setBIgstRate(String BIgstRate) {
        this.BIgstRate = BIgstRate;
    }

    public String getBIforMonth() {
        return BIforMonth;
    }

    public void setBIforMonth(String BIforMonth) {
        this.BIforMonth = BIforMonth;
    }

    public String getBIdescription() {
        return BIdescription;
    }

    public void setBIdescription(String BIdescription) {
        this.BIdescription = BIdescription;
    }

    public String getBIhsnSac() {
        return BIhsnSac;
    }

    public void setBIhsnSac(String BIhsnSac) {
        this.BIhsnSac = BIhsnSac;
    }

    public String getBIparticulars() {
        return BIparticulars;
    }

    public void setBIparticulars(String BIparticulars) {
        this.BIparticulars = BIparticulars;
    }

    public String getBIstateOfSupplyCode() {
        return BIstateOfSupplyCode;
    }

    public void setBIstateOfSupplyCode(String BIstateOfSupplyCode) {
        this.BIstateOfSupplyCode = BIstateOfSupplyCode;
    }

    public String getBItransactionType() {
        return BItransactionType;
    }

    public void setBItransactionType(String BItransactionType) {
        this.BItransactionType = BItransactionType;
    }

}
