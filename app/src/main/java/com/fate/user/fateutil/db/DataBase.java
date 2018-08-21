package com.fate.user.fateutil.db;

import android.provider.BaseColumns;

public class DataBase {

    // 계약 클래스
    // DB 내용
    private DataBase(){};

    // 1. 서번트 테이블 변수 생성
    public static final class ServantTable implements BaseColumns {
        public static final String TABLE_NAME = "servant";
        public static final String ID = "id";
        public static final String SERVANT_ICON = "servantIcon";
        public static final String SERVANT_NAME = "servantName";
        public static final String SERVANT_CLASS = "servantClass";
        public static final String SERVANT_GRADE = "servantGrade";
    }

    public static final class ServantNameTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantName";
        public static final String ID = "id";
        public static final String SERVANT_ICON = "icon";
        public static final String SERVANT_NAME = "name";
    }

    // 2. 경험치 테이블 변수 생성
    public static final class ExpTable implements BaseColumns {
        public static final String TABLE_NAME = "exp";
        public static final String ID = "id";
        public static final String SERVANT_LEVEL = "servantLevel";
        public static final String SERVANT_EXP = "servantExp";
    }

    // 3. 스킬 테이블 변수 생성
    public static final class SkillTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantSkill";
        public static final String ID = "id";
        public static final String SKILL_ICON = "skill_icon";
        public static final String SKILL_NAME = "skill_name";
        public static final String SKILL_RANK = "skill_rank";
        public static final String SKILL_CLASSIFICATION = "skill_classification";
        public static final String SKILL_LEVEL = "skill_level";
        public static final String SKILL_TARGET = "skill_target";
        public static final String SKILL_RANGE = "skill_range";
        public static final String SKILL_EFFECT = "skill_effect";
        public static final String SKILL_VALUE = "skill_value";
        public static final String SKILL_MERIT = "skill_merit";
        public static final String SKILL_DURATION = "skill_duration";
        public static final String SKILL_COOLDOWN = "skill_coolDown";
        public static final String SKILL_PERCENT = "skill_percent";
        public static final String SKILL_ENHANCE = "skill_enhance";
    }

    // 보구 테이블 변수
    public static final class WeaponTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantWeapon";
        public static final String ID = "id";
        public static final String WEAPON_NAME = "weapon_name";
        public static final String WEAPON_SUB_NAME = "weapon_sub_name";
        public static final String WEAPON_RANK = "weapon_rank";
        public static final String WEAPON_CLASSIFICATION = "weapon_classification";
        public static final String WEAPON_LEVEL = "weapon_level";
        public static final String WEAPON_TARGET = "weapon_target";
        public static final String WEAPON_RANGE = "weapon_range";
        public static final String WEAPON_EXCEPT = "weapon_except";
        public static final String WEAPON_EFFECT = "weapon_effect";
        public static final String WEAPON_VALUE = "weapon_value";
        public static final String WEAPON_TYPE = "weapon_type";
        public static final String WEAPON_MERIT = "weapon_merit";
        public static final String WEAPON_HIT = "weapon_hit";
        public static final String WEAPON_DURATION = "weapon_duration";
        public static final String WEAPON_PERCENT = "weapon_percent";
        public static final String WEAPON_ENHANCE = "weapon_enhance";
    }

    public static final class ServantJoinSkillTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantJoinSkill";
        public static final String ID = "id";
        public static final String SERVANT_ID = "servant_id";
        public static final String SKILL_ID = "skill_id";
    }


    // 서번트 테이블 생성문
    public static final String SQL_CREATE_SERVANT =
            "create table " +
                    ServantTable.TABLE_NAME + " (" +
                    ServantTable.ID + " integer primary key not null, " +
                    ServantTable.SERVANT_ICON + " text not null ," +
                    ServantTable.SERVANT_NAME  + " text not null , " +
                    ServantTable.SERVANT_CLASS + " text not null , " +
                    ServantTable.SERVANT_GRADE + " integer not null" + ");";

    public static final String SQL_CREATE_SERVANT_NAME =
            "create table " +
                    ServantNameTable.TABLE_NAME + " (" +
                    ServantNameTable.ID + " integer primary key not null, " +
                    ServantNameTable.SERVANT_ICON + " text not null ," +
                    ServantNameTable.SERVANT_NAME  + " text not null" + ");";

    public static final String SQL_CREATE_SERVANT_JOIN_SKILL =
            "create table " +
                    ServantJoinSkillTable.TABLE_NAME + " (" +
                    ServantJoinSkillTable.ID + " integer primary key not null, " +
                    ServantJoinSkillTable.SERVANT_ID + " integer not null ," +
                    ServantJoinSkillTable.SKILL_ID  + " integer not null" + ");";


    // 서번트 경험치 테이블 생성
    public static final String SQL_CREATE_EXP =
            "create table " +
                    ExpTable.TABLE_NAME + " (" +
                    ExpTable.ID + " integer not null , " +
                    ExpTable.SERVANT_LEVEL + " integer not null ," +
                    ExpTable.SERVANT_EXP  + " integer not null" + ");";

    // 스킬 테이블 생성
    public static final String SQL_CREATE_SKILL =
            "create table " +
                    SkillTable.TABLE_NAME + " (" +
                    SkillTable.ID + " integer primary key not null, " +
                    SkillTable.SKILL_ICON + " text not null ," +
                    SkillTable.SKILL_NAME  + " text not null , " +
                    SkillTable.SKILL_RANK + " text , " +
                    SkillTable.SKILL_CLASSIFICATION + " text ," +
                    SkillTable.SKILL_LEVEL + " integer , " +
                    SkillTable.SKILL_TARGET + " text , " +
                    SkillTable.SKILL_RANGE + " text , " +
                    SkillTable.SKILL_EFFECT + " text , " +
                    SkillTable.SKILL_VALUE + " real , " +
                    SkillTable.SKILL_MERIT + " text , " +
                    SkillTable.SKILL_DURATION + " integer ," +
                    SkillTable.SKILL_COOLDOWN + " integer , " +
                    SkillTable.SKILL_PERCENT + " integer , " +
                    SkillTable.SKILL_ENHANCE + " integer " +
                    ");";

    public static final String SQL_CREATE_WEAPON =
            "create table " +
                    WeaponTable.TABLE_NAME + " (" +
                    WeaponTable.ID + " integer primary key not null, " +
                    WeaponTable.WEAPON_NAME  + " text not null , " +
                    WeaponTable.WEAPON_SUB_NAME  + " text , " +
                    WeaponTable.WEAPON_RANK + " text , " +
                    WeaponTable.WEAPON_CLASSIFICATION + " text ," +
                    WeaponTable.WEAPON_LEVEL + " integer , " +
                    WeaponTable.WEAPON_TARGET + " text , " +
                    WeaponTable.WEAPON_RANGE + " text , " +
                    WeaponTable.WEAPON_EXCEPT + " text , " +
                    WeaponTable.WEAPON_EFFECT + " text , " +
                    WeaponTable.WEAPON_VALUE + " real , " +
                    WeaponTable.WEAPON_TYPE + " text , " +
                    WeaponTable.WEAPON_MERIT + " text , " +
                    WeaponTable.WEAPON_HIT + " integer ," +
                    WeaponTable.WEAPON_DURATION + " integer ," +
                    WeaponTable.WEAPON_PERCENT + " integer , " +
                    WeaponTable.WEAPON_ENHANCE + " integer " +
                    ");";

}
