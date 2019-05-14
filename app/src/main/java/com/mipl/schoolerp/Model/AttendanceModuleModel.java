package com.mipl.schoolerp.Model;

import java.util.ArrayList;

public class AttendanceModuleModel {

    String Id,StudentName,RollNo,Date,PresentAbsent,StandId,DivId;


    public AttendanceModuleModel(String id, String studentName, String rollNo, String date, String presentAbsent, String standId, String divId) {
        Id = id;
        StudentName = studentName;
        RollNo = rollNo;
        Date = date;
        PresentAbsent = presentAbsent;
        StandId = standId;
        DivId = divId;
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

    public String getStandId() {
        return StandId;
    }

    public void setStandId(String standId) {
        StandId = standId;
    }

    public String getDivId() {
        return DivId;
    }

    public void setDivId(String divId) {
        DivId = divId;
    }
}
