package com.fate.user.fateutil.db;

public class ServantJoinSkillContract {

    int id;
    int servantId;
    int skillId;


    public ServantJoinSkillContract(int id, int servantId, int skillId) {
        this.id = id;
        this.servantId = servantId;
        this.skillId = skillId;
    }


    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getServantId(){
        return this.servantId;
    }

    public void setServantId(int servantId){
        this.servantId = servantId;
    }


    public int getSkillId(){
        return this.skillId;
    }

    public void setSkillId(int SkillId){
        this.skillId = skillId;
    }

}
