package com.fate.user.fateutil.layout;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fate.user.fateutil.MainActivity;
import com.fate.user.fateutil.R;
import com.fate.user.fateutil.db.DbOpenHelper;
import com.fate.user.fateutil.db.contact.Magic.MagicContact;
import com.fate.user.fateutil.db.contact.Magic.MagicExpContact;
import com.fate.user.fateutil.model.MagicEffectForFirstModel;
import com.fate.user.fateutil.model.MagicEffectForSecondModel;

import org.w3c.dom.Text;

import java.util.List;

public class MagicLayout extends LinearLayout {

    private RelativeLayout currentLayout;
    private LayoutInflater li;
    private AssetManager assetManager = getResources().getAssets();
    public MainActivity mainActivity;

    // DB 변수
    private DbOpenHelper mDbOpenHelper;

    // 마술 예장 데이터
    private ArrayAdapter<CharSequence> magicSpinnerList;

    private TextView tab1Button;
    private TextView tab2Button;
    private TextView tab3Button;

    private LinearLayout tab1Layout;
    private LinearLayout tab2Layout;
    private LinearLayout tab3Layout;

    // 1탭 요소
    private ImageView magicImageView;
    private TextView magicContentView;

    // 2탭 요소
    private LinearLayout effectGridSecondMenuLayout;
    private GridLayout effectGridLayoutSecond;

    private LinearLayout effectGridFirstLayout1;
    private ImageView effectGridFirstImage1;
    private TextView effectGridFirstText1;
    private LinearLayout effectGridFirstLayout2;
    private ImageView effectGridFirstImage2;
    private TextView effectGridFirstText2;
    private LinearLayout effectGridFirstLayout3;
    private ImageView effectGridFirstImage3;
    private TextView effectGridFirstText3;

    private TextView effectContent;

    private List<MagicEffectForFirstModel> magicEffectForFirstModelList;    // 현재 선택된 마술예장의 효과 목록

    // 3탭 요소
    private LinearLayout expMenuLayout;
    private GridLayout expGridLayout;

    // 파라미터 세팅
    private int selectMagicId = 1;
    private String selectedMenu = "";

    public MagicLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
    }

    /**
     * 시작 시, 세팅
     */
    public void init(){
        currentLayout = (RelativeLayout) li.inflate(R.layout.magic_main, null);
        addView(currentLayout);

        // DB Setting
        mDbOpenHelper = new DbOpenHelper(currentLayout.getContext());

        // first data setting
        selectMagicId = 1;
        selectedMenu = "설명";

        setViewData();
    }

    /**
     * 외부에서 데이터 세팅
     */
    public void setData(SQLiteDatabase db){
    }

    /**
     * 데이터 세팅
     */
    private void setViewData(){
       // List<MagicContact> magicList =  mDbOpenHelper.getMagicList();

        Spinner weaponSpinner = (Spinner) findViewById(R.id.magic_spinner);
        magicSpinnerList = ArrayAdapter.createFromResource(getContext(), R.array.magicArray, R.layout.spinner_style_1);
        weaponSpinner.setAdapter(magicSpinnerList);
        weaponSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectMagicId = selectMagic(magicSpinnerList.getItem(position).toString());
                drawGridLayout();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 탭 세팅
        tab1Button = (TextView) findViewById(R.id.tab_1);
        tab1Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevSelectedMenu = selectedMenu;
                selectedMenu = "설명";
                if(!prevSelectedMenu.equals(selectedMenu)) {
                    drawGridLayout();

                    tab1Button.setBackgroundResource(R.drawable.tab_background_gradient_selected);
                    tab2Button.setBackgroundResource(R.drawable.tab_background_gradient);
                    tab3Button.setBackgroundResource(R.drawable.tab_background_gradient);

                    tab1Button.setTextColor(Color.parseColor("#FFFFFF"));
                    tab2Button.setTextColor(Color.parseColor("#AAAAAA"));
                    tab3Button.setTextColor(Color.parseColor("#AAAAAA"));
                }
            }
        });

        tab2Button = (TextView) findViewById(R.id.tab_2);
        tab2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevSelectedMenu = selectedMenu;
                selectedMenu = "효과";
                if(!prevSelectedMenu.equals(selectedMenu)) {
                    drawGridLayout();

                    tab1Button.setBackgroundResource(R.drawable.tab_background_gradient);
                    tab2Button.setBackgroundResource(R.drawable.tab_background_gradient_selected);
                    tab3Button.setBackgroundResource(R.drawable.tab_background_gradient);

                    tab1Button.setTextColor(Color.parseColor("#AAAAAA"));
                    tab2Button.setTextColor(Color.parseColor("#FFFFFF"));
                    tab3Button.setTextColor(Color.parseColor("#AAAAAA"));
                }
            }
        });

        tab3Button = (TextView) findViewById(R.id.tab_3);
        tab3Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevSelectedMenu = selectedMenu;
                selectedMenu = "경험치";
                if(!prevSelectedMenu.equals(selectedMenu)) {
                    drawGridLayout();

                    tab1Button.setBackgroundResource(R.drawable.tab_background_gradient);
                    tab2Button.setBackgroundResource(R.drawable.tab_background_gradient);
                    tab3Button.setBackgroundResource(R.drawable.tab_background_gradient_selected);

                    tab1Button.setTextColor(Color.parseColor("#AAAAAA"));
                    tab2Button.setTextColor(Color.parseColor("#AAAAAA"));
                    tab3Button.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

        // 탭 세팅
        tab1Layout = (LinearLayout) findViewById(R.id.tab_layout_1);
        tab2Layout = (LinearLayout) findViewById(R.id.tab_layout_2);
        tab3Layout = (LinearLayout) findViewById(R.id.tab_layout_3);

        // 1탭 요소
        magicImageView = (ImageView) findViewById(R.id.magic_image);
        magicContentView = (TextView) findViewById(R.id.magic_content);

        // 2탭 요소
        effectGridFirstLayout1 = (LinearLayout) findViewById(R.id.effect_grid_first_layout_1);
        effectGridFirstImage1 = (ImageView) findViewById(R.id.effect_grid_first_image_1);
        effectGridFirstText1 = (TextView) findViewById(R.id.effect_grid_first_text_1);
        effectGridFirstLayout2 = (LinearLayout) findViewById(R.id.effect_grid_first_layout_2);
        effectGridFirstImage2 = (ImageView) findViewById(R.id.effect_grid_first_image_2);
        effectGridFirstText2 = (TextView) findViewById(R.id.effect_grid_first_text_2);
        effectGridFirstLayout3 = (LinearLayout) findViewById(R.id.effect_grid_first_layout_3);
        effectGridFirstImage3 = (ImageView) findViewById(R.id.effect_grid_first_image_3);
        effectGridFirstText3 = (TextView) findViewById(R.id.effect_grid_first_text_3);

        effectContent = (TextView) findViewById(R.id.effect_content);

        effectGridSecondMenuLayout = (LinearLayout) findViewById(R.id.effect_grid_second_menu_layout);
        effectGridLayoutSecond = (GridLayout) findViewById(R.id.effect_grid_second_layout);

        // 3탭 요소
        expMenuLayout = (LinearLayout) findViewById(R.id.exp_menu_layout);
        expGridLayout = (GridLayout) findViewById(R.id.exp_grid_layout);
    }

    /**
     * 화면 뿌려주기
     */
    private void drawGridLayout(){

        int allSizeWidthWithMargin = mainActivity.deviceWidth - 20;

        if(selectedMenu != null){
            mDbOpenHelper.open();
            if(selectedMenu.equals("설명")){
                // VIEW SHOW & HIDE
                tab1Layout.setVisibility(View.VISIBLE);
                tab2Layout.setVisibility(View.GONE);
                tab3Layout.setVisibility(View.GONE);

                // 탭1 기능 시작
                MagicContact getMagic = mDbOpenHelper.getMagic(selectMagicId);
                magicImageView.setImageResource(getContext().getResources().getIdentifier("@drawable/" + getMagic.getMagicImage(), "drawable",getContext().getPackageName()));
                magicContentView.setText(getMagic.getMagicContent());
            }else if(selectedMenu.equals("효과")){
                // VIEW SHOW & HIDE
                tab1Layout.setVisibility(View.GONE);
                tab2Layout.setVisibility(View.VISIBLE);
                tab3Layout.setVisibility(View.GONE);

                // 데이터 호출
                // 해당 마술예장에 속해있는 리스트 가져오기
                magicEffectForFirstModelList = mDbOpenHelper.getMagicEffectListForFirst(selectMagicId);

                // 탭2 기능 시작
                //effectGridFirstLayout1 = (LinearLayout) findViewById(R.id.effect_grid_first_layout_1);
                //effectGridFirstLayout2 = (LinearLayout) findViewById(R.id.effect_grid_first_layout_2);
                //effectGridFirstLayout3 = (LinearLayout) findViewById(R.id.effect_grid_first_layout_3);

                // 첫번째 스킬
                effectGridFirstImage1.setImageResource(getContext().getResources().getIdentifier("@drawable/" + magicEffectForFirstModelList.get(0).getMagicEffectImage(), "drawable",getContext().getPackageName()));
                effectGridFirstText1.setText(magicEffectForFirstModelList.get(0).getMagicEffectName() + "\n" + magicEffectForFirstModelList.get(0).getMagicEffectGoal());
                effectGridFirstLayout1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectContent.setText(magicEffectForFirstModelList.get(0).getMagicEffectContent());
                        drawEffectWithEffectId(selectMagicId, magicEffectForFirstModelList.get(0).getMagicEffectName());
                    }
                });

                // 두번째 스킬
                effectGridFirstImage2.setImageResource(getContext().getResources().getIdentifier("@drawable/" + magicEffectForFirstModelList.get(1).getMagicEffectImage(), "drawable",getContext().getPackageName()));
                effectGridFirstText2.setText(magicEffectForFirstModelList.get(1).getMagicEffectName() + "\n" + magicEffectForFirstModelList.get(1).getMagicEffectGoal());
                effectGridFirstLayout2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectContent.setText(magicEffectForFirstModelList.get(1).getMagicEffectContent());
                        drawEffectWithEffectId(selectMagicId, magicEffectForFirstModelList.get(1).getMagicEffectName());
                    }
                });

                // 세번째 스킬
                effectGridFirstImage3.setImageResource(getContext().getResources().getIdentifier("@drawable/" + magicEffectForFirstModelList.get(2).getMagicEffectImage(), "drawable",getContext().getPackageName()));
                effectGridFirstText3.setText(magicEffectForFirstModelList.get(2).getMagicEffectName() + "\n" + magicEffectForFirstModelList.get(2).getMagicEffectGoal());
                effectGridFirstLayout3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        effectContent.setText(magicEffectForFirstModelList.get(2).getMagicEffectContent());
                        drawEffectWithEffectId(selectMagicId, magicEffectForFirstModelList.get(2).getMagicEffectName());
                    }
                });

                // 해당 마술예장에 속해있는 리스트 가져오기
                // 그 리스트 클릭 시, 옆에 레벨 보여주기
            }else if(selectedMenu.equals("경험치")){
                // VIEW SHOW & HIDE
                tab1Layout.setVisibility(View.GONE);
                tab2Layout.setVisibility(View.GONE);
                tab3Layout.setVisibility(View.VISIBLE);

                // 표 초기화
                expGridLayout.removeAllViews();

                // 탭3 기능 시작
                int columnWidth = (int) ((float)( allSizeWidthWithMargin ) / 3 );
                int lastColumnWidth = allSizeWidthWithMargin - ( columnWidth * 2 );

                drawExpGridLayoutData("레벨", columnWidth,30,25, "M");
                drawExpGridLayoutData("경험치", columnWidth,30,25, "M");
                drawExpGridLayoutData("누적 경험치", lastColumnWidth,30,25, "M");

                // 데이터 호출
                List<MagicExpContact> magicExpList = mDbOpenHelper.getMagicExpList(selectMagicId);

                for (int expIndex = 0 ; expIndex < magicExpList.size() ; expIndex++){
                    // 레벨
                    drawExpGridLayoutData(String.valueOf(magicExpList.get(expIndex).getMagicExpLevel()), columnWidth, 30, 15, "C");

                    // 경험치
                    drawExpGridLayoutData(String.valueOf(magicExpList.get(expIndex).getMagicExpCount()), columnWidth, 30, 15, "C");

                    // 누적 경험치
                    drawExpGridLayoutData(String.valueOf(magicExpList.get(expIndex).getMagicExpTotal()), lastColumnWidth, 30, 15, "C");
                }
            }   // 구분
            mDbOpenHelper.close();
        }   // null check
    }

    /**
     * 선택된 마술예장의 아이디 반환
     * @param selectMagicName
     * @return
     */
    private int selectMagic(String selectMagicName){
        int returnId = 1;
        if(selectMagicName != null){
            switch (selectMagicName){
                case "마술예장 칼데아" :
                    returnId = 1;
                    break;
                case "마술협회 제복" :
                    returnId = 2;
                    break;
                case "아틀라스원 제복" :
                    returnId = 3;
                    break;
                case "칼데아 전투복" :
                    returnId = 4;
                    break;
                case "애니버서리 블론드" :
                    returnId = 5;
                    break;
                case "로열 브랜드" :
                    returnId = 6;
                    break;
                case "브릴리언트 서머" :
                    returnId = 7;
                    break;
                case "달의 바다의 기억" :
                    returnId = 8;
                    break;
                case "달의 뒷편의 기억" :
                    returnId = 9;
                    break;
                case "2004년의 단편" :
                    returnId = 10;
                    break;
                case "극지용 칼데아 제복" :
                    returnId = 11;
                    break;
                case "트로피칼 서머" :
                    returnId = 12;
                    break;
            }
        }   // null check

        return returnId;
    }

    /**
     * 이펙트 아이디 받아와서 이펙트 레이아웃 그려주는 곳으로 토스
     * @param magicId
     * @param magicName
     */
    private void drawEffectWithEffectId(int magicId, String magicName){
        effectGridSecondMenuLayout.removeAllViews();
        effectGridLayoutSecond.removeAllViews();

        // 이펙트 아이디로 정보가져오기
        List<MagicEffectForSecondModel> magicEffectForSecondList = mDbOpenHelper.getMagicEffectListForSecond(magicId, magicName);

        // 그려주기 시작
        drawEffectGridLayoutData("레벨", 50,30,20, "M");
        drawEffectGridLayoutData("성능", 50,30,20, "M");
        drawEffectGridLayoutData("쿨타임", 70,30,20, "M");

        for (int effectSecondIndex = 0 ; effectSecondIndex < magicEffectForSecondList.size() ; effectSecondIndex++){
            // 레벨
            drawEffectGridLayoutData(String.valueOf(magicEffectForSecondList.get(effectSecondIndex).getMagicEffectLevel()), 50, 30, 15, "C");

            // 경험치
            drawEffectGridLayoutData(String.valueOf(magicEffectForSecondList.get(effectSecondIndex).getMagicEffectUtil()), 50, 30, 15, "C");

            // 누적 경험치
            drawEffectGridLayoutData(String.valueOf(magicEffectForSecondList.get(effectSecondIndex).getMagicEffectTime()), 70, 30, 15, "C");
        }
    }

    /**
     * 이펙트 레이아웃에 그려주기 - 데이터
     * @param data
     * @param objWidth
     * @param objHeight
     * @param objTextSize
     */
    public void drawEffectGridLayoutData(String data, int objWidth, int objHeight, int objTextSize, String kindGubun){
        if("M".equals(kindGubun)) {
            TextView tempTextView = new TextView(getContext());
            tempTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, objTextSize);
            tempTextView.setTextColor(Color.parseColor("#FFFFFF"));
            tempTextView.setGravity(Gravity.CENTER);
            tempTextView.setText(data);

            ViewGroup.LayoutParams tempViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tempViewParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objWidth, getResources().getDisplayMetrics());
            tempViewParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objHeight, getResources().getDisplayMetrics());
            tempTextView.setLayoutParams(tempViewParams);

            tempTextView.setBackgroundResource(R.drawable.grid_border_2);

            effectGridSecondMenuLayout.addView(tempTextView);
        }else if("C".equals(kindGubun)) {
            TextView tempView = new TextView(getContext());
            tempView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, objTextSize);
            tempView.setTextColor(Color.parseColor("#FFFFFF"));
            tempView.setGravity(Gravity.CENTER);
            tempView.setText(data);

            ViewGroup.LayoutParams tempParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tempParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objWidth, getResources().getDisplayMetrics());
            tempParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objHeight, getResources().getDisplayMetrics());
            tempView.setLayoutParams(tempParams);

            //tempView.setBackgroundResource(R.drawable.grid_border_2);

            effectGridLayoutSecond.addView(tempView);
        }
    }

    /**
     * 메뉴 레이아웃에 그려주기 - 데이터
     * @param data
     * @param objWidth
     * @param objHeight
     * @param objTextSize
     */
    public void drawExpGridLayoutData(String data, int objWidth, int objHeight, int objTextSize, String kindGubun){
        if("M".equals(kindGubun)) {
            TextView tempTextView = new TextView(getContext());
            tempTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, objTextSize);
            tempTextView.setTextColor(Color.parseColor("#FFFFFF"));
            tempTextView.setGravity(Gravity.CENTER);
            tempTextView.setText(data);

            ViewGroup.LayoutParams tempViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tempViewParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objWidth, getResources().getDisplayMetrics());
            tempViewParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objHeight, getResources().getDisplayMetrics());
            tempTextView.setLayoutParams(tempViewParams);

            tempTextView.setBackgroundResource(R.drawable.grid_border_2);

            expMenuLayout.addView(tempTextView);
        }else if("C".equals(kindGubun)) {
            TextView tempView = new TextView(getContext());
            tempView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, objTextSize);
            tempView.setTextColor(Color.parseColor("#FFFFFF"));
            tempView.setGravity(Gravity.CENTER);
            tempView.setText(data);

            ViewGroup.LayoutParams tempParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tempParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objWidth, getResources().getDisplayMetrics());
            tempParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objHeight, getResources().getDisplayMetrics());
            tempView.setLayoutParams(tempParams);

            //tempView.setBackgroundResource(R.drawable.grid_border_2);

            expGridLayout.addView(tempView);
        }
    }
}