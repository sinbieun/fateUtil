package com.fate.user.fateutil.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.LinearLayout;

import com.fate.user.fateutil.db.contact.Exp.ExpContact;
import com.fate.user.fateutil.db.contact.Servant.ServantClassContact;
import com.fate.user.fateutil.db.contact.Servant.ServantContact;
import com.fate.user.fateutil.db.contact.Servant.ServantIconContact;
import com.fate.user.fateutil.db.contact.Skill.ServantJoinSkillContact;
import com.fate.user.fateutil.db.contact.Servant.ServantNameContact;
import com.fate.user.fateutil.db.contact.Skill.SkillContact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Parser extends LinearLayout {

    private AssetManager assetManager = getResources().getAssets();
    private DbOpenHelper mDbOpenHelper;
    private String jsonString;
    private String fileName;
    private JSONArray jarray;

    // 생성자
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

    /*
        JSON 파일 파싱해서 DB에 집어 넣기
        1. 서번트
        1_1) 서번트 리스트 조인 테이블에 데이터 삽입
        1_2) 서번트 아이콘 테이블에 데이터 삽입
        1_3) 서번트 이름 테이블에 데이터 삽입
        1_4) 서번트 클래스 테이블에 데이터 삽입

        2. 서번트 스킬
        2_1) 서번트 조인 스킬 테이블에 데이터 삽입
        2_2) 서번트 액티브 스킬 테이블에 데이터 삽입

        3. 경험치
        3_1) 서번트 경험치를 집어 넣는다.

     */
    // 1_1) 서번트 리스트 조인 테이블에 데이터 삽입
    public void servanJointListParser() {

        mDbOpenHelper.open();

        // 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다. (수정 예정)
        if (mDbOpenHelper.checkData(DataBase.ServantJoinListTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        fileName = "databases/ServantJoinList.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString); // JSON 데이터를 JSON 배열에 집어 넣는다.

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 서번트 아이디 , 서번트 아이콘 아이디, 서번트 이름 아이디, 서번트 클래스 아이디, 서번트 등급을 JSON 배열에서 가져온다.
                int servantId = jObject.getInt("servant_id");
                int servantIconId = jObject.getInt("icon_id");
                int servantNameId = jObject.getInt("name_id");
                int servantClassId = jObject.getInt("class_id");
                int servantGrade = jObject.getInt("grade_value");

                // 데이터 삽입
                mDbOpenHelper.addServantJoinList(new ServantContact(servantId, servantIconId, servantNameId, servantClassId, servantGrade));
            }
            mDbOpenHelper.mDB.setTransactionSuccessful(); // 데이터 삽입이 완전히 종료되면 실행된다.
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
            mDbOpenHelper.close(); // DB 닫기
        }

    }

    // 1_2) 서번트 아이콘 테이블에 데이터 삽입
    public void servantIconParser() {
        // 0. 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다.

        mDbOpenHelper.open();

        // 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다. (수정 예정)
        if (mDbOpenHelper.checkData(DataBase.ServantIconTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        fileName = "databases/ServantIcon.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString); // JSON 데이터를 JSON 배열에 집어 넣는다.

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 클래스 아이디 , 클래스 이름을 SON 배열에서 가져온다.
                int id = jObject.getInt("icon_id");
                String servantIcon = jObject.getString("icon_name");

                // 데이터를 삽입한다.
                mDbOpenHelper.addServantIcon(new ServantIconContact(id, servantIcon));
            }
            mDbOpenHelper.mDB.setTransactionSuccessful(); // 데이터 삽입이 완전히 종료되면 실행된다.
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            mDbOpenHelper.mDB.endTransaction(); // 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.close(); // DB 닫기
        }
    }

    // 1_3) 서번트 이름 테이블에 데이터 삽입
    public void servantNameParser() {
        // 0. 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다.

        mDbOpenHelper.open();
        // 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다. (수정 예정)
        if (mDbOpenHelper.checkData(DataBase.ServantNameTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        fileName = "databases/ServantName.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString); // JSON 데이터를 JSON 배열에 집어 넣는다.

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 클래스 아이디 , 클래스 이름을 SON 배열에서 가져온다.
                int id = jObject.getInt("name_id");
                String servantName = jObject.getString("name_value");

                // 데이터를 삽입한다.
                mDbOpenHelper.addServantName(new ServantNameContact(id, servantName));
            }
            mDbOpenHelper.mDB.setTransactionSuccessful(); // 데이터 삽입이 완전히 종료되면 실행된다.
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            mDbOpenHelper.mDB.endTransaction(); // 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.close(); // DB 닫기
        }
    }

    // 1_4) 서번트 클래스 테이블에 데이터 삽입
    public void servantClassParser() {
        // 0. 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다.

        mDbOpenHelper.open();

        // 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다. (수정 예정)
        if (mDbOpenHelper.checkData(DataBase.ServantClassTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        fileName = "databases/ServantClass.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString); // JSON 데이터를 JSON 배열에 집어 넣는다.

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 클래스 아이디 , 클래스 이름을 SON 배열에서 가져온다.
                int id = jObject.getInt("class_id");
                String servantClass = jObject.getString("class_name");

                // 데이터를 삽입한다.
                mDbOpenHelper.addServantClass(new ServantClassContact(id, servantClass));
            }
            mDbOpenHelper.mDB.setTransactionSuccessful(); // 데이터 삽입이 완전히 종료되면 실행된다.
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            mDbOpenHelper.mDB.endTransaction(); // 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.close(); // DB 닫기
        }
    }

    // 2_1) 서번트 조인 스킬 테이블에 데이터 삽입
    public void servantJoinSkillParser() {
        // 0. 테이블에 값이 있다면 checkData에서 true를 반환하고 데이터 값을 넣지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantJoinSkillTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        fileName = "databases/ServantJoinSkill.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                int id = jObject.getInt("id");
                int servantId = jObject.getInt("servant_id");
                String skillClassification = jObject.getString("skill_classification");
                int skillId = jObject.getInt("skill_id");


                // 데이터 삽입
                mDbOpenHelper.addServantJoinSkillContact(new ServantJoinSkillContact(id, servantId, skillClassification, skillId));
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

    // 2_2) 서번트 액티브 스킬 테이블에 데이터 삽입
    public void activeSkillParser() {
        // 0. 테이블에 값이 있으면 db에 저장하지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ActiveSkillTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }
        fileName = "databases/ServantActiveSkill.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2. 아이디, 서번트 아이콘 서번트 이름, 서번트 클래스, 서번트 등급에 따라 저장
                int skillId = jObject.getInt("skill_id");
                String skillIcon = jObject.getString("skill_icon");
                String skillName = jObject.getString("skill_name");
                String skillRank = jObject.getString("skill_rank");
                String skillClassification = jObject.getString("skill_classification");
                int skillLevel = jObject.getInt("skill_level");
                String skillTarget = jObject.getString("skill_target");
                String skillRange = jObject.getString("skill_range");
                String skillExplain = jObject.getString("skill_explain");
                String skillEffect = jObject.getString("skill_effect");
                double skillValue = jObject.getDouble("skill_value");
                String skillMerit = jObject.getString("skill_merit");
                int skillDuration = jObject.getInt("skill_duration");
                int skillCoolDown = jObject.getInt("skill_coolDown");
                int skillPercent = jObject.getInt("skill_percent");
                int skillEnhance = jObject.getInt("skill_enhance");

                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addActiveSkillList(new SkillContact(
                        skillId, skillIcon, skillName, skillRank, skillClassification, skillLevel, skillTarget, skillRange,
                        skillExplain, skillEffect, skillValue, skillMerit, skillDuration, skillCoolDown, skillPercent,
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

    // 2_3) 서번트 패시브 스킬 테이블에 데이터 삽입
    public void passiveSkillParser() {
        // 0. 테이블에 값이 있으면 db에 저장하지 않는다.
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.PassiveSkillTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }
        fileName = "databases/ServantPassiveSkill.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2. 아이디, 서번트 아이콘 서번트 이름, 서번트 클래스, 서번트 등급에 따라 저장
                int skillId = jObject.getInt("skill_id");
                String skillIcon = jObject.getString("skill_icon");
                String skillName = jObject.getString("skill_name");
                String skillRank = jObject.getString("skill_rank");
                String skillExplain = jObject.getString("skill_explain");


                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addPassiveSkillList(new SkillContact(skillId, skillIcon, skillName, skillRank, skillExplain));

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

    // 3_1) 서번트 경험치를 집어 넣는다.
    public void expParser() {
        // 0. 테이블에 값이 있으면 저장하지 않는다.

        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantExpTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }
        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/ServantExp.json";
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

}
