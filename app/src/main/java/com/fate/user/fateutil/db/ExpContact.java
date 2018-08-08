package com.fate.user.fateutil.db;

public class ExpContact {

    int id;
    int servantLevel;
    int servantExp;

    public ExpContact(){

    }

    public ExpContact(int id, int servantLevel, int servantExp){
        this.id = id;
        this.servantLevel = servantLevel;
        this.servantExp = servantExp;

    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getServantLevel(){
        return this.servantLevel;
    }

    public void setServantLevel(int servantLevel){
        this.servantLevel = servantLevel;
    }

    public int getServantExp(){
        return this.servantExp;
    }

    public void setServantExp(int servantExp){
        this.servantExp = servantExp;
    }

}
