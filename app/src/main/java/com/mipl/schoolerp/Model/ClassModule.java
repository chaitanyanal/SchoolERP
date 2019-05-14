package com.mipl.schoolerp.Model;

public class ClassModule {


    public ClassModule(String id, String standardId, String divisionId, String standardNm, String division) {
        Id = id;
        StandardId = standardId;
        DivisionId = divisionId;
        this.standardNm = standardNm;
        Division = division;
    }

    String Id,StandardId,DivisionId,standardNm,Division;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStandardId() {
        return StandardId;
    }

    public void setStandardId(String standardId) {
        StandardId = standardId;
    }

    public String getDivisionId() {
        return DivisionId;
    }

    public void setDivisionId(String divisionId) {
        DivisionId = divisionId;
    }

    public String getStandardNm() {
        return standardNm;
    }

    public void setStandardNm(String standardNm) {
        this.standardNm = standardNm;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }
}
