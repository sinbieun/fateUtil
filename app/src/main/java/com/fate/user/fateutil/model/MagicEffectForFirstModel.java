package com.fate.user.fateutil.model;

import java.io.Serializable;

public class MagicEffectForFirstModel implements Serializable {

    private int id;                             // 마술예장 효과 아이디
    private int magicId;                        // 마술예장 아이디
    private String magicEffectName;             // 마술예장 효과 이름
    private String magicEffectImage;            // 마술예장 효과 이미지
    private String magicEffectGoal;             // 마술예장 효과 대상
    private String magicEffectContent;          // 마술예장 효과 설명

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

    public String getMagicEffectImage() {
        return magicEffectImage;
    }

    public void setMagicEffectImage(String magicEffectImage) {
        this.magicEffectImage = magicEffectImage;
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
}
