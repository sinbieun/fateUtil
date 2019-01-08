package com.fate.user.fateutil.db.contact.Servant;

public class ServantNameContact {

    private int servantNameId; // 서번트 이름 아이디
    private String servantName; // 서번트 이름
    private String name_delete_yn; // 서번트 이름 삭제 여부

    public ServantNameContact(){ }
    public ServantNameContact(int servantNameId, String servantName, String name_delete_yn){
        this.servantNameId = servantNameId;
        this.servantName = servantName;
        this.name_delete_yn = name_delete_yn;
    }

    // 서번트 이름 아이디, 이름값 getter, setter
    public int getServantNameId() {
        return servantNameId;
    }
    public void setServantNameId(int servantNameId) {
        this.servantNameId = servantNameId;
    }
    public String getServantName(){
        return this.servantName;
    }
    public void setServantName(String servantName){
        this.servantName = servantName;
    }
    public String getName_delete_yn(){
        return name_delete_yn;
    }

}
