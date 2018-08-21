package com.fate.user.fateutil.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbOpenHelper{

    private static final String DATABASE_NAME = "fatedb.db";
    private static final int DATABASE_VERSION = 1;
    private DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDB;
    private Context mContext;

    private class DatabaseHelper extends SQLiteOpenHelper{
        // 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        // 최초 DB를 만들 때 한번만 호출
        // 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DataBase.SQL_CREATE_SERVANT);
            db.execSQL(DataBase.SQL_CREATE_EXP);
            db.execSQL(DataBase.SQL_CREATE_SKILL);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_NAME);
            db.execSQL(DataBase.SQL_CREATE_SERVANT_JOIN_SKILL);

        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ExpTable.TABLE_NAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mContext = context;
    }

    public DbOpenHelper open() throws SQLException{
        // 읽기
        mDBHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }

    public boolean checkData(String tableName){

        mDB = mDBHelper.getReadableDatabase();

        String selectServantQuery = "SELECT id FROM " + tableName;

        Cursor cursor = mDB.rawQuery(selectServantQuery, null);

        if(cursor.getCount() >0){
            return true;
        }

        return false;

    }

    // DB에 새로운 ServantContact 추가
    public void addServantContact(ServantContact contact){

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantTable.ID, contact.getId());
        cv.put(DataBase.ServantTable.SERVANT_ICON, contact.getServantIcon());
        cv.put(DataBase.ServantTable.SERVANT_NAME, contact.getServantName());
        cv.put(DataBase.ServantTable.SERVANT_CLASS, contact.getServantClass());
        cv.put(DataBase.ServantTable.SERVANT_GRADE, contact.getServantGrade());

        mDB.insert(DataBase.ServantTable.TABLE_NAME,null,cv);

    }

    public void addServantNameContact(ServantContact contact){

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantNameTable.ID, contact.getId());
        cv.put(DataBase.ServantNameTable.SERVANT_ICON, contact.getServantIcon());
        cv.put(DataBase.ServantNameTable.SERVANT_NAME, contact.getServantName());

        mDB.insert(DataBase.ServantNameTable.TABLE_NAME,null,cv);

    }

    public void addServantJoinSkillContact(ServantJoinSkillContract contact){

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantJoinSkillTable.ID, contact.getId());
        cv.put(DataBase.ServantJoinSkillTable.SERVANT_ID, contact.getServantId());
        cv.put(DataBase.ServantJoinSkillTable.SKILL_ID, contact.getSkillId());

        mDB.insert(DataBase.ServantJoinSkillTable.TABLE_NAME,null,cv);

    }

    // 스킬 테이블에 데이터 삽입
    public void addSkillContact(SkillContact contact){

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.SkillTable.ID, contact.getId());
        cv.put(DataBase.SkillTable.SKILL_ICON, contact.getSkillIcon());
        cv.put(DataBase.SkillTable.SKILL_NAME, contact.getSkillName());
        cv.put(DataBase.SkillTable.SKILL_RANK, contact.getSkillRank());
        cv.put(DataBase.SkillTable.SKILL_CLASSIFICATION, contact.getSkillClassification());
        cv.put(DataBase.SkillTable.SKILL_LEVEL, contact.getSkillLevel());
        cv.put(DataBase.SkillTable.SKILL_TARGET, contact.getSkillTarget());
        cv.put(DataBase.SkillTable.SKILL_RANGE, contact.getSkillRange());
        cv.put(DataBase.SkillTable.SKILL_EFFECT, contact.getSkillEffect());
        cv.put(DataBase.SkillTable.SKILL_VALUE, contact.getSkillValue());
        cv.put(DataBase.SkillTable.SKILL_MERIT, contact.getSkillMerit());
        cv.put(DataBase.SkillTable.SKILL_DURATION, contact.getSkillDuration());
        cv.put(DataBase.SkillTable.SKILL_COOLDOWN, contact.getSkillCoolDown());
        cv.put(DataBase.SkillTable.SKILL_PERCENT, contact.getSkillPercent());
        cv.put(DataBase.SkillTable.SKILL_ENHANCE, contact.getSkillEnhance());

        mDB.insert(DataBase.SkillTable.TABLE_NAME,null,cv);

    }

    // 보구 테이블에 데이터 삽입
    public void addWeaponContact(WeaponContact contact){

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

        mDB.insert(DataBase.WeaponTable.TABLE_NAME,null,cv);

    }


    // 아이디에 해당하는 ServantContact 가져오기
    public ServantContact getServantContact(int id){
        mDB = mDBHelper.getReadableDatabase();

        Cursor cursor = mDB.query(DataBase.ServantTable.TABLE_NAME,
                new String[] {DataBase.ServantTable.ID,
                        DataBase.ServantTable.SERVANT_ICON,
                        DataBase.ServantTable.SERVANT_NAME,
                        DataBase.ServantTable.SERVANT_CLASS,
                        DataBase.ServantTable.SERVANT_GRADE},
                DataBase.ServantTable.ID + "=?",
                new String [] {String.valueOf(id)}, null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        // id, 서번트 아이콘 경로, 서번트 이름, 서번트 클래스, 서번트 등급
        ServantContact contact = new ServantContact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)));

        return contact;
    }

    // 모든 서번트 리스트 가져오기
    public List<ServantContact> getAllServantContacts(){

        List<ServantContact> contactServantList = new ArrayList<ServantContact>();

        // 검색 쿼리 저장
        String selectServantQuery = "SELECT * FROM " + DataBase.ServantTable.TABLE_NAME;

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectServantQuery, null);

        if(cursor.moveToFirst()){

            do{
                ServantContact contact = new ServantContact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setServantIcon(cursor.getString(1));
                contact.setServantName(cursor.getString(2));
                contact.setServantClass(cursor.getString(3));
                contact.setServantGrade(Integer.parseInt(cursor.getString(4)));

                contactServantList.add(contact);

            } while (cursor.moveToNext());
        }
        return contactServantList;
    }

    // ServantContact 업데이트 (실행 확인 x)
    public int updateServantContact(ServantContact contact){
        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ServantTable.ID, contact.getId());
        cv.put(DataBase.ServantTable.SERVANT_ICON, contact.getServantIcon());
        cv.put(DataBase.ServantTable.SERVANT_NAME, contact.getServantName());
        cv.put(DataBase.ServantTable.SERVANT_CLASS, contact.getServantClass());
        cv.put(DataBase.ServantTable.SERVANT_GRADE, contact.getServantGrade());

        return mDB.update(DataBase.ServantTable.TABLE_NAME, cv, DataBase.ServantTable.ID + "= ? ",
                new String [] {String.valueOf(contact.getId())});
    }

    // ServantContact 삭제하기 (실행 확인 x)
    public void deleteServantContact(ServantContact contact){
        mDB = mDBHelper.getWritableDatabase();
        mDB.delete(DataBase.ServantTable.TABLE_NAME, DataBase.ServantTable.ID + "= ? ", new String[]{String.valueOf(contact.getId())});
        mDB.close();
    }


    // 1. 서번트 경험치 넣기
    public void addExpContact(ExpContact contact){

        mDB = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataBase.ExpTable.ID, contact.getId());
        cv.put(DataBase.ExpTable.SERVANT_LEVEL, contact.getServantLevel());
        cv.put(DataBase.ExpTable.SERVANT_EXP, contact.getServantExp());

        mDB.insert(DataBase.ExpTable.TABLE_NAME,null,cv);

    }

    // 1) 서번트 레벨에 해당하는 경험치값 가져오기 (단순히 값만 가져오는 것으로)
    public int getExpContact(String Level){

        int exp = 0;

        mDB = mDBHelper.getReadableDatabase();

        // id 서번트 레벨, 서번트 경험치 컬럼
        String [] columns = {DataBase.ExpTable.ID, DataBase.ExpTable.SERVANT_LEVEL, DataBase.ExpTable.SERVANT_EXP};
        String [] parms = new String[1]; // 파라메터
        parms[0] = Level;



        // 2) 커서를 통해 데이터를 뽑아온다.
        Cursor cursor = mDB.query(DataBase.ExpTable.TABLE_NAME, columns,  DataBase.ExpTable.SERVANT_LEVEL + "=?",parms,null,null,null);

        if(cursor != null){
            while(cursor.moveToNext() == true){
                // 레벨에 따른 경험치를 얻어온다.
                exp = cursor.getInt(2);
            }
        }
        return exp;

    }

    public List<SkillContact> getServantHavingSkill(int servantId){
        List<SkillContact> skillHaving = new ArrayList<SkillContact>();

        // 서번트가 가지고 있는 스킬 데이터를 가져온다.
        String selectHavingSkill = "SELECT " +
                "(SS.skill_name || ' ' SS.skill_rank) as skill_name " +
                "SS.skill_icon as skill_icon " +
                " from  " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME +" as SN " +
                " on SJS.servant_id = " + servantId + " AND SN.id = " + servantId +
                " inner join " + DataBase.SkillTable.TABLE_NAME +" as SS " +
                " on SJS.skill_id = SS.id" +
                " group by SS.skill_name";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectHavingSkill, null);

        if(cursor.moveToFirst()){
            do {
                SkillContact contact = new SkillContact();
                contact.setSkillName(cursor.getString(cursor.getColumnIndex("skill_name")));
                contact.setSkillIcon(cursor.getString(cursor.getColumnIndex("skill_icon")));
            } while(cursor.moveToNext());
        }

        return skillHaving;
    }

    // 스킬 리스트 가져오기
    public List<SkillContact> getServantJoinSkill(int servantId){

        List<SkillContact> contactSkillList = new ArrayList<SkillContact>();
        // 검색 쿼리 저장
        String selectServantJoinSkill = "SELECT " +
                "SS.skill_name as skill_name, " +
                "SS.skill_icon as skill_icon, " +
                "SS.skill_rank as skill_rank, " +
                "SS.skill_classification as skill_classification, " +
                "SS.skill_level as skill_level, " +
                "SS.skill_target as skill_target, " +
                "SS.skill_range as skill_range, " +
                "SS.skill_effect as skill_effect, " +
                "SS.skill_value as skill_value, " +
                "SS.skill_merit as skill_merit, " +
                "SS.skill_duration as skill_duration, " +
                "SS.skill_coolDown as skill_cd, " +
                "SS.skill_percent as skill_percent, " +
                "SS.skill_enhance as skill_enhance " +
                " from " + DataBase.ServantJoinSkillTable.TABLE_NAME + " as SJS " +
                " inner join " + DataBase.ServantNameTable.TABLE_NAME + " as SN " +
                " on SJS.servant_id = " + servantId + " AND SN.id = " + servantId +
                " inner join " + DataBase.SkillTable.TABLE_NAME +" as SS " +
                " on SJS.skill_id = SS.id";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectServantJoinSkill, null);

        if(cursor.moveToFirst()){
            do{
                SkillContact contact = new SkillContact();
                contact.setSkillIcon(cursor.getString(cursor.getColumnIndex("skill_icon")));
                contact.setSkillName(cursor.getString(cursor.getColumnIndex("skill_name")));
                contact.setSkillRank(cursor.getString(cursor.getColumnIndex("skill_rank")));
                contact.setSkillClassification(cursor.getString(cursor.getColumnIndex("skill_classification")));
                contact.setSkillLevel(cursor.getInt(cursor.getColumnIndex("skill_level")));
                contact.setSkillTarget(cursor.getString(cursor.getColumnIndex("skill_target")));
                contact.setSkillRange(cursor.getString(cursor.getColumnIndex("skill_range")));
                contact.setSkillEffect(cursor.getString(cursor.getColumnIndex("skill_effect")));
                contact.setSkillValue(cursor.getDouble(cursor.getColumnIndex("skill_value")));
                contact.setSkillMerit(cursor.getString(cursor.getColumnIndex("skill_merit")));
                contact.setSkillDuration(cursor.getInt(cursor.getColumnIndex("skill_duration")));
                contact.setSkillCoolDown(cursor.getInt(cursor.getColumnIndex("skill_cd")));
                contact.setSkillPercent(cursor.getInt(cursor.getColumnIndex("skill_percent")));
                contact.setSkillEnhance(cursor.getInt(cursor.getColumnIndex("skill_enhance")));

                contactSkillList.add(contact);

            } while (cursor.moveToNext());
        }
        return contactSkillList;
    }

    // 보구 리스트 가져오기
    public List<WeaponContact> getServantJoinWeapon(int servantId){

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
                " inner join " + DataBase.SkillTable.TABLE_NAME +" as SS " +
                " on SJS.weapon_id = SS.id";

        mDB = mDBHelper.getReadableDatabase();

        // 커서에 검색 쿼리 삽입
        Cursor cursor = mDB.rawQuery(selectServantJoinWeapon, null);

        if(cursor.moveToFirst()){
            do{
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




