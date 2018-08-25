/*
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
    private List<SkillContact> skillHavingList; //  서번트가 소유하고 있는 스킬
    private List<SkillContact> skillValueList;
    private ArrayList<SkillContact> arrayList;

    // 1. 서번트 리스트와 Context를 받아온다.

    public SearchIntentAdapter(Context context, List<SkillContact> skillContactsList) {
        this.context = context;
        this.skillContactsList = skillContactsList;
        li = LayoutInflater.from(context);
        this.arrayList = new ArrayList<SkillContact>();
        this.arrayList.addAll(skillContactsList); // 스킬 리스트의 모든 데이터를 arraylist에 복사한다.
    }


    public SearchIntentAdapter(Context context, List<SkillContact> skillHavingList) {
        this.context = context;
        this.skillHavingList = skillHavingList;
        li = LayoutInflater.from(context);
        this.arrayList = new ArrayList<SkillContact>();
        this.arrayList.addAll(skillHavingList); // 스킬 리스트의 모든 데이터를 arraylist에 복사한다.
    }

    public SearchIntentAdapter(Context context, List<SkillContact> servantHavingSkillValue, String skillName) {
        this.context = context;
        this.skillValueList = servantHavingSkillValue;
        li = LayoutInflater.from(context);
    }


    public class SkillHavingViewHolder {
        public ImageView skillIcon;
        public TextView skillFullName;
        public TextView skillEffect;

        public TextView skillValue1;
        public TextView skillValue2;
        public TextView skillValue3;
        public TextView skillValue4;
        public TextView skillValue5;
        public TextView skillValue6;
        public TextView skillValue7;
        public TextView skillValue8;
        public TextView skillValue9;
        public TextView skillValue10;

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
        return skillHavingList.size();
    }

    // 스킬 리스트에서 위치에 따라 내용물을 가져온다.
    @Override
    public SkillContact getItem(int position) {
        return skillHavingList.get(position);
    }

    // 아이템 아이디를 가져온다.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 뷰 컨트롤
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final SkillHavingViewHolder skillViewHolder;
        final SkillContact skillData = skillHavingList.get(position);

        String packName = context.getPackageName();

        // 1. 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다.
        if (view == null) {

            skillViewHolder = new SkillHavingViewHolder();
            view = li.inflate(R.layout.search_intent_skill_list_item, null);

            // 1). 리스트 뷰를 만들때 사용 될 id를 가지고 온다.
            skillViewHolder.skillIcon = (ImageView) view.findViewById(R.id.skill_icon);
            skillViewHolder.skillFullName = (TextView) view.findViewById(R.id.skill_name);
            skillViewHolder.skillEffect = (TextView) view.findViewById(R.id.skill_effect);



            skillViewHolder.skillValue1 = (TextView)view.findViewById(R.id.skill_value_1);
            skillViewHolder.skillValue2 = (TextView)view.findViewById(R.id.skill_value_2);
            skillViewHolder.skillValue3 = (TextView)view.findViewById(R.id.skill_value_3);
            skillViewHolder.skillValue4 = (TextView)view.findViewById(R.id.skill_value_4);
            skillViewHolder.skillValue5 = (TextView)view.findViewById(R.id.skill_value_5);
            skillViewHolder.skillValue6 = (TextView)view.findViewById(R.id.skill_value_6);
            skillViewHolder.skillValue7 = (TextView)view.findViewById(R.id.skill_value_7);
            skillViewHolder.skillValue8 = (TextView)view.findViewById(R.id.skill_value_8);
            skillViewHolder.skillValue9 = (TextView)view.findViewById(R.id.skill_value_9);
            skillViewHolder.skillValue10 = (TextView)view.findViewById(R.id.skill_value_10);



            view.setTag(skillViewHolder);

        }
        // 2. 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다..
        else {
            skillViewHolder = (SkillHavingViewHolder) view.getTag();


        }

        skillViewHolder.skillIcon.setImageResource(context.getResources().getIdentifier("@drawable/" + skillData.getSkillIcon(), "drawable",packName));
        skillViewHolder.skillFullName.setText(skillData.getSkillFullName());
        skillViewHolder.skillEffect.setText(skillData.getSkillEffect());



        for(int i = 0; i < skillValueList.size(); i++){
            skillViewHolder.skillValue1.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue2.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue3.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue4.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue5.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue6.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue7.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue8.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue9.setText(skillData.getSkillNumber());
            skillViewHolder.skillValue10.setText(skillData.getSkillNumber());
        }



        return view;

    }


}

*/