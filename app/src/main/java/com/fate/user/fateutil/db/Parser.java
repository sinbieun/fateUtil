package com.fate.user.fateutil.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.LinearLayout;

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
        1. 서번트 리스트
        1_1) 서번트 리스트 조인 테이블에 데이터 삽입
        1_2) 서번트 아이콘 테이블에 데이터 삽입
        1_3) 서번트 이름 테이블에 데이터 삽입
        1_4) 서번트 클래스 테이블에 데이터 삽입
        1_5) 서번트 경험치를 집어 넣는다.

        2. 서번트 스테이터스
        2_1) 서번트 영기재림 이미지 테이블 데이터 삽입
        2_2) 서번트 조인 스킬 테이블에 데이터 삽입
        2_3) 서번트 액티브 스킬 테이블에 데이터 삽입
        2_4) 서번트 패시브 스킬 테이블에 데이터 삽입
        2_5) 서번트 조인 재료 테이블에 데이터 삽입
        2_6) 서번트 재료 테이블에 데이터 삽입

        3. 마술 예장 데이터 ( Magic )
        3_1) 마술 예장 데이터 삽입
        3_2) 마술 예장 효과 데이터 삽입
        3_3) 마술 예장 경험치 데이터 삽입

     */
    // 1_1) 서번트 리스트 조인 테이블에 데이터 삽입
    public void servantJointListParser() {

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
    // 1_5) 서번트 경험치를 집어 넣는다.
    public void servantExpParser() {
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
                mDbOpenHelper.addServantExpContact(new ServantExpContact(id, servantLevel, servantExp));

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

    // 2. 서번트 스테이터스
    // 2_1) 서번트 영기재림 이미지 테이블에 데이터 삽입
    public void servantAscensionImgParser(){
        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantAscensionImageTable.TABLE_NAME) == true) {
            mDbOpenHelper.close();
            return;
        }

        fileName = "databases/ServantAscensionImage.json"; // 파일 이름 저장 변수
        jsonString = loadServantFromAsset(fileName); // JSON에서 데이터를 뽑아서 집어 넣는다.

        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            jarray = new JSONArray(jsonString);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                int ascensionId = jObject.getInt("ascension_id");
                int servantId = jObject.getInt("servant_id");
                int ascensionLevel = jObject.getInt("ascension_level");
                String ascensionClassification =  jObject.getString("ascension_classification");
                String ascensionImgName = jObject.getString("ascension_img_name");

                // 데이터 삽입
                mDbOpenHelper.addServantAscensionImage(new ServantAscensionContact(ascensionId, servantId, ascensionClassification, ascensionLevel, ascensionImgName));
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
    // 2_2) 서번트 조인 스킬 테이블에 데이터 삽입
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
                mDbOpenHelper.addServantJoinSkill(new ServantJoinSkillContact(id, servantId, skillClassification, skillId));
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
    // 2_3) 서번트 액티브 스킬 테이블에 데이터 삽입
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
    // 2_4) 서번트 패시브 스킬 테이블에 데이터 삽입
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
    // 2_5) 서번트 조인 재료 테이블에 데이터 삽입
    public void servantJoinMaterialParser(){
        // 0. 테이블에 값이 있으면 저장하지 않는다.

        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantJoinMaterialTable.TABLE_NAME)) {
            mDbOpenHelper.close();
            return;
        }
        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/ServantJoinMaterial.json";
        String jsonString = loadServantFromAsset(fileName);
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2. 강화 아이디, 서번트 아이디, 강화 분류, 강화 레벨, 재료 아이디, 재료 갯수
                int upgradeId = jObject.getInt("upgrade_id");
                int servantId = jObject.getInt("servant_id");
                String upgradeClassification = jObject.getString("upgrade_classification");
                int upgradeLevel = jObject.getInt("upgrade_level");
                int materialId = jObject.getInt("material_id");
                int materialValue = jObject.getInt("material_value");

                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addServantJoinMaterial(new MaterialContact(upgradeId, servantId, upgradeClassification, upgradeLevel,materialId, materialValue));
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
    // 2_6) 서번트 재료 테이블에 데이터 삽입
    public void servantMaterialParser(){
        // 0. 테이블에 값이 있으면 저장하지 않는다.

        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.ServantMaterialTable.TABLE_NAME)) {
            mDbOpenHelper.close();
            return;
        }
        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/ServantMaterial.json";
        String jsonString = loadServantFromAsset(fileName);
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2.재료 아이디, 재료 이름
                int materialId = jObject.getInt("material_id");
                String materialName = jObject.getString("material_name");
                String materialKorName = jObject.getString("material_kor_name");

                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addServantMaterial(new MaterialContact(materialId, materialName, materialKorName));
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

    // 3_1) 마술예장 데이터 삽입
    public void magicParser(){
        // 0. 테이블에 값이 있으면 저장하지 않는다.

        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.MagicTable.TABLE_NAME)) {
            mDbOpenHelper.close();
            return;
        }
        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/Magic.json";
        String jsonString = loadServantFromAsset(fileName);
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2. 데이터 세팅
                MagicContact magicContact = new MagicContact();
                magicContact.setId(jObject.getInt("id"));
                magicContact.setMagicName(jObject.getString("magic_name"));
                magicContact.setMagicContent(jObject.getString("magic_content"));
                magicContact.setMagicImage(jObject.getString("magic_image"));
                magicContact.setMagicDeleteYn(jObject.getString("magic_delete_yn"));


                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addMagic(magicContact);
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

    // 3_2) 마술예장 효과 데이터 삽입
    public void magicEffectParser(){
        // 0. 테이블에 값이 있으면 저장하지 않는다.

        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.MagicEffectTable.TABLE_NAME)) {
            mDbOpenHelper.close();
            return;
        }
        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/MagicEffect.json";
        String jsonString = loadServantFromAsset(fileName);
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2. 데이터 세팅
                MagicEffectContact magicEffectContact = new MagicEffectContact();

                magicEffectContact.setId(jObject.getInt("id"));
                magicEffectContact.setMagicId(jObject.getInt("magic_id"));
                magicEffectContact.setMagicEffectName(jObject.getString("magic_effect_name"));
                magicEffectContact.setMagicEffectGoal(jObject.getString("magic_effect_goal"));
                magicEffectContact.setMagicEffectContent(jObject.getString("magic_effect_content"));
                magicEffectContact.setMagicEffectTime(jObject.getString("magic_effect_time"));
                magicEffectContact.setMagicEffectLevel(jObject.getString("magic_effect_level"));
                magicEffectContact.setMagicEffectImage(jObject.getString("magic_effect_image"));
                magicEffectContact.setMagicEffectUtil(jObject.getString("magic_effect_util"));
                magicEffectContact.setMagicEffectDeleteYn(jObject.getString("magic_effect_delete_yn"));

                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addMagicEffect(magicEffectContact);
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

    // 3_3) 마술예장 경험치 데이터 삽입
    public void magicExpParser(){
        // 0. 테이블에 값이 있으면 저장하지 않는다.

        mDbOpenHelper.open();
        if (mDbOpenHelper.checkData(DataBase.MagicExpTable.TABLE_NAME)) {
            mDbOpenHelper.close();
            return;
        }
        // 1. Servant.json 파일을 String에 저장
        String fileName = "databases/MagicExp.json";
        String jsonString = loadServantFromAsset(fileName);
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try {
            JSONArray jarray = new JSONArray(jsonString);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);

                // 2. 데이터 세팅
                MagicExpContact magicExpContact = new MagicExpContact();

                magicExpContact.setId(jObject.getInt("id"));
                magicExpContact.setMagicId(jObject.getInt("magic_id"));
                magicExpContact.setMagicExpLevel(jObject.getInt("magic_exp_level"));
                magicExpContact.setMagicExpCount(jObject.getInt("magic_exp_count"));
                magicExpContact.setMagicExpTotal(jObject.getInt("magic_exp_total"));
                magicExpContact.setMagicExpDeleteYn(jObject.getString("magic_delete_yn"));

                // 3. 데이터 삽입을 하여준다.
                mDbOpenHelper.addMagicExp(magicExpContact);
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
