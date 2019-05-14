package com.mipl.schoolerp.Model;

public class ExamScheduleModel {

    String examId,ExamName,SubjectName,ExamDate,Description;

    public ExamScheduleModel(String examId, String examName, String subjectName, String examDate, String description) {
        this.examId = examId;
        ExamName = examName;
        SubjectName = subjectName;
        ExamDate = examDate;
        Description = description;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        ExamName = examName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getExamDate() {
        return ExamDate;
    }

    public void setExamDate(String examDate) {
        ExamDate = examDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
