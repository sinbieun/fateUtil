package com.fate.user.fateutil.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fate.user.fateutil.R;

public class NoticeLayout extends LinearLayout {

    LinearLayout currentLayout;
    LayoutInflater li = null;

    public NoticeLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
        currentLayout = (LinearLayout) li.inflate(R.layout.notice_main, null);
        addView(currentLayout);
    }
}
