package com.fate.user.fateutil.db.contract.Servant;

public class ServantClassContract {
    private int servantClassId; // 서번트 클래스 아이디
    private String servantClass; // 서번트 클래스


    public ServantClassContract(int servantClassId, String servantClass){
        this.servantClassId = servantClassId;
        this.servantClass = servantClass;
    }

    // 서번트 클래스 아이디, 클래스 값 getter, setter
    public int getServantClassId() {
        return servantClassId;
    }
    public String getServantClass(){
        return this.servantClass;
    }

}
