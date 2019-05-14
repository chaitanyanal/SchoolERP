package com.mipl.schoolerp.Model;

public class StaffBirthdayModule {

    String TeacherId,Fullname,DOB;

    public StaffBirthdayModule(String teacherId, String fullname, String DOB) {
        TeacherId = teacherId;
        Fullname = fullname;
        this.DOB = DOB;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }


}
