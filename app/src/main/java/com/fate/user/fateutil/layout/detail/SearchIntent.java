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
import com.fate.user.fateutil.db.contract.Skill.SkillContact;


import java.util.List;

public class SearchIntent extends AppCompatActivity {

    private DbOpenHelper mDbOpenHelper;
    private int position;
    private TabHost tabHost1;
    private List<SkillContact> servantHavingSkillValue;
    private List<SkillContact> servantHavingSkill;
    private List<SkillContact> servantHavingSkillExplain;
    private List<SkillContact> servantHavingSkillEffect;

    private String skillName;

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
        tableSkillHaving(servantHavingSkill.size(), 3);

        mDbOpenHelper.close();

    }

    // 스킬 테이블 생성
    public void tableSkillHaving(int trCt, int tdCt) {

        // 테이블 변수
        TableLayout skillTable = (TableLayout) findViewById(R.id.active_skill_table);
        skillTable.setPadding(2,2,2,2);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        String packName = this.getPackageName();
        TableRow row[] = new TableRow[trCt];
        ImageView skillIcon[][] = new ImageView[trCt][tdCt];
        TextView textViews[][] = new TextView[trCt][tdCt];


        for (int tr = 0; tr < trCt; tr++) {
            row[tr] = new TableRow(this);
            // row[tr].setLayoutParams(rowLayout);

            // 스킬 이름을 가져온다.
            SkillContact skillNameContact = servantHavingSkill.get(tr);
            skillName = skillNameContact.getSkillName();

            servantHavingSkillExplain = mDbOpenHelper.getServantSkillExplain(position, skillName); // 해당 스킬 설명을 가져온다
            servantHavingSkillEffect = mDbOpenHelper.getServantSkillEffect(position, skillName); // 해당 스킬의 이펙트를 가져온다.

            // 1. 스킬 이미지, 스킬 이름, 스킬 설명을 넣는다.
            for (int td = 0; td < 3; td++) {
                String skillFullName = skillNameContact.getSkillFullName();
                String skillPath = skillNameContact.getSkillIcon();

                switch (td) {
                    // 1) 스킬 이미지 넣기
                    case 0: {
                        skillIcon[tr][td] = new ImageView(this);
                        skillIcon[tr][td].setImageResource(getResources().getIdentifier("@drawable/" + skillPath, "drawable", packName));
                        rowLayout.weight = 1;
                        row[tr].addView(skillIcon[tr][td]);
                        break;
                    }
                    // 2) 스킬 이름 넣기
                    case 1: {
                        textViews[tr][td] = new TextView(this);
                        textViews[tr][td].setText(skillFullName);
                        textViews[tr][td].setTextSize(8);
                        textViews[tr][td].setGravity(Gravity.CENTER);
                        rowLayout.weight = 2;
                        row[tr].addView(textViews[tr][td]);
                        break;
                    }

                    // 3) 스킬 설명 넣기
                    case 2: {
                        String strExplain = "", str;
                        SkillContact skillExplain;
                        for(int tr1 = 0; tr1 < servantHavingSkillExplain.size(); tr1++){
                            skillExplain = servantHavingSkillExplain.get(tr1); // 스킬 설명을 가져온다.
                            str = skillExplain.getSkillExplain() + "\n";
                            strExplain += str;
                        }

                        textViews[tr][td] = new TextView(this);
                        textViews[tr][td].setText(strExplain);
                        textViews[tr][td].setTextSize(8);
                        textViews[tr][td].setGravity(Gravity.CENTER);
                        rowLayout.weight = 3;
                        row[tr].addView(textViews[tr][td]);
                        break;
                    }


                }
                //skillTable.addView(row[tr], rowLayout);
            } // td 의 끝
            skillTable.addView(row[tr], rowLayout);
            // 3. 스킬이미지, 스킬 이름, 스킬 설명을 띄워준다.
            // 4. 스킬 수치 값을 집어 넣는다.
            tableSkillValue(servantHavingSkillEffect.size()+2, 10);

        } // tr 의 끝

    }

    // 스킬 수치 테이블 생성
    public void tableSkillValue(int trCt, int tdCt) {

        // 테이블 변수
        TableLayout skillTable = (TableLayout) findViewById(R.id.active_skill_table);
        //TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        TableRow.LayoutParams textLayout = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow row[] = new TableRow[trCt];
        TextView text[][] = new TextView[trCt][tdCt];


        SkillContact skillEffect; // 스킬 효과 contact
        SkillContact skillValue; // 스킬 수치 contact

        String effect; // 스킬 효과

        for (int tr = 0; tr < trCt; tr++) {
            row[tr] = new TableRow(this);

            // 스킬 레벨, 스킬 효과, 스킬 쿨다운 표시
            for (int td1 = 0; td1 < 1; td1++) {
                text[tr][td1] = new TextView(this);
               text[tr][td1].setLayoutParams(textLayout);


                // 첫 줄
                if (tr == 0) {
                    text[tr][td1].setText("레벨");
                    text[tr][td1].setTextSize(10);
                    text[tr][td1].setGravity(Gravity.CENTER);
                    row[tr].addView(text[tr][td1]);
                }
                // 두번째 줄 부터 마지막의 전 줄 까지 효과 입력
                else if (tr > 0 && tr < (trCt-1)) {
                    // 스킬 효과를 가져온다.
                    skillEffect = servantHavingSkillEffect.get(tr-1);
                    effect = skillEffect.getSkillEffect();
                    servantHavingSkillValue = mDbOpenHelper.getServantHavingValue(position, skillName, effect); // 효과를 사용해서 스킬 수치를 가져온다.

                    text[tr][td1].setText(effect);
                    text[tr][td1].setTextSize(10);
                    text[tr][td1].setGravity(Gravity.CENTER);
                    row[tr].addView(text[tr][td1]);
                }
                // 마지막은 쿨다운 표시
                else {
                    // 효과 가져오기
                    skillEffect = servantHavingSkillEffect.get(tr-2);
                    effect = skillEffect.getSkillEffect();
                    servantHavingSkillValue = mDbOpenHelper.getServantHavingValue(position, skillName, effect); // 쿨다운 가져오기

                    text[tr][td1].setText("쿨다운");
                    text[tr][td1].setTextSize(10);
                    text[tr][td1].setGravity(Gravity.CENTER);
                    row[tr].addView(text[tr][td1]);
                }
            }

            // 스킬 레벨, 스킬 수치, 스킬 쿨다운 테이블
            for (int td2 = 0; td2 < tdCt; td2++) {
                text[tr][td2] = new TextView(this);
                String lv; // 레벨
                String skillNumber; // 스킬 수치
                String skillCoolDown; // 스킬 쿨다운


                // 레벨 넣기
                if (tr == 0) {
                    lv = String.valueOf(td2 + 1);
                    text[tr][td2].setText(lv);
                    text[tr][td2].setTextSize(10);
                    //text[tr][td2].setGravity(Gravity.CENTER);
                    row[tr].addView(text[tr][td2]);
                }

                // 스킬 수치 넣기
                else if (tr > 0 && tr < (trCt - 1)) {
                    // 열에 따라 스킬 수치 가져오기
                    skillValue = servantHavingSkillValue.get(td2);
                    skillNumber = skillValue.getSkillNumber();

                    text[tr][td2].setText(skillNumber);
                    text[tr][td2].setTextSize(10);
                    //text[tr][td2].setGravity(Gravity.CENTER);
                    row[tr].addView(text[tr][td2]);

                }
                // 쿨다운 수치
                else if (tr == (trCt - 1)) {
                    // 열에 따라 스킬 쿨다운 가져오기
                    skillValue = servantHavingSkillValue.get(td2);
                    skillCoolDown = String.valueOf(skillValue.getSkillCoolDown());

                    text[tr][td2].setText(skillCoolDown);
                    text[tr][td2].setTextSize(10);
                    //text[tr][td2].setGravity(Gravity.CENTER);
                    row[tr].addView(text[tr][td2]);
                }

            } // td for end
            skillTable.addView(row[tr], rowLayout); // 뷰에 추가
        } // tr for end

    }
}


