package com.fate.user.fateutil.db.contract.Servant;

public class ServantContact {

    private int servantId; // 서번트 아이디
    private int servantIconId; // 서번트 아이콘 아이디
    private String servantIcon; // 서번트 아이콘 이름
    private int servantNameId; // 서번트 이름 아이디
    private String servantName;
    private int servantClassId; // 서번트 클래스 아이디
    private String servantClass;
    private int servantGrade; // 서번트 등급


    // 생성자
    public ServantContact(){}
    public ServantContact(int servantId, int servantIconId, int servantNameId, int servantClassId, int servantGrade){
        this.servantId = servantId;
        this.servantIconId = servantIconId;
        this.servantNameId = servantNameId;
        this.servantClassId = servantClassId;
        this.servantGrade = servantGrade;
    }
    public ServantContact(int servantGrade){
        this.servantGrade = servantGrade;

    }


    // getter, setter
    // 서번트 아이디 getter, setter
    public int getServantId(){
        return servantId;
    }
    public void setServantId(int servantId){
        this.servantId = servantId;
    }

    // 서번트 아이콘 아이디, 아이콘 이름값 getter, setter
    public int getServantIconId() {
        return servantIconId;
    }
    public void setServantIconId(int servantIconId) {
        this.servantIconId = servantIconId;
    }

    public String getServantIcon(){
        return servantIcon;
    }

    public void setServantIcon(String servantIcon){
        this.servantIcon = servantIcon;
    }


    // 서번트 이름 아이디, 이름값 getter, setter
    public int getServantNameId() {
        return servantNameId;
    }
    public void setServantNameId(int servantNameId) {
        this.servantNameId = servantNameId;
    }

    public String getServantName(){
        return servantName;
    }

    public void setServantName(String servantName){
        this.servantName = servantName;
    }

    // 서번트 클래스 아이디, 클래스 값 getter, setter
    public int getServantClassId() {
        return servantClassId;
    }
    public void setServantClassId(int servantClassId) {
        this.servantClassId = servantClassId;
    }

    public String getServantClass(){
        return servantClass;
    }

    public void setServantClass(String servantClass){
        this.servantClass = servantClass;
    }

    // 서번트 등급값 getter, setter
    public int getServantGrade(){
        return this.servantGrade;
    }
    public void setServantGrade(int servantGrade){
        this.servantGrade = servantGrade;
    }

} // ServantContact 끝
