package com.app.sanyou.view.viewpager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.CollectHistory;
import com.app.sanyou.entity.IndustryData;
import com.app.sanyou.entity.VerticalityDataVo;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ScanResultActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private TextView scan_code_text;
    private TextView verticality_text;
    private LinearLayout detect_count_ll;
    private LinearLayout detect_date_ll;
    private LinearLayout detect_value_ll;
    private ImageView collect_img;


    private String userId;
    private String scanCode;
    private String tag;
    private String verticality;
    private int collectStatus=2;
    private List<IndustryData> industryDataList;

    private Handler handler = new Handler();

    private CallListener scanListener = new CallListener() {
        Gson gson = new Gson();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void success(JsonResult result) {
            VerticalityDataVo verticalityDataVo = gson.fromJson(gson.toJson(result.getData()), VerticalityDataVo.class);
            handler.post(() -> {
                if(verticalityDataVo == null || StringUtil.isNull(verticalityDataVo.getVerticalityId())){
                    Toast.makeText(ScanResultActivity.this,"阴极板不存在!",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    scanCode = verticalityDataVo.getVerticalityId();
                    verticality = String.valueOf(verticalityDataVo.getVerticality());
                    industryDataList = verticalityDataVo.getIndustryDataList();

                    scan_code_text.setText(scanCode);
                    verticality_text.setText(verticality);

                    if(industryDataList != null && industryDataList.size()>0){
                        industryDataList = industryDataList.stream().sorted((o1,o2)-> o2.getDatatime().compareTo(o1.getDatatime())).collect(Collectors.toList());
                        int count = industryDataList.size();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        for (IndustryData industryData : industryDataList) {
                            //检测次数
                            TextView detectCount = new TextView(ScanResultActivity.this);
                            detectCount.setText(String.valueOf(count--));
                            detectCount.setTextSize(15);
                            detect_count_ll.addView(detectCount);
                            //检测时间
                            TextView detectDate = new TextView(ScanResultActivity.this);
                            detectDate.setText(sdf.format(industryData.getDatatime()));
                            detectDate.setTextSize(15);
                            detect_date_ll.addView(detectDate);
                            //检测数值 平面度MAX
                            TextView detectValue = new TextView(ScanResultActivity.this);
                            detectValue.setText(String.valueOf(industryData.getMax()));
                            detectValue.setTextSize(15);
                            detect_value_ll.addView(detectValue);
                        }
                    }

                    if(verticalityDataVo.getCollectStatus() == 1){
                        collectStatus = 1;
                        collect_img.setImageResource(R.drawable.ic_collect_pressed1);
                    }
                }
            });
        }

        @Override
        public void failure(JsonResult result) {
            Looper.prepare();
            Toast.makeText(ScanResultActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
            Looper.loop();
            finish();
        }
    };

    private CallListener collectListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            if(collectStatus == 1){
                collectStatus = 2;
                collect_img.setImageResource(R.drawable.ic_collect1);
                Looper.prepare();
                Toast.makeText(ScanResultActivity.this,"取消收藏!",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else if(collectStatus == 2){
                collectStatus = 1;
                collect_img.setImageResource(R.drawable.ic_collect_pressed1);
                Looper.prepare();
                Toast.makeText(ScanResultActivity.this,"收藏成功!",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }

        @Override
        public void failure(JsonResult result) {
            Looper.prepare();
            Toast.makeText(ScanResultActivity.this,"收藏失败!",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        //初始化View
        initView();
        //初始化点击事件
        initClickListener();

        userId = UserUtil.getUserId(this);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        Intent intent = getIntent();
        scanCode = intent.getStringExtra("scanCode");
        tag = intent.getStringExtra("tag");

        HttpUtil.get(Request.URL + "/app/scancode/getInfo?scanCode=" + scanCode + "&userId=" + userId + "&tag=" + tag,scanListener);
    }

    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("阴极板信息");

        scan_code_text = findViewById(R.id.scan_code_text);
        verticality_text = findViewById(R.id.verticality_text);
        detect_count_ll = findViewById(R.id.detect_count_ll);
        detect_date_ll = findViewById(R.id.detect_date_ll);
        detect_value_ll = findViewById(R.id.detect_value_ll);

        collect_img = findViewById(R.id.collect_img);

    }

    private void initClickListener(){
        //返回
        back_img.setOnClickListener(v->{
            finish();
        });
        back_text.setOnClickListener(v->{
            finish();
        });

        collect_img.setOnClickListener(v->{
            Gson gson = new Gson();
            CollectHistory collectHistory = new CollectHistory();
            collectHistory.setCollectCode(scanCode);
            collectHistory.setUserId(userId);
            collectHistory.setVerticality(Integer.valueOf(verticality));
            if(collectStatus == 1){
                HttpUtil.post(Request.URL + "/app/collectHistory/unCollect",gson.toJson(collectHistory),collectListener);
            }else{
                HttpUtil.post(Request.URL + "/app/collectHistory/collect",gson.toJson(collectHistory),collectListener);
            }
        });
    }
}