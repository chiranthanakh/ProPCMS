package com.proteam.propcms.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClientList {

    @SerializedName("client_list")
    @Expose
    private ArrayList<ClientItems> client_list = null;

    public ArrayList<ClientItems> getClient_list() {
        return client_list;
    }

    public void setClient_list(ArrayList<ClientItems> client_list) {
        this.client_list = client_list;
    }
}
