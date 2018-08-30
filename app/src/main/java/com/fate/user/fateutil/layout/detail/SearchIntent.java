package com.fate.user.fateutil.layout.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fate.user.fateutil.R;

import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.SkillContact;


import java.util.List;

public class SearchIntent extends AppCompatActivity {

    private DbOpenHelper mDbOpenHelper;
    private int position;
    private TabHost tabHost1;
    private List<SkillContact> servantHavingSkillValue;
    private List<SkillContact> servantHavingSkill;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDbOpenHelper = new DbOpenHelper(this);
        // 인텐트 생성
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent);

        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("POSITION");


        init(); // 초기화
        createTable(); // 스킬 테이블 생성

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

        // 상단 탭 관리
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

        // 스킬 탭
        TabHost.TabSpec tabSpec2 = tabHost1.newTabSpec("Tab Spec 2");
        tabSpec2.setContent(R.id.content2);
        tabSpec2.setIndicator("스킬");
        tabHost1.addTab(tabSpec2);

        // 강화 탭
        TabHost.TabSpec tabSpec3 = tabHost1.newTabSpec("Tab Spec 3");
        tabSpec3.setContent(R.id.content3);
        tabSpec3.setIndicator("강화");
        tabHost1.addTab(tabSpec3);

        // 뒤로가기 버튼 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void createTable() {


        // DB를 열고 데이터를 가져온다.
        mDbOpenHelper.open();
        // 1. 서번트가 보유하고 있는 스킬들을 가져온다.
        servantHavingSkill = mDbOpenHelper.getServantHavingSkill(position); // 리스트 위치에 따라 검색을 다르게 한다.
        // 2. 보유하고 있는 스킬 갯수에 따라 테이블의 행을 생성한다.
        for (int i = 0; i < 1; i++) {
            // 2. 스킬 테이블을 만들고 스킬 이름, 설명, 표시할 효과를 넣는다.
            tableSkillHaving(servantHavingSkill.size(), 3);
        }

        mDbOpenHelper.close();

    }

    // 스킬 테이블 생성
    public void tableSkillHaving(int trCt, int tdCt) {

        // 테이블 변수
        TableLayout skillTable = (TableLayout) findViewById(R.id.active_skill_table);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        String packName = this.getPackageName();
        TableRow row[] = new TableRow[trCt];
        ImageView skillIcon[][] = new ImageView[trCt][tdCt];
        TextView textViews[][] = new TextView[trCt][tdCt];

        for (int tr = 0; tr < trCt; tr++) {
            row[tr] = new TableRow(this);
            SkillContact contact = servantHavingSkill.get(tr);
            String skillName = contact.getSkillName();
            // 스킬의 수치, 쿨다운을 얻어온다.
            servantHavingSkillValue = mDbOpenHelper.getServantHavingValue(position, skillName);

            // 1. 스킬 이미지, 스킬 이름, 스킬 설명을 넣는다.
            for (int td = 0; td < 3; td++) {
                String skillFullName = contact.getSkillFullName();
                String skillPath = contact.getSkillIcon();

                // 2. 넣을 행을 만들고 데이터를 집어 넣는다.
                switch (td) {
                    // 스킬 이미지 넣기
                    case 0: {
                        skillIcon[tr][td] = new ImageView(this);
                        skillIcon[tr][td].setImageResource(getResources().getIdentifier("@drawable/" + skillPath, "drawable", packName));
                        row[tr].addView(skillIcon[tr][td]);
                        break;
                    }
                    // 스킬 이름 넣기
                    case 1: {
                        textViews[tr][td] = new TextView(this);
                        textViews[tr][td].setText(skillFullName);
                        textViews[tr][td].setTextSize(5);
                        textViews[tr][td].setGravity(Gravity.CENTER);
                        row[tr].addView(textViews[tr][td]);
                        break;
                    }

                    // 스킬 설명 넣기
                    case 2: {
                        break;
                    }

                }
            } // td 의 끝

            // 3. 스킬이미지, 스킬 이름, 스킬 설명을 추가 시킨 후에 수치 값을 집어 넣는다. (스킬 설명 추가 예정)
            skillTable.addView(row[tr], rowLayout);
            // 4. 스킬 수치 값을 집어 넣는다. (좀더 동적으로 수정 예정)
            tableSkillValue(3, servantHavingSkillValue.size());
        } // tr 의 끝
    }

    // 스킬 수치 테이블 생성
    public void tableSkillValue(int trCt, int tdCt) {

        // 테이블 변수
        TableLayout skillTable = (TableLayout) findViewById(R.id.active_skill_table);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        TableRow row[] = new TableRow[trCt];
        TextView text[][] = new TextView[trCt][tdCt];

        SkillContact skillEffect, skillValue;
        for (int tr = 0; tr < trCt; tr++) {
            row[tr] = new TableRow(this);

            for (int td = 0; td < tdCt; td++) {
                skillValue = servantHavingSkillValue.get(td);
                switch (tr) {
                    case 0: {
                        // 1렙부터 10렙 표시
                        String lv = String.valueOf((td + 1));
                        text[tr][td] = new TextView(this);
                        text[tr][td].setText(lv);
                        text[tr][td].setTextSize(5);
                        text[tr][td].setGravity(Gravity.CENTER);
                        row[tr].addView(text[tr][td]);
                        break;
                    }

                    case 1: {
                        // 스킬 수치 넣기
                        String skillNumber = skillValue.getSkillNumber();
                        text[tr][td] = new TextView(this);
                        text[tr][td].setText(skillNumber);
                        text[tr][td].setTextSize(5);
                        text[tr][td].setGravity(Gravity.CENTER);
                        row[tr].addView(text[tr][td]);
                        break;
                    }

                    case 2: {
                        // 스킬 쿨다운 넣기
                        String skillCoolDown = String.valueOf(skillValue.getSkillCoolDown());
                        text[tr][td] = new TextView(this);
                        text[tr][td].setText(skillCoolDown);
                        text[tr][td].setTextSize(5);
                        text[tr][td].setGravity(Gravity.CENTER);
                        row[tr].addView(text[tr][td]);
                        break;
                    }
                }

            } // td for end
            skillTable.addView(row[tr], rowLayout);
        } // tr for end

    }

}
