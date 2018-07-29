package com.fate.user.fateutil.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fate.user.fateutil.db.ServantContact;
import com.fate.user.fateutil.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchAdapter extends BaseAdapter{

    Context context;
    LayoutInflater li;
    private List<ServantContact> servantList; // 서번트 데이터를 받은 리스트 변수
    private ArrayList<ServantContact> arrayList;

    public SearchAdapter(Context context, List<ServantContact> servantList){
        this.context = context;
        this.servantList = servantList;
        li = LayoutInflater.from(context);
        // this.li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = new ArrayList<ServantContact>();
        this.arrayList.addAll(servantList); // 서번트 리스트의 모든 데이터를 arraylist에 복사한다.
    }

    public class ServantViewHolder
    {
        public ImageView servantIcon;
        public TextView servantName;
        public TextView servantClass;
        public TextView servantGrade;
    }

    @Override
    public int getCount() {
        return servantList.size();
    }

    @Override
    public ServantContact getItem(int position) {
        return servantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ServantViewHolder viewHolder;
        final ServantContact servant = servantList.get(position);

        // 1. 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다.
        if(view == null){

            viewHolder = new ServantViewHolder();
            view = li.inflate(R.layout.search_listview, null);


            // viewHolder.servantIcon = (ImageView) view.findViewById(R.id.servant_icon);
            viewHolder.servantName = (TextView)view.findViewById(R.id.servant_name);
            viewHolder.servantClass = (TextView)view.findViewById(R.id.servant_class);
            viewHolder.servantGrade = (TextView)view.findViewById(R.id.servant_grade);

            view.setTag(viewHolder);

        }
        // 2. 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다..
        else{
            viewHolder = (ServantViewHolder)view.getTag();
        }
        
        // viewHolder.servantName.setText(servantList.get(position).getId());
        viewHolder.servantName.setText(servant.getServantName());
        viewHolder.servantClass.setText(servant.getServantClass());
        viewHolder.servantGrade.setText(String.valueOf(servant.getServantGrade()));

        servant.getServantClass();
        return view;
    }

    // 검색 필터 클래스
    public void Filter(String charText){
        // 1. 펄터 클래스에 검색어 입력된다.
        // 2. 검색 필터에 저장된 서번트 리스트들을 모두 지워준다.
        charText = charText.toLowerCase(Locale.getDefault());
        servantList.clear();

        // 3. 문자 입력이 없을 때는 모든 데이터를 보여준다.
        if(charText.length() == 0){
            servantList.addAll(arrayList);
        }

        // 4. 문자가 입력 되었을 때
        // 1) 리스트의 모든 데이터 검색
        // 2) 리스트에서 가져온 이름과 에디트 텍스트에 입력된 이름이 같으면 servant 리스트에 서번트 리스트를 집어 넣는다.
        else{
            for (ServantContact servant : arrayList){
                if(servant.getServantName().toLowerCase().contains(charText)){
                    servantList.add(servant);
                }
            }
        }
        // 5. 리스트 데이터가 변경 되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        notifyDataSetChanged();
    }
}
