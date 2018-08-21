package com.fate.user.fateutil.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Parser extends LinearLayout {

    private AssetManager assetManager = getResources().getAssets();
    private DbOpenHelper mDbOpenHelper;

    public Parser(Context context) {
        super(context);
        mDbOpenHelper = new DbOpenHelper(context);
    }

    // Asset에 저장된 Servant.json을 읽어오는 함수
    public String loadServantFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = assetManager.open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public void servantParser() {

        // 0. 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }
        // 1. 파일 이름 저장 변수
        String fileName = "databases/Servant.json";
        // 2. Servant.json 파일을 String에 저장
        String jsonString = loadServantFromAsset(fileName);

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 3. 아이디, 서번트 아이콘 서번트 이름, 서번트 클래스, 서번트 등급에 따라 저장
                int id = jObject.getInt("id");
                String servantIcon = jObject.getString("servantIcon");
                String servantName = jObject.getString("servantName");
                String servantClass = jObject.getString("servantClass");
                int servantGrade = jObject.getInt("servantGrade");

                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addServantContact(new ServantContact(id, servantIcon, servantName, servantClass, servantGrade));

            }
            // 5. 완전히 종료되면 실행
            mDbOpenHelper.mDB.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // 6. 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
            // 7. DB 닫기
            mDbOpenHelper.close();
        }

    }

    public void expParser() {
        // 0. 테이블에 값이 있으면 저장하지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ExpTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/Exp.json";
        String jsonString = loadServantFromAsset(fileName);
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);
                // 2. 아이디, 서번트 아이콘 서번트 이름, 서번트 클래스, 서번트 등급에 따라 저장
                int id = jObject.getInt("id");
                int servantLevel = jObject.getInt("servant_level");
                int servantExp = jObject.getInt("servant_exp");
                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addExpContact(new ExpContact(id, servantLevel, servantExp));

            }
            // 4. 완전히 종료되면 실행
            mDbOpenHelper.mDB.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // 5. 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
            // 6. DB 닫기
            mDbOpenHelper.close();
        }

    }

    public void servantNameParser() {
        // 0. 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantNameTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }
        // 1. 파일 이름 저장 변수
        String fileName = "databases/ServantName.json";
        // 2. Servant.json 파일을 String에 저장
        String jsonString = loadServantFromAsset(fileName);

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);
                // 3. 아이디, 서번트 아이콘 서번트 이름, 서번트 클래스, 서번트 등급에 따라 저장
                int id = jObject.getInt("id");
                String servantIcon = jObject.getString("icon");
                String servantName = jObject.getString("name");
                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addServantNameContact(new ServantContact(id, servantIcon, servantName));
            }
            // 5. 완전히 종료되면 실행
            mDbOpenHelper.mDB.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // 6. 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
            // 7. DB 닫기
            mDbOpenHelper.close();
        }

    }

    public void servantJoinSkillParser() {
        // 0. 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantJoinSkillTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }
        // 1. 파일 이름 저장 변수
        String fileName = "databases/ServantJoinSkill.json";
        // 2. Servant.json 파일을 String에 저장
        String jsonString = loadServantFromAsset(fileName);

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);
                // 3. 아이디, 서번트 아이콘 서번트 이름, 서번트 클래스, 서번트 등급에 따라 저장
                int id = jObject.getInt("id");
                int servantId = jObject.getInt("servant_id");
                int skillId = jObject.getInt("skill_id");
                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addServantJoinSkillContact(new ServantJoinSkillContract(id, servantId, skillId));
            }
            // 5. 완전히 종료되면 실행
            mDbOpenHelper.mDB.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // 6. 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
            // 7. DB 닫기
            mDbOpenHelper.close();
        }

    }


    // 스킬 파싱 테이블
    public void skillParser() {
        // 0. 테이블에 값이 있으면 db에 저장하지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.SkillTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/ServantSkill.json";
        String jsonString = loadServantFromAsset(fileName);
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2. 아이디, 서번트 아이콘 서번트 이름, 서번트 클래스, 서번트 등급에 따라 저장
                int id = jObject.getInt("id");
                String skillIcon = jObject.getString("skill_icon");
                String skillName = jObject.getString("skill_name");
                String skillRank = jObject.getString("skill_rank");
                String skillClassification = jObject.getString("skill_classification");
                int skillLevel = jObject.getInt("skill_level");
                String skillTarget = jObject.getString("skill_target");
                String skillEffect = jObject.getString("skill_effect");
                double skillValue = jObject.getDouble("skill_value");
                String skillMerit = jObject.getString("skill_merit");
                int skillDuration = jObject.getInt("skill_duration");
                int skillCoolDown = jObject.getInt("skill_coolDown");
                int skillPercent = jObject.getInt("skill_percent");
                int skillEnhance = jObject.getInt("skill_enhance");

                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addSkillContact(new SkillContact(
                        id, skillIcon, skillName, skillRank, skillClassification, skillLevel, skillTarget,
                        skillEffect, skillValue, skillMerit, skillDuration, skillCoolDown, skillPercent,
                        skillEnhance));

            }
            // 4. 완전히 종료되면 실행
            mDbOpenHelper.mDB.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // 5. 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
            // 6. DB 닫기
            mDbOpenHelper.close();
        }
    }


}
