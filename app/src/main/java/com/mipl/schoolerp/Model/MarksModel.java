package com.mipl.schoolerp.Model;

public class MarksModel {

    String id,marks,subjectName;

    public MarksModel(String id, String marks, String subjectName) {
        this.id = id;
        this.marks = marks;
        this.subjectName = subjectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
