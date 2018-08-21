package com.fate.user.fateutil.model;

import java.io.Serializable;

public class NoticeModel implements Serializable {

    private int noticeId;
    private String noticeKind;
    private String noticeTitle;
    private String noticeContent;
    private String noticeRegUser;
    private String noticeRegDate;

    public NoticeModel(){

    }

    public NoticeModel(int noticeId, String noticeKind, String noticeTitle, String noticeContent, String noticeRegUser, String noticeRegDate){
        this.noticeId = noticeId;
        this.noticeKind = noticeKind;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeRegUser = noticeRegUser;
        this.noticeRegDate = noticeRegDate;
    }

    public NoticeModel(int noticeId, String noticeTitle){
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeKind() {
        return noticeKind;
    }

    public void setNoticeKind(String noticeKind) {
        this.noticeKind = noticeKind;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeRegUser() {
        return noticeRegUser;
    }

    public void setNoticeRegUser(String noticeRegUser) {
        this.noticeRegUser = noticeRegUser;
    }

    public String getNoticeRegDate() {
        return noticeRegDate;
    }

    public void setNoticeRegDate(String noticeRegDate) {
        this.noticeRegDate = noticeRegDate;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
