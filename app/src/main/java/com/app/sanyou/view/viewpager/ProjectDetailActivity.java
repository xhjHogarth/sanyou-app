package com.app.sanyou.view.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.ContractVo;
import com.app.sanyou.utils.HttpUtil;
import com.google.gson.Gson;

public class ProjectDetailActivity extends AppCompatActivity {

    private TextView contractNameText;
    private TextView codeStartText;
    private TextView codeEndText;
    private TextView totalNumText;
    private TextView usingNumText;
    private TextView maintainNumText;
    private TextView reserveNumText;
    private TextView deprecatedNumText;

    private Handler handler = new Handler();

    private CallListener contractListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            ContractVo contract = gson.fromJson(gson.toJson(result.getData()), ContractVo.class);
            handler.post(() -> {
                if(contract != null){
                    contractNameText.setText(contract.getContractName() == null ? "":contract.getContractName());
                    codeStartText.setText(contract.getCodeStart() == null ? "":contract.getCodeStart());
                    codeEndText.setText(contract.getCodeEnd() == null ? "":contract.getCodeEnd());
                    totalNumText.setText(String.valueOf(contract.getTotalNum()));
                    usingNumText.setText(String.valueOf(contract.getUsingNum()));
                    maintainNumText.setText(String.valueOf(contract.getMaintainNum()));
                    reserveNumText.setText(String.valueOf(contract.getReserveNum()));
                    deprecatedNumText.setText(String.valueOf(contract.getDeprecatedNum()));
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
        String contractId = intent.getStringExtra("contractId");

        initView();

        HttpUtil.get(Request.URL + "/app/project/getContractDetail?projectId=" + projectId + "&contractId=" + contractId
                , contractListener);
    }

    private void initView() {
        contractNameText = findViewById(R.id.contractNameText);
        codeStartText = findViewById(R.id.codeStartText);
        codeEndText = findViewById(R.id.codeEndText);
        totalNumText = findViewById(R.id.totalNumText);
        usingNumText = findViewById(R.id.usingNumText);
        maintainNumText = findViewById(R.id.maintainNumText);
        reserveNumText = findViewById(R.id.reserveNumText);
        deprecatedNumText = findViewById(R.id.deprecatedNumText);
    }
}