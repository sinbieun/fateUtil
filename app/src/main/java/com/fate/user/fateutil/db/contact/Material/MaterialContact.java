package com.fate.user.fateutil.db.contact.Material;

public class MaterialContact {
    private int upgradeId; // 강화 아이디
    private int servantId; // 서번트 아이디
    private String upgradeClassification; // 강화 분류
    private int upgradeLevel; // 강화 레벨
    private int materialId; // 재료 아이디
    private int materialValue; // 필요 재료 갯수
    private String materialName; // 재료 영어 이름
    private String materialKorName; // 재료 한글 이름

    public MaterialContact(){

    }

    public MaterialContact(int upgradeId, int servantId, String upgradeClassification, int upgradeLevel, int materialId, int materialValue){
        this.upgradeId = upgradeId;
        this.servantId = servantId;
        this.upgradeClassification = upgradeClassification;
        this.upgradeLevel = upgradeLevel;
        this.materialId = materialId;
        this.materialValue = materialValue;
    }

    public MaterialContact(int materialId, String materialName, String materialKorName){
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialKorName = materialKorName;
    }

    public int getUpgradeId(){
        return this.upgradeId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public int getMaterialValue() {
        return materialValue;
    }

    public int getServantId() {
        return servantId;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public String getUpgradeClassification() {
        return upgradeClassification;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public void setMaterialValue(int materialValue) {
        this.materialValue = materialValue;
    }

    public void setServantId(int servantId) {
        this.servantId = servantId;
    }

    public void setUpgradeClassification(String upgradeClassification) {
        this.upgradeClassification = upgradeClassification;
    }

    public void setUpgradeId(int upgradeId) {
        this.upgradeId = upgradeId;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialKorName() {
        return materialKorName;
    }

    public void setMaterialKorName(String materialKorName) {
        this.materialKorName = materialKorName;
    }
}
