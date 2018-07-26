package com.fate.user.fateutil.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbOpenHelper{

    private static final String DATABASE_NAME = "fatedb.db";
    private static final int DATABASE_VERSION = 1;
    private DatabaseHelper mDBHelper;
    private Context mContext;
    public static SQLiteDatabase mDB;

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
            db.execSQL("DROP TABLE IF EXISTS " + DataBase.CreateServant.TABLE_NAME);
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

    public void insertServant(int id, String servantName, String servantClass, int servantGrade){

        try {
                ContentValues cv = new ContentValues();
                cv.put(DataBase.CreateServant.ID, id);
                cv.put(DataBase.CreateServant.SERVANT_NAME, servantName);
                cv.put(DataBase.CreateServant.SERVANT_CLASS, servantClass);
                cv.put(DataBase.CreateServant.SERVANT_GRADE, servantGrade);
                mDB.insert(DataBase.CreateServant.TABLE_NAME,null,cv);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}




