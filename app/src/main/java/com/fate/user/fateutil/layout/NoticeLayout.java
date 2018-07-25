package com.fate.user.fateutil.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fate.user.fateutil.R;

public class NoticeLayout extends LinearLayout {
    // 서번트 공지사항 레이아웃
    public NoticeLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context.getSystemService(infService);

        LinearLayout currentLayout = (LinearLayout) li.inflate(R.layout.notice_main, null);
        addView(currentLayout);
    }
}
