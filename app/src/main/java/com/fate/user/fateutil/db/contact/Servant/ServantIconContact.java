package com.fate.user.fateutil.db.contact.Servant;

public class ServantIconContact {

    private int servantIconId; // 서번트 아이콘 아이디
    private String servantIcon; // 서번트 아이콘 이름


    public ServantIconContact(int servantIconId, String servantIcon){
        this.servantIconId = servantIconId;
        this.servantIcon = servantIcon;
    }
    // 서번트 아이콘 아이디, 아이콘 이름값 getter, setter
    public int getServantIconId() {
        return servantIconId;
    }
    public String getServantIcon() {
        return this.servantIcon;
    }

}
