package com.example.jurist;

public class jurist_customer {

    String first_name;
    String last_name;
    String email;
    String phone_no;


    public jurist_customer(){



    }

    public jurist_customer( String first_name, String last_name, String email, String phone_no) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_no = phone_no;


    }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_no() {
        return phone_no;
    }


}
