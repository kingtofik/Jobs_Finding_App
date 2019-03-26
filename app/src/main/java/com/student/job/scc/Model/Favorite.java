package com.student.job.scc.Model;

public class Favorite {
    private String jid, time, date, Job_Title, Job_Desc, Job_Location, Job_Salary, image;

    public Favorite() {

    }

    public Favorite(String jid, String time, String date, String job_Title, String job_Desc, String job_Location, String job_Salary, String image) {
        this.jid = jid;
        this.time = time;
        this.date = date;
        Job_Title = job_Title;
        Job_Desc = job_Desc;
        Job_Location = job_Location;
        Job_Salary = job_Salary;
        this.image = image;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJob_Title() {
        return Job_Title;
    }

    public void setJob_Title(String job_Title) {
        Job_Title = job_Title;
    }

    public String getJob_Desc() {
        return Job_Desc;
    }

    public void setJob_Desc(String job_Desc) {
        Job_Desc = job_Desc;
    }

    public String getJob_Location() {
        return Job_Location;
    }

    public void setJob_Location(String job_Location) {
        Job_Location = job_Location;
    }

    public String getJob_Salary() {
        return Job_Salary;
    }

    public void setJob_Salary(String job_Salary) {
        Job_Salary = job_Salary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
