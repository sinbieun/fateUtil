package com.fate.user.fateutil.layout;

import android.content.Context;
import android.content.res.AssetManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.adapter.SearchAdapter;
import com.fate.user.fateutil.db.DbOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.fate.user.fateutil.db.ServantContact;

public class SearchLayout extends LinearLayout {

    Button btnSearch = null;
    EditText editSearch = null;
    AssetManager assetManager = getResources().getAssets();
    private DbOpenHelper mDbOpenHelper;
    private SearchAdapter mAdapter;
    private ListView listView = null;
    LinearLayout currentLayout;
    LayoutInflater li = null;

    public SearchLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
        currentLayout = (LinearLayout) li.inflate(R.layout.search_main, null);
        addView(currentLayout);

        // 1. searchLayout에 들어가야하는 id 찾기
        btnSearch = currentLayout.findViewById(R.id.btn_Search);
        editSearch = currentLayout.findViewById(R.id.edit_Search);
        listView = currentLayout.findViewById(R.id.list_View);

        mDbOpenHelper = new DbOpenHelper(currentLayout.getContext());
        // 2. DB에서 서번트 항목 전체를 할당 받고 Adapter와 연결하여준다.
        init();
        // 3. 필터를 사용하여 검색어가 editText에 입력될 때 마다 서번트 항목들을 띄워준다.

        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editSearch.getText().toString();
                mAdapter.Filter(text);
            }


        });

        // 4. 버튼을 누르면 검색어에 따라 검색을 다르게 한다.
        btnSearch.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // 서번트 테이블 삽입
                // servantParser();
                // 서번트 모든 서번트 불러오기

                // getAllServant();

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
    public void servantParser(){

        // Servant.json 파일을 String에 저장
        String jsonString = loadServantFromAsset();
        // 트랜잭션 시작
        mDbOpenHelper.open();
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
                mDbOpenHelper.addServantContact(new ServantContact(id, servantName,servantClass,servantGrade));

            }
            // 완전히 종료되면 실행
            mDbOpenHelper.mDB.setTransactionSuccessful();
        } catch (JSONException e){
            e.printStackTrace();
        } finally {
            // 삽입 끝나면 트랜잭션 종료
            mDbOpenHelper.mDB.endTransaction();
            // DB 닫기
            mDbOpenHelper.close();
        }

    }

    // 서번트 리스트 생성, 리스트 뷰와 어댑터 연결
    public void init(){
        mDbOpenHelper.open();
        // 1. DB에서 서번트 리스트를 가져오고 저장한다.
        List<ServantContact> contacts = mDbOpenHelper.getAllServantContacts();
        // 2. 리스트에 연동 될 어댑터 생성
        mAdapter = new SearchAdapter(currentLayout.getContext(), contacts);
        listView = (ListView)findViewById(R.id.list_View);
        listView.setAdapter(mAdapter);
        mDbOpenHelper.close();

    }

}
