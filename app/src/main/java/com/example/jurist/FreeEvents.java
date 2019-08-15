package com.example.jurist;

public class FreeEvents {

    private String lawyer_uid;
    private String customer_uid;
    private String date;

    public FreeEvents(){

    }

    public FreeEvents(String lawyer_uid,String customer_uid,String date){
        this.customer_uid = customer_uid;
        this.lawyer_uid = lawyer_uid;
        this.date = date;
    }


    public void setLawyer_uid(String lawyer_uid){
        this.lawyer_uid = lawyer_uid;
    }

    public void setCustomer_uid(String customer_uid){
        this.customer_uid = customer_uid;
    }

    public void setDate(String date){
        this.date = date;
    }


    public String getLawyer_uid(){
        return lawyer_uid;
    }

    public String getCustomer_uid(){
        return customer_uid;
    }


    public String getDate(){
        return date;
    }


}
