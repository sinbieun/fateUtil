package com.fate.user.fateutil.layout;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.db.DataBase;
import com.fate.user.fateutil.db.DbOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class SearchLayout extends LinearLayout {

    public String strSearch;
    public String strSearchQuery;
    Button btnSearch = null;
    EditText editSearch = null;
    AssetManager assetManager = getResources().getAssets();
    InputStream is = null;
    String json = null;
    private DbOpenHelper mDbOpenHelper;
    public SQLiteDatabase mDB;
    Cursor cursor;




    public SearchLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context.getSystemService(infService);

        final LinearLayout currentLayout = (LinearLayout) li.inflate(R.layout.search_main, null);
        addView(currentLayout);

        btnSearch = currentLayout.findViewById(R.id.btn_Search);
        editSearch = currentLayout.findViewById(R.id.edit_Search);

        btnSearch.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {

                // Servant.json 파일을 String에 저장
                String jsonString = loadServantFromAsset();

                // DB 열기
                mDbOpenHelper = new DbOpenHelper(currentLayout.getContext());
                mDbOpenHelper.open();
                // 서번트 테이블 삽입
                servantParser(jsonString);
                // DB 닫기
                mDbOpenHelper.close();

            }
        });
    }
    
    // Asset에 저장된 Servant.json을 읽어오는 함수
    public String loadServantFromAsset(){
        String json = null;

        try {
            InputStream is = assetManager.open("databases/Servant.json");
            int size = is.available();
            byte [] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e){
            e.printStackTrace();
            return  null;
        }
        return json;
    }

    // JSON 읽어 온 파일을 파싱하고 DB에 넣어주는 함수
    public void servantParser(String jsonString){
        // 트랜잭션 시작
        mDbOpenHelper.mDB.beginTransaction();
        try{
            JSONArray jarray = new JSONArray(jsonString);

            for(int i = 0; i < jarray.length(); i++){
                JSONObject jObject = jarray.getJSONObject(i);

                // 아이디, 서번트 이름, 서번트 클래스, 서번트 등급
                int id = jObject.getInt("id");
                String servantName = jObject.getString("servantName");
                String servantClass = jObject.getString("servantClass");
                int servantGrade = jObject.getInt("servantGrade");

                // db에 삽입
                mDbOpenHelper.insertServant(id, servantName, servantClass, servantGrade);

            }
            // 완전히 종료되면 실행
            mDbOpenHelper.mDB.setTransactionSuccessful();
        } catch (JSONException e){
            e.printStackTrace();
        } finally {
            // 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
        }

    }


}
