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

    private RelativeLayout currentLayout;
    private LayoutInflater li = null;

    private ListView listView = null; // 리스트 뷰
    private NoticeAdapter noticeAdapter;
    private AssetManager assetManager = getResources().getAssets();

    public HomeLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
        currentLayout = (RelativeLayout) li.inflate(R.layout.home_main, null);
        addView(currentLayout);

        // 월 ~ 일

    }

    public void init(){
    }
}