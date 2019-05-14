package com.mipl.schoolerp.Model;

public class ProgressReportSubModel {

    String SubjectId,SubjectName,Mark,ExamId,OutMark;

    public ProgressReportSubModel(String subjectId, String subjectName, String mark, String examId, String outMark) {
        SubjectId = subjectId;
        SubjectName = subjectName;
        Mark = mark;
        ExamId = examId;
        OutMark = outMark;
    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getMark() {
        return Mark;
    }

    public void setMark(String mark) {
        Mark = mark;
    }

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        ExamId = examId;
    }

    public String getOutMark() {
        return OutMark;
    }

    public void setOutMark(String outMark) {
        OutMark = outMark;
    }
}
