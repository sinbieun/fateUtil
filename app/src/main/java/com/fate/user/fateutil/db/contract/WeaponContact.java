package com.fate.user.fateutil.db.contract;

// 스킬 getter, setter
public class WeaponContact {

    private int id; // 보구 아이디
    private String weaponName; // 보구 이름
    private String weaponSubName; // 보구 서브 이름
    private String weaponRank; // 보구 랭크
    private String weaponClassification; // 스킬 분류 (레벨 오버차지 아니면 고정값)
    private int weaponLevel; // 보구 레벨 (0부터 10까지 0은 고정값)
    private String weaponTarget; // 보구 목표(아군, 적, 자기 자신)
    private String weaponRange; // 보구 범위(전체, 한 명)
    private String weaponExcept; // 보구 제외
    private String weaponEffect; // 스킬 효과
    private float weaponValue; // 스킬 효과 수치
    private String weaponType; // 보구 타입
    private String weaponMerit; // 보구 장단점
    private int weaponHit; // 보구 타수
    private int weaponDuration; // 보구 지속 시간 (0부터 3까지 0이면 즉발)
    private int weaponPercent; // 스킬 효과 수치가 퍼센트가 포함됬는지 아닌지 true면 % false면 일반 숫자
    private int weaponEnhance; // 스킬 강화여부 true면 강회퀘 받음, 아니면 강화퀘 받지 않음

    public WeaponContact() {

    }


    // getter, setter
    public int getWeaponId() {
        return this.id;
    }

    public void setWeaponId(int id) {
        this.id = id;
    }

    public String getWeaponName() {
        return this.weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getWeaponSubName() {
        return this.weaponSubName;
    }

    public void setWeaponSubName(String weaponSubName) {
        this.weaponSubName = weaponSubName;
    }

    public String getWeaponRank() {
        return this.weaponRank;
    }

    public void setWeaponRank(String weaponRank) {
        this.weaponRank = weaponRank;
    }

    public String getWeaponClassification() {
        return this.weaponClassification;
    }

    public void setWeaponClassification(String weaponClassification) {
        this.weaponClassification = weaponClassification;
    }

    public int getWeaponLevel() {
        return this.weaponLevel;
    }

    public void setWeaponLevel(int weaponLevel) {
        this.weaponLevel = weaponLevel;
    }

    public String getWeaponTarget() {
        return this.weaponTarget;
    }

    public void setWeaponTarget(String weaponTarget) {
        this.weaponTarget = weaponTarget;
    }
    public String getWeaponRange() {
        return this.weaponRange;
    }

    public void setWeaponRange(String weaponRange) {
        this.weaponRange = weaponRange;
    }

    public String getWeaponExcept(){
        return this.weaponExcept;
    }

    public void setWeaponExcept(String weaponExcept){
        this.weaponExcept = weaponExcept;
    }

    public String getWeaponEffect() {
        return this.weaponEffect;
    }

    public void setWeaponEffect(String weaponEffect) {
        this.weaponEffect = weaponEffect;
    }

    public double getWeaponValue() {
        return this.weaponValue;
    }

    public void setWeaponValue(float weaponValue) {
        this.weaponValue = weaponValue;
    }

    public String getWeaponType(){
        return this.weaponType;
    }

    public void setWeaponType(String weaponType){
        this.weaponType = weaponType;
    }

    public String getWeaponMerit() {
        return this.weaponMerit;
    }

    public void setWeaponMerit(String weaponMerit) {
        this.weaponMerit = weaponMerit;
    }

    public int getWeaponHit(){
        return this.weaponHit;
    }

    public void setWeaponHit(int weaponHit){
        this.weaponHit = weaponHit;
    }

    public int getWeaponDuration() {
        return this.weaponDuration;
    }

    public void setWeaponDuration(int weaponDuration) {
        this.weaponDuration = weaponDuration;
    }

    public int getWeaponPercent () {
        return this.weaponPercent;
    }

    public void setWeaponPercent(int weaponPercent) {
        this.weaponPercent = weaponPercent;
    }

    public int getWeaponEnhance() {
        return this.weaponEnhance;
    }

    public void setWeaponEnhance(int weaponEnhance) {
        this.weaponEnhance = weaponEnhance;
    }

}