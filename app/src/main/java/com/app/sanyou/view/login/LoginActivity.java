package com.app.sanyou.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.User;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.view.user.UserInfoActivity;
import com.app.sanyou.view.viewpager.TabViewPagerActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText usernameText;
    private EditText passwordText;
    private Button loginBtn;
    private TextView registerView;
    private TextView modifyPasswordView;

    private String username;
    private String password;

    private CallListener listener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            User user = gson.fromJson(gson.toJson(result.getData()), User.class);
            if(user == null || TextUtils.isEmpty(user.getId()) || TextUtils.isEmpty(user.getUsername()))
                Log.d(TAG, "user: 获取用户数据异常!");

            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("userId",user.getId());
            edit.putString("username",user.getUsername());
            edit.commit();

            Looper.prepare();
            Toast.makeText(getApplicationContext(),"登录成功!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, TabViewPagerActivity.class);
            startActivity(intent);
            Looper.loop();
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
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        loginBtn = findViewById(R.id.login_btn);
        registerView = findViewById(R.id.register_view);
        modifyPasswordView = findViewById(R.id.modify_password_view);


        loginBtn.setOnClickListener(view -> {

            username = usernameText.getText().toString();
            password = passwordText.getText().toString();


            if(TextUtils.isEmpty(username)){
                Toast toast = Toast.makeText(getApplicationContext(),"用户名不能为空!",Toast.LENGTH_SHORT);
                toast.show();
            }else if(TextUtils.isEmpty(password)){
                Toast toast = Toast.makeText(getApplicationContext(),"密码不能为空!",Toast.LENGTH_SHORT);
                toast.show();
            }else{
                JsonResult result = new JsonResult();
                Map<String,String> postData = new HashMap<>();
                postData.put("username",username);
                postData.put("password",password);
                Gson gson = new Gson();
                String json = gson.toJson(postData);
                HttpUtil.post(Request.URL + "/user/login",json,listener);
            }
        });

        registerView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });

        modifyPasswordView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,ModifyPasswordActivity.class);
            startActivity(intent);
        });
    }
}