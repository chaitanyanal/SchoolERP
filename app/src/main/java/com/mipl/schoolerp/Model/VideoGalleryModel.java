package com.mipl.schoolerp.Model;

public class VideoGalleryModel {

    String Id,VideoDetails,VideoLink,AcademicYear,Month;

    public VideoGalleryModel(String id, String videoDetails, String videoLink, String academicYear, String month) {
        Id = id;
        VideoDetails = videoDetails;
        VideoLink = videoLink;
        AcademicYear = academicYear;
        Month = month;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getVideoDetails() {
        return VideoDetails;
    }

    public void setVideoDetails(String videoDetails) {
        VideoDetails = videoDetails;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }

    public void setAcademicYear(String academicYear) {
        AcademicYear = academicYear;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }
}
