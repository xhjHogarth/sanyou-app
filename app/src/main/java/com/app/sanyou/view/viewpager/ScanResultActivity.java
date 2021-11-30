package com.app.sanyou.view.viewpager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
import com.app.sanyou.view.adapter.ScanResultListAdapter;
import com.app.sanyou.view.login.LoginActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScanResultActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private TextView scan_code_text;
    private Spinner statusSelector;
    private ImageView collect_img;
    private ImageView browse_img;

    private ExpandableListView verticality_list;

    private String userId;
    private String scanCode;
    private String tag;
    private String verticality;
    private int collectStatus=2;
    private List<IndustryData> industryDataList;
    private VerticalityDataVo verticalityDataVo;
    private int currentState=-1;

    private Handler handler = new Handler();

    private CallListener scanListener = new CallListener() {
        Gson gson = new Gson();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void success(JsonResult result) {
            verticalityDataVo = gson.fromJson(gson.toJson(result.getData()), VerticalityDataVo.class);
            handler.post(() -> {
                if(verticalityDataVo == null || StringUtil.isNull(verticalityDataVo.getVerticalityId())){
                    ScanResultActivity.this.runOnUiThread(()->Toast.makeText(ScanResultActivity.this,"阴极板不存在!",Toast.LENGTH_SHORT).show());
                    finish();
                }else{
                    scanCode = verticalityDataVo.getVerticalityId();
                    verticality = String.valueOf(verticalityDataVo.getVerticality());
                    industryDataList = verticalityDataVo.getIndustryDataList();

                    scan_code_text.setText(scanCode);
                    if(verticalityDataVo.getVerticality() == 0){
                        statusSelector.setSelection(0);
                    }else if(verticalityDataVo.getState() == 1){
                        statusSelector.setSelection(1);
                    }else if(verticalityDataVo.getState() == 2){
                        statusSelector.setSelection(2);
                    }else if(verticalityDataVo.getState() == 3){
                        statusSelector.setSelection(3);
                    }else{
                        statusSelector.setSelection(0);
                    }

                    ScanResultListAdapter scanResultListAdapter = new ScanResultListAdapter(ScanResultActivity.this);


                    if(industryDataList != null && industryDataList.size()>0){
                        industryDataList = industryDataList.stream().sorted((o1,o2)-> o2.getDatatime().compareTo(o1.getDatatime())).collect(Collectors.toList());
                        scanResultListAdapter.setDataSource(industryDataList);
                        verticality_list.setAdapter(scanResultListAdapter);
                        int count = verticality_list.getCount();
                        for(int i=0;i<count;i++){
                            verticality_list.expandGroup(i);
                        }
                    }else{
                        scanResultListAdapter.setDataSource(new ArrayList<>());
                        verticality_list.setAdapter(scanResultListAdapter);
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
            ScanResultActivity.this.runOnUiThread(()->Toast.makeText(ScanResultActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show());
            finish();
        }
    };

    private CallListener selectorChangeListener = new CallListener() {
        @Override
        public void success(JsonResult result) {
            verticalityDataVo.setState(currentState);
            ScanResultActivity.this.runOnUiThread(()->Toast.makeText(ScanResultActivity.this,"修改成功!",Toast.LENGTH_SHORT).show());
        }

        @Override
        public void failure(JsonResult result) {
            ScanResultActivity.this.runOnUiThread(()->Toast.makeText(ScanResultActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show());
        }
    };

    private CallListener collectListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            if(collectStatus == 1){
                collectStatus = 2;
                collect_img.setImageResource(R.drawable.ic_collect1);
                ScanResultActivity.this.runOnUiThread(()->Toast.makeText(ScanResultActivity.this,"取消收藏!",Toast.LENGTH_SHORT).show());
            }else if(collectStatus == 2){
                collectStatus = 1;
                collect_img.setImageResource(R.drawable.ic_collect_pressed1);
                ScanResultActivity.this.runOnUiThread(()->Toast.makeText(ScanResultActivity.this,"收藏成功!",Toast.LENGTH_SHORT).show());
            }
        }

        @Override
        public void failure(JsonResult result) {
            ScanResultActivity.this.runOnUiThread(()->Toast.makeText(ScanResultActivity.this,"收藏失败!",Toast.LENGTH_SHORT).show());
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
        statusSelector = findViewById(R.id.status_selector);

        verticality_list = findViewById(R.id.verticality_list);

        collect_img = findViewById(R.id.collect_img);
        browse_img = findViewById(R.id.browse_img);
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
            collectHistory.setVerticality(Float.valueOf(verticality));
            if(collectStatus == 1){
                HttpUtil.post(Request.URL + "/app/collectHistory/unCollect",gson.toJson(collectHistory),collectListener);
            }else{
                HttpUtil.post(Request.URL + "/app/collectHistory/collect",gson.toJson(collectHistory),collectListener);
            }
        });

        browse_img.setOnClickListener(v->{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            String msg = "";
            //出厂日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(verticalityDataVo != null && verticalityDataVo.getCreatetime()!=null){
                msg += "出厂日期:   "+ sdf.format(verticalityDataVo.getCreatetime()) + "\n";
            }else{
                msg += "出厂日期:   \n";
            }
            //导电棒尺寸
            msg += "导电棒尺寸:  "+ verticalityDataVo.getDdbSize() +"\n";
            //阴极板尺寸
            msg += "阴极板尺寸:  "+ verticalityDataVo.getYjbSize() +"\n";
            //出厂垂直度
            if(verticalityDataVo != null && verticalityDataVo.getVerticality() != null){
                msg += "出厂垂直度:  "+ verticalityDataVo.getVerticality();
            }else{
                msg += "出厂垂直度:  ";
            }

            dialog.setTitle("阴极板信息").setMessage(msg);
            dialog.create().show();
        });

        //初始化阴极板状态修改的选择事件
        initSelectorClick();
    }

    private void initSelectorClick(){
        //阴极板状态修改的选择事件
        statusSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    if(verticalityDataVo != null && verticalityDataVo.getState() != position){
                        VerticalityDataVo verticalityData = new VerticalityDataVo();
                        verticalityData.setVerticalityId(scanCode);
                        verticalityData.setState(position);

                        final CharSequence[] items = {"槽面换板","烧板","镀铜层脱落","弹性板"};
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ScanResultActivity.this);
                        dialog.setSingleChoiceItems(items, 0, (dialog13, which) -> {
                            verticalityData.setMaintainType(which+1);
                        })
                                .setPositiveButton("确定", (dialog14, which) -> {
                                    currentState = position;

                                    if(verticalityData.getMaintainType() == null){
                                        verticalityData.setMaintainType(1);
                                    }
                                    verticalityData.setUserid(userId);

                                    Gson gson = new Gson();
                                    String json = gson.toJson(verticalityData);
                                    HttpUtil.post(Request.URL + "/app/verticality/updateState", json, selectorChangeListener);
                                })
                                .setNegativeButton("取消", (dialog15, which) -> {
                                    statusSelector.setSelection(verticalityDataVo.getState());
                                })
                                .create().show();
                    }
                }else{
                    if(StringUtil.isNull(scanCode))
                        Toast.makeText(ScanResultActivity.this,"阴极板不存在!",Toast.LENGTH_SHORT).show();
                    else{
                        if(verticalityDataVo != null && verticalityDataVo.getState() != position) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ScanResultActivity.this);
                            dialog.setTitle("提示").setMessage("确认要修改阴极板状态吗?")
                                    .setPositiveButton("确定", (dialog1, which) -> {
                                        currentState = position;
                                        VerticalityDataVo verticalityData = new VerticalityDataVo();
                                        verticalityData.setVerticalityId(scanCode);
                                        verticalityData.setState(position);
                                        verticalityData.setUserid(userId);

                                        Gson gson = new Gson();
                                        String json = gson.toJson(verticalityData);
                                        HttpUtil.post(Request.URL + "/app/verticality/updateState", json, selectorChangeListener);
                                    })
                                    .setNegativeButton("取消", (dialog12, which) -> {
                                        statusSelector.setSelection(verticalityDataVo.getState());
                                    }).create().show();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}