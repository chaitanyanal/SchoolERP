package com.mipl.schoolerp.Model;

import java.util.Comparator;

public class HolidayModel {

    String Id,StartDate,EndDate,TotalDays,Name,Remark,Active,CreatedBy,UpdatedBy,CreatedDate,UpdatedDate,CurrentAccedmicYear;

    public HolidayModel(String id, String startDate, String endDate, String totalDays, String name, String remark, String active, String createdBy, String updatedBy, String createdDate, String updatedDate, String currentAccedmicYear) {
        Id = id;
        StartDate = startDate;
        EndDate = endDate;
        TotalDays = totalDays;
        Name = name;
        Remark = remark;
        Active = active;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        CurrentAccedmicYear = currentAccedmicYear;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getTotalDays() {
        return TotalDays;
    }

    public void setTotalDays(String totalDays) {
        TotalDays = totalDays;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getCurrentAccedmicYear() {
        return CurrentAccedmicYear;
    }

    public void setCurrentAccedmicYear(String currentAccedmicYear) {
        CurrentAccedmicYear = currentAccedmicYear;
    }


    public static Comparator<HolidayModel> DateComparator = new Comparator<HolidayModel>() {

        public int compare(HolidayModel s1, HolidayModel s2) {
            String Date1 = s1.getStartDate();
            String Date2 = s2.getStartDate();

            //ascending order
            return Date1.compareTo(Date2);

            //descending order
           // return StudentName2.compareTo(StudentName1);
        }};
}
