package com.app.sanyou.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.User;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.app.sanyou.view.viewpager.TabViewPagerActivity;
import com.google.gson.Gson;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private EditText usernameText;
    private EditText userGroupText;
    private EditText realNameText;
    private EditText factoryText;
    private EditText subFactoryText;
    private EditText departText;
    private EditText mobileText;
    private EditText emailText;
    private EditText qqText;
    private EditText wechatText;
    private EditText cityText;
    private EditText addressText;
    private Spinner sexText;

    private Button updateInfoBtn;

    private User user;
    private String userId;

    private Handler handler = new Handler();

    private String realName;
    private Byte sex;
    private String depart;
    private String mobile;
    private String email;
    private String qq;
    private String wechat;
    private String address;

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

                if(user.getSex() == 2){
                    sexText.setSelection(0);
                }else if(user.getSex() == 3){
                    sexText.setSelection(1);
                }else{
                    sexText.setSelection(1);
                }

            });
        }

        @Override
        public void failure(JsonResult result) {
            Log.d(TAG, "onLoad: " + gson.toJson(result));
            UserInfoActivity.this.runOnUiThread(()->Toast.makeText(getApplicationContext(),result.getMsg(),Toast.LENGTH_SHORT).show());
        }
    };

    private CallListener updateListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            UserInfoActivity.this.runOnUiThread(()->Toast.makeText(getApplicationContext(),"保存成功!",Toast.LENGTH_SHORT).show());
            Intent intent = new Intent(UserInfoActivity.this, TabViewPagerActivity.class);
            startActivity(intent);
        }

        @Override
        public void failure(JsonResult result) {
            Log.d(TAG, "onClick: " + gson.toJson(result));
            UserInfoActivity.this.runOnUiThread(()->Toast.makeText(getApplicationContext(),result.getMsg(),Toast.LENGTH_SHORT).show());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userId = UserUtil.getUserId(this);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            HttpUtil.get(Request.URL + "/app/userInfo/getUserInfo?userId=" + userId,listener);
        }

        //初始化View
        initView();
        //初始化点击事件
        initClickListener();
    }

    private void initView() {
        //初始化顶部栏
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("个人信息");

        usernameText = findViewById(R.id.username_text);
        userGroupText = findViewById(R.id.userGroup_text);
        realNameText = findViewById(R.id.realName_text);
        sexText = findViewById(R.id.sex_spinner);
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
            realName = realNameText.getText().toString();
            if(sexText.getSelectedItemPosition() == 0){
                sex = 2;
            }else if(sexText.getSelectedItemPosition() == 1){
                sex = 3;
            }else{
                sex = 1;
            }
            depart = departText.getText().toString();
            mobile = mobileText.getText().toString();
            email = emailText.getText().toString();
            qq = qqText.getText().toString();
            wechat = wechatText.getText().toString();
            address = addressText.getText().toString();


            if(StringUtil.isNull(userId)){
                userId = UserUtil.getUserId(this);
                if(StringUtil.isNull(userId)){
                    UserUtil.loginOut(this);
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            User updateUser = new User();
            updateUser.setId(userId);

            updateUser.setSex(sex);
            user.setSex(sex);

            if(StringUtil.isNotNull(realName)){
                updateUser.setRealname(realName);
                user.setRealname(realName);
            }
            if(StringUtil.isNotNull(depart)){
                updateUser.setDepart(depart);
                user.setDepart(depart);
            }
            if(StringUtil.isNotNull(mobile)) {
                updateUser.setMobile(mobile);
                user.setMobile(mobile);
            }
            if(StringUtil.isNotNull(email)){
                updateUser.setEmail(email);
                user.setEmail(email);
            }
            if(StringUtil.isNotNull(qq)){
                updateUser.setQq(qq);
                user.setQq(qq);
            }
            if(StringUtil.isNotNull(wechat)){
                updateUser.setWechat(wechat);
                user.setWechat(wechat);
            }
            if(StringUtil.isNotNull(address)){
                updateUser.setAddress(address);
                user.setAddress(address);
            }


            Gson gson = new Gson();
            String json = gson.toJson(updateUser);

            HttpUtil.post(Request.URL + "/app/userInfo/updateUserInfo",json,updateListener);
        });


    }

    private void initClickListener(){
        //返回
        back_img.setOnClickListener(v->{
            finish();
        });
        back_text.setOnClickListener(v->{
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}