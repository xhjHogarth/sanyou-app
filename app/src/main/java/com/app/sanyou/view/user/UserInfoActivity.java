package com.app.sanyou.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.User;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.google.android.material.badge.BadgeUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";

    private EditText usernameText;
    private EditText userGroupText;
    private EditText realNameText;
    private EditText sexText;
    private EditText factoryText;
    private EditText subFactoryText;
    private EditText departText;
    private EditText mobileText;
    private EditText emailText;
    private EditText qqText;
    private EditText wechatText;
    private EditText cityText;
    private EditText addressText;

    private Button updateInfoBtn;

    private User user;
    private String userId;

    private Handler handler = new Handler();

    private CallListener listener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            user = gson.fromJson(gson.toJson(result.getData()), User.class);

            handler.post(() -> {
                usernameText.setText(user.getUsername());
                userGroupText.setText(user.getGroupName());
                realNameText.setText(user.getRealname());
                factoryText.setText(user.getFactoryName());
                subFactoryText.setText(user.getSubFactoryName());
                departText.setText(user.getPosition());
                mobileText.setText(user.getMobile());
                emailText.setText(user.getEmail());
                qqText.setText(user.getQq());
                wechatText.setText(user.getWechat());
                cityText.setText(user.getAdministration());
                addressText.setText(user.getAddress());

                sexText.setText(user.getSexName());
            });
        }

        @Override
        public void failure(JsonResult result) {
            Log.d(TAG, "onClick: " + gson.toJson(result));
            Looper.prepare();
            Toast.makeText(getApplicationContext(),result.getMsg(),Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //初始化View
        initView();

        userId = UserUtil.getUserId(this);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            HttpUtil.get(Request.URL + "/app/userInfo/getUserInfo?userId=" + userId,listener);
        }
    }

    private void initView() {
        usernameText = findViewById(R.id.username_text);
        userGroupText = findViewById(R.id.userGroup_text);
        realNameText = findViewById(R.id.realName_text);
        //TODO 性别下拉选择框
        sexText = findViewById(R.id.sex_text);
        factoryText = findViewById(R.id.factory_text);
        subFactoryText = findViewById(R.id.subFactory_text);
        departText = findViewById(R.id.depart_text);
        mobileText = findViewById(R.id.mobile_text);
        emailText = findViewById(R.id.email_text);
        qqText = findViewById(R.id.qq_text);
        wechatText = findViewById(R.id.wechat_text);
        cityText = findViewById(R.id.city_text);
        addressText = findViewById(R.id.address_text);

        updateInfoBtn = findViewById(R.id.update_info_btn);
        updateInfoBtn.setOnClickListener(v -> {

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}