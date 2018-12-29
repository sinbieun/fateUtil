package com.fate.user.fateutil;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fate.user.fateutil.db.Parser;
import com.fate.user.fateutil.layout.HomeLayout;
import com.fate.user.fateutil.layout.LevelLayout;
import com.fate.user.fateutil.layout.MagicLayout;
import com.fate.user.fateutil.layout.NoticeLayout;
import com.fate.user.fateutil.layout.SearchLayout;
import com.fate.user.fateutil.layout.ServantLayout;

import java.io.File;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteDatabase sqliteDB;
    private RelativeLayout contentView;
    private Parser parser;

    // VIEW
    public NavigationView navigationView;

    // ALERT
    private long backKeyPressedTime = 0;
    private Toast toast;

    // layout parameter
    private String whereCurrentLayout = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        // 레이아웃 관리
        contentView = findViewById(R.id.content_view);

        InputData();

        // 데이터 삽입 관리
        // 1. 경험치 테이블

        //parser.skillParser(); // 스킬 테이블 삽입
        //parser.servantParser(); // 서번트 데이터 삽입
        //parser.servantNameParser(); // 서번트 이름 삽입
        // parser.servantJoinSkillParser(); // 조인 테이블 삽입

        // 맨처음 변경점
        firstLayout();
    }

    /**
     * 첫번째 레이아웃
     */
    public void firstLayout(){
        // 저장된 레이아웃이 있다면 뷰 삭제
        if (contentView != null) {
            contentView.removeAllViews();
        }

        // 저장된 레이아웃이 없다면 뷰를 띄워준다.
        HomeLayout homeLayout = new HomeLayout(this);
        homeLayout.mainActivity = this;
        contentView.addView(homeLayout);

        // 공지사항 레이아웃 그려주기
        homeLayout.init();

        // 타이틀 변경
        this.toolbarTitleChange(getString(R.string.toolbar_title_main));
        whereCurrentLayout = "home";
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if("home".equals(whereCurrentLayout)) {
            if(drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(Gravity.START);
                return;
            }

            if(System.currentTimeMillis() > (backKeyPressedTime + 2000)) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }

            if(System.currentTimeMillis() <= (backKeyPressedTime + 2000)) {
                finish();
                toast.cancel();
            }
        }else{
            // 저장된 레이아웃이 있다면 뷰 삭제
            if (contentView != null) {
                contentView.removeAllViews();
            }

            // 저장된 레이아웃이 없다면 뷰를 띄워준다.
            HomeLayout homeLayout = new HomeLayout(this);
            homeLayout.mainActivity = this;
            contentView.addView(homeLayout);

            // 공지사항 레이아웃 그려주기
            homeLayout.init();

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_main));
            whereCurrentLayout = "home";
        }
    }

    /**
     * 앱 종료 시, 2번 클릭하기 전에 ALERT 창 보여줌.
     */
    public void showGuide() {
        toast = Toast.makeText(this, "앱 종료를 하시려면 '뒤로 가기'를 한번 더 눌러주십시오.", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_history) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // 네비게이션 메뉴 설정
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // 저장된 레이아웃이 있다면 뷰 삭제
            if (contentView != null) {
                contentView.removeAllViews();
            }

            // 저장된 레이아웃이 없다면 뷰를 띄워준다.
            HomeLayout homeLayout = new HomeLayout(this);
            homeLayout.mainActivity = this;
            contentView.addView(homeLayout);

            // 공지사항 레이아웃 그려주기
            homeLayout.init();

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_main));
            whereCurrentLayout = "home";
        }else if (id == R.id.nav_notice) {
            // 저장된 레이아웃이 있다면 뷰 삭제
            if (contentView != null) {
                contentView.removeAllViews();
            }

            // 저장된 레이아웃이 없다면 뷰를 띄워준다.
            NoticeLayout noticeLayout = new NoticeLayout(this);
            contentView.addView(noticeLayout);

            // 공지사항 레이아웃 그려주기
            noticeLayout.init();

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_notice));
            whereCurrentLayout = "notice";
        } else if (id == R.id.nav_search) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            SearchLayout searchLayout = new SearchLayout(this);        // 1. Layout Setting
            searchLayout.init();                                                // 2. DB에서 서번트 항목 전체를 할당 받고 Adapter와 연결하여준다.
            contentView.addView(searchLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_search));
            whereCurrentLayout = "servant";
        } else if (id == R.id.nav_servant) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            ServantLayout servantLayout = new ServantLayout(this);
            contentView.addView(servantLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_servant));
            whereCurrentLayout = "bookmark";
        } else if (id == R.id.nav_level) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            LevelLayout levelLayout = new LevelLayout(this);
            contentView.addView(levelLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_exp));
            whereCurrentLayout = "exp";
        } else if (id == R.id.nav_magic) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            MagicLayout magicLayout = new MagicLayout(this);
            contentView.addView(magicLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_magic_exp));
            whereCurrentLayout = "magic";
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 타 레이아웃에서 다른 레이아웃으로 이동
     * @param gubun
     */
    public void moveMenuForLayout(String gubun){
        if (gubun.equals("home")) {
            // 저장된 레이아웃이 있다면 뷰 삭제
            if (contentView != null) {
                contentView.removeAllViews();
            }

            // 저장된 레이아웃이 없다면 뷰를 띄워준다.
            HomeLayout homeLayout = new HomeLayout(this);
            homeLayout.mainActivity = this;
            contentView.addView(homeLayout);

            // 공지사항 레이아웃 그려주기
            homeLayout.init();

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_main));
            whereCurrentLayout = "home";
        }else if (gubun.equals("notice")) {
            // 저장된 레이아웃이 있다면 뷰 삭제
            if (contentView != null) {
                contentView.removeAllViews();
            }

            // 저장된 레이아웃이 없다면 뷰를 띄워준다.
            NoticeLayout noticeLayout = new NoticeLayout(this);
            contentView.addView(noticeLayout);

            // 공지사항 레이아웃 그려주기
            noticeLayout.init();

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_notice));
            whereCurrentLayout = "notice";
        } else if (gubun.equals("servant")) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            SearchLayout searchLayout = new SearchLayout(this);        // 1. Layout Setting
            searchLayout.init();                                                // 2. DB에서 서번트 항목 전체를 할당 받고 Adapter와 연결하여준다.
            contentView.addView(searchLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_search));
            whereCurrentLayout = "servant";
        } else if (gubun.equals("bookmark")) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            ServantLayout servantLayout = new ServantLayout(this);
            contentView.addView(servantLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_servant));
            whereCurrentLayout = "bookmark";
        } else if (gubun.equals("exp")) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            LevelLayout levelLayout = new LevelLayout(this);
            contentView.addView(levelLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_exp));
            whereCurrentLayout = "exp";
        }
    }

    /**
     * 툴바 타이틀을 바꿔주는 함수
     *
     * @param title
     */
    public void toolbarTitleChange(String title) {
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title);
    }

    private SQLiteDatabase init_database() {
        SQLiteDatabase db = null;
        File file = new File(getFilesDir(), "fatedb.db");
        try {
            db = SQLiteDatabase.openOrCreateDatabase(file, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (db == null) {
            System.out.println("DB creation failed." + file.getAbsolutePath());
        }

        return db;
    }

    public void InputData() {
        parser = new Parser(this);
        parser.servantExpParser();
        parser.servantJointListParser();
        parser.servantIconParser();
        parser.servantNameParser();
        parser.servantClassParser();

        parser.activeSkillParser();
        parser.passiveSkillParser();
        parser.servantJoinSkillParser();

        parser.servantJoinMaterialParser();
        parser.servantMaterialParser();

        parser.servantAscensionImgParser();
    }
}
