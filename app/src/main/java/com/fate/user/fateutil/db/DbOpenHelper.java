package com.fate.user.fateutil.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fate.user.fateutil.db.contact.Magic.MagicContact;
import com.fate.user.fateutil.db.contact.Magic.MagicEffectContact;
import com.fate.user.fateutil.db.contact.Magic.MagicExpContact;
import com.fate.user.fateutil.db.contact.Material.MaterialContact;
import com.fate.user.fateutil.db.contact.Servant.ServantAscensionContact;
import com.fate.user.fateutil.db.contact.Servant.ServantExpContact;
import com.fate.user.fateutil.db.contact.Servant.ServantClassContact;
import com.fate.user.fateutil.db.contact.Servant.ServantContact;
import com.fate.user.fateutil.db.contact.Servant.ServantIconContact;
import com.fate.user.fateutil.db.contact.Skill.ServantJoinSkillContact;
import com.fate.user.fateutil.db.contact.Servant.ServantNameContact;
import com.fate.user.fateutil.db.contact.Skill.SkillContact;
import com.fate.user.fateutil.db.contact.Weapon.WeaponContact;
import com.fate.user.fateutil.db.contact.Weapon.WeaponJoinContact;
import com.fate.user.fateutil.model.MagicEffectForFirstModel;
import com.fate.user.fateutil.model.MagicEffectForSecondModel;

import java.util.ArrayList;
import java.util.List;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "fatedb.db";
    private static final int DATABASE_VERSION = 1_1_36;
    private DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDB;
    private Context mContext;

    private class DatabaseHelper extends SQLiteOpenHelper {
        // 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들 때 한번만 호출
        // 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 서번트 테이블 생성
            db.execSQL(DataBase.SQL_CREATE_SERVANT_ICON);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_NAME);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_CLASS);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_EXP);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_JOIN_LIST);
            // 서번트 스테이터스
            db.execSQL(DataBase.SQL_CREATE_ASCENSION_IMG);
            // 서번트 스킬
            db.execSQL(DataBase.SQL_CREATE_ACTIVE_SKILL);
            db.execSQL(DataBase.SQL_CREATE_PASSIVE_SKILL);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_JOIN_SKILL);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_WEAPON);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_JOIN_WEAPON);
            // 서번트 재료
            db.execSQL(DataBase.SQL_CREATE_SERVANT_MATERIAL);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_JOIN_MATERIAL);

            // 마술 예장
            db.execSQL(DataBase.SQL_CREATE_MAGIC);
            db.execSQL(DataBase.SQL_CREATE_MAGIC_EFFECT);
            db.execSQL(DataBase.SQL_CREATE_MAGIC_EXP);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // 서번트 테이블
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantIconTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantNameTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantClassTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantExpTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantJoinListTable.TABLE_NAME);
            // 서번트 스테이터스
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantAscensionImageTable.TABLE_NAME);
            // 서번트 스킬 테이블
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ActiveSkillTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.PassiveSkillTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantJoinSkillTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.WeaponJoinTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.WeaponTable.TABLE_NAME);
            // 서번트 재료 테이블
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantMaterialTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantJoinMaterialTable.TABLE_NAME);
            // 마술 예장 테이블
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.MagicTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.MagicEffectTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.MagicExpTable.TABLE_NAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        this.mContext = context;
    }

    public DbOpenHelper open() throws SQLException {
        // 읽기
        mDBHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDB.close();
    }

    // 데이터베이스에 데이터가 들어있는지 확인
    public boolean checkData(String tableName) {

        mDB = mDBHelper.getReadableDatabase();

        // String getId = "SELECT " + tableName.
        String getData = "SELECT * FROM " + tableName;

        Cursor cursor = mDB.rawQuery(getData, null);

        // 데이터가 있으면 true를 반환
        if (cursor.getCount() > 0) {
            return true;
        }

        // 아니면 false
        return false;

    }

    /*
    - 데이터 베이스에 데이터 삽입 -
    1. 서번트 리스트
    1_1) 서번트 조인 리스트 데이터 집어넣기
    1_2) 서번트 아이콘 데이터 집어 넣기
    1_3) 서번트 이름 데이터 집어 넣기
    1_4) 서번트 클래스 데이터 집어넣기
    1_5) 서번트 경험치 데이터 집어 넣기

    2. 서번트 스테이터스
    2_1) 서번트 영기재림 이미지 데이터 집어 넣기
    2_2) 서번트 액티브 스킬 조인 리스트 데이터 집어 넣기
    2_3) 서번트 액티브 스킬 데이터 집어 넣기
    2_4) 서번트 패시트 스킬 데이터 집어 닣기
    2_5) 서번트 재료 조인 테이블에 데이터 집어 넣기
    2_6) 서번트 재료 테이블 데이터 집어 넣기

    3. 서번트 보구
    3_1) 서번트 보구 조인 리스트

     */

    // 1. 서번트 리스트
    // 1_1) 서번트 조인 리스트 데이터 집어넣기
    public void addServantJoinList(ServantContact contact) {

        mDB = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DataBase.ServantJoinListTable.SERVANT_ID, contact.getServantId()); // 서번트 아이디
        cv.put(DataBase.ServantJoinListTable.ICON_ID, contact.getServantIconId()); // 서번트 아이콘 아이디
        cv.put(DataBase.ServantJoinListTable.NAME_ID, contact.getServantNameId()); // 서번트 이름 아이디
        cv.put(DataBase.ServantJoinListTable.CLASS_ID, contact.getServantClassId()); // 서번트 클래스 아이디
        cv.put(DataBase.ServantJoinListTable.GRADE_VALUE, contact.getServantGrade()); // 서번트 등급 값

        mDB.insert(DataBase.ServantJoinListTable.TABLE_NAME, null, cv);
    }

    // 1_2) 서번트 아이콘 데이터 집어 넣기
    public void addServantIcon(ServantIconContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantIconTable.ICON_ID, contact.getServantIconId()); // 서번트 아이콘 아이디
        cv.put(DataBase.ServantIconTable.ICON_NAME, contact.getServantIcon()); // 서번트 아이콘

        mDB.insert(DataBase.ServantIconTable.TABLE_NAME, null, cv);

    }

    // 1_3) 서번트 이름 데이터 집어 넣기
    public void addServantName(ServantNameContact contact) {

        mDB = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DataBase.ServantNameTable.NAME_ID, contact.getServantNameId()); // 서번트 이름 아이디
        cv.put(DataBase.ServantNameTable.NAME_VALUE, contact.getServantName()); // 서번트 이름

        mDB.insert(DataBase.ServantNameTable.TABLE_NAME, null, cv);

    }

    // 1_4) 서번트 클래스 데이터 집어넣기
    public void addServantClass(ServantClassContact contact) {

        mDB = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DataBase.ServantClassTable.CLASS_ID, contact.getServantClassId()); // 서번트 클래스 아이디
        cv.put(DataBase.ServantClassTable.CLASS_NAME, contact.getServantClass()); // 서번트 클래스

        mDB.insert(DataBase.ServantClassTable.TABLE_NAME, null, cv);

    }

    // 1_5) 서번트 경험치 데이터 넣기넣기
    public void addServantExpContact(ServantExpContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantExpTable.ID, contact.getId());
        cv.put(DataBase.ServantExpTable.SERVANT_LEVEL, contact.getServantLevel());
        cv.put(DataBase.ServantExpTable.SERVANT_EXP, contact.getServantExp());

        mDB.insert(DataBase.ServantExpTable.TABLE_NAME, null, cv);

    }

    // 2. 서번트 스테이터스
    // 2_1) 서번트 영기재림 이미지 데이터 집어 넣기
    public void addServantAscensionImage(ServantAscensionContact contact){

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantAscensionImageTable.ASCENSION_ID, contact.getAscensionId());
        cv.put(DataBase.ServantAscensionImageTable.SERVANT_ID, contact.getServantId());
        cv.put(DataBase.ServantAscensionImageTable.ASCENSION_CLASSIFICATION, contact.getAscensionClassification());
        cv.put(DataBase.ServantAscensionImageTable.ASCENSION_LEVEL, contact.getAscensionLevel());
        cv.put(DataBase.ServantAscensionImageTable.ASCENSION_IMG_NAME, contact.getAscensionImgName());

        mDB.insert(DataBase.ServantAscensionImageTable.TABLE_NAME, null, cv);

    }
    // 2_2) 서번트 액티브 스킬 조인 리스트 데이터 집어 넣기
    public void addServantJoinSkill(ServantJoinSkillContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantJoinSkillTable.ID, contact.getId()); // JOIN 부분
        cv.put(DataBase.ServantJoinSkillTable.SERVANT_ID, contact.getServantId());
        cv.put(DataBase.ServantJoinSkillTable.SKILL_CLASSIFICATION, contact.getSkillClassification());
        cv.put(DataBase.ServantJoinSkillTable.SKILL_ID, contact.getSkillId());

        mDB.insert(DataBase.ServantJoinSkillTable.TABLE_NAME, null, cv);

    }
    // 2_3) 서번트 액티브 스킬 데이터 집어 넣기
    public void addActiveSkillList(SkillContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ActiveSkillTable.SKILL_ID, contact.getSkillId());
        cv.put(DataBase.ActiveSkillTable.SKILL_ICON, contact.getSkillIcon());
        cv.put(DataBase.ActiveSkillTable.SKILL_NAME, contact.getSkillName());
        cv.put(DataBase.ActiveSkillTable.SKILL_RANK, contact.getSkillRank());
        cv.put(DataBase.ActiveSkillTable.SKILL_CLASSIFICATION, contact.getSkillClassification());
        cv.put(DataBase.ActiveSkillTable.SKILL_LEVEL, contact.getSkillLevel());
        cv.put(DataBase.ActiveSkillTable.SKILL_TARGET, contact.getSkillTarget());
        cv.put(DataBase.ActiveSkillTable.SKILL_RANGE, contact.getSkillRange());
        cv.put(DataBase.ActiveSkillTable.SKILL_EFFECT, contact.getSkillEffect());
        cv.put(DataBase.ActiveSkillTable.SKILL_EXPLAIN, contact.getSkillExplain());
        cv.put(DataBase.ActiveSkillTable.SKILL_VALUE, contact.getSkillValue());
        cv.put(DataBase.ActiveSkillTable.SKILL_MERIT, contact.getSkillMerit());
        cv.put(DataBase.ActiveSkillTable.SKILL_DURATION, contact.getSkillDuration());
        cv.put(DataBase.ActiveSkillTable.SKILL_COOLDOWN, contact.getSkillCoolDown());
        cv.put(DataBase.ActiveSkillTable.SKILL_PERCENT, contact.getSkillPercent());
        cv.put(DataBase.ActiveSkillTable.SKILL_ENHANCE, contact.getSkillEnhance());

        mDB.insert(DataBase.ActiveSkillTable.TABLE_NAME, null, cv);

    }
    // 2_4) 서번트 패시브 스킬 데이터 집어 넣기
    public void addPassiveSkillList(SkillContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.PassiveSkillTable.SKILL_ID, contact.getSkillId());
        cv.put(DataBase.PassiveSkillTable.SKILL_ICON, contact.getSkillIcon());
        cv.put(DataBase.PassiveSkillTable.SKILL_NAME, contact.getSkillName());
        cv.put(DataBase.PassiveSkillTable.SKILL_RANK, contact.getSkillRank());
        cv.put(DataBase.PassiveSkillTable.SKILL_EXPLAIN, contact.getSkillExplain());

        mDB.insert(DataBase.PassiveSkillTable.TABLE_NAME, null, cv);

    }
    // 2_5) 서번트 조인 재료 데이터 삽입
    public void addServantJoinMaterial(MaterialContact contact) {
        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantJoinMaterialTable.UPGRADE_ID, contact.getUpgradeId());
        cv.put(DataBase.ServantJoinMaterialTable.SERVANT_ID, contact.getServantId());
        cv.put(DataBase.ServantJoinMaterialTable.UPGRADE_LEVEL, contact.getUpgradeLevel());
        cv.put(DataBase.ServantJoinMaterialTable.UPGRADE_CLASSIFICATION, contact.getUpgradeClassification());
        cv.put(DataBase.ServantJoinMaterialTable.UPGRADE_LEVEL, contact.getUpgradeLevel());
        cv.put(DataBase.ServantJoinMaterialTable.MATERIAL_ID, contact.getMaterialId());
        cv.put(DataBase.ServantJoinMaterialTable.MATERIAL_VALUE, contact.getMaterialValue());

        mDB.insert(DataBase.ServantJoinMaterialTable.TABLE_NAME, null, cv);
    }
    // 2_6) 서번트 재료 데이터 삽입
    public void addServantMaterial(MaterialContact contact) {
        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantMaterialTable.MATERIAL_ID, contact.getMaterialId());
        cv.put(DataBase.ServantMaterialTable.MATERIAL_NAME, contact.getMaterialName());
        cv.put(DataBase.ServantMaterialTable.MATERIAL_KOR_NAME, contact.getMaterialKorName());

        mDB.insert(DataBase.ServantMaterialTable.TABLE_NAME, null, cv);

    }


    // 5. 서번트 보구
    // 5_1) 보구 테이블에 데이터 삽입
    public void addServantWeaponList(WeaponContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.WeaponTable.WEAPON_ID, contact.getWeaponId());
        cv.put(DataBase.WeaponTable.WEAPON_NAME, contact.getWeaponName());
        cv.put(DataBase.WeaponTable.WEAPON_SUB_NAME, contact.getWeaponSubName());
        cv.put(DataBase.WeaponTable.WEAPON_RANK, contact.getWeaponRank());
        cv.put(DataBase.WeaponTable.WEAPON_CLASSIFICATION, contact.getWeaponClassification());
        cv.put(DataBase.WeaponTable.WEAPON_LEVEL, contact.getWeaponLevel());
        cv.put(DataBase.WeaponTable.WEAPON_TARGET, contact.getWeaponTarget());
        cv.put(DataBase.WeaponTable.WEAPON_RANGE, contact.getWeaponRange());
        cv.put(DataBase.WeaponTable.WEAPON_EXCEPT, contact.getWeaponExcept());
        cv.put(DataBase.WeaponTable.WEAPON_EFFECT, contact.getWeaponEffect());
        cv.put(DataBase.WeaponTable.WEAPON_VALUE, contact.getWeaponValue());
        cv.put(DataBase.WeaponTable.WEAPON_TYPE, contact.getWeaponType());
        cv.put(DataBase.WeaponTable.WEAPON_MERIT, contact.getWeaponMerit());
        cv.put(DataBase.WeaponTable.WEAPON_HIT, contact.getWeaponHit());
        cv.put(DataBase.WeaponTable.WEAPON_DURATION, contact.getWeaponDuration());
        cv.put(DataBase.WeaponTable.WEAPON_PERCENT, contact.getWeaponPercent());
        cv.put(DataBase.WeaponTable.WEAPON_ENHANCE, contact.getWeaponEnhance());

        mDB.insert(DataBase.WeaponTable.TABLE_NAME, null, cv);

    }
    // 5_1) 보구 조인 테이블에 데이터 삽입
    public void addServantJoinWeapon(WeaponJoinContact contact){
        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.WeaponJoinTable.ID, contact.getId());
        cv.put(DataBase.WeaponJoinTable.SERVANT_ID, contact.getServantId());
        cv.put(DataBase.WeaponJoinTable.WEAPON_ID, contact.getWeaponId());

        mDB.insert(DataBase.WeaponJoinTable.TABLE_NAME, null, cv);
    }

    /*
    - 데이터 베이스에서 검색하기 -
    1. 서번트 검색
    1_1) 모든 서번트 리스트 가져오기
    1_2) 서번트 조인 리스트 에서 아이디에 맞는 서번트 리스트 가져오기
    1_3) 서번트 경험치에서 레벨에 해당하는 경험치 값 가져오기

    2. 서번트 스테이터스
    2_1) 서번트가 가지고 있는 액티브 스킬 아이콘, 이름 가지고 오기
    2_2) 서번트가 가지고 있는 액티브 스킬의 설명 리스트 가져오기
    2_3) 서번트가 가지고 있는 액티브 스킬의 효과 가져오기
    2_4) 서번트가 가지고 있는 액티브 스킬의 수치 가져오기
    2_5) 서번트가 가지고 있는 패시브 스킬 가져오기
    2_6) 서번트가 가지고 있는 영기재료, 스킬재료 가져오기
    2_7) 서번트가 가지고 있는 서번트 재림 이미지 가져오기


    3. 보구 검색

     */

    // 1. 서번트 검색
    // 1_1) 모든 서번트 리스트 가져오기
    public List<ServantContact> getAllServantList(String gradeData, String classData) {

        List<ServantContact> contactServantList = new ArrayList<>();

        // 검색 쿼리 저장
        String getAllServantQuery = "select" +
                " SJL.servant_id as servant_id," +
                " SI.icon_name as servant_icon," +
                " SN.name_value as servant_name," +
                " SC.class_name as servant_class," +
                " SJL.grade_value as servant_grade" +
                " from ServantJoinList as SJL" +
                " inner join ServantIcon as SI" +
                " on SJL.icon_id = SI.icon_id" +
                " inner join ServantName as SN" +
                " on SJL.name_id = SN.name_id" +
                " inner join ServantClass as SC" +
                " on SJL.class_id = SC.class_id";

        getAllServantQuery += " WHERE 0=0 ";

        // 등급 데이터가 있을 경우
        if (!"".equals(gradeData) && !"전체".equals(gradeData)) {
            gradeData = gradeData.replace("성", "");
            getAllServantQuery += " AND SJL.grade_value = '" + gradeData + "'";
        }

        // 클래스 데이터가 있을 경우
        if (!"".equals(classData) && !"전체".equals(classData)) {
            getAllServantQuery += " AND SC.class_name = '" + classData + "'";
        }

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(getAllServantQuery, null);

        if (cursor.moveToFirst()) {

            do {
                ServantContact contact = new ServantContact();

                contact.setServantId(cursor.getInt(cursor.getColumnIndex("servant_id")));
                contact.setServantIcon(cursor.getString(cursor.getColumnIndex("servant_icon")));
                contact.setServantName(cursor.getString(cursor.getColumnIndex("servant_name")));
                contact.setServantClass(cursor.getString(cursor.getColumnIndex("servant_class")));
                contact.setServantGrade(cursor.getInt(cursor.getColumnIndex("servant_grade")));

                contactServantList.add(contact);

            } while (cursor.moveToNext());
        }
        return contactServantList;
    }

    // 1_2) 서번트 조인 리스트 에서 아이디에 맞는 서번트 리스트 가져오기
    public List<ServantContact> getSelectList(int servantId) {

        List<ServantContact> contactServantList = new ArrayList<>();

        String getAllServantQuery = "select" +
                " SJL.servant_id as servant_id," +
                " SI.icon_name as servant_icon," +
                " SN.name_value as servant_name," +
                " SC.class_name as servant_class," +
                " SJL.grade_value as servant_grade" +
                " from ServantJoinList as SJL" +
                " inner join ServantIcon as SI" +
                " on SJL.icon_id = SI.icon_id" +
                " inner join ServantName as SN" +
                " on SJL.name_id = SN.name_id" +
                " inner join ServantClass as SC" +
                " on SJL.class_id = SC.class_id" +
                " where servant_Id=" + servantId;

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(getAllServantQuery, null);

        if (cursor.moveToFirst()) {

            do {
                ServantContact contact = new ServantContact();

                contact.setServantId(cursor.getInt(cursor.getColumnIndex("servant_id")));
                contact.setServantIcon(cursor.getString(cursor.getColumnIndex("servant_icon")));
                contact.setServantName(cursor.getString(cursor.getColumnIndex("servant_name")));
                contact.setServantClass(cursor.getString(cursor.getColumnIndex("servant_class")));
                contact.setServantGrade(cursor.getInt(cursor.getColumnIndex("servant_grade")));

                contactServantList.add(contact);

            } while (cursor.moveToNext());
        }
        return contactServantList;
    }

    // 1_3) 서번트 레벨에 해당하는 경험치값 가져오기 (단순히 값만 가져오는 것으로)
    public int getExpContact(String Level) {

        int exp = 0;

        mDB = mDBHelper.getReadableDatabase();

        // id 서번트 레벨, 서번트 경험치 컬럼
        String[] columns = {DataBase.ServantExpTable.ID, DataBase.ServantExpTable.SERVANT_LEVEL, DataBase.ServantExpTable.SERVANT_EXP};
        String[] parms = new String[1]; // 파라메터
        parms[0] = Level;


        // 2) 커서를 통해 데이터를 뽑아온다.
        Cursor cursor = mDB.query(DataBase.ServantExpTable.TABLE_NAME, columns, DataBase.ServantExpTable.SERVANT_LEVEL + "=?", parms, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext() == true) {
                // 레벨에 따른 경험치를 얻어온다.
                exp = cursor.getInt(2);
            }
        }
        return exp;

    }

    // 2. 스킬 검색
    // 2_1) 서번트가 가지고 있는 액티브 스킬 아이콘, 이름 가지고 오기
    public List<SkillContact> getServantHavingActiveSkill(int servantId) {
        List<SkillContact> skillHaving = new ArrayList<SkillContact>();

        // 서번트가 가지고 있는 스킬 데이터를 가져온다.
        String selectHavingSkill = "SELECT " +
                "SJS.id as join_id, " +
                "SAS.skill_name as skill_name," +
                "(SAS.skill_name || ' ' || SAS.skill_rank) as skill_full_name," +
                "SAS.skill_icon as skill_icon" +
                " from  " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME + " as SN" +
                " on SJS.servant_id = " + servantId + " AND SN.name_id=" + servantId +
                " inner join " + DataBase.ActiveSkillTable.TABLE_NAME + " as SAS" +
                " on SJS.skill_id = SAS.skill_id" +
                " where SAS.skill_classification =" + "'L'" + " and SJS.skill_classification =" + "'A'" +
                " group by skill_full_name" +
                " order by join_id";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectHavingSkill, null);

        if (cursor.moveToFirst()) {
            do {
                SkillContact contact = new SkillContact();
                contact.setSkillIcon(cursor.getString(cursor.getColumnIndex("skill_icon")));
                contact.setSkillName(cursor.getString(cursor.getColumnIndex("skill_name")));
                contact.setSkillFullName(cursor.getString(cursor.getColumnIndex("skill_full_name")));
                skillHaving.add(contact);
            } while (cursor.moveToNext());
        }

        return skillHaving;
    }

    // 2_2) 서번트가 가지고 있는 액티브 스킬의 설명 리스트 가져오기
    public List<SkillContact> getServantActiveSkillExplain(int servantId, String skillName) {
        List<SkillContact> skillExplain = new ArrayList<>();

        // 설명 리스트를 가져온다.
        String selectSkillExplain = "select" +
                " (case " +
                " when SAS.skill_range = '' and SAS.skill_duration = 0" +
                " then (SAS.skill_target || SAS.skill_explain)" +
                " when SAS.skill_range = '' and SAS.skill_duration != 0" +
                " then (SAS.skill_target || SAS.skill_explain || ' (' || SAS.skill_duration || '턴)')" +
                " when SAS.skill_range != '' and SAS.skill_duration = 0" +
                " then (SAS.skill_target || ' ' || SAS.skill_range || ' ' || SAS.skill_explain)" +
                " else (SAS.skill_target || ' ' || SAS.skill_range || ' ' || SAS.skill_explain || ' (' || SAS.skill_duration || '턴)')" +
                " end)as skill_explain" +
                " from  " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME + " as SN" +
                " on SJS.servant_id = " + servantId + " AND SN.name_id=" + servantId +
                " inner join " + DataBase.ActiveSkillTable.TABLE_NAME + " as SAS" +
                " on SJS.skill_id = SAS.skill_id" +
                " where SAS.skill_name ='" + skillName + "'" +
                " and SAS.skill_level between 0 and 1" +
                " group by skill_effect" +
                " order by SAS.skill_id";

        mDB = mDBHelper.getReadableDatabase();


        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectSkillExplain, null);

        if (cursor.moveToFirst()) {
            do {
                SkillContact contact = new SkillContact();
                contact.setSkillExplain(cursor.getString(cursor.getColumnIndex("skill_explain")));
                skillExplain.add(contact);
            } while (cursor.moveToNext());
        }

        return skillExplain;
    }

    // 2_3) 서번트가 가지고 있는 액티브 스킬의 효과 가져오기
    public List<SkillContact> getServantActiveSkillEffect(int servantId, String skillName) {
        List<SkillContact> skillEffect = new ArrayList<>();

        String selectHavingSkillEffect = "select" +
                " SAS.skill_effect as skill_effect" +
                " from  ServantJoinSkill as SJS " +
                " inner join ServantName as SN " +
                " on SJS.servant_id = " + servantId + " AND SN.name_id=" + servantId +
                " inner join ServantActiveSkill as SAS " +
                " on SJS.skill_id = SAS.skill_id " +
                " where SAS.skill_name ='" + skillName + "'" +
                " and SAS.skill_level = 1" +
                " order by skill_level asc";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectHavingSkillEffect, null);

        if (cursor.moveToFirst()) {
            do {
                SkillContact contact = new SkillContact();
                contact.setSkillEffect(cursor.getString(cursor.getColumnIndex("skill_effect")));
                skillEffect.add(contact);
            } while (cursor.moveToNext());
        }

        return skillEffect;

    }

    // 2_4) 서번트가 가지고 있는 스킬의 수치 가져오기
    public List<SkillContact> getServantActiveSkillValue(int servantId, String skillName, String skillEffect) {
        List<SkillContact> skillNumber = new ArrayList<SkillContact>();

        // 서번트가 가지고 있는 수치를 가져온다.
        String selectHavingSkillValue = "select " +
                " SAS.skill_effect as skill_effect," +
                " SAS.skill_level as skill_level," +
                "(case when skill_percent = 1 then (SAS.skill_value || ' ' || '% ')" +
                " else SAS.skill_value " +
                " end) as skill_number," +
                " SAS.skill_coolDown as skill_coolDown" +
                " from  " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME + " as SN" +
                " on SJS.servant_id = " + servantId + " AND SN.name_id=" + servantId +
                " inner join " + DataBase.ActiveSkillTable.TABLE_NAME + " as SAS" +
                " on SJS.skill_id = SAS.skill_id" +
                " where SAS.skill_name ='" + skillName + "'" +
                " and SAS.skill_classification =" + "'L'" +
                " and SAS.skill_effect = '" + skillEffect + "'" +
                " order by skill_level asc";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectHavingSkillValue, null);

        if (cursor.moveToFirst()) {
            do {
                SkillContact contact = new SkillContact();
                contact.setSkillEffect(cursor.getString(cursor.getColumnIndex("skill_effect")));
                contact.setSkillLevel(cursor.getInt(cursor.getColumnIndex("skill_level")));
                contact.setSkillNumber(cursor.getString(cursor.getColumnIndex("skill_number")));
                contact.setSkillCoolDown(cursor.getInt(cursor.getColumnIndex("skill_coolDown")));
                skillNumber.add(contact);
            } while (cursor.moveToNext());
        }

        return skillNumber;

    }

    // 2_5) 서번트가 가지고 있는 패시브 스킬 가져오기
    public List<SkillContact> getServantHavingPassiveSkill(int servantId) {
        List<SkillContact> skillHaving = new ArrayList<SkillContact>();

        // 서번트가 가지고 있는 스킬 데이터를 가져온다.
        String selectHavingSkill = "SELECT " +
                " SPS.skill_id as skill_id, " +
                " SPS.skill_icon as skill_icon, " +
                " (SPS.skill_name || ' ' || SPS.skill_rank ) as skill_full_name, " +
                " SPS.skill_explain as skill_explain " +
                " from  " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME + " as SN" +
                " on SJS.servant_id = " + servantId + " AND SN.name_id=" + servantId +
                " inner join " + DataBase.PassiveSkillTable.TABLE_NAME + " as SPS" +
                " on SJS.skill_id = SPS.skill_id" +
                " where SJS.skill_classification =" + "'P'" +
                " order by skill_id";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectHavingSkill, null);

        if (cursor.moveToFirst()) {
            do {
                SkillContact contact = new SkillContact();
                contact.setSkillIcon(cursor.getString(cursor.getColumnIndex("skill_icon")));
                contact.setSkillFullName(cursor.getString(cursor.getColumnIndex("skill_full_name")));
                contact.setSkillExplain(cursor.getString(cursor.getColumnIndex("skill_explain")));
                skillHaving.add(contact);
            } while (cursor.moveToNext());
        }

        return skillHaving;
    }
    // 2_6) 서번트가 가지고 있는 영기재료, 스킬재료 가져오기
    public List<MaterialContact> getServantMaterial(int servantId, char classification) {
        List<MaterialContact> materialHaving = new ArrayList<>();

        // 서번트가 가지고 있는 스킬 데이터를 가져온다.
        String selectHavingMaterial = "select " +
                " SJM.upgrade_id as upgrade_id, " +
                " SN.name_value as servant_name, " +
                " SJM.upgrade_classification as upgrade_classification, " +
                " SJM.upgrade_level as upgrade_level, " +
                "SM.material_name as material_name, " +
                "SJM.material_value as material_value " +
                " from ServantMaterial SM " +
                " inner join ServantJoinMaterial SJM " +
                " on SM.material_id = SJM.material_id " +
                " inner join ServantName SN " +
                " on SN.name_id = SJM.servant_id " +
                " where upgrade_classification = '" + classification + "' " +
                " and  SJM.servant_id = " + servantId +
                " order by upgrade_id ";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectHavingMaterial, null);

        if (cursor.moveToFirst()) {
            do {
                MaterialContact contact = new MaterialContact();
                contact.setMaterialName(cursor.getString(cursor.getColumnIndex("material_name")));
                contact.setUpgradeClassification(cursor.getString(cursor.getColumnIndex("upgrade_classification")));
                contact.setUpgradeLevel(cursor.getInt(cursor.getColumnIndex("upgrade_level")));
                contact.setMaterialName(cursor.getString(cursor.getColumnIndex("material_name")));
                contact.setMaterialValue(cursor.getInt(cursor.getColumnIndex("material_value")));

                materialHaving.add(contact);
            } while (cursor.moveToNext());
        }

        return materialHaving;

    }
    // 2_7) 서번트가 가지고 있는 서번트 재림 이미지 가져오기
    public List<ServantAscensionContact> getServantAscensionImage(int servantId){
        List<ServantAscensionContact> servantImage = new ArrayList<>();
        // 서번트가 가지고 있는 재림 이미지를 가져온다.
        String selectServantImage = "select " +
                " SAI.ascension_id as ascension_id," +
                " SAI.servant_id as servant_id," +
                " SAI.ascension_classification as ascension_classification, " +
                " SAI.ascension_level as ascension_level," +
                " SAI.ascension_img_name as ascension_img_name" +
                " from ServantAscensionImg SAI" +
                " inner join ServantName SN" +
                " on SAI.servant_id = SN.name_id" +
                " and SN.name_id = " + servantId;

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectServantImage, null);

        if (cursor.moveToFirst()) {
            do {
                ServantAscensionContact contact = new ServantAscensionContact();
                contact.setAscensionId(cursor.getInt(cursor.getColumnIndex("ascension_id"))); // 재림 아이디
                contact.setServantId(cursor.getInt(cursor.getColumnIndex("servant_id"))); // 서번트 아이디
                contact.setAscensionClassification(cursor.getString(cursor.getColumnIndex("ascension_classification"))); // 영기재림 분류
                contact.setAscensionLevel(cursor.getInt(cursor.getColumnIndex("ascension_level"))); // 영기 재림 단계
                contact.setAscensionImgName(cursor.getString(cursor.getColumnIndex("ascension_img_name"))); // 이미지 이름

                servantImage.add(contact);
            } while (cursor.moveToNext());
        }

        return servantImage;
    }

    // 3. 보구 검색
    // 3_1) 보구 리스트 가져오기 (수정 예정)
    public List<WeaponContact> getServantJoinWeapon(int servantId) {

        List<WeaponContact> contactSkillList = new ArrayList<WeaponContact>();
        // 검색 쿼리 저장
        String selectServantJoinWeapon = "SELECT " +
                "SS.weapon_name as weapon_name, " +
                "SS.weapon_icon as weapon_icon, " +
                "SS.weapon_rank as weapon_rank, " +
                "SS.weapon_classification as weapon_classification, " +
                "SS.weapon_level as weapon_level, " +
                "SS.weapon_target as weapon_target, " +
                "SS.weapon_range as weapon_range, " +
                "SS.weapon_effect as weapon_effect, " +
                "SS.weapon_value as weapon_value, " +
                "SS.weapon_merit as weapon_merit, " +
                "SS.weapon_duration as weapon_duration, " +
                "SS.weapon_coolDown as weapon_cd, " +
                "SS.weapon_percent as weapon_percent, " +
                "SS.weapon_enhance as weapon_enhance " +
                " from " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME + " as SN " +
                " on SJS.servant_id = " + servantId + " AND SN.id = " + servantId +
                " inner join " + DataBase.ActiveSkillTable.TABLE_NAME + " as SS " +
                " on SJS.weapon_id = SS.id";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectServantJoinWeapon, null);

        if (cursor.moveToFirst()) {
            do {
                WeaponContact contact = new WeaponContact();
                contact.setWeaponId(cursor.getInt(cursor.getColumnIndex("weapon_id")));
                contact.setWeaponSubName(cursor.getString(cursor.getColumnIndex("weapon_sub_name")));
                contact.setWeaponName(cursor.getString(cursor.getColumnIndex("weapon_name")));
                contact.setWeaponRank(cursor.getString(cursor.getColumnIndex("weapon_rank")));
                contact.setWeaponClassification(cursor.getString(cursor.getColumnIndex("weapon_classification")));
                contact.setWeaponLevel(cursor.getInt(cursor.getColumnIndex("weapon_level")));
                contact.setWeaponTarget(cursor.getString(cursor.getColumnIndex("weapon_target")));
                contact.setWeaponRange(cursor.getString(cursor.getColumnIndex("weapon_range")));
                contact.setWeaponExcept(cursor.getString(cursor.getColumnIndex("weapon_except")));
                contact.setWeaponEffect(cursor.getString(cursor.getColumnIndex("weapon_effect")));
                contact.setWeaponValue(cursor.getFloat(cursor.getColumnIndex("weapon_value")));
                contact.setWeaponType(cursor.getString(cursor.getColumnIndex("weapon_type")));
                contact.setWeaponMerit(cursor.getString(cursor.getColumnIndex("weapon_merit")));
                contact.setWeaponHit(cursor.getInt(cursor.getColumnIndex("weapon_hit")));
                contact.setWeaponDuration(cursor.getInt(cursor.getColumnIndex("weapon_duration")));
                contact.setWeaponPercent(cursor.getInt(cursor.getColumnIndex("weapon_percent")));
                contact.setWeaponEnhance(cursor.getInt(cursor.getColumnIndex("weapon_enhance")));

                contactSkillList.add(contact);

            } while (cursor.moveToNext());
        }
        return contactSkillList;
    }

    // 4. 마술예장

    // 4_1) 마술 예장 정보 추가
    public void addMagic(MagicContact contact) {
        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.MagicTable.ID, contact.getId());
        cv.put(DataBase.MagicTable.MAGIC_CONTENT, contact.getMagicContent());
        cv.put(DataBase.MagicTable.MAGIC_NAME, contact.getMagicName());
        cv.put(DataBase.MagicTable.MAGIC_IMAGE, contact.getMagicImage());
        cv.put(DataBase.MagicTable.MAGIC_DELETE_YN, contact.getMagicDeleteYn());

        mDB.insert(DataBase.MagicTable.TABLE_NAME, null, cv);

    }

    // 4_2) 마술 예장 효과 정보 추가
    public void addMagicEffect(MagicEffectContact contact) {
        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.MagicEffectTable.ID, contact.getId());

        cv.put(DataBase.MagicEffectTable.MAGIC_ID , contact.getMagicId());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_NAME, contact.getMagicEffectName());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_GOAL, contact.getMagicEffectGoal());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_CONTENT, contact.getMagicEffectContent());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_TIME, contact.getMagicEffectTime());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_LEVEL, contact.getMagicEffectLevel());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_IMAGE, contact.getMagicEffectImage());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_UTIL, contact.getMagicEffectUtil());
        cv.put(DataBase.MagicEffectTable.MAGIC_EFFECT_DELETE_YN, contact.getMagicEffectDeleteYn());

        mDB.insert(DataBase.MagicEffectTable.TABLE_NAME, null, cv);

    }

    // 4_3) 마술 예장 경험치 정보 추가
    public void addMagicExp(MagicExpContact contact) {
        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.MagicExpTable.ID, contact.getId());

        cv.put(DataBase.MagicExpTable.MAGIC_ID , contact.getMagicId());
        cv.put(DataBase.MagicExpTable.MAGIC_EXP_LEVEL, contact.getMagicExpLevel());
        cv.put(DataBase.MagicExpTable.MAGIC_EXP_COUNT, contact.getMagicExpCount());
        cv.put(DataBase.MagicExpTable.MAGIC_EXP_TOTAL, contact.getMagicExpTotal());
        cv.put(DataBase.MagicExpTable.MAGIC_DELETE_YN, contact.getMagicExpDeleteYn());

        mDB.insert(DataBase.MagicExpTable.TABLE_NAME, null, cv);

    }

    // 4-4) 마술예장 정보 가져오기
    public MagicContact getMagic(int magicId){
        MagicContact magicData = new MagicContact();

        // 검색 쿼리 저장
        String selectMagicListQuery = "";

        selectMagicListQuery += "SELECT ";
        selectMagicListQuery += "magic_name as magicName ";
        selectMagicListQuery += ", magic_content as magicContent ";
        selectMagicListQuery += ", magic_image as magicImage ";
        selectMagicListQuery += "FROM Magic ";
        selectMagicListQuery += "WHERE id = " + magicId;


        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectMagicListQuery, null);

        if (cursor.moveToFirst()) {
            do {
                magicData.setMagicName(cursor.getString(cursor.getColumnIndex("magicName")));
                magicData.setMagicContent(cursor.getString(cursor.getColumnIndex("magicContent")));
                magicData.setMagicImage(cursor.getString(cursor.getColumnIndex("magicImage")));
            } while (cursor.moveToNext());
        }

        return magicData;
    }

    // 4-5) 마술예장 효과 정보 가져오기 ( 대분류 0
    public List<MagicEffectForFirstModel> getMagicEffectListForFirst(int magicId){
        List<MagicEffectForFirstModel> magicEffectForFirstData = new ArrayList<MagicEffectForFirstModel>();

        // 검색 쿼리 저장
        String selectMagicLExpistQuery = "";

        selectMagicLExpistQuery += "SELECT ";
        selectMagicLExpistQuery += "id as id ";
        selectMagicLExpistQuery += ", magic_effect_name as magicEffectName ";
        selectMagicLExpistQuery += ", magic_effect_image as magicEffectImage ";
        selectMagicLExpistQuery += ", magic_effect_goal as magicEffectGoal ";
        selectMagicLExpistQuery += ", magic_effect_content as magicEffectContent ";
        selectMagicLExpistQuery += "FROM MagicEffect ";
        selectMagicLExpistQuery += "WHERE magic_id = " + magicId;
        selectMagicLExpistQuery += " GROUP BY magicEffectName";


        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectMagicLExpistQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MagicEffectForFirstModel tempMagicEffectForFirstModel = new MagicEffectForFirstModel();
                tempMagicEffectForFirstModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tempMagicEffectForFirstModel.setMagicEffectName(cursor.getString(cursor.getColumnIndex("magicEffectName")));
                tempMagicEffectForFirstModel.setMagicEffectImage(cursor.getString(cursor.getColumnIndex("magicEffectImage")));
                tempMagicEffectForFirstModel.setMagicEffectGoal(cursor.getString(cursor.getColumnIndex("magicEffectGoal")));
                tempMagicEffectForFirstModel.setMagicEffectContent(cursor.getString(cursor.getColumnIndex("magicEffectContent")));

                magicEffectForFirstData.add(tempMagicEffectForFirstModel);
            } while (cursor.moveToNext());
        }

        return magicEffectForFirstData;
    }

    // 4-6) 마술예장 효과 정보 가져오기 ( 소분류 )
    public List<MagicEffectForSecondModel> getMagicEffectListForSecond(int magicId, String magicName){
        List<MagicEffectForSecondModel> magicEffectForSecondData = new ArrayList<MagicEffectForSecondModel>();

        // 검색 쿼리 저장
        String selectMagicEffectSecondListQuery = "";

        selectMagicEffectSecondListQuery += "SELECT ";
        selectMagicEffectSecondListQuery += "id as id ";
        selectMagicEffectSecondListQuery += ", magic_effect_level as magicEffectLevel ";
        selectMagicEffectSecondListQuery += ", magic_effect_util as magicEffectUtil ";
        selectMagicEffectSecondListQuery += ", magic_effect_time as magicEffectTime ";
        selectMagicEffectSecondListQuery += "FROM MagicEffect ";
        selectMagicEffectSecondListQuery += "WHERE magic_id = " + magicId;
        selectMagicEffectSecondListQuery += " AND magic_effect_name = '" + magicName + "'";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectMagicEffectSecondListQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MagicEffectForSecondModel tempMagicEffectForSecondModel = new MagicEffectForSecondModel();
                tempMagicEffectForSecondModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tempMagicEffectForSecondModel.setMagicEffectLevel(cursor.getInt(cursor.getColumnIndex("magicEffectLevel")));
                tempMagicEffectForSecondModel.setMagicEffectUtil(cursor.getString(cursor.getColumnIndex("magicEffectUtil")));
                tempMagicEffectForSecondModel.setMagicEffectTime(cursor.getInt(cursor.getColumnIndex("magicEffectTime")));

                magicEffectForSecondData.add(tempMagicEffectForSecondModel);
            } while (cursor.moveToNext());
        }

        return magicEffectForSecondData;
    }

    // 4-7) 마술예장 경험치 정보 가져오기
    public List<MagicExpContact> getMagicExpList(int magicId){
        List<MagicExpContact> magicExpData = new ArrayList<MagicExpContact>();

        // 검색 쿼리 저장
        String selectMagicLExpistQuery = "";

        selectMagicLExpistQuery += "SELECT ";
        selectMagicLExpistQuery += "magic_exp_level as magicExpLevel ";
        selectMagicLExpistQuery += ", magic_exp_count as magicExpCount ";
        selectMagicLExpistQuery += ", magic_exp_total as magicExpTotal ";
        selectMagicLExpistQuery += "FROM MagicExp ";
        selectMagicLExpistQuery += "WHERE magic_id = " + magicId;


        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectMagicLExpistQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MagicExpContact tempMagicExpContact = new MagicExpContact();
                tempMagicExpContact.setMagicExpLevel(cursor.getInt(cursor.getColumnIndex("magicExpLevel")));
                tempMagicExpContact.setMagicExpCount(cursor.getInt(cursor.getColumnIndex("magicExpCount")));
                tempMagicExpContact.setMagicExpTotal(cursor.getInt(cursor.getColumnIndex("magicExpTotal")));

                magicExpData.add(tempMagicExpContact);
            } while (cursor.moveToNext());
        }

        return magicExpData;
    }
}




