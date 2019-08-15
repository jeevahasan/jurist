package com.example.jurist;



/**

 */

public class Lawyers {

    public String first_name,last_name,district,case_type;

    public Lawyers(){

    }

    public String getName() {
        return first_name;
    }

    public void setName(String first_name) {
        this.first_name = first_name;
    }



    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }



    public String getLocation() {
        return district;
    }

    public void setLocation(String location) {
        this.district = location;
    }


    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }



    public Lawyers(String first_name, String last_name, String location, String case_type) {
        this.first_name = first_name;
        this.last_name=last_name;
        this.district=location;
        this.case_type=case_type;

    }
}
