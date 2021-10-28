package com.app.sanyou.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.utils.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ModifyPasswordActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button modifyPasswordBtn;

    private String username;
    private String password;
    private String confirmPassword;

    private CallListener listener = new CallListener() {
        @Override
        public void success(JsonResult result) {
            Looper.prepare();
            Toast.makeText(getApplicationContext(),"密码修改成功!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ModifyPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            Looper.loop();
        }

        @Override
        public void failure(JsonResult result) {
            Looper.prepare();
            Toast.makeText(getApplicationContext(),"密码修改失败,请联系管理员!",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏

        setContentView(R.layout.activity_modify_password);

        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        confirmPasswordText = findViewById(R.id.confirm_password_text);
        modifyPasswordBtn = findViewById(R.id.modify_password_btn);

        modifyPasswordBtn.setOnClickListener(view -> {
            username = usernameText.getText().toString();
            password = passwordText.getText().toString();
            confirmPassword = confirmPasswordText.getText().toString();

            if(TextUtils.isEmpty(username)){
                Toast.makeText(getApplicationContext(),"用户名不能为空!",Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                Toast.makeText(getApplicationContext(),"密码不能为空!",Toast.LENGTH_SHORT).show();
            }else if(!password.equals(confirmPassword)){
                Toast.makeText(getApplicationContext(),"两次输入的密码不同!",Toast.LENGTH_SHORT).show();
            }else{
                JsonResult result = new JsonResult();
                Map<String,String> postData = new HashMap<>();
                postData.put("username",username);
                postData.put("password",password);
                Gson gson = new Gson();
                String json = gson.toJson(postData);
                HttpUtil.post(Request.URL + "/user/updatePwd",json,listener);
            }
        });
    }
}