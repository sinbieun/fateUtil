package com.fate.user.fateutil.layout.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.SkillContact;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class SearchIntent extends AppCompatActivity {

    private DbOpenHelper mDbOpenHelper;
    private int position;
    private GridView gridView;
    private ListView listView;
    private TextView skillName, tempTextView;
    private ImageView skillIcon;
    private TabHost tabHost1;
    private TableLayout container;
    private List<SkillContact> servantHavingSkillValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDbOpenHelper = new DbOpenHelper(this);
        // 인텐트 생성
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent);

        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("POSITION");

        init(); // 초기화


        // 뒤로가기 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // 뒤로 가기 버튼 누를 SearchLayout으로 돌아감
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 초기 상태 설정
    public void init() {
        // id를 가져오는 부분
        // 테이블 레이아웃의 id를 가져온다.
        TableLayout servantSkillList;
        servantSkillList = (TableLayout) findViewById(R.id.active_skill_table);

        TableRow tr_head = new TableRow(this);
        // tr_head.setId(10);
        tr_head.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


        // DB를 열고 데이터를 가지고 온다.
        mDbOpenHelper.open();
        // 서번트가 보유하고 있는 스킬들을 가져온다.
        List<SkillContact> servantHavingSkill = mDbOpenHelper.getServantHavingSkill(position);
        // 서번트가 보유하고 있는 스킬들의 수치를 가지고 온다.
        for(int i= 0; i <servantHavingSkill.size(); i++){
            SkillContact contact = servantHavingSkill.get(i);
            String skillFullName = contact.getSkillFullName();
            
        }

        for (int i = 0; i < servantHavingSkill.size(); i++) {
            SkillContact contact = servantHavingSkill.get(i);
            String skillName = contact.getSkillName();
            servantHavingSkillValue = mDbOpenHelper.getServantHavingValue(position, skillName);
            tableSkillValue(1, servantHavingSkillValue.size());
        }

        mDbOpenHelper.close();

        tabHost1 = (TabHost) findViewById(R.id.tabhost);
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
    public void tableSkillName(int trCt){
        TextView textView = (TextView)findViewById(R.id.skill_name);
        TextView text[] = new TextView[trCt];

        for(int tr = 0; tr < trCt; tr++){
            tr
        }

    }
    */

    public void tableSkillValue(int trCt, int tdCt) {

        TableLayout table = (TableLayout)findViewById(R.id.active_skill_table);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow row[] = new TableRow[trCt];
        TextView text[][] = new TextView[trCt][tdCt];

        SkillContact contact;
        for(int tr = 0; tr < trCt; tr++){
            row[tr] = new TableRow(this);

            for(int td = 0; td < tdCt; td++){
                contact = servantHavingSkillValue.get(td);
                String skillNumber = contact.getSkillNumber();
                text[tr][td] = new TextView(this);
                text[tr][td].setText(skillNumber);
                text[tr][td].setTextSize(8);
                text[tr][td].setGravity(Gravity.CENTER);

                row[tr].addView(text[tr][td]);
            } // td for end

            table.addView(row[tr], rowLayout);
        } // tr for end

    }

}
