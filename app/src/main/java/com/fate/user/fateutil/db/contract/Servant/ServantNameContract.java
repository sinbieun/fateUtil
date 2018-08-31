package com.fate.user.fateutil.db.contract.Servant;

public class ServantNameContract {

    private int servantNameId; // 서번트 이름 아이디
    private String servantName; // 서번트 이름

    public ServantNameContract(){ }
    public ServantNameContract(int servantNameId, String servantName){
        this.servantNameId = servantNameId;
        this.servantName = servantName;
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

}
