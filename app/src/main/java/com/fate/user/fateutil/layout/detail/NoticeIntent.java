package com.fate.user.fateutil.layout.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.adapter.SearchIntentAdapter;
import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.SkillContact;
import com.fate.user.fateutil.model.NoticeModel;

import java.util.ArrayList;
import java.util.List;

public class NoticeIntent extends AppCompatActivity{

    private DbOpenHelper mDbOpenHelper;
    private SearchIntentAdapter mAdapter;
    private GridView gridView;

    private int position;
    private ArrayList<NoticeModel> noticeArray = new ArrayList<NoticeModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면 설정
        setContentView(R.layout.search_intent);

        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("POSITION");
        //noticeArray = (List) intent.getSerializableExtra("POSITION");

        init();

        // 뒤로가기 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // 뒤로 가기 버튼 누를 시, 현재 페이지 종료
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 초기 상태 설정
    public void init(){
        /*mDbOpenHelper.open();
        List<SkillContact> skillContacts = mDbOpenHelper.getServantJoinSkill(position); // 스킬 들을 조인한다.
        mAdapter = new SearchIntentAdapter(this, skillContacts); // 어댑터에 DB에서 받은 스킬들을 넣어준다.
        listView = (ListView) findViewById(R.id.active_skill_list); // listView 아이디를 불러온다.
        listView.setAdapter(mAdapter); // 리스트 뷰에 어댑터 설정을 저장한다.
        mDbOpenHelper.close();*/
    }


}
