package com.fate.user.fateutil.layout.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fate.user.fateutil.R;

import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.contact.Material.MaterialContact;
import com.fate.user.fateutil.db.contact.Servant.ServantAscensionContact;
import com.fate.user.fateutil.db.contact.Servant.ServantIconContact;
import com.fate.user.fateutil.db.contact.Skill.SkillContact;
import com.fate.user.fateutil.db.contact.Weapon.WeaponContact;


import java.util.List;

public class SearchIntent extends AppCompatActivity {

    private DbOpenHelper mDbOpenHelper;
    private int position;
    private TabHost tabHost1;

    // 스킬 명
    private String skillName;

    // 액티브 스킬 변수
    private List<SkillContact> servantHavingActiveSkill;
    private List<SkillContact> servantHavingSkillExplain;
    private List<SkillContact> servantHavingSkillEffect;
    private List<SkillContact> servantHavingSkillValue;

    // 패시브 스킬 변수
    private List<SkillContact> servantHavingPassiveSkill;
    // 보구 변수
    private List<WeaponContact> servantHavingWeapon;
    private List<WeaponContact> servantHavingWeapon1;


    // 재료 변수
    private List<MaterialContact> servantMaterial;

    // 서번트 영기재림 이미지 변수
    private List<ServantAscensionContact> servantAscensionImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDbOpenHelper = new DbOpenHelper(this);
        // 인텐트 생성
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent);

        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("POSITION");


        init(); // 초기화
        createSkillTable(); // 스킬 테이블 생성

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
        tabHost1 = findViewById(R.id.tabhost);
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //
        // 버튼 이미지 뷰 호출
        Button ascention_btn_1 = findViewById(R.id.first_ascention_button);
        Button ascention_btn_2 = findViewById(R.id.second_ascention_button);
        Button ascention_btn_3 = findViewById(R.id.third_ascention_button);
        Button ascention_btn_4 = findViewById(R.id.forth_ascention_button);
        final ImageView ascention_img = findViewById(R.id.img_ascention);


    }

    // DB 불러오기
    public void createSkillTable() {

        // DB를 열고 데이터를 가져온다.
        mDbOpenHelper.open();

        // 1. 서번트 영기재림 이미지
        servantAscensionImg = mDbOpenHelper.getServantAscensionImage(position); // 이미지를 가지고 온다.
        tableAcsensionImg(servantAscensionImg);

        // 2. 서번트가 보유하고 있는 스킬들을 가져온다.
        // 2_1) 서번트가 소유하고 있는 액티브 스킬을 가져온다.
        servantHavingActiveSkill = mDbOpenHelper.getServantHavingActiveSkill(position); // 리스트 위치에 따라 검색을 다르게 한다.
        // 2_2) 서번트가 소유하고 있는 패시브 스킬을 가져온다.
        servantHavingPassiveSkill = mDbOpenHelper.getServantHavingPassiveSkill(position);
        // 2_3) 보유하고 있는 스킬 갯수에 따라 테이블의 행을 생성한다.
        tableHavingActiveSkill(servantHavingActiveSkill.size(), 3); // 액티브 스킬
        tablePassiveSkil(servantHavingPassiveSkill.size(), 3); // 패시브 스킬

        // 3. 보유하고 있는 강화 재료를 가져온다.
        // 2_1) 서번트가 보유하고 있는 영기재림 재료를 가져온다.
        servantMaterial = mDbOpenHelper.getServantMaterial(position, 'A');
        tableMaterial(5, 3, servantMaterial);
        // 2_2) 서번트가 보유하고 있는 스킬재림 재료를 가져온다.
        servantMaterial = mDbOpenHelper.getServantMaterial(position, 'S');
        tableMaterial(10, 3, servantMaterial);

        // 보구
        // 1. 이름 랭크 히트수
        //servantHavingWeapon1 = mDbOpenHelper.getServantWeapon1(position);
        //tableWeaponMain(0,3,servantHavingWeapon1);
        servantHavingWeapon = mDbOpenHelper.getServantWeapon1(position);
        tableWeaponMain(servantHavingWeapon);

        mDbOpenHelper.close();

    }

    // 액티브 스킬 테이블 생성
    public void tableHavingActiveSkill(int trCt, int tdCt) {

        // 테이블 변수
        TableLayout skillTable = findViewById(R.id.active_skill_table);
        skillTable.setPadding(2, 2, 2, 2);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        String packName = this.getPackageName();
        TableRow row[] = new TableRow[trCt];
        ImageView skillIcon[][] = new ImageView[trCt][tdCt];
        TextView textViews[][] = new TextView[trCt][tdCt];


        for (int tr = 0; tr < trCt; tr++) {
            row[tr] = new TableRow(this);
            // row[tr].setLayoutParams(rowLayout);

            // 스킬 이름을 가져온다.
            SkillContact skillNameContact = servantHavingActiveSkill.get(tr);
            skillName = skillNameContact.getSkillName();

            servantHavingSkillExplain = mDbOpenHelper.getServantActiveSkillExplain(position, skillName); // 해당 스킬 설명을 가져온다
            servantHavingSkillEffect = mDbOpenHelper.getServantActiveSkillEffect(position, skillName); // 해당 스킬의 이펙트를 가져온다.

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
                        StringBuilder sb = new StringBuilder();
                        String str;
                        SkillContact skillExplain;
                        for (int tr1 = 0; tr1 < servantHavingSkillExplain.size(); tr1++) {
                            skillExplain = servantHavingSkillExplain.get(tr1); // 스킬 설명을 가져온다.
                            str = skillExplain.getSkillExplain() + "\n";
                            sb.append(str);
                        }
                        textViews[tr][td] = new TextView(this);
                        textViews[tr][td].setText(sb.toString());
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
            tableActiveSkillValue(servantHavingSkillEffect.size() + 2, 10);

        } // tr 의 끝

    }

    // 액티브 스킬 수치 테이블 생성
    public void tableActiveSkillValue(int trCt, int tdCt) {

        // 테이블 변수
        TableLayout skillTable = findViewById(R.id.active_skill_table);
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
                else if (tr < (trCt - 1)) {
                    // 스킬 효과를 가져온다.
                    skillEffect = servantHavingSkillEffect.get(tr - 1);
                    effect = skillEffect.getSkillEffect();
                    servantHavingSkillValue = mDbOpenHelper.getServantActiveSkillValue(position, skillName, effect); // 효과를 사용해서 스킬 수치를 가져온다.

                    text[tr][td1].setText(effect);
                    text[tr][td1].setTextSize(10);
                    text[tr][td1].setGravity(Gravity.CENTER);
                    row[tr].addView(text[tr][td1]);
                }
                // 마지막은 쿨다운 표시
                else {
                    // 효과 가져오기
                    skillEffect = servantHavingSkillEffect.get(tr - 2);
                    effect = skillEffect.getSkillEffect();
                    servantHavingSkillValue = mDbOpenHelper.getServantActiveSkillValue(position, skillName, effect); // 쿨다운 가져오기

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
                else if (tr < (trCt - 1)) {
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

    // 패시브 스킬 테이블 생성
    public void tablePassiveSkil(int trCt, int tdCt) {
        // 테이블 변수
        TableLayout skillTable = findViewById(R.id.passive_skill_table);
        skillTable.setPadding(5, 5, 5, 5);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        String packName = this.getPackageName();
        TableRow row[] = new TableRow[trCt];
        ImageView skillIcon[][] = new ImageView[trCt][tdCt];
        TextView textViews[][] = new TextView[trCt][tdCt];

        for (int tr = 0; tr < trCt; tr++) {
            row[tr] = new TableRow(this);

            // 스킬 이름을 가져온다.
            SkillContact skillContact = servantHavingPassiveSkill.get(tr);

            // 1. 스킬 이미지, 스킬 이름, 스킬 설명을 넣는다.
            for (int td = 0; td < 3; td++) {
                switch (td) {
                    // 1) 스킬 이미지 넣기
                    case 0: {
                        String skillPath = skillContact.getSkillIcon();
                        skillIcon[tr][td] = new ImageView(this);
                        skillIcon[tr][td].setImageResource(getResources().getIdentifier("@drawable/" + skillPath, "drawable", packName));
                        rowLayout.weight = 1;
                        row[tr].addView(skillIcon[tr][td]);
                        break;
                    }
                    // 2) 스킬 이름 넣기
                    case 1: {
                        String skillFullName = skillContact.getSkillFullName();
                        textViews[tr][td] = new TextView(this);
                        textViews[tr][td].setText(skillFullName);
                        textViews[tr][td].setTextSize(8);
                        textViews[tr][td].setGravity(Gravity.CENTER);
                        rowLayout.weight = 2;
                        row[tr].addView(textViews[tr][td]);
                        break;
                    }

                    // 3) 스킬 설명 넣기
                    // 패시브 스킬이 한개에 효과가 여러개인 경우 수정 필요
                    case 2: {
                        String skillExplain = skillContact.getSkillExplain();
                        textViews[tr][td] = new TextView(this);
                        textViews[tr][td].setText(skillExplain);
                        textViews[tr][td].setTextSize(8);
                        textViews[tr][td].setGravity(Gravity.CENTER);
                        rowLayout.weight = 3;
                        row[tr].addView(textViews[tr][td]);
                        break;
                    }

                }
            } // td 의 끝
            skillTable.addView(row[tr], rowLayout);
        } // tr 의 끝

    }

    // 서번트 보구 테이블 생성
    /*
    public void tableWeaponTable(int trCt, int tdCt, List<WeaponContact> weaponList){
        // 1. 보구 데이터 받음
        TableLayout skillTable = findViewById(R.id.weapon_table_1);
        skillTable.setPadding(2, 2, 2, 2);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        String packName = this.getPackageName();
        TableRow row[] = new TableRow[trCt];
        TextView textViews[][] = new TextView[trCt][tdCt];




        // 1) 보구 강화 x / 보구 강화 O
        // 2) 보구 이름, 서브 이름
        // 3) 보구 타입, 보구 랭크, 보구 히트수
        // 4) 보구 효과 , 보구 오버차지
        // 5) 보구 lv에 따른 데미지 비율 , 보구 오버 차지 레벨에 따른 비율

    }
    */
    public void tableWeaponMain(List<WeaponContact> weaponMain) {
        TextView weaponName = findViewById(R.id.weapon_name);
        TableLayout weaponFirst = findViewById(R.id.weapon_table_1); // 타입, 랭크 히트수
        TableLayout weaponSecond = findViewById(R.id.weapon_table_2); // 효과
        TableLayout weaponThird = findViewById(R.id.weapon_table_3);

        String packName = this.getPackageName();

        TableRow row2[] = new TableRow[weaponMain.size()];

        //ImageView skillIcon[][] = new ImageView[trCt][tdCt];


        // 표1에 그려줄것
        TableRow row1[] = new TableRow[2];
        TextView textViews1[][] = new TextView[2][3];

        weaponName.setPadding(2, 2, 2, 2);
        weaponSecond.setPadding(2, 2, 2, 2);
        weaponThird.setPadding(2, 2, 2, 2);

        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

        WeaponContact weaponContact = weaponMain.get(0);
        weaponName.setText(weaponContact.getWeaponFullName());
        weaponName.setTextSize(20);
        weaponName.setGravity(Gravity.CENTER);

        // 타입 랭크 히트
        for (int tr1 = 0; tr1 < 2; tr1++) {
            row1[tr1] = new TableRow(this);

            weaponContact = weaponMain.get(tr1);
            for (int td1 = 0; td1 < 3; td1++) {
                textViews1[tr1][td1] = new TextView(this);

                switch (td1) {
                    case 0: {
                        if (tr1 == 0) {
                            textViews1[tr1][td1].setText("타입");
                            textViews1[tr1][td1].setTextSize(20);
                            textViews1[tr1][td1].setGravity(Gravity.CENTER);
                            row1[tr1].addView(textViews1[tr1][td1]);
                            break;
                        }
                        textViews1[tr1][td1].setText(weaponContact.getWeaponType());
                        textViews1[tr1][td1].setTextSize(15);
                        textViews1[tr1][td1].setGravity(Gravity.CENTER);
                        row1[tr1].addView(textViews1[tr1][td1]);
                        break;

                    }

                    case 1: {
                        if (tr1 == 0) {
                            textViews1[tr1][td1].setText("랭크");
                            textViews1[tr1][td1].setTextSize(20);
                            textViews1[tr1][td1].setGravity(Gravity.CENTER);
                            row1[tr1].addView(textViews1[tr1][td1]);
                            break;
                        }
                        textViews1[tr1][td1].setText(weaponContact.getWeaponRank());
                        textViews1[tr1][td1].setTextSize(15);
                        textViews1[tr1][td1].setGravity(Gravity.CENTER);
                        row1[tr1].addView(textViews1[tr1][td1]);
                        break;

                    }

                    case 2: {
                        if (tr1 == 0) {
                            textViews1[tr1][td1].setText("히트");
                            textViews1[tr1][td1].setTextSize(20);
                            textViews1[tr1][td1].setGravity(Gravity.CENTER);
                            row1[tr1].addView(textViews1[tr1][td1]);
                            break;
                        }
                        textViews1[tr1][td1].setText(String.valueOf(weaponContact.getWeaponHit()));
                        textViews1[tr1][td1].setTextSize(15);
                        textViews1[tr1][td1].setGravity(Gravity.CENTER);
                        row1[tr1].addView(textViews1[tr1][td1]);
                        break;

                    }
                }
            }

            weaponFirst.addView(row1[tr1], rowLayout);
        }
    }


    // 영기 재림 재료 테이블 생성
    public void tableMaterial(int trCt, int tdCt, List<MaterialContact> Material) {
        // 테이블 변수
        TableLayout tableLayout;
        if (trCt == 5) {
            tableLayout = findViewById(R.id.acsension_material_table);
        } else {
            tableLayout = findViewById(R.id.skill_enhance_material_table);
        }

        //tableLayout.setPadding(50, 50, 50, 50);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow row[] = new TableRow[trCt];
        ImageView materialIcon[][] = new ImageView[trCt][tdCt];
        TextView textViews[][] = new TextView[trCt][tdCt];

        String packName = this.getPackageName();

        // 레벨
        int level = 1;
        int upgradeLevel;

        // 아이템 이름
        String ascensionMaterialName = null; //
        String MaterialName = null; // 재료 이름
        String materialPath; // 재료 경로

        // 아이템 갯수
        int ascensionMaterialValue;

        // 영기재림 자료나 스킬 강화 재료가 없으면 작성하지 않음
        if (Material == null)
            return;

        for (int i = 0; i < Material.size(); i++) {
            MaterialContact materialContact = Material.get(i); // 영기재림 데이터
            upgradeLevel = materialContact.getUpgradeLevel(); // 업그레이드 레벨을 가져옴
            // ascensionMaterialName = materialContact.getMaterialName(); // 필요 재료 이름 가져옴
            ascensionMaterialValue = materialContact.getMaterialValue(); // 필요 재료 갯수 가져옴
            materialPath = materialContact.getMaterialName();


            row[upgradeLevel - 2] = new TableRow(this); // 행

            for (int td = 0; td < tdCt; td++) {

                //row[upgradeLevel - 2] = new TableRow(this); // 행
                textViews[upgradeLevel - 2][td] = new TextView(this); // 텍스트
                materialIcon[upgradeLevel - 2][td] = new ImageView(this); // 재료 아이콘

                // 1. 현재 레벨 표시
                // 2. 레벨 아래에 아이콘 표시
                // 3. 아이콘 옆에 갯수 표시

                switch (td) {
                    // 레벨 표시
                    case 0: {
                        if (level != upgradeLevel) {
                            //textViews[upgradeLevel - 2][td] = new TextView(this); // 텍스트
                            textViews[upgradeLevel - 2][td].setText(String.valueOf("Lv " + upgradeLevel));
                            textViews[upgradeLevel - 2][td].setTextSize(15);
                            textViews[upgradeLevel - 2][td].setGravity(Gravity.CENTER);
                            row[upgradeLevel - 2].addView(textViews[upgradeLevel - 2][td]);
                            level = upgradeLevel;
                        } else {
                            textViews[upgradeLevel - 2][td].setText(" ");
                            textViews[upgradeLevel - 2][td].setTextSize(15);
                            textViews[upgradeLevel - 2][td].setGravity(Gravity.CENTER);
                            row[upgradeLevel - 2].addView(textViews[upgradeLevel - 2][td]);
                        }
                        break;
                    }

                    // 사진 표시
                    case 1: {
                        //materialIcon[upgradeLevel - 2][td] = new ImageView(this); // 재료 아이콘
                        materialIcon[upgradeLevel - 2][td].setImageResource(getResources().getIdentifier("@drawable/" + materialPath, "drawable", packName));
                        row[upgradeLevel - 2].addView(materialIcon[upgradeLevel - 2][td]);
                        break;
                    }

                    // 재료 갯수 표시
                    case 2: {
                        //textViews[upgradeLevel - 2][td] = new TextView(this); // 텍스트
                        textViews[upgradeLevel - 2][td].setText(String.valueOf(ascensionMaterialValue));
                        textViews[upgradeLevel - 2][td].setTextSize(15);
                        textViews[upgradeLevel - 2][td].setGravity(Gravity.CENTER);
                        row[upgradeLevel - 2].addView(textViews[upgradeLevel - 2][td]);
                        break;
                    }

                }
                //tableLayout.addView(row[upgradeLevel - 2], rowLayout);
            }
            tableLayout.addView(row[upgradeLevel - 2], rowLayout);
        }

    }

    // 서번트 영기재림 이미지
    public void tableAcsensionImg(final List<ServantAscensionContact> servantAscensionImg) {

        // 초기화
        Button ascention_btn_1 = findViewById(R.id.first_ascention_button);
        Button ascention_btn_2 = findViewById(R.id.second_ascention_button);
        Button ascention_btn_3 = findViewById(R.id.third_ascention_button);
        Button ascention_btn_4 = findViewById(R.id.forth_ascention_button);
        final String packName = this.getPackageName();

        // 버튼 리스너
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView ascention_img = findViewById(R.id.img_ascention);
                String imgPath; // 이미지 경로
                ServantAscensionContact servantAscensionContact;

                switch (view.getId()) {
                    case R.id.first_ascention_button:
                        ascention_img.setImageResource(0); // 이미지 초기화
                        servantAscensionContact = servantAscensionImg.get(0); // contact
                        imgPath = servantAscensionContact.getAscensionImgName();
                        ascention_img.setImageResource(getResources().getIdentifier("@drawable/" + imgPath, "drawable", packName));
                        break;
                    case R.id.second_ascention_button:
                        ascention_img.setImageResource(0);
                        servantAscensionContact = servantAscensionImg.get(1); // contact
                        imgPath = servantAscensionContact.getAscensionImgName();
                        ascention_img.setImageResource(getResources().getIdentifier("@drawable/" + imgPath, "drawable", packName));
                        break;
                    case R.id.third_ascention_button:
                        ascention_img.setImageResource(0);
                        servantAscensionContact = servantAscensionImg.get(2); // contact
                        imgPath = servantAscensionContact.getAscensionImgName();
                        ascention_img.setImageResource(getResources().getIdentifier("@drawable/" + imgPath, "drawable", packName));
                        break;
                    case R.id.forth_ascention_button:
                        ascention_img.setImageResource(0);
                        servantAscensionContact = servantAscensionImg.get(3); // contact
                        imgPath = servantAscensionContact.getAscensionImgName();
                        ascention_img.setImageResource(getResources().getIdentifier("@drawable/" + imgPath, "drawable", packName));
                        break;
                }
            }
        };

        // 리스너 설정
        ascention_btn_1.setOnClickListener(onClickListener);
        ascention_btn_2.setOnClickListener(onClickListener);
        ascention_btn_3.setOnClickListener(onClickListener);
        ascention_btn_4.setOnClickListener(onClickListener);
    }
}




