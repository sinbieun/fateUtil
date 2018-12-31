package com.fate.user.fateutil.db.contact.Servant;

public class ServantAscensionContact {
    private int ascensionId; // 서번트 재림 아이디
    private int servantId; // 서번트 아이디
    private String ascensionClassification; // 서번트 클래스 분류
    private int ascensionLevel; // 재림 단계
    private String ascensionImgName; // 재림 이미지 이름

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
