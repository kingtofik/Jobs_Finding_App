package com.student.job.scc.Model;

public class CompanyJobList {

    private String  jid,name,phone,city,college,email,date,time;

    public CompanyJobList() {
    }

    public CompanyJobList(String jid, String name, String phone, String city, String college, String email, String date, String time) {
        this.jid=jid;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.college = college;
        this.email = email;
        this.date = date;
        this.time = time;
    }


    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
