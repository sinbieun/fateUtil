package com.fate.user.fateutil.layout.detail;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fate.user.fateutil.R;

import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.model.NoticeModel;


import java.util.ArrayList;


public class NoticeIntent extends AppCompatActivity{

    private DbOpenHelper mDbOpenHelper;
    private GridView gridView;

    private int position;
    private ArrayList<NoticeModel> noticeArray = new ArrayList<NoticeModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면 설정
        setContentView(R.layout.notice_intent);

        // 데이터 설정
        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("POSITION");
        noticeArray = (ArrayList<NoticeModel>) getIntent().getSerializableExtra("DATA");

        // TOOL BAR 세팅
        // TOOL BAR 배경 화면
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.img_navigation_bar));

        // TOOL BAR TITLE 제거 ( 폰트가 먹지 않음 )
        getSupportActionBar().setTitle("");

        // TOOL BAR TITLE 대신 들어갈 TextView 세팅
        TextView titleView = new TextView(this);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        titleView.setTextColor(Color.WHITE);
        titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);
        titleView.setText(getString(R.string.toolbar_title_notice));

        // TOOL BAR 설정 및 TextView 삽입
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(titleView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
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
        NoticeModel noticeModel = noticeArray.get(position);

        TextView noticeKind = (TextView) findViewById(R.id.noticeKind);
        TextView noticeTitle = (TextView) findViewById(R.id.noticeTitle);
        TextView noticeContent = (TextView) findViewById(R.id.noticeContent);
        TextView noticeReguser = (TextView) findViewById(R.id.noticeReguser);
        TextView noticeRegdate = (TextView) findViewById(R.id.noticeRegdate);

        noticeKind.setText(noticeModel.getNoticeKind());
        noticeTitle.setText(noticeModel.getNoticeTitle());
        noticeContent.setText(noticeModel.getNoticeContent());
        noticeReguser.setText(noticeModel.getNoticeRegUser());
        noticeRegdate.setText(noticeModel.getNoticeRegDate());

    }


}
