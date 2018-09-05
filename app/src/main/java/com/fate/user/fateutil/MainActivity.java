package com.fate.user.fateutil;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.fate.user.fateutil.layout.LevelLayout;
import com.fate.user.fateutil.layout.NoticeLayout;
import com.fate.user.fateutil.layout.SearchLayout;
import com.fate.user.fateutil.layout.ServantLayout;

import java.io.File;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteDatabase sqliteDB;
    private RelativeLayout contentView;
    private Parser parser;

    // ALERT
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        // 타이틀 변경
        this.toolbarTitleChange(getString(R.string.toolbar_title_main));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        }

        return super.onOptionsItemSelected(item);
    }


    // 네비게이션 메뉴 설정
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notice) {
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
        } else if (id == R.id.nav_search) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            SearchLayout searchLayout = new SearchLayout(this);        // 1. Layout Setting
            searchLayout.init();                                                // 2. DB에서 서번트 항목 전체를 할당 받고 Adapter와 연결하여준다.
            contentView.addView(searchLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_search));
        } else if (id == R.id.nav_servant) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            ServantLayout servantLayout = new ServantLayout(this);
            contentView.addView(servantLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_servant));
        } else if (id == R.id.nav_level) {

            if (contentView != null) {
                contentView.removeAllViews();
            }

            LevelLayout levelLayout = new LevelLayout(this);
            contentView.addView(levelLayout);

            // 타이틀 변경
            this.toolbarTitleChange(getString(R.string.toolbar_title_exp));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 툴바 타이틀을 바꿔주는 함수
     *
     * @param title
     */
    public void toolbarTitleChange(String title) {
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
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
        parser.expParser();
        parser.servanJointListParser();
        parser.servantIconParser();
        parser.servantNameParser();
        parser.servantClassParser();

        parser.activeSkillParser();
        parser.servantJoinActiveSkillParser();
    }
}
