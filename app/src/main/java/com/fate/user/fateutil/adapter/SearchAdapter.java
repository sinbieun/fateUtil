package com.fate.user.fateutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fate.user.fateutil.db.contract.Servant.ServantContact;
import com.fate.user.fateutil.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// 리스트 어댑터
public class SearchAdapter extends BaseAdapter {

    private  Context context;
    private  LayoutInflater li;
    private List<ServantContact> servantList; // 서번트 데이터를 받은 리스트 변수
    private ArrayList<ServantContact> arrayList;


    // 1. 서번트 리스트와 Context를 받아온다.
    public SearchAdapter(Context context, List<ServantContact> servantList) {
        this.context = context;
        this.servantList = servantList;
        li = LayoutInflater.from(context);
        this.arrayList = new ArrayList<ServantContact>();
        this.arrayList.addAll(servantList); // 서번트 리스트의 모든 데이터를 arraylist에 복사한다.
    }

    // 뷰 홀더 변수
    public class ServantViewHolder {
        public ImageView servantIcon;
        public TextView servantName;

        public ImageView gradeStar1;
        public ImageView gradeStar2;
        public ImageView gradeStar3;
        public ImageView gradeStar4;
        public ImageView gradeStar5;

        public ImageView servantClass;
    }

    // 리스트 사이즈 크기를 얻는다.
    @Override
    public int getCount() {
        return servantList.size();
    }

    // 서번트 리스트에서 위치에 따라 내용물을 가져온다.
    @Override
    public ServantContact getItem(int position) {
        return servantList.get(position);
    }

    // 아이템 아이디를 가져온다.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 뷰 컨트롤
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ServantViewHolder viewHolder;
        final ServantContact servant = servantList.get(position);
        String packName = context.getPackageName();

        // 1. 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다. ( 캐시 삭제 2018-09-19 : 데이터가 중복하여 호출 됨 )
        viewHolder = new ServantViewHolder();
        view = li.inflate(R.layout.search_listview, null);

        // 1). 리스트 뷰를 만들때 사용 될 id를 가지고 온다.
        viewHolder.servantIcon = (ImageView) view.findViewById(R.id.servant_icon);
        viewHolder.servantName = (TextView) view.findViewById(R.id.servant_name);

        viewHolder.gradeStar1 = (ImageView) view.findViewById(R.id.grade_star_1);
        viewHolder.gradeStar2 = (ImageView) view.findViewById(R.id.grade_star_2);
        viewHolder.gradeStar3 = (ImageView) view.findViewById(R.id.grade_star_3);
        viewHolder.gradeStar4 = (ImageView) view.findViewById(R.id.grade_star_4);
        viewHolder.gradeStar5 = (ImageView) view.findViewById(R.id.grade_star_5);

        viewHolder.servantClass = (ImageView) view.findViewById(R.id.class_icon);

        view.setTag(viewHolder);

        // 뷰 홀더에 넣어준다.
        viewHolder.servantIcon.setImageResource(context.getResources().getIdentifier("@drawable/" + servant.getServantIcon(), "drawable",packName));
        viewHolder.servantName.setText(servant.getServantName());

        // 그리드의 숫자에 맞춰서 별 이미지를 넣어줌
        int servantGrade = servant.getServantGrade();
        switch (servantGrade) {
            case 1 :
                viewHolder.gradeStar1.setVisibility(View.VISIBLE);
                break;
            case 2 :
                viewHolder.gradeStar1.setVisibility(View.VISIBLE);
                viewHolder.gradeStar2.setVisibility(View.VISIBLE);
                break;
            case 3 :
                viewHolder.gradeStar1.setVisibility(View.VISIBLE);
                viewHolder.gradeStar2.setVisibility(View.VISIBLE);
                viewHolder.gradeStar3.setVisibility(View.VISIBLE);
                break;
            case 4 :
                viewHolder.gradeStar1.setVisibility(View.VISIBLE);
                viewHolder.gradeStar2.setVisibility(View.VISIBLE);
                viewHolder.gradeStar3.setVisibility(View.VISIBLE);
                viewHolder.gradeStar4.setVisibility(View.VISIBLE);
                break;
            case 5 :
                viewHolder.gradeStar1.setVisibility(View.VISIBLE);
                viewHolder.gradeStar2.setVisibility(View.VISIBLE);
                viewHolder.gradeStar3.setVisibility(View.VISIBLE);
                viewHolder.gradeStar4.setVisibility(View.VISIBLE);
                viewHolder.gradeStar5.setVisibility(View.VISIBLE);
                break;
        }

        // 클래스에 맞춰서 이미지를 넣어줌
        String servantClass = servant.getServantClass();
        switch (servantClass){
            case "세이버" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_saber", "drawable",packName));
                break;
            case "랜서" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_lancer", "drawable",packName));
                break;
            case "아처" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_archer", "drawable",packName));
                break;
            case "라이더" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_rider", "drawable",packName));
                break;
            case "캐스터" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_caster", "drawable",packName));
                break;
            case "어쌔신" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_assassin", "drawable",packName));
                break;
            case "버서커" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_berserker", "drawable",packName));
                break;
            case "실더" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_shielder", "drawable",packName));
                break;
            case "룰러" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_ruler", "drawable",packName));
                break;
            case "어벤저" :
                viewHolder.servantClass.setImageResource(context.getResources().getIdentifier("@drawable/icon_class_avenger", "drawable",packName));
                break;
        }


        return view;


    }



    // 검색 필터 클래스
    public void Filter(String charText) {

        // 1. 펄터 클래스에 검색어 입력된다.
        // 2. 검색 필터에 저장된 서번트 리스트들을 모두 지워준다.
        charText = charText.toLowerCase(Locale.getDefault());
        servantList.clear();

        // 3. 문자 입력이 없을 때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            servantList.addAll(arrayList);
        }

        // 4. 문자가 입력 되었을 때
        // 1) 리스트의 모든 데이터 검색
        // 2) 리스트에서 가져온 이름과 에디트 텍스트에 입력된 이름이 같으면 servant 리스트에 서번트 리스트를 집어 넣는다.
        else {
            for (ServantContact servant : arrayList) {
                if (servant.getServantName().toLowerCase().contains(charText)) {
                    servantList.add(servant);
                }
            }
        }
        // 5. 리스트 데이터가 변경 되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        notifyDataSetChanged();
    }

}

