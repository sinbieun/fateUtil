package com.fate.user.fateutil.db.contact.Skill;

public class ServantJoinSkillContact {

    private int id;
    private int servantId;
    private String skillClassification;
    private int skillId;



    public ServantJoinSkillContact(int id, int servantId, String skillClassification, int skillId) {
        this.id = id;
        this.servantId = servantId;
        this.skillClassification = skillClassification;
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

    public String getSkillClassification(){
        return this.skillClassification;
    }

    public int getSkillId(){
        return this.skillId;
    }

    public void setSkillId(int SkillId){
        this.skillId = skillId;
    }

}
