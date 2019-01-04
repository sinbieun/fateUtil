package com.fate.user.fateutil.model;

import java.io.Serializable;

public class MagicEffectForSecondModel implements Serializable {

    private int id;                             // 마술예장 효과 아이디
    private int magicId;                        // 마술예장 아이디
    private int magicEffectLevel;               // 마술예장 효과 레벨
    private String magicEffectUtil;             // 마술예장 효과 성능
    private int magicEffectTime;                // 마술예장 효과 쿨타임

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

    public int getMagicEffectLevel() {
        return magicEffectLevel;
    }

    public void setMagicEffectLevel(int magicEffectLevel) {
        this.magicEffectLevel = magicEffectLevel;
    }

    public String getMagicEffectUtil() {
        return magicEffectUtil;
    }

    public void setMagicEffectUtil(String magicEffectUtil) {
        this.magicEffectUtil = magicEffectUtil;
    }

    public int getMagicEffectTime() {
        return magicEffectTime;
    }

    public void setMagicEffectTime(int magicEffectTime) {
        this.magicEffectTime = magicEffectTime;
    }
}
