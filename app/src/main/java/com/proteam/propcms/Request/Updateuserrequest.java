package com.proteam.propcms.Request;

public class Updateuserrequest {

    String user_id,email,password,new_password,first_name,last_name,phone;

    public Updateuserrequest(String user_id, String email, String password, String new_password, String first_name, String last_name, String phone) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.new_password = new_password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }
}
