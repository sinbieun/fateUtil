package com.fate.user.fateutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.db.SkillContact;

import java.util.ArrayList;
import java.util.List;


// 리스트 어댑터
// 리스트에 데이터를 넣어준다.
public class SearchIntentAdapter extends BaseAdapter {

    Context context;
    LayoutInflater li;
    private List<SkillContact> skillContactsList; // 서번트 스킬 데이터를 받은 리스트 변수
    private ArrayList<SkillContact> arrayList;

    // 1. 서번트 리스트와 Context를 받아온다.
    public SearchIntentAdapter(Context context, List<SkillContact> skillContactsList) {
        this.context = context;
        this.skillContactsList = skillContactsList;
        li = LayoutInflater.from(context);
        this.arrayList = new ArrayList<SkillContact>();
        this.arrayList.addAll(skillContactsList); // 스킬 리스트의 모든 데이터를 arraylist에 복사한다.
    }



    public class SkillHavingViewHolder{
        public ImageView skillIcon;
        public TextView skillName;
    }
    // 뷰 홀더 변수
    public class SkillViewHolder {

        public TextView skillRank;
        public TextView skillClassification;
        public TextView skillLevel;
        public TextView skillTarget;
        public TextView skillRange;
        public TextView skillEffect;
        public TextView skillValue;
        public TextView skillMerit;
        public TextView skillDuration;
        public TextView skillCoolDown;
        public TextView skillPercent;
        public TextView skillEnhance;

    }

    // 리스트 사이즈 크기를 얻는다.
    @Override
    public int getCount() {
        return skillContactsList.size();
    }

    // 스킬 리스트에서 위치에 따라 내용물을 가져온다.
    @Override
    public SkillContact getItem(int position) {
        return skillContactsList.get(position);
    }

    // 아이템 아이디를 가져온다.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 뷰 컨트롤
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final SkillViewHolder viewHolder;
        String packName = context.getPackageName();

        // 1. 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다.
        if (view == null) {

           viewHolder = new SkillViewHolder();
           view = li.inflate(R.layout.search_intent_skill_list_item, null);

            // 1). 리스트 뷰를 만들때 사용 될 id를 가지고 온다.
           //viewHolder.skillIcon = (ImageView) view.findViewById(R.id.skill_icon);
           //viewHolder.skillName = (TextView) view.findViewById(R.id.skill_name);
           //viewHolder.skillLevel = (TextView) view.findViewById(R.id.skill_level_item);
           //viewHolder.skillValue = (TextView) view.findViewById(R.id.skill_effect_item);

            //view.setTag(viewHolder);

        }
        // 2. 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다..
        else {
            viewHolder = (SkillViewHolder) view.getTag();

        }

        // 위치를 잡는다.
        SkillContact skillContact = skillContactsList.get(position);

        // 뷰 홀더에 넣어준다.
        /*
        if(skillContact.getSkillIcon() != null){
            viewHolder.skillIcon.setVisibility(View.VISIBLE);
            viewHolder.skillIcon.setImageResource(context.getResources().getIdentifier("@drawable/" + skillContact.getSkillIcon(), "drawable",packName));
        } else{
            viewHolder.skillIcon.setVisibility(View.GONE);
        }
        */

        //viewHolder.skillIcon.setImageResource(context.getResources().getIdentifier("@drawable/" + skillContact.getSkillIcon(), "drawable",packName));
        //viewHolder.skillName.setText(skillContact.getSkillName());
        //viewHolder.skillLevel.setText(String.valueOf(skillContact.getSkillLevel()));
        //viewHolder.skillValue.setText(String.valueOf(skillContact.getSkillValue()));


        return view;

    }



}

