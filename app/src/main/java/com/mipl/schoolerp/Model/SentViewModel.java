package com.mipl.schoolerp.Model;

public class SentViewModel {

    String Id,Tolist,Subject,msgbody,date,Attachment1,Attachment2,Attachment3,File1,File2,File3;

    public SentViewModel(String id, String tolist, String subject, String msgbody, String date, String attachment1, String attachment2, String attachment3, String file1, String file2, String file3) {
        Id = id;
        Tolist = tolist;
        Subject = subject;
        this.msgbody = msgbody;
        this.date = date;
        Attachment1 = attachment1;
        Attachment2 = attachment2;
        Attachment3 = attachment3;
        File1 = file1;
        File2 = file2;
        File3 = file3;
    }

    public String getId() {
        return Id;
    }

    public String getAttachment1() {
        return Attachment1;
    }

    public void setAttachment1(String attachment1) {
        Attachment1 = attachment1;
    }

    public String getAttachment2() {
        return Attachment2;
    }

    public void setAttachment2(String attachment2) {
        Attachment2 = attachment2;
    }

    public String getAttachment3() {
        return Attachment3;
    }

    public void setAttachment3(String attachment3) {
        Attachment3 = attachment3;
    }

    public String getFile1() {
        return File1;
    }

    public void setFile1(String file1) {
        File1 = file1;
    }

    public String getFile2() {
        return File2;
    }

    public void setFile2(String file2) {
        File2 = file2;
    }

    public String getFile3() {
        return File3;
    }

    public void setFile3(String file3) {
        File3 = file3;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTolist() {
        return Tolist;
    }

    public void setTolist(String tolist) {
        Tolist = tolist;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMsgbody() {
        return msgbody;
    }

    public void setMsgbody(String msgbody) {
        this.msgbody = msgbody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
