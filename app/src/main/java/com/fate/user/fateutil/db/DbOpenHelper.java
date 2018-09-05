package com.fate.user.fateutil.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fate.user.fateutil.db.contract.Servant.ServantClassContract;
import com.fate.user.fateutil.db.contract.Servant.ServantContact;
import com.fate.user.fateutil.db.contract.Servant.ServantIconContract;
import com.fate.user.fateutil.db.contract.Skill.ServantJoinSkillContract;
import com.fate.user.fateutil.db.contract.Servant.ServantNameContract;
import com.fate.user.fateutil.db.contract.Skill.SkillContact;
import com.fate.user.fateutil.db.contract.WeaponContact;

import java.util.ArrayList;
import java.util.List;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "fatedb.db";
    private static final int DATABASE_VERSION = 1_1_10;
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
            db.execSQL(DataBase.SQL_CREATE_SERVANT_JOIN_LIST);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_ICON);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_NAME);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_CLASS);
            db.execSQL(DataBase.SQL_CREATE_EXP);
            db.execSQL(DataBase.SQL_CREATE_ACTIVE_SKILL);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_JOIN_SKILL);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantIconTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantNameTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantClassTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantJoinListTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ExpTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ActiveSkillTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantJoinSkillTable.TABLE_NAME);
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
    1_1) 서번트 조인 리스트 데이터 집어넣기
    1_2) 서번트 아이콘 데이터 집어 넣기
    1_3) 서번트 이름 데이터 집어 넣기
    1_4) 서번트 클래스 데이터 집어넣기

    2_1) 서번트 액티브 스킬 조인 리스트 데이터 집어 넣기
    2_2) 서번트 액티브 스킬 데이터 집어 넣기
     */

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
    public void addServantIcon(ServantIconContract contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantIconTable.ICON_ID, contact.getServantIconId()); // 서번트 아이콘 아이디
        cv.put(DataBase.ServantIconTable.ICON_NAME, contact.getServantIcon()); // 서번트 아이콘

        mDB.insert(DataBase.ServantIconTable.TABLE_NAME, null, cv);

    }

    // 1_3) 서번트 이름 데이터 집어 넣기
    public void addServantName(ServantNameContract contact) {

        mDB = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DataBase.ServantNameTable.NAME_ID, contact.getServantNameId()); // 서번트 이름 아이디
        cv.put(DataBase.ServantNameTable.NAME_VALUE, contact.getServantName()); // 서번트 이름

        mDB.insert(DataBase.ServantNameTable.TABLE_NAME, null, cv);

    }

    // 1_4) 서번트 클래스 데이터 집어넣기
    public void addServantClass(ServantClassContract contact) {

        mDB = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DataBase.ServantClassTable.CLASS_ID, contact.getServantClassId()); // 서번트 클래스 아이디
        cv.put(DataBase.ServantClassTable.CLASS_NAME, contact.getServantClass()); // 서번트 클래스

        mDB.insert(DataBase.ServantClassTable.TABLE_NAME, null, cv);

    }


    // 조인 부분
    public void addServantJoinSkillContact(ServantJoinSkillContract contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantJoinSkillTable.ID, contact.getId()); // JOIN 부분
        cv.put(DataBase.ServantJoinSkillTable.SERVANT_ID, contact.getServantId());
        cv.put(DataBase.ServantJoinSkillTable.SKILL_ID, contact.getSkillId());

        mDB.insert(DataBase.ServantJoinSkillTable.TABLE_NAME, null, cv);

    }

    // 스킬 테이블에 데이터 삽입
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

    // 보구 테이블에 데이터 삽입
    public void addWeaponContact(WeaponContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.WeaponTable.ID, contact.getWeaponId());
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

    // 1. 서번트 경험치 넣기
    public void addExpContact(ExpContact contact) {

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ExpTable.ID, contact.getId());
        cv.put(DataBase.ExpTable.SERVANT_LEVEL, contact.getServantLevel());
        cv.put(DataBase.ExpTable.SERVANT_EXP, contact.getServantExp());

        mDB.insert(DataBase.ExpTable.TABLE_NAME, null, cv);

    }

    // 1) 서번트 레벨에 해당하는 경험치값 가져오기 (단순히 값만 가져오는 것으로)
    public int getExpContact(String Level) {

        int exp = 0;

        mDB = mDBHelper.getReadableDatabase();

        // id 서번트 레벨, 서번트 경험치 컬럼
        String[] columns = {DataBase.ExpTable.ID, DataBase.ExpTable.SERVANT_LEVEL, DataBase.ExpTable.SERVANT_EXP};
        String[] parms = new String[1]; // 파라메터
        parms[0] = Level;


        // 2) 커서를 통해 데이터를 뽑아온다.
        Cursor cursor = mDB.query(DataBase.ExpTable.TABLE_NAME, columns, DataBase.ExpTable.SERVANT_LEVEL + "=?", parms, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext() == true) {
                // 레벨에 따른 경험치를 얻어온다.
                exp = cursor.getInt(2);
            }
        }
        return exp;

    }


    /*
    - 데이터 베이스에서 검색하기 -
    1. 서번트 검색
    1_1) 모든 서번트 리스트 가져오기
    1_2) 서번트 조인 리스트 에서 아이디에 맞는 서번트 리스트 가져오기

    2. 스킬 검색
    2_1) 서번트가 가지고 있는 스킬 아이콘, 이름 가지고 오기
    2_2) 서번트가 가지고 있는 스킬의 설명 리스트 가져오기
    2_3) 서번트가 가지고 있는 스킬의 수치 가져오기
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
        if(!"".equals(gradeData) && !"전체".equals(gradeData)){
            gradeData = gradeData.replace("성","");
            getAllServantQuery += " AND SJL.grade_value = '" + gradeData + "'";
        }

        // 클래스 데이터가 있을 경우
        if(!"".equals(classData) && !"전체".equals(classData)){
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
    public List<ServantContact> getSelectList(int servantId){

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

    // 2_1) 서번트가 가지고 있는 스킬 아이콘, 이름 가지고 오기
    public List<SkillContact> getServantHavingSkill(int servantId) {
        List<SkillContact> skillHaving = new ArrayList<SkillContact>();

        // 서번트가 가지고 있는 스킬 데이터를 가져온다.
        String selectHavingSkill = "SELECT " +
                "SAS.skill_name as skill_name," +
                "(SAS.skill_name || ' ' || SAS.skill_rank) as skill_full_name," +
                "SAS.skill_icon as skill_icon" +
                " from  " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME + " as SN" +
                " on SJS.servant_id = " + servantId + " AND SN.name_id=" + servantId +
                " inner join " + DataBase.ActiveSkillTable.TABLE_NAME + " as SAS" +
                " on SJS.skill_id = SAS.skill_id" +
                " where SAS.skill_classification =" + "'L'" +
                " group by skill_name" +
                " order by SAS.skill_id";

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

    // 2_2) 서번트가 가지고 있는 스킬의 설명 리스트 가져오기
    public List<SkillContact> getServantSkillExplain(int servantId, String skillName){
        List <SkillContact> skillExplain = new ArrayList<>();

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

    // 수치가 있는 서번트 스킬 효과 가져오기
    public List<SkillContact> getServantSkillEffect(int servantId, String skillName){
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
    public List<SkillContact> getServantHavingValue(int servantId, String skillName, String skillEffect) {
        List<SkillContact> skillNumber = new ArrayList<SkillContact>();

        // 서번트가 가지고 있는 수치를 가져온다.
        String selectHavingSkillValue = "select " +
                " SAS.skill_effect as skill_effect," +
                " SAS.skill_level as skill_level," +
                "(case when skill_percent = 1 then (SAS.skill_value || ' ' || '%') " +
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


    // 보구 리스트 가져오기
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
}




