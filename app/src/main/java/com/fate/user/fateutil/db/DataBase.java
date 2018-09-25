package com.fate.user.fateutil.db;

import android.provider.BaseColumns;

public class DataBase {

    // 계약 클래스
    // DB 내용
    private DataBase() {
    }

    // 1. 서번트 테이블 생성
    // 1_1) 서번트 조인 리스트 테이블 생성 (서번트 아이디, 아이콘 아이디, 이름 아이디, 클래스 아이디)
    public static final class ServantJoinListTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantJoinList";
        public static final String SERVANT_ID = "servant_id";
        public static final String ICON_ID = "icon_id";
        public static final String NAME_ID = "name_id";
        public static final String CLASS_ID = "class_id";
        public static final String GRADE_VALUE = "grade_value";
    }

    // 1_2) 서번트 아이콘 테이블 생성 (서번트 아이콘 이름 아이디, 서번트 아이콘 이름)
    public static final class ServantIconTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantIcon";
        public static final String ICON_ID = "icon_id";
        public static final String ICON_NAME = "icon_name";
    }

    // 1_3) 서번트 이름 테이블 생성 (서번트 이름 아이디, 서번트 이름 값)
    public static final class ServantNameTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantName";
        public static final String NAME_ID = "name_id";
        public static final String NAME_VALUE = "name_value";
    }

    // 1_4) 서번트 클래스 테이블 생성 (서번트 클래스 아이디, 서번트 클래스 이름)
    public static final class ServantClassTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantClass";
        public static final String CLASS_ID = "class_id";
        public static final String CLASS_NAME = "class_name";
    }

    // 3. 스킬 테이블 변수 생성
    // 3_1) 서번트 조인 액티브스킬 테이블 생성 (아이디, 서번트 아이디, 스킬 아이디)
    public static final class ServantJoinSkillTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantJoinSkill";
        public static final String ID = "id";
        public static final String SERVANT_ID = "servant_id";
        public static final String SKILL_CLASSIFICATION ="skill_classification";
        public static final String SKILL_ID = "skill_id";
    }

    // 3_2) 서번트 액티브 스킬 테이블 생성
    // (스킬 아이디, 스킬 아이콘, 스킬 이름, 스킬 랭크, 스킬 분류, 스킬 레벨, 스킬 타겟, 스킬 범위
    // 스킬 효과, 스킬 값, 스킬 장단점, 스킬 지속시간, 스킬 쿨다운, 스킬 퍼센트 여부, 스킬 강화여부)
    public static final class ActiveSkillTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantActiveSkill";
        public static final String SKILL_ID = "skill_id";
        public static final String SKILL_ICON = "skill_icon";
        public static final String SKILL_NAME = "skill_name";
        public static final String SKILL_RANK = "skill_rank";
        public static final String SKILL_CLASSIFICATION = "skill_classification";
        public static final String SKILL_LEVEL = "skill_level";
        public static final String SKILL_TARGET = "skill_target";
        public static final String SKILL_RANGE = "skill_range";
        public static final String SKILL_EXPLAIN = "skill_explain";
        public static final String SKILL_EFFECT = "skill_effect";
        public static final String SKILL_VALUE = "skill_value";
        public static final String SKILL_MERIT = "skill_merit";
        public static final String SKILL_DURATION = "skill_duration";
        public static final String SKILL_COOLDOWN = "skill_coolDown";
        public static final String SKILL_PERCENT = "skill_percent";
        public static final String SKILL_ENHANCE = "skill_enhance";
    }

    // 3_3) 서번트 패시브 스킬 테이블 생성
    public static final class PassiveSkillTable implements BaseColumns {
        public static final String TABLE_NAME = "ServantPassiveSkill";
        public static final String SKILL_ID = "skill_id";
        public static final String SKILL_ICON = "skill_icon";
        public static final String SKILL_NAME = "skill_name";
        public static final String SKILL_RANK = "skill_rank";
        public static final String SKILL_EXPLAIN = "skill_explain";
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

    // 4. 경험치 테이블 변수 생성
    public static final class ExpTable implements BaseColumns {
        public static final String TABLE_NAME = "exp";
        public static final String ID = "id";
        public static final String SERVANT_LEVEL = "servantLevel";
        public static final String SERVANT_EXP = "servantExp";
    }

    // 5. 마술예장 테이블 변수 생성
    public static final class MagicTable implements BaseColumns {
        public static final String TABLE_NAME = "Magic";
        public static final String ID = "id";
        public static final String MAGIC_NAME = "magicName";
        public static final String MAGIC_CONTENT = "magicContent";
        public static final String MAGIC_IMAGE = "magicImage";
        public static final String MAGIC_DELETE_YN = "magicDeleteYn";
    }

    // 6. 마술예장 스킬 테이블 변수 생성
    public static final class MagicEffectTable implements BaseColumns {
        public static final String TABLE_NAME = "MagicEffect";
        public static final String ID = "id";
        public static final String MAGIC_ID = "magicId";
        public static final String MAGIC_EFFECT_NAME = "magicEffectName";
        public static final String MAGIC_EFFECT_GOAL = "magicEffectGoal";
        public static final String MAGIC_EFFECT_CONTENT = "magicEffectContent";
        public static final String MAGIC_EFFECT_TIME = "magicEffectTime";
        public static final String MAGIC_EFFECT_LEVEL = "magicEffectLevel";
        public static final String MAGIC_EFFECT_IMAGE = "magicEffectImage";
        public static final String MAGIC_EFFECT_UTIL = "magicEffectUtil";
        public static final String MAGIC_EFFECT_DELETE_YN = "magicEffectDeleteYn";
    }

    // 7. 마술예장 경험치 테이블 변수 생성
    public static final class MagicExpTable implements BaseColumns {
        public static final String TABLE_NAME = "MagicExp";
        public static final String ID = "id";
        public static final String MAGIC_ID = "magicId";
        public static final String MAGIC_EXP_LEVEL = "magicExpLevel";
        public static final String MAGIC_EXP_COUNT = "magicExpCount";
        public static final String MAGIC_EXP_TOTAL = "magicExpTotal";
        public static final String MAGIC_DELETE_YN = "magicDeleteYn";
    }


    // 1. 서번트 조인 리스트
    // 1_1) 서번트 조인 리스트 테이블 생성
    public static final String SQL_CREATE_SERVANT_JOIN_LIST =
            "create table " +
                    ServantJoinListTable.TABLE_NAME + " (" +
                    ServantJoinListTable.SERVANT_ID + " integer primary key not null, " +
                    ServantJoinListTable.ICON_ID + " integer not null ," +
                    ServantJoinListTable.NAME_ID + " integer not null , " +
                    ServantJoinListTable.CLASS_ID + " integer not null , " +
                    ServantJoinListTable.GRADE_VALUE + " integer not null" + ");";

    // 1_2) 서번트 아이콘 테이블 생성
    public static final String SQL_CREATE_SERVANT_ICON =
            "create table " +
                    ServantIconTable.TABLE_NAME + " (" +
                    ServantIconTable.ICON_ID + " integer primary key not null, " +
                    ServantIconTable.ICON_NAME + " text not null" + ");";

    // 1_3) 서번트 이름 테이블 생성
    public static final String SQL_CREATE_SERVANT_NAME =
            "create table " +
                    ServantNameTable.TABLE_NAME + " (" +
                    ServantNameTable.NAME_ID + " integer primary key not null, " +
                    ServantNameTable.NAME_VALUE + " text not null" + ");";

    // 1_4) 서번트 클래스 테이블 생성
    public static final String SQL_CREATE_SERVANT_CLASS =
            "create table " +
                    ServantClassTable.TABLE_NAME + " (" +
                    ServantClassTable.CLASS_ID + " integer primary key not null, " +
                    ServantClassTable.CLASS_NAME + " text not null" + ");";

    // 2. 서번트 스킬
    // 2_1) 서번트 조인 액티브 스킬 테이블 생성
    public static final String SQL_CREATE_SERVANT_JOIN_SKILL =
            "create table " +
                    ServantJoinSkillTable.TABLE_NAME + " (" +
                    ServantJoinSkillTable.ID + " integer primary key not null, " +
                    ServantJoinSkillTable.SERVANT_ID + " integer not null ," +
                    ServantJoinSkillTable.SKILL_CLASSIFICATION + " text not null, " +
                    ServantJoinSkillTable.SKILL_ID + " integer not null" + ");";

    // 2_2) 서번트 액티브 스킬 테이블 생성
    public static final String SQL_CREATE_ACTIVE_SKILL =
            "create table " +
                    ActiveSkillTable.TABLE_NAME + " (" +
                    ActiveSkillTable.SKILL_ID + " integer primary key not null, " +
                    ActiveSkillTable.SKILL_ICON + " text not null ," +
                    ActiveSkillTable.SKILL_NAME + " text not null , " +
                    ActiveSkillTable.SKILL_RANK + " text , " +
                    ActiveSkillTable.SKILL_CLASSIFICATION + " text ," +
                    ActiveSkillTable.SKILL_LEVEL + " integer , " +
                    ActiveSkillTable.SKILL_TARGET + " text , " +
                    ActiveSkillTable.SKILL_RANGE + " text , " +
                    ActiveSkillTable.SKILL_EXPLAIN + " text , " +
                    ActiveSkillTable.SKILL_EFFECT + " text , " +
                    ActiveSkillTable.SKILL_VALUE + " real , " +
                    ActiveSkillTable.SKILL_MERIT + " text , " +
                    ActiveSkillTable.SKILL_DURATION + " integer ," +
                    ActiveSkillTable.SKILL_COOLDOWN + " integer , " +
                    ActiveSkillTable.SKILL_PERCENT + " integer , " +
                    ActiveSkillTable.SKILL_ENHANCE + " integer " +
                    ");";

    // 2_3) 서번트 패시브 스킬 테이블 생성
    public static final String SQL_CREATE_PASSIVE_SKILL =
            "create table " +
                    PassiveSkillTable.TABLE_NAME + " (" +
                    PassiveSkillTable.SKILL_ID + " integer primary key not null, " +
                    PassiveSkillTable.SKILL_ICON + " text not null ," +
                    PassiveSkillTable.SKILL_NAME + " text not null , " +
                    PassiveSkillTable.SKILL_RANK + " text , " +
                    PassiveSkillTable.SKILL_EXPLAIN + " text " +
                    ");";

    // 3. 서번트 조인 보구 테이블 생성
    // 3_1) 서번트 보구 테이블 생성
    public static final String SQL_CREATE_WEAPON =
            "create table " +
                    WeaponTable.TABLE_NAME + " (" +
                    WeaponTable.ID + " integer primary key not null, " +
                    WeaponTable.WEAPON_NAME + " text not null , " +
                    WeaponTable.WEAPON_SUB_NAME + " text , " +
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

    // 4. 경험치 테이블
    // 서번트 경험치 테이블 생성
    public static final String SQL_CREATE_EXP =
            "create table " +
                    ExpTable.TABLE_NAME + " (" +
                    ExpTable.ID + " integer not null , " +
                    ExpTable.SERVANT_LEVEL + " integer not null ," +
                    ExpTable.SERVANT_EXP + " integer not null" + ");";

    // 5. 마술예장 테이블
    // 마술예장 테이블 생성
    public static final String SQL_CREATE_MAGIC =
            "create table " +
                    MagicTable.TABLE_NAME + " (" +
                    MagicTable.ID + " integer not null , " +
                    MagicTable.MAGIC_NAME + " text not null , " +
                    MagicTable.MAGIC_CONTENT + " text not null ," +
                    MagicTable.MAGIC_IMAGE + " text not null ," +
                    MagicTable.MAGIC_DELETE_YN + " char(1) not null" + ");";

    // 6. 마술예장 스킬 테이블
    // 마술예장 스킬 테이블 생성
    public static final String SQL_CREATE_MAGIC_EFFECT =
            "create table " +
                    MagicEffectTable.TABLE_NAME + " (" +
                    MagicEffectTable.ID + " integer not null , " +
                    MagicEffectTable.MAGIC_ID + " integer not null , " +
                    MagicEffectTable.MAGIC_EFFECT_NAME + " text not null ," +
                    MagicEffectTable.MAGIC_EFFECT_GOAL + " text not null ," +
                    MagicEffectTable.MAGIC_EFFECT_CONTENT + " text not null ," +
                    MagicEffectTable.MAGIC_EFFECT_TIME + " integer not null ," +
                    MagicEffectTable.MAGIC_EFFECT_LEVEL + " integer not null ," +
                    MagicEffectTable.MAGIC_EFFECT_IMAGE + " text not null ," +
                    MagicEffectTable.MAGIC_EFFECT_UTIL + " text not null ," +
                    MagicEffectTable.MAGIC_EFFECT_DELETE_YN + " char(1) not null" + ");";

    // 7. 마술예장 경험치 테이블
    // 마술예장 경험치 테이블 생성
    public static final String SQL_CREATE_MAGIC_EXP =
            "create table " +
                    MagicExpTable.TABLE_NAME + " (" +
                    MagicExpTable.ID + " integer not null , " +
                    MagicExpTable.MAGIC_ID + " integer not null , " +
                    MagicExpTable.MAGIC_EXP_LEVEL + " integer not null ," +
                    MagicExpTable.MAGIC_EXP_COUNT + " integer not null ," +
                    MagicExpTable.MAGIC_EXP_TOTAL + " integer not null ," +
                    MagicExpTable.MAGIC_DELETE_YN + " char(1) not null" + ");";
}
