package com.app.sanyou.view.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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
import com.app.sanyou.view.viewpager.ScanResultActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryRecordActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private LinearLayout list_ll;

    private String userId;

    private List<SearchHistory> historyList;

    private Handler handler = new Handler();

    private CallListener historyListener = new CallListener() {
        Gson gson = new Gson();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void success(JsonResult result) {
            historyList = JsonUtil.jsonToList(result.getData(), SearchHistory[].class);
            if(historyList != null && historyList.size() > 0){
                historyList = historyList.stream().sorted((o1,o2)->o2.getSearchDate().compareTo(o1.getSearchDate())).collect(Collectors.toList());
                handler.post(() -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    int count = 1;
                    for (SearchHistory history : historyList){
                        View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.history_record_item, null);
                        TextView codeText = itemView.findViewById(R.id.tvCode);
                        TextView dateText = itemView.findViewById(R.id.tvDate);
                        TextView valueText = itemView.findViewById(R.id.tvValue);

                        codeText.setText(history.getSearchCode());
                        dateText.setText(sdf.format(history.getSearchDate()));
                        if(history.getVerticality() == null)
                            valueText.setText("0");
                        else
                            valueText.setText(String.valueOf(history.getVerticality()));

                        itemView.setOnClickListener(v->{
                            Intent intent = new Intent(HistoryRecordActivity.this, ScanResultActivity.class);
                            intent.putExtra("scanCode",history.getSearchCode());
                            intent.putExtra("tag","2");
                            startActivity(intent);
                        });

                        if(count % 2 == 0){
                            itemView.setBackgroundColor(getResources().getColor(R.color.gray));
                        }
                        count++;

                        list_ll.addView(itemView);
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

        list_ll = findViewById(R.id.list_ll);
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