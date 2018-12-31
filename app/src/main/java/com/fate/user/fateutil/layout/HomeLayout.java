package com.fate.user.fateutil.layout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fate.user.fateutil.MainActivity;
import com.fate.user.fateutil.R;
import com.fate.user.fateutil.adapter.NoticeAdapter;
import com.fate.user.fateutil.layout.detail.NoticeIntent;
import com.fate.user.fateutil.model.NoticeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeLayout extends LinearLayout {

    public MainActivity mainActivity;
    private RelativeLayout currentLayout;
    private LayoutInflater li = null;

    private ListView listView = null; // 리스트 뷰
    private NoticeAdapter noticeAdapter;
    private AssetManager assetManager = getResources().getAssets();

    private TextView mainMenu1;
    private TextView mainMenu2;
    private TextView mainMenu3;

    public HomeLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
    }

    public void init(){
        currentLayout = (RelativeLayout) li.inflate(R.layout.home_main, null);
        addView(currentLayout);

        setView();
        setEvent();
    }

    public void setView(){
        mainMenu1 = (TextView) findViewById(R.id.main_menu_1);
        mainMenu2 = (TextView) findViewById(R.id.main_menu_2);
        mainMenu3 = (TextView) findViewById(R.id.main_menu_3);
    }

    public void setEvent(){
        mainMenu1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.navigationView.getMenu().getItem(1).setChecked(true);
                mainActivity.moveMenuForLayout("notice");
            }
        });

        mainMenu2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.navigationView.getMenu().getItem(2).setChecked(true);
                mainActivity.moveMenuForLayout("servant");
            }
        });

        mainMenu3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.navigationView.getMenu().getItem(4).setChecked(true);
                mainActivity.moveMenuForLayout("exp");
            }
        });
    }
}