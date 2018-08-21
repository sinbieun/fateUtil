package com.fate.user.fateutil.layout.detail;


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
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.adapter.SearchAdapter;
import com.fate.user.fateutil.adapter.SearchIntentAdapter;
import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.SkillContact;

import java.util.List;

public class SearchIntent extends AppCompatActivity{

    private DbOpenHelper mDbOpenHelper;
    private  int position;
    private SearchIntentAdapter mAdapter, skillHavingAdapter;
    private GridView gridView;
    private ListView listView;
    private TextView skillName;
    private ImageView skillIcon;
    private TabHost tabHost1;

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
        // drawTable();

        //List<SkillContact> skillContacts = mDbOpenHelper.getServantJoinSkill(position); // 스킬 들을 조인한다.
        //mAdapter = new SearchIntentAdapter(this, skillContacts); // 어댑터에 DB에서 받은 스킬들을 넣어준다.
        //listView = (ListView) findViewById(R.id.active_skill_list); // listView 아이디를 불러온다.
        //listView.setAdapter(mAdapter); // 리스트 뷰에 어댑터 설정을 저장한다.
        mDbOpenHelper.close();


        tabHost1 = (TabHost)findViewById(R.id.tabhost);
        tabHost1.setup();

        // 서번트 기본 스텟
        // "Tab Spec" 태그를 가진 TabSpec 객체 생성
        TabHost.TabSpec tabSpec1 = tabHost1.newTabSpec("Tab Spec 1");
        // 탭이 눌러졌을 때 FrameLayout에 표시될 content 뷰에 대한 리소스 id 지정
        tabSpec1.setContent(R.id.content1);
        // 탭에 표시될 문자열 지정
        tabSpec1.setIndicator("스테이터스");
        // Tab host에 탭 추가
        tabHost1.addTab(tabSpec1);

        // 스킬
        TabHost.TabSpec tabSpec2 = tabHost1.newTabSpec("Tab Spec 2");
        tabSpec2.setContent(R.id.content2);
        tabSpec2.setIndicator("스킬");
        tabHost1.addTab(tabSpec2);

        // 강화
        TabHost.TabSpec tabSpec3 = tabHost1.newTabSpec("Tab Spec 3");
        tabSpec3.setContent(R.id.content3);
        tabSpec3.setIndicator("강화");
        tabHost1.addTab(tabSpec3);

    }

    /*
    public void drawTable(){
        List<SkillContact> skillHaving = mDbOpenHelper.getServantHavingSkill(position); // 가지고 있는 스킬을 가져온다.
        int size = skillHaving.size();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_layout);

        for(int i = 0; i < size; i++){
            TextView skillName = new TextView(this);
            TableRow tableRow = new TableRow(this);

            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            skillName.setText("카리스마");
            skillName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);


        }

    }
    */

}
