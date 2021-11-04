package com.app.sanyou.view.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.ProjectVo;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.view.adapter.ProjectDetailAdapter;
import com.google.gson.Gson;

public class ProjectDetailActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private TextView projectNameText;
    private TextView totalNumText;
    private TextView usingNumText;
    private TextView maintainNumText;
    private TextView reserveNumText;
    private TextView deprecatedNumText;

    private RecyclerView rv;

    private ProjectDetailAdapter projectDetailAdapter;

    private Handler handler = new Handler();

    private CallListener contractListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            ProjectVo projectVo = gson.fromJson(gson.toJson(result.getData()), ProjectVo.class);
            handler.post(() -> {
                if(projectVo != null){
                    totalNumText.setText(String.valueOf(projectVo.getTotalNum()));
                    usingNumText.setText(String.valueOf(projectVo.getUsingNum()));
                    maintainNumText.setText(String.valueOf(projectVo.getMaintainNum()));
                    reserveNumText.setText(String.valueOf(projectVo.getReserveNum()));
                    deprecatedNumText.setText(String.valueOf(projectVo.getDeprecatedNum()));

                    projectDetailAdapter.setDataSource(projectVo.getContractDataList());
                }
            });
        }

        @Override
        public void failure(JsonResult result) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        Intent intent = getIntent();
        String projectId = intent.getStringExtra("projectId");
        String projectName = intent.getStringExtra("projectName");

        initView();
        initClickListener();

        projectNameText.setText(projectName == null ? "":projectName);
        HttpUtil.get(Request.URL + "/app/project/getContractDetail?projectId=" + projectId, contractListener);
    }

    private void initView() {
        //初始化顶部栏
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("");

        projectNameText = findViewById(R.id.projectNameText);
        totalNumText = findViewById(R.id.totalNumText);
        usingNumText = findViewById(R.id.usingNumText);
        maintainNumText = findViewById(R.id.maintainNumText);
        reserveNumText = findViewById(R.id.reserveNumText);
        deprecatedNumText = findViewById(R.id.deprecatedNumText);

        rv = findViewById(R.id.rv);
        projectDetailAdapter = new ProjectDetailAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(projectDetailAdapter);
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