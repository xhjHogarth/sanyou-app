package com.app.sanyou.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.SearchHistory;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.JsonUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryRecordActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private LinearLayout search_code_ll;
    private LinearLayout search_date_ll;
    private LinearLayout verticality_ll;

    private String userId;

    private Handler handler = new Handler();

    private CallListener historyListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            List<SearchHistory> historyList = JsonUtil.jsonToList(result.getData(), SearchHistory[].class);
            if(historyList != null && historyList.size() > 0){
                handler.post(() -> {
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    for (SearchHistory history : historyList) {
                        TextView codeText = new TextView(HistoryRecordActivity.this);
                        codeText.setText(history.getSearchCode());
                        codeText.setTextSize(20);
                        codeText.setGravity(Gravity.CENTER);
                        codeText.setLayoutParams(layoutParams);
                        search_code_ll.addView(codeText);

                        TextView dateText = new TextView(HistoryRecordActivity.this);
                        dateText.setText(sdf.format(history.getSearchDate()));
                        dateText.setTextSize(20);
                        dateText.setGravity(Gravity.CENTER);
                        dateText.setLayoutParams(layoutParams);
                        search_date_ll.addView(dateText);

                        TextView valueText = new TextView(HistoryRecordActivity.this);
                        if(history.getVerticality() == null)
                            valueText.setText("0");
                        else
                            valueText.setText(String.valueOf(history.getVerticality()));
                        valueText.setTextSize(20);
                        valueText.setGravity(Gravity.CENTER);
                        valueText.setLayoutParams(layoutParams);
                        verticality_ll.addView(valueText);
                    }
                });
            }
        }

        @Override
        public void failure(JsonResult result) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);

        initView();
        initClickListener();

        userId = UserUtil.getUserId(this);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        HttpUtil.get(Request.URL + "/app/searchHistory/getSearchHistory?userId=" + userId,historyListener);
    }

    private void initView(){
        //初始化顶部栏
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("历史记录");

        search_code_ll = findViewById(R.id.search_code_ll);
        search_date_ll = findViewById(R.id.search_date_ll);
        verticality_ll = findViewById(R.id.verticality_ll);
    }

    private void initClickListener(){
        //返回
        back_img.setOnClickListener(v->{
            finish();
        });
        back_text.setOnClickListener(v->{
            finish();
        });


    }
}