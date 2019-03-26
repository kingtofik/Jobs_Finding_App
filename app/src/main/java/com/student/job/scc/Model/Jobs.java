package com.student.job.scc.Model;

public class Jobs
{
    private String jid,date,time,image,Category,Company_name,Company_Desc,
            Job_Title,Job_Desc,Job_Exp,Job_Salary,Job_Location;


    public Jobs()
    {

    }

    public Jobs(String jid, String date, String time, String image, String category, String company_name,
                String company_Desc, String job_Title, String job_Desc, String job_Exp,
                String job_Salary, String job_Location)
    {
        this.jid = jid;
        this.date = date;
        this.time = time;
        this.image = image;
        Category = category;
        Company_name = company_name;
        Company_Desc = company_Desc;
        Job_Title = job_Title;
        Job_Desc = job_Desc;
        Job_Exp = job_Exp;
        Job_Salary = job_Salary;
        Job_Location = job_Location;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCompany_name() {
        return Company_name;
    }

    public void setCompany_name(String company_name) {
        Company_name = company_name;
    }

    public String getCompany_Desc() {
        return Company_Desc;
    }

    public void setCompany_Desc(String company_Desc) {
        Company_Desc = company_Desc;
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

    public String getJob_Exp() {
        return Job_Exp;
    }

    public void setJob_Exp(String job_Exp) {
        Job_Exp = job_Exp;
    }

    public String getJob_Salary() {
        return Job_Salary;
    }

    public void setJob_Salary(String job_Salary) {
        Job_Salary = job_Salary;
    }

    public String getJob_Location() {
        return Job_Location;
    }

    public void setJob_Location(String job_Location) {
        Job_Location = job_Location;
    }
}
