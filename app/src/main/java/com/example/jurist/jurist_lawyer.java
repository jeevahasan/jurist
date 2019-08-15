package com.example.jurist;

public class jurist_lawyer {

    String first_name;
    String last_name;
    String email;
    String phone_no;
    String reference_no;
    String district;
    String case_type;


    public jurist_lawyer(){



    }

    public jurist_lawyer(String first_name, String last_name, String email,String phone_no, String reference_no) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_no = phone_no;
        this.reference_no = reference_no;


    }

    public void setDistrict(String district){
        this.district = district;
    }

    public void setCase_type(String case_type){
        this.case_type = case_type;
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

    public String getReference_no() {
        return reference_no;
    }
    public String getDistrict() {
        return district;
    }

}
