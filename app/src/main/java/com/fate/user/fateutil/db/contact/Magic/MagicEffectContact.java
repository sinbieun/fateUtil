package com.fate.user.fateutil.db.contact.Magic;

public class MagicEffectContact {
    private int id;                         // 마술 예장 효과 아이디
    private int magicId;                    // 마술 예장 고유 아이디

    private String magicEffectName;         // 마술 예장 효과 이름
    private String magicEffectGoal;         // 마술 예장 효과 대상
    private String magicEffectContent;      // 마술 예장 효과 설명
    private String magicEffectTime;         // 마술 예장 효과 쿨타임
    private String magicEffectLevel;        // 마술 예장 효과 레벨
    private String magicEffectImage;        // 마술 예장 효과 사진 이름
    private String magicEffectUtil;         // 마술 예장 효과 성능
    private String magicEffectDeleteYn;     // 마술 예장 삭제 여부

    /**
     * GETTER SETTER
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMagicId() {
        return magicId;
    }

    public void setMagicId(int magicId) {
        this.magicId = magicId;
    }

    public String getMagicEffectName() {
        return magicEffectName;
    }

    public void setMagicEffectName(String magicEffectName) {
        this.magicEffectName = magicEffectName;
    }

    public String getMagicEffectGoal() {
        return magicEffectGoal;
    }

    public void setMagicEffectGoal(String magicEffectGoal) {
        this.magicEffectGoal = magicEffectGoal;
    }

    public String getMagicEffectContent() {
        return magicEffectContent;
    }

    public void setMagicEffectContent(String magicEffectContent) {
        this.magicEffectContent = magicEffectContent;
    }

    public String getMagicEffectTime() {
        return magicEffectTime;
    }

    public void setMagicEffectTime(String magicEffectTime) {
        this.magicEffectTime = magicEffectTime;
    }

    public String getMagicEffectLevel() {
        return magicEffectLevel;
    }

    public void setMagicEffectLevel(String magicEffectLevel) {
        this.magicEffectLevel = magicEffectLevel;
    }

    public String getMagicEffectImage() {
        return magicEffectImage;
    }

    public void setMagicEffectImage(String magicEffectImage) {
        this.magicEffectImage = magicEffectImage;
    }

    public String getMagicEffectUtil() {
        return magicEffectUtil;
    }

    public void setMagicEffectUtil(String magicEffectUtil) {
        this.magicEffectUtil = magicEffectUtil;
    }

    public String getMagicEffectDeleteYn() {
        return magicEffectDeleteYn;
    }

    public void setMagicEffectDeleteYn(String magicEffectDeleteYn) {
        this.magicEffectDeleteYn = magicEffectDeleteYn;
    }
}
