package com.mipl.schoolerp.Model;

public class PhotoGalleryModel {

    String Id,GalleryName,ImagePath,AcademicYear,CreatedDate;

    public PhotoGalleryModel(String id, String galleryName, String imagePath, String academicYear, String createdDate) {
        Id = id;
        GalleryName = galleryName;
        ImagePath = imagePath;
        AcademicYear = academicYear;
        CreatedDate = createdDate;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGalleryName() {
        return GalleryName;
    }

    public void setGalleryName(String galleryName) {
        GalleryName = galleryName;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }

    public void setAcademicYear(String academicYear) {
        AcademicYear = academicYear;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }
}
