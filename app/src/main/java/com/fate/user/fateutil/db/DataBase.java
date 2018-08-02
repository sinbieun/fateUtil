package com.fate.user.fateutil.db;

import android.provider.BaseColumns;

public class DataBase {

    // 계약 클래스
    // DB 내용
    // 경험치 테이블

    private DataBase(){};

    // 서번트 테이블 변수 생성
    public static final class ServantTable implements BaseColumns {
        public static final String TABLE_NAME = "servant";
        public static final String ID = "id";
        public static final String SERVANT_ICON = "servantIcon";
        public static final String SERVANT_NAME = "servantName";
        public static final String SERVANT_CLASS = "servantClass";
        public static final String SERVANT_GRADE = "servantGrade";
    }

    // 서번트 테이블 생성
    public static final String SQL_CREATE_SERVANT =
            "create table " +
                    ServantTable.TABLE_NAME + " (" +
                    ServantTable.ID + " integer primary key not null, " +
                    ServantTable.SERVANT_ICON + " text not null ," +
                    ServantTable.SERVANT_NAME  + " text not null , " +
                    ServantTable.SERVANT_CLASS + " text not null , " +
                    ServantTable.SERVANT_GRADE + " integer not null" + ");";

    // 서번트 테이블 삭제
    // 서번트 테이블 검색
    public static final String SQL_SELECT_SERVANT = "SELECT Servant FROM WHERE = " + ServantTable.SERVANT_NAME;

}
