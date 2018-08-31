package com.fate.user.fateutil.db.contract.Skill;

// 스킬 getter, setter
public class SkillContact {

    private int skillId; // 아이디
    private String skillIcon; // 스킬 아이콘 이름
    private String skillName; // 스킬 이름
    private String skillFullName; // 스킬이름 + 스킬 랭크
    private String skillRank; // 스킬 랭크
    private String skillClassification; // 스킬 분류 (레벨 아니면 고정값)
    private int skillLevel; // 스킬 레벨 (0부터 10까지 0은 고정값)
    private String skillTarget; // 스킬 목표(아군, 적, 자기 자신)
    private String skillRange; // 스킬 범위(전체, 한 명)
    private String skillEffect; // 스킬 효과
    private double skillValue; // 스킬 효과 수치
    private String skillMerit; // 스킬 장단점
    private int skillDuration; // 스킬 지속 시간 (0부터 3까지 0이면 즉발)
    private int skillCoolDown; // 스킬 쿨다운
    private int skillPercent; // 스킬 효과 수치가 퍼센트가 포함됬는지 아닌지 true면 % false면 일반 숫자
    private int skillEnhance; // 스킬 강화여부 true면 강회퀘 받음, 아니면 강화퀘 받지 않음
    private String skillNumber;

    public SkillContact() {

    }

    public SkillContact(int skillId, String skillIcon, String skillName, String skillRank, String skillClassification,
                        int skillLevel, String skillTarget, String skillRange, String skillEffect, double skillValue, String skillMerit,
                        int skillDuration, int skillCoolDown, int skillPercent, int skillEnhance) {
        this.skillId = skillId;
        this.skillIcon = skillIcon;
        this.skillName = skillName;
        this.skillRank = skillRank;
        this.skillClassification = skillClassification;
        this.skillLevel = skillLevel;
        this.skillTarget = skillTarget;
        this.skillRange = skillRange;
        this.skillEffect = skillEffect;
        this.skillValue = skillValue;
        this.skillMerit = skillMerit;
        this.skillDuration = skillDuration;
        this.skillCoolDown = skillCoolDown;
        this.skillPercent = skillPercent;
        this.skillEnhance = skillEnhance;
    }

    // getter, setter
    public int getSkillId() {
        return this.skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillIcon() {
        return this.skillIcon;
    }

    public void setSkillIcon(String skillIcon) {
        this.skillIcon = skillIcon;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillFullName() {
        return this.skillFullName;
    }

    public void setSkillFullName(String skillFullName) {
        this.skillFullName = skillFullName;
    }

    public String getSkillRank() {
        return this.skillRank;
    }

    public void setSkillRank(String skillRank) {
        this.skillRank = skillRank;
    }

    public String getSkillClassification() {
        return this.skillClassification;
    }

    public void setSkillClassification(String skillClassification) {
        this.skillClassification = skillClassification;
    }

    public int getSkillLevel() {
        return this.skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getSkillRange() {
        return this.skillRange;
    }

    public void setSkillRange(String skillRange) {
        this.skillRange = skillRange;
    }

    public String getSkillTarget() {
        return this.skillTarget;
    }

    public void setSkillTarget(String skillTarget) {
        this.skillTarget = skillTarget;
    }

    public String getSkillEffect() {
        return this.skillEffect;
    }

    public void setSkillEffect(String skillEffect) {
        this.skillEffect = skillEffect;
    }

    public double getSkillValue() {
        return this.skillValue;
    }

    public void setSkillValue(double skillValue) {
        this.skillValue = skillValue;
    }

    public String getSkillMerit() {
        return this.skillMerit;
    }

    public void setSkillMerit(String skillMerit) {
        this.skillMerit = skillMerit;
    }

    public int getSkillDuration() {
        return this.skillDuration;
    }

    public void setSkillDuration(int skillDuration) {
        this.skillDuration = skillDuration;
    }

    public int getSkillCoolDown() {
        return this.skillCoolDown;
    }

    public void setSkillCoolDown(int skillCoolDown) {
        this.skillCoolDown = skillCoolDown;
    }

    public int getSkillPercent() {
        return this.skillPercent;
    }

    public void setSkillPercent(int skillPercent) {
        this.skillPercent = skillPercent;
    }

    public int getSkillEnhance() {
        return this.skillEnhance;
    }

    public void setSkillEnhance(int skillEnhance) {
        this.skillEnhance = skillEnhance;
    }

    public String getSkillNumber(){
        return  this.skillNumber;
    }


    public void setSkillNumber(String skillNumber) {
        this.skillNumber = skillNumber;
    }
}
