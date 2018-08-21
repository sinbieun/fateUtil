package com.fate.user.fateutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.model.NoticeModel;

import java.util.ArrayList;
import java.util.List;

// 리스트 어댑터
public class NoticeAdapter extends BaseAdapter {

    Context context;
    LayoutInflater li;
    private ArrayList<NoticeModel> noticeList; // 공지사항 데이터를 받은 리스트 변수
    private ArrayList<NoticeModel> arrayList;


    // 1. 공지사항 리스트와 Context를 받아온다.
    public NoticeAdapter(Context context, ArrayList<NoticeModel> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
        li = LayoutInflater.from(context);
        this.arrayList = new ArrayList<NoticeModel>();
        this.arrayList.addAll(noticeList); // 공지사항 리스트의 모든 데이터를 arraylist에 복사한다.
    }

    // 뷰 홀더 변수
    public class NoticeViewHolder {
        public TextView titleContent;
        public TextView regContent;
    }

    // 리스트 사이즈 크기를 얻는다.
    @Override
    public int getCount() {
        return noticeList.size();
    }

    // 공지사항 리스트에서 위치에 따라 내용물을 가져온다.
    @Override
    public NoticeModel getItem(int position) {
        return noticeList.get(position);
    }

    // 아이템 아이디를 가져온다.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 뷰 컨트롤
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final NoticeViewHolder viewHolder;
        final NoticeModel noticeData = noticeList.get(position);

        // 1. 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다.
        if (view == null) {

            viewHolder = new NoticeViewHolder();
            view = li.inflate(R.layout.notice_listview, null);

            // 1). 리스트 뷰를 만들때 사용 될 id를 가지고 온다.
            viewHolder.titleContent = (TextView) view.findViewById(R.id.title_content);
            viewHolder.regContent = (TextView) view.findViewById(R.id.reg_content);

            view.setTag(viewHolder);

        }
        // 2. 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다..
        else {
            viewHolder = (NoticeViewHolder) view.getTag();
        }

        // 뷰 홀더에 넣어준다.
        viewHolder.titleContent.setText("[" + noticeData.getNoticeKind() + "]" + " " + noticeData.getNoticeTitle());
        viewHolder.regContent.setText(noticeData.getNoticeRegUser() + " || " + noticeData.getNoticeRegDate());
        return view;

    }
}
