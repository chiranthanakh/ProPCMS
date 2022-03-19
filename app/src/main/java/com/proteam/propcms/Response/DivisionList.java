package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DivisionList {

    @SerializedName("division_id")
    @Expose
    private String division_id;

    @SerializedName("division_name")
    @Expose
    private String division_name;

    @SerializedName("division_code")
    @Expose
    private String division_code;

    @SerializedName("order_cli")
    @Expose
    private String order_cli;

    @SerializedName("rawcode")
    @Expose
    private String rawcode;

    @SerializedName("asset_division")
    @Expose
    private String asset_division;

    public String getDivision_id() {
        return division_id;
    }

    public void setDivision_id(String division_id) {
        this.division_id = division_id;
    }

    public String getDivision_name() {
        return division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    public String getDivision_code() {
        return division_code;
    }

    public void setDivision_code(String division_code) {
        this.division_code = division_code;
    }

    public String getOrder_cli() {
        return order_cli;
    }

    public void setOrder_cli(String order_cli) {
        this.order_cli = order_cli;
    }

    public String getRawcode() {
        return rawcode;
    }

    public void setRawcode(String rawcode) {
        this.rawcode = rawcode;
    }

    public String getAsset_division() {
        return asset_division;
    }

    public void setAsset_division(String asset_division) {
        this.asset_division = asset_division;
    }
}
