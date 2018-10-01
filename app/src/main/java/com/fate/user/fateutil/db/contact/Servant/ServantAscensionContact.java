package com.fate.user.fateutil.db.contact.Servant;

public class ServantAscensionContact {
    private int ascensionId;
    private int servantId;
    private String ascensionClassification;
    private int ascensionLevel;
    private String ascensionImgName;

    public ServantAscensionContact(){

    }

    public ServantAscensionContact(int ascensionId, int servantId, String ascensionClassification, int ascensionLevel, String ascensionImgName) {
        this.ascensionId = ascensionId;
        this.servantId = servantId;
        this.ascensionClassification = ascensionClassification;
        this.ascensionLevel = ascensionLevel;
        this.ascensionImgName = ascensionImgName;
    }

    public int getAscensionId() {
        return ascensionId;
    }

    public void setAscensionId(int ascensionId) {
        this.ascensionId = ascensionId;
    }

    public int getServantId() {
        return servantId;
    }

    public void setServantId(int servantId) {
        this.servantId = servantId;
    }

    public String getAscensionClassification() {
        return ascensionClassification;
    }

    public void setAscensionClassification(String ascensionClassification) {
        this.ascensionClassification = ascensionClassification;
    }

    public int getAscensionLevel() {
        return ascensionLevel;
    }

    public void setAscensionLevel(int ascensionLevel) {
        this.ascensionLevel = ascensionLevel;
    }

    public String getAscensionImgName() {
        return ascensionImgName;
    }

    public void setAscensionImgName(String ascensionImgName) {
        this.ascensionImgName = ascensionImgName;
    }
}
