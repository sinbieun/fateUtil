package com.fate.user.fateutil.layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.adapter.SearchAdapter;
import com.fate.user.fateutil.adapter.SearchIntentAdapter;
import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.SkillContact;

import java.util.List;

public class SearchIntent extends AppCompatActivity{

    private DbOpenHelper mDbOpenHelper;
    int position;
    private SearchIntentAdapter mAdapter;
    private GridView gridView;
    private ListView listView;
    private TextView skillName;
    private ImageView skillIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDbOpenHelper = new DbOpenHelper(this);
        // 인텐트 생성
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent);

        //skillIcon = (ImageView) findViewById(R.id.skill_icon);
        //skillName = (TextView) findViewById(R.id.skill_name);



        Intent intent = getIntent();
        position = (int)intent.getSerializableExtra("POSITION");

        init();

        // 뒤로가기 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // 뒤로 가기 버튼 누를 SearchLayout으로 돌아감
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
        mDbOpenHelper.open();
        List<SkillContact> skillContacts = mDbOpenHelper.getServantJoinSkill(position); // 스킬 들을 조인한다.
        mAdapter = new SearchIntentAdapter(this, skillContacts); // 어댑터에 DB에서 받은 스킬들을 넣어준다.
        listView = (ListView) findViewById(R.id.active_skill_list); // listView 아이디를 불러온다.
        listView.setAdapter(mAdapter); // 리스트 뷰에 어댑터 설정을 저장한다.
        mDbOpenHelper.close();
    }


}
