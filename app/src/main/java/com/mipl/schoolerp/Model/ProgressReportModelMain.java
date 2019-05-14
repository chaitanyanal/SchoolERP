package com.mipl.schoolerp.Model;

import java.util.ArrayList;

public class ProgressReportModelMain {

    String ExamName,ExamId;
    ArrayList<ProgressReportSubModel> arrayList;


    public ProgressReportModelMain(String examName, String examId, ArrayList<ProgressReportSubModel> arrayList) {
        ExamName = examName;
        ExamId = examId;
        this.arrayList = arrayList;
    }

    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        ExamName = examName;
    }

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        ExamId = examId;
    }

    public ArrayList<ProgressReportSubModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ProgressReportSubModel> arrayList) {
        this.arrayList = arrayList;
    }
}
