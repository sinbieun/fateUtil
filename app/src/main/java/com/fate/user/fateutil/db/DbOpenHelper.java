package com.fate.user.fateutil.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fate.user.fateutil.layout.SearchLayout;

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
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DataBase.SQL_CREATE_SERVANT);

        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.ServantTable.TABLE_NAME);
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

        mDB = mDBHelper.getWritableDatabase();

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


}




