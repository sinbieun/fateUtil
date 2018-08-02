package com.fate.user.fateutil.db;

import android.graphics.drawable.Drawable;

public class ServantContact {

    int id;
    String servantIcon;
    String servantName;
    String servantClass;
    int servantGrade;

    public ServantContact(){

    }

    public ServantContact(int id, String servantIcon, String servantName, String servantClass, int servantGrade){
        this.id = id;
        this.servantIcon = servantIcon;
        this.servantName = servantName;
        this.servantClass = servantClass;
        this.servantGrade = servantGrade;
    }

    public ServantContact(int id, String servantName, String servantClass, int servantGrade){
        this.id = id;
        this.servantName = servantName;
        this.servantClass = servantClass;
        this.servantGrade = servantGrade;
    }

    public ServantContact(String servantName, String servantClass, int servantGrade){
        this.servantName = servantName;
        this.servantClass = servantClass;
        this.servantGrade = servantGrade;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getServantIcon() {
        return this.servantIcon;
    }

    public void setServantIcon(String servantIcon){
        this.servantIcon = servantIcon;
    }


    public String getServantName(){
        return this.servantName;
    }

    public void setServantName(String servantName){
        this.servantName = servantName;
    }

    public String getServantClass(){
        return this.servantClass;
    }

    public void setServantClass(String servantClass){
        this.servantClass = servantClass;
    }

    public int getServantGrade(){
        return this.servantGrade;
    }

    public void setServantGrade(int servantGrade){
        this.servantGrade = servantGrade;
    }


}