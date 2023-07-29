package com.company;

public abstract class Driver {
    private String fname;
    private String lname;
    private String dcountry;
    private String dteam;

    public Driver(String fname, String lname, String dcountry, String dteam) {
        this.fname = fname;
        this.lname = lname;
        this.dcountry = dcountry;
        this.dteam = dteam;
    }

    public String getFname() {return fname;}

    public String getLname() {
        return lname;
    }

    public String getDlocation() {
        return dcountry;
    }

    public String getDteam() {
        return dteam;
    }

    public void setFname(String fname) {this.fname = fname;}

    public void setLname(String lname) {this.lname = lname;}

    public void setDcountry(String dcountry) {this.dcountry = dcountry;}

    public void setDteam(String dteam) {this.dteam = dteam;}
}

