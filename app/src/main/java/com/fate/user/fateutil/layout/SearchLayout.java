package com.fate.user.fateutil.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fate.user.fateutil.R;

public class SearchLayout extends LinearLayout {
    // 서번트 검색 레이아웃
    public SearchLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context.getSystemService(infService);

        LinearLayout currentLayout = (LinearLayout) li.inflate(R.layout.search_main, null);
        addView(currentLayout);
    }
}
