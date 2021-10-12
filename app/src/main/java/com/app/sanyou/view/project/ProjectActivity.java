package com.app.sanyou.view.project;

import static com.app.sanyou.constants.Request.URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.sanyou.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalNum;
    Button btnCheck;
    private static final String TAG = "ProjectActivity";
    String contractId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        totalNum = findViewById(R.id.total_num);
        btnCheck = findViewById(R.id.btn_check);

        //获取projectFragment的数据
        Intent intent = getIntent();
        contractId = intent.getStringExtra("contractId");
//        Log.d(TAG, contractId);

        String totalNum = intent.getStringExtra("totalNum");

        this.totalNum.setText(totalNum);

        //按钮绑定点击事件的监听器
        btnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(ProjectActivity.this, "正在发送请求...", Toast.LENGTH_SHORT).show();
        //发送请求
        send();
    }

    private void send() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                FormBody formBody = new FormBody.Builder()
//                        .add("dirpath", "/home/data/project_pdfFile/uploadfinish")
//                        .add("filename", "45")
//                        .add("extension", "pdf")
//                        .build();
                //post请求
                Request request = new Request.Builder()
                        .url(URL + "/app/project/getFile?contractId=JJXMB-SB-401-123")
//                        .post(formBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                try {
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();

                    Log.d(TAG,"result："+result);
                    show(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void show(String result) {
        Log.d(TAG,result);
    }

}
