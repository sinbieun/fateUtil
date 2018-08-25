package com.fate.user.fateutil.layout;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.db.DataBase;
import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.ExpContact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class LevelLayout extends LinearLayout {


    private  Button btnLevel = null;
    private  EditText currentLevel, targetLevel;
    private  LinearLayout currentLayout;
    private  TextView fourClass, fourNormal, threeClass, threeNormal;
    private  LayoutInflater li;
    private  AssetManager assetManager = getResources().getAssets();

    // DB 변수
    private DbOpenHelper mDbOpenHelper;


    public LevelLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
        currentLayout = (LinearLayout) li.inflate(R.layout.level_main, null);
        addView(currentLayout);


        // 1. id 찾기
        btnLevel = currentLayout.findViewById(R.id.btn_Level);
        currentLevel = currentLayout.findViewById(R.id.current_level);
        targetLevel = currentLayout.findViewById(R.id.target_level);
        fourClass = currentLayout.findViewById(R.id.four_class);
        fourNormal = currentLayout.findViewById(R.id.four_normal);
        threeClass = currentLayout.findViewById(R.id.three_class);
        threeNormal = currentLayout.findViewById(R.id.three_normal);

        mDbOpenHelper = new DbOpenHelper(currentLayout.getContext());

        // 2. 계산 버튼 클릭시 작동
        btnLevel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Calculate();
            }
        });

    }

    public void Calculate (){

        int currentExp = 0; // 현재의 경험치
        int targetExp = 0; // 목표의 경험치

        int currentLv = 0; // 현재의 레벨
        int targetLv = 0; // 목표의 레벨

        int sumExp; // 전체 경험치 - 1 한 값

        // 경험치 카드 경험치
        final int fourClassExp = 32400; // 4성 클래스
        final int fourNormalExp = 27000; // 4성 일반
        final int threeClassExp = 10800; // 3성 클래스
        final int threeNormalExp = 9000; // 3성 일반

        int exp [] = new int[4];
        exp[0] = fourClassExp;
        exp[1] = fourNormalExp;
        exp[2] = threeClassExp;
        exp[3] = threeNormalExp;

        // 4성 클래스, 4성 일반, 3성 클래스, 3성 일반 저장
        int result[] = new int [4];

        // 경험치 입력칸이 공백이면 계산하지 않음
        if((currentLevel.getText().toString().length() == 0) || (targetLevel.getText().toString().length() == 0)){
            Toast.makeText(getContext(),"다시 입력해주십시오", Toast.LENGTH_SHORT).show();
            return ;
        }
        // 공백이 아니면 경험치 입력 텍스트 값을 정수형으로 변환한다.
        else {
            currentLv = Integer.parseInt(currentLevel.getText().toString());
            targetLv = Integer.parseInt(targetLevel.getText().toString());
        }

        // 계산 부분
        // 목표 레벨이 현재 레벨보다 작거나 같을 때
        if (currentLv >= targetLv){
            Toast.makeText(getContext(),"목표레벨이 현재 레벨보다 작거나 같습니다.", Toast.LENGTH_SHORT).show();
        }
        // 입력된 레벨이 100보다 클때
        else if ((currentLv > 100) || (targetLv > 100)){
            Toast.makeText(getContext(),"최대 레벨은 100입니다", Toast.LENGTH_SHORT).show();
        }
        // DB에서 커서를 사용하여 데이터 값을 가져온다.
        else{
            mDbOpenHelper.open(); // db열기
            currentExp = mDbOpenHelper.getExpContact(currentLevel.getText().toString());  // 현재 경험치
            targetExp = mDbOpenHelper.getExpContact(targetLevel.getText().toString());  // 목표 경험치
            mDbOpenHelper.close();
        }


        // 가져온 경험치 값을 사용하여 계산한다.
        sumExp = targetExp - currentExp;
        for(int i = 0; i < exp.length; i++){
            result[i] = sumExp / exp[i];
        }

        // 계산 한 것을 각각의 테이블에 띄워준다.
        fourClass.setText(Integer.toString(result[0]));
        fourNormal.setText(Integer.toString(result[1]));
        threeClass.setText(Integer.toString(result[2]));
        threeNormal.setText(Integer.toString(result[3]));
    }

}