package com.mipl.schoolerp.Util;

import com.mipl.schoolerp.Model.SendDetailsModule;
import java.util.ArrayList;

public class Url {


    public static  String Base="http://192.168.1.192:2253/WebServiceERpSchool";

    public static  String IP="http://192.168.1.192:2253";

    public static  String GetAllTeacher=Base+"/GetAllTeacher";

    public static  String GetAllClassesList=Base+"/GetStandDivision";

    public static String GetStandDivisionWiseStudent=Base+"/GetStandDivisionWiseStudent";

    public static String Login=Base+"/Login";

    public static String PrincipalName=Base+"/GetPrincipal";

    public static String ComposeMessage=Base+"/ComposeMessage";

    public static String UploadDocs=Base+"/testAttachment";

    public static String Attachment1=Base+"/Attachment1";
    public static String Attachment2=Base+"/Attachment2";
    public static String Attachment3=Base+"/Attachment3";

    public static String InboxView=Base+"/InboxMessage";
    public static String SentView=Base+"/SentBox";

    public static String ReadUpdate=Base+"/ReadUpdate";

    public static String StaffBirthday=Base+"/StaffBirthday";

    public static String GetAllPhotos=Base+"/PhotoGallery";

    public static String GetAllVideos=Base+"/VideoGallery";

    public static String SchoolNotices=Base+"/SchoolNotice";

    public static String Holiday=Base+"/Holiday";

    public static String ChangePass=Base+"/ChangePassword";

    public static String Annual_Planner=Base+"/AnnualPlanner";

    public static String Attendance=Base+"/ListAttendance";

    public static String AddAttendance=Base+"/AddAttendance";

    public static String AllSubjectTeacher=Base+"/AllSubjectTeacher";

    public static String MissingAttendance=Base+"/MissingAttendance";

    public static String ClassTeacherName=Base+"/ClassTeacherName";

    public static String ExamNameList=Base+"/ExamName";

    public static String Fees=Base+"/Feedetail";

    public static String ProgressReport=Base+"/ProgressReport1";

    public static String SubjectData=Base+"/SubjectName";

    public static String MonthWisePresentAbsentData=Base+"/MonthWisePresentAbsentData";


    public static String ParentData=Base+"/ParentTeacherAssociateParent";
    public static String TeacherData=Base+"/ParentTeacherAssociate";


    public static String SMSCenter =Base+"/SMSCenter";

    public static String ParentContatNo =Base+"/ParentContatNo";

    public static String SiblingDetails =Base+"/Siblings";









    public static ArrayList<SendDetailsModule>  ArrayListOfDetails=new ArrayList<>();

    public static ArrayList<SendDetailsModule>  ArrayListOfDetailsCc=new ArrayList<>();

    public static ArrayList<SendDetailsModule>  ArrayListOfDetailsSms=new ArrayList<>();

    public static  String classFullName;
    public static  String UID;

    public static  String PrincipalToId="",PrincipalToRole="",PrincipalToIdCC="",PrincipalToRoleCC="";

}
