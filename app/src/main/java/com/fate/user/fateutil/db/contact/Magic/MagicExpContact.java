package com.fate.user.fateutil.db.contact.Magic;

public class MagicExpContact {
    private int id;                         // 마술 예장 효과 아이디
    private int magicId;                    // 마술 예장 고유 아이디

    private int magicExpLevel;           // 마술 예장 레벨
    private int magicExpCount;           // 마술 예장 필요 경험치
    private int magicExpTotal;           // 마술 예장 누적 경험치
    private String magicExpDeleteYn;        // 삭제 여부

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

    public int getMagicExpLevel() {
        return magicExpLevel;
    }

    public void setMagicExpLevel(int magicExpLevel) {
        this.magicExpLevel = magicExpLevel;
    }

    public int getMagicExpCount() {
        return magicExpCount;
    }

    public void setMagicExpCount(int magicExpCount) {
        this.magicExpCount = magicExpCount;
    }

    public int getMagicExpTotal() {
        return magicExpTotal;
    }

    public void setMagicExpTotal(int magicExpTotal) {
        this.magicExpTotal = magicExpTotal;
    }

    public String getMagicExpDeleteYn() {
        return magicExpDeleteYn;
    }

    public void setMagicExpDeleteYn(String magicExpDeleteYn) {
        this.magicExpDeleteYn = magicExpDeleteYn;
    }
}
