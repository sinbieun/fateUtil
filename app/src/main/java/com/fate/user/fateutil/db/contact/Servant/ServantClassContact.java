package com.fate.user.fateutil.db.contact.Servant;

public class ServantClassContact {
    private int servantClassId; // 서번트 클래스 아이디
    private String servantClass; // 서번트 클래스
    private String class_delete_yn; // 서번트 클래스 삭제여부


    public ServantClassContact(int servantClassId, String servantClass, String class_delete_yn){
        this.servantClassId = servantClassId;
        this.servantClass = servantClass;
        this.class_delete_yn = class_delete_yn;
    }

    // 서번트 클래스 아이디, 클래스 값 getter, setter
    public int getServantClassId() {
        return servantClassId;
    }
    public String getServantClass(){
        return this.servantClass;
    }
    public String getClass_delete_yn(){
        return class_delete_yn;
    }

}
