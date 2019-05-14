package com.mipl.schoolerp.Model;

public class StudentDetailsModule {

    String StudId,StandardId,DivisionId,StudentName,Role;
    Boolean isChecked;

    /*public StudentDetailsModule(String studId, String standardId, String divisionId, String studentName, Boolean isChecked) {
        StudId = studId;
        StandardId = standardId;
        DivisionId = divisionId;
        StudentName = studentName;
        this.isChecked = isChecked;
    }*/

    public String getStudId() {
        return StudId;
    }

    public void setStudId(String studId) {
        StudId = studId;
    }

    public String getStandardId() {
        return StandardId;
    }

    public void setStandardId(String standardId) {
        StandardId = standardId;
    }

    public String getDivisionId() {
        return DivisionId;
    }

    public void setDivisionId(String divisionId) {
        DivisionId = divisionId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
