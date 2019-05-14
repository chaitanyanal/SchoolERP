package com.mipl.schoolerp.Model;

public class SchoolNoticeModel {

    String Id,Description,NoticeImage,NoticeFile,startDate,EndDate;


    public SchoolNoticeModel(String id, String description, String noticeImage, String noticeFile, String startDate, String endDate) {
        Id = id;
        Description = description;
        NoticeImage = noticeImage;
        NoticeFile = noticeFile;
        this.startDate = startDate;
        EndDate = endDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNoticeImage() {
        return NoticeImage;
    }

    public void setNoticeImage(String noticeImage) {
        NoticeImage = noticeImage;
    }

    public String getNoticeFile() {
        return NoticeFile;
    }

    public void setNoticeFile(String noticeFile) {
        NoticeFile = noticeFile;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
