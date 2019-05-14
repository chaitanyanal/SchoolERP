package com.mipl.schoolerp.Model;

public class TeacherListModel {

    String TeacherId,Teachername,SubjectName;

    public TeacherListModel(String teacherId, String teachername, String subjectName) {
        TeacherId = teacherId;
        Teachername = teachername;
        SubjectName = subjectName;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getTeachername() {
        return Teachername;
    }

    public void setTeachername(String teachername) {
        Teachername = teachername;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
}
