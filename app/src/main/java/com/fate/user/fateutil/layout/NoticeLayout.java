package com.fate.user.fateutil.layout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.adapter.NoticeAdapter;
import com.fate.user.fateutil.adapter.SearchAdapter;
import com.fate.user.fateutil.db.ServantContact;
import com.fate.user.fateutil.layout.detail.NoticeIntent;
import com.fate.user.fateutil.model.NoticeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NoticeLayout extends LinearLayout {

    private LinearLayout currentLayout;
    private LayoutInflater li = null;

    private ListView listView = null; // 리스트 뷰
    private NoticeAdapter noticeAdapter;
    private AssetManager assetManager = getResources().getAssets();

    public NoticeLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
        currentLayout = (LinearLayout) li.inflate(R.layout.notice_main, null);
        addView(currentLayout);
    }

    public void init(){

        // List View 그려주기
        setListView();
    }

    /**
     * 공지사항 리스트뷰 세팅
     */
    public void setListView(){
        // 1. 파일 이름 저장 변수
        String fileName = "databases/Notice.json";

        // 2. Servant.json 파일을 String에 저장
        String jsonString = loadJsonFromAsset(fileName);

        // 3. parsing 시작

        // parameter setting
        final ArrayList<NoticeModel> noticeArray = new ArrayList<NoticeModel>();
        try {
            JSONArray jArray = new JSONArray(jsonString);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                NoticeModel tempNoticeModel = new NoticeModel();

                // 4. 공지사항 모델 세팅
                tempNoticeModel.setNoticeId(jObject.getInt("notice_id"));
                tempNoticeModel.setNoticeKind(jObject.getString("notice_kind"));
                tempNoticeModel.setNoticeTitle(jObject.getString("notice_title"));
                tempNoticeModel.setNoticeContent(jObject.getString("notice_content"));
                tempNoticeModel.setNoticeRegUser(jObject.getString("notice_reg_user"));
                tempNoticeModel.setNoticeRegDate(jObject.getString("notice_reg_date"));

                noticeArray.add(tempNoticeModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        noticeAdapter = new NoticeAdapter(currentLayout.getContext(), noticeArray);
        listView = (ListView)findViewById(R.id.notice_list_view);
        listView.setAdapter(noticeAdapter);

        // Listview Click Event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭을 하였을 때 position 값을 인텐트에 넘겨준다.
                // 인텐트 생성
                Intent intent = new Intent(getContext(), NoticeIntent.class);

                // Data send
                intent.putExtra("POSITION", (position + 1));
                //intent.putExtra("DATA", noticeArray[position + 1]);
                getContext().startActivity(intent);
            }
        });
    }

    // Asset에 저장된 json을 읽어오는 함수
    public String loadJsonFromAsset(String fileName) {
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

}