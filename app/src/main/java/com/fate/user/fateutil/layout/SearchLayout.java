package com.fate.user.fateutil.layout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.fate.user.fateutil.R;
import com.fate.user.fateutil.adapter.SearchAdapter;
import com.fate.user.fateutil.db.DataBase;
import com.fate.user.fateutil.db.DbOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fate.user.fateutil.db.Parser;
import com.fate.user.fateutil.db.ServantContact;

public class SearchLayout extends LinearLayout implements AbsListView.OnScrollListener {

    // 레이아웃 변수
    Button btnSearch = null;
    EditText editSearch = null;
    LinearLayout currentLayout;
    LayoutInflater li = null;
    private ListView listView = null; // 리스트 뷰
    private ProgressBar progressBar; // 데이터 로딩중 표시할 프로그레스바
    GridView gridView;

    // 어댑터 변수
    AssetManager assetManager = getResources().getAssets();
    private SearchAdapter mAdapter;
    private boolean lastItemVisibleFlag = false; // 리스트 스크롤이 마지막 셀로 이동했을 지 체크하는 변수
    private int page = 0; // 페이징 변수 초기값은 0
    private final int OFFSET = 20; // 한 페이지 마다 로드할 데이터 갯수
    private boolean lockListView = false;

    // DB 변수
    private DbOpenHelper mDbOpenHelper;
    private Parser parser;

    public SearchLayout(Context context) {
        super(context);

        String infService = context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) context.getSystemService(infService);
        currentLayout = (LinearLayout) li.inflate(R.layout.search_main, null);
        addView(currentLayout);

        // 1. searchLayout에 들어가야하는 id 찾기
        btnSearch = currentLayout.findViewById(R.id.btn_Search);
        editSearch = currentLayout.findViewById(R.id.edit_Search);
        listView = currentLayout.findViewById(R.id.list_View);
        progressBar = currentLayout.findViewById(R.id.progressbar);
        gridView = currentLayout.findViewById(R.id.grid_skill);

        mDbOpenHelper = new DbOpenHelper(currentLayout.getContext());

        // 2. DB에서 서번트 항목 전체를 할당 받고 Adapter와 연결하여준다.
        init();
        getItem();
        // 3. 필터를 사용하여 검색어가 editText에 입력될 때 마다 서번트 항목들을 띄워준다.
        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editSearch.getText().toString();
                mAdapter.Filter(text);
            }


        });

        // 리스트 뷰를 클릭할 때 일어남
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭을 하였을 때 position 값을 인텐트에 넘겨준다.
                // 인텐트 생성
                Intent intent = new Intent(getContext(), SearchIntent.class);
                // position 값을 넘긴다.
                intent.putExtra("POSITION", (position+1));
                getContext().startActivity(intent);
            }
        });

    }

    // 서번트 리스트 생성, 리스트 뷰와 어댑터 연결
    public void init(){
        mDbOpenHelper.open();
        // 1. DB에서 서번트 리스트를 가져오고 저장한다.
        List<ServantContact> contacts = mDbOpenHelper.getAllServantContacts();
        // 2. 리스트에 연동 될 어댑터 생성
        mAdapter = new SearchAdapter(currentLayout.getContext(), contacts);
        listView = (ListView)findViewById(R.id.list_View);
        listView.setAdapter(mAdapter);
        // 프로그레스바
        progressBar.setVisibility(View.GONE);
        listView.setOnScrollListener(this);
        mDbOpenHelper.close();
    }

    // 페이징
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 1. AbsListView.OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을 떄의 이벤트
        // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을 때
        // 3. lockListView == flase : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을 때
        // 4. 모두가 참이면 다음 데이터를 불러온다.
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && lockListView == false){
            // 화면이 바닥에 닿을 때 처리
            // 로딩중을 알리는 프로그레스바를 보인다.
            progressBar.setVisibility(View.VISIBLE);
        }
        getItem();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // firstVisibleItem : 화면에 보이는 처번째 리스트의 아이템 번호
        // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
        // totalItemCount : 리스트 전체의 총 갯수
        // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을 때(리스트의 끝일때) true
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount > totalItemCount);

    }

    private void getItem(){
        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 lockListView를 true로 설정
        lockListView = true;

        // 1초뒤 프로그레스바를 감추고 데이터를 갱신
        // 중복 로딩 체그하는 Lock을 했던 mLock변수를 풀어준다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                lockListView = false;
            }
        },1000);

    }
}
