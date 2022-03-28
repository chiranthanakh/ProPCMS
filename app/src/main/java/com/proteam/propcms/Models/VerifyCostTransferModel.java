package com.proteam.propcms.Models;

public class VerifyCostTransferModel {

    String  vctCtn,vctmonth,vctexpenseTypeName,vctfromPcCodeName,vcttoPcCodeName,vctamount,vctremarks,id,vctuserId,vctuploadUserId;

    public VerifyCostTransferModel(String vctCtn, String vctmonth, String vctexpenseTypeName, String vctfromPcCodeName, String vcttoPcCodeName, String vctamount, String vctremarks, String id, String vctuserId, String vctuploadUserId) {
        this.vctCtn = vctCtn;
        this.vctmonth = vctmonth;
        this.vctexpenseTypeName = vctexpenseTypeName;
        this.vctfromPcCodeName = vctfromPcCodeName;
        this.vcttoPcCodeName = vcttoPcCodeName;
        this.vctamount = vctamount;
        this.vctremarks = vctremarks;
        this.id = id;
        this.vctuserId = vctuserId;
        this.vctuploadUserId = vctuploadUserId;
    }

    public String getVctCtn() {
        return vctCtn;
    }

    public void setVctCtn(String vctCtn) {
        this.vctCtn = vctCtn;
    }

    public String getVctmonth() {
        return vctmonth;
    }

    public void setVctmonth(String vctmonth) {
        this.vctmonth = vctmonth;
    }

    public String getVctexpenseTypeName() {
        return vctexpenseTypeName;
    }

    public void setVctexpenseTypeName(String vctexpenseTypeName) {
        this.vctexpenseTypeName = vctexpenseTypeName;
    }

    public String getVctfromPcCodeName() {
        return vctfromPcCodeName;
    }

    public void setVctfromPcCodeName(String vctfromPcCodeName) {
        this.vctfromPcCodeName = vctfromPcCodeName;
    }

    public String getVcttoPcCodeName() {
        return vcttoPcCodeName;
    }

    public void setVcttoPcCodeName(String vcttoPcCodeName) {
        this.vcttoPcCodeName = vcttoPcCodeName;
    }

    public String getVctamount() {
        return vctamount;
    }

    public void setVctamount(String vctamount) {
        this.vctamount = vctamount;
    }

    public String getVctremarks() {
        return vctremarks;
    }

    public void setVctremarks(String vctremarks) {
        this.vctremarks = vctremarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVctuserId() {
        return vctuserId;
    }

    public void setVctuserId(String vctuserId) {
        this.vctuserId = vctuserId;
    }

    public String getVctuploadUserId() {
        return vctuploadUserId;
    }

    public void setVctuploadUserId(String vctuploadUserId) {
        this.vctuploadUserId = vctuploadUserId;
    }
}
