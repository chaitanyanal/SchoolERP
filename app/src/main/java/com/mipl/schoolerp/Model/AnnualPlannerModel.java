package com.mipl.schoolerp.Model;

public class AnnualPlannerModel {


    String Id,Name,Startdate,EndDate,totaldays,Remark,Standard;

    public AnnualPlannerModel(String id, String name, String startdate, String endDate, String totaldays, String remark,String standerd) {
        Id = id;
        Name = name;
        Startdate = startdate;
        EndDate = endDate;
        this.totaldays = totaldays;
        Remark = remark;
        Standard=standerd;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStartdate() {
        return Startdate;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getTotaldays() {
        return totaldays;
    }

    public void setTotaldays(String totaldays) {
        this.totaldays = totaldays;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String standard) {
        Standard = standard;
    }
}
