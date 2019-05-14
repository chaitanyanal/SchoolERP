package com.mipl.schoolerp.Model;

public class AttendanceSubModuleModel {

    String Id,StudentName,RollNo,Date,PresentAbsent;

    public AttendanceSubModuleModel(String id, String studentName, String rollNo, String date, String presentAbsent) {
        Id = id;
        StudentName = studentName;
        RollNo = rollNo;
        Date = date;
        PresentAbsent = presentAbsent;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPresentAbsent() {
        return PresentAbsent;
    }

    public void setPresentAbsent(String presentAbsent) {
        PresentAbsent = presentAbsent;
    }
}
