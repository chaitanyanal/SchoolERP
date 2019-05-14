package com.mipl.schoolerp.Model;

public class TeacherModel {

    String TeacherName,TeacherId,TeacherRole;
    Boolean isChecked;

    /*public TeacherModel(String teacherName, String teacherId, String teacherRole, Boolean isChecked) {
        TeacherName = teacherName;
        TeacherId = teacherId;
        TeacherRole = teacherRole;
        this.isChecked = isChecked;
    }*/

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getTeacherRole() {
        return TeacherRole;
    }

    public void setTeacherRole(String teacherRole) {
        TeacherRole = teacherRole;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
