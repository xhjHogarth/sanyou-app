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
import com.app.sanyou.entity.CollectHistory;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.JsonUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UIUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.app.sanyou.view.viewpager.ScanResultActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class CollectRecordActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private LinearLayout list_ll;

    private String userId;

    private Handler handler = new Handler();


    private CallListener historyListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            List<CollectHistory> historyList = JsonUtil.jsonToList(result.getData(), CollectHistory[].class);
            if(historyList != null && historyList.size() > 0){
                handler.post(() -> {
                    ViewGroup.LayoutParams layoutParams1 = new ViewGroup.LayoutParams(UIUtil.dip(CollectRecordActivity.this,100), ViewGroup.LayoutParams.WRAP_CONTENT);
                    ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(UIUtil.dip(CollectRecordActivity.this,150), ViewGroup.LayoutParams.WRAP_CONTENT);
                    ViewGroup.LayoutParams layoutParams3 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    for (CollectHistory history : historyList) {

                        LinearLayout item_ll = new LinearLayout(CollectRecordActivity.this);
                        item_ll.setHorizontalGravity(LinearLayout.HORIZONTAL);

                        TextView codeText = new TextView(CollectRecordActivity.this);
                        codeText.setText(history.getCollectCode());
                        codeText.setTextSize(20);
                        codeText.setGravity(Gravity.CENTER);
                        codeText.setLayoutParams(layoutParams1);
                        item_ll.addView(codeText);

                        TextView dateText = new TextView(CollectRecordActivity.this);
                        dateText.setText(sdf.format(history.getCollectDate()));
                        dateText.setTextSize(20);
                        dateText.setGravity(Gravity.CENTER);
                        dateText.setLayoutParams(layoutParams2);
                        item_ll.addView(dateText);

                        TextView valueText = new TextView(CollectRecordActivity.this);
                        if(history.getVerticality() == null)
                            valueText.setText("0");
                        else
                            valueText.setText(String.valueOf(history.getVerticality()));
                        valueText.setTextSize(20);
                        valueText.setGravity(Gravity.CENTER);
                        valueText.setLayoutParams(layoutParams3);
                        item_ll.addView(valueText);

                        item_ll.setOnClickListener(v->{
                            Intent intent = new Intent(CollectRecordActivity.this, ScanResultActivity.class);
                            intent.putExtra("scanCode",history.getCollectCode());
                            intent.putExtra("tag","2");
                            startActivity(intent);
                        });

                        list_ll.addView(item_ll);
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
        setContentView(R.layout.activity_collect_record);

        initView();
        initClickListener();

        userId = UserUtil.getUserId(this);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        HttpUtil.get(Request.URL + "/app/collectHistory/getCollectHistory?userId=" + userId,historyListener);
    }

    private void initView() {
        //初始化顶部栏
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("我的收藏");

        list_ll = findViewById(R.id.list_ll);
    }

    private void initClickListener() {
        //返回
        back_img.setOnClickListener(v->{
            finish();
        });
        back_text.setOnClickListener(v->{
            finish();
        });
    }
}