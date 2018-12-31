package com.fate.user.fateutil.db.contact.Magic;

public class MagicContact {
    private int id;                         // 마술 예장 아이디
    private String magicName;               // 마술 예장 이름
    private String magicContent;            // 마술 예장 내용
    private String magicComment;            // 마술 예장 설명
    private String magicImage;              // 마술 예장 사진 경로
    private String magicDeleteYn;           // 마술 예장 삭제 여부

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

    public String getMagicName() {
        return magicName;
    }

    public void setMagicName(String magicName) {
        this.magicName = magicName;
    }

    public String getMagicContent() {
        return magicContent;
    }

    public void setMagicContent(String magicContent) {
        this.magicContent = magicContent;
    }

    public String getMagicComment() {
        return magicComment;
    }

    public void setMagicComment(String magicComment) {
        this.magicComment = magicComment;
    }

    public String getMagicImage() {
        return magicImage;
    }

    public void setMagicImage(String magicImage) {
        this.magicImage = magicImage;
    }

    public String getMagicDeleteYn() {
        return magicDeleteYn;
    }

    public void setMagicDeleteYn(String magicDeleteYn) {
        this.magicDeleteYn = magicDeleteYn;
    }
}
