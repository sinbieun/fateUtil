package com.fate.user.fateutil.db.contact.Servant;

public class ServantIconContact {

    private int servantIconId; // 서번트 아이콘 아이디
    private String servantIcon; // 서번트 아이콘 이름
    private String icon_delete_yn; // 삭제 여부



    public ServantIconContact(int servantIconId, String servantIcon, String icon_delete_yn){
        this.servantIconId = servantIconId;
        this.servantIcon = servantIcon;
        this.icon_delete_yn = icon_delete_yn;
    }
    // 서번트 아이콘 아이디, 아이콘 이름값 getter, setter
    public int getServantIconId() {
        return servantIconId;
    }
    public String getServantIcon() {
        return servantIcon;
    }
    public String getIcon_delete_yn(){
        return icon_delete_yn;
    }

}
