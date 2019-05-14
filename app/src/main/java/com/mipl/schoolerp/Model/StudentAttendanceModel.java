package com.mipl.schoolerp.Model;

public class StudentAttendanceModel {

    String  PreentDays,AbsentDays,Month,StudentId,totalWorkingDays;

    public StudentAttendanceModel(String preentDays, String absentDays, String month, String studentId, String totalWorkingDays) {
        PreentDays = preentDays;
        AbsentDays = absentDays;
        Month = month;
        StudentId = studentId;
        this.totalWorkingDays = totalWorkingDays;
    }

    public String getPreentDays() {
        return PreentDays;
    }

    public void setPreentDays(String preentDays) {
        PreentDays = preentDays;
    }

    public String getAbsentDays() {
        return AbsentDays;
    }

    public void setAbsentDays(String absentDays) {
        AbsentDays = absentDays;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public void setTotalWorkingDays(String totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }
}
