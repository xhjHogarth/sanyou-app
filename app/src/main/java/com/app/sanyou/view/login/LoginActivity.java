package com.app.sanyou.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.User;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.view.viewpager.TabViewPagerActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private EditText usernameText;
    private EditText passwordText;
    private Button loginBtn;
    private TextView registerView;
    private TextView modifyPasswordView;
    private ImageView ivDeleteName;
    private ImageView ivDeletePwd;

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

            LoginActivity.this.runOnUiThread(()->Toast.makeText(getApplicationContext(),"登录成功!",Toast.LENGTH_SHORT).show());
            Intent intent = new Intent(LoginActivity.this, TabViewPagerActivity.class);
            startActivity(intent);

        }

        @Override
        public void failure(JsonResult result) {
            Log.d(TAG, "onClick: " + gson.toJson(result));
            LoginActivity.this.runOnUiThread(()->Toast.makeText(getApplicationContext(),result.getMsg(),Toast.LENGTH_SHORT).show());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initClickListener();
    }

    private void initView() {
        //初始化顶部栏
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        //隐藏返回按钮和文字
        back_img.setVisibility(View.GONE);
        back_text.setVisibility(View.GONE);
        title_text.setText("用户登录");

        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        loginBtn = findViewById(R.id.login_btn);
        registerView = findViewById(R.id.register_view);
        modifyPasswordView = findViewById(R.id.modify_password_view);

        ivDeleteName = findViewById(R.id.ivDeleteName);
        ivDeletePwd = findViewById(R.id.ivDeletePwd);
    }

    private void initClickListener(){

        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0)
                    ivDeleteName.setVisibility(View.GONE);
                else
                    ivDeleteName.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivDeleteName.setOnClickListener(v->{
            usernameText.setText("");
            ivDeleteName.setVisibility(View.GONE);
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0)
                    ivDeletePwd.setVisibility(View.GONE);
                else
                    ivDeletePwd.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivDeletePwd.setOnClickListener(v->{
            passwordText.setText("");
            ivDeletePwd.setVisibility(View.GONE);
        });

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