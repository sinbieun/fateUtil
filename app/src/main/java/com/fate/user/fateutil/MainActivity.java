package com.fate.user.fateutil;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fate.user.fateutil.layout.LevelLayout;
import com.fate.user.fateutil.layout.NoticeLayout;
import com.fate.user.fateutil.layout.SearchLayout;
import com.fate.user.fateutil.layout.ServantLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ConstraintLayout drawLayout = null;
    private String[] listMenu = {"List1", "List2", "List3"};

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

        drawLayout = findViewById(R.id.draw_layout);

        // 리스트 뷰 어댑터 설정
        //ArrayAdapter adapter = new ArrayAdapter(this, R.layout.search_main, listMenu);

        // 리스트 뷰 적용
        //ListView listView = (ListView)findViewById(R.id.listview1);
        // listView.setAdapter(adapter);

        // 검색창에서 텍스트 얻어옴
        /*
        EditText editText1 = (EditText)findViewById(R.id.search_editText1);
        Button button = (Button)findViewById(R.id.search_Button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  // String strText = editText1.getText().toString();
            }
        });

        */
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            // Handle the camera action
            if(drawLayout != null){
                drawLayout.removeAllViews();
            }
            NoticeLayout noticeLayout = new NoticeLayout(this);
            drawLayout.addView(noticeLayout);

        }
        else if (id == R.id.nav_search) {

            if(drawLayout != null){
                drawLayout.removeAllViews();
            }

            SearchLayout searchLayout = new SearchLayout(this);
            drawLayout.addView(searchLayout);
        }

        else if (id == R.id.nav_servant) {

            if(drawLayout != null){
                drawLayout.removeAllViews();
            }

            ServantLayout servantLayout = new ServantLayout(this);
            drawLayout.addView(servantLayout);
        }

        else if (id == R.id.nav_level) {

            if(drawLayout != null){
                drawLayout.removeAllViews();
            }

            LevelLayout levelLayout = new LevelLayout(this);
            drawLayout.addView(levelLayout);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
