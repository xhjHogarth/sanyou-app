package com.app.sanyou.view.user;

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
import com.app.sanyou.entity.Question;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.JsonUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.adapter.MessageListAdapter;
import com.app.sanyou.view.login.LoginActivity;

import java.util.List;

public class MessageCenterActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private RecyclerView rv;
    private MessageListAdapter messageListAdapter;

    private String userId;
    private List<Question> questionList;

    private Handler handler = new Handler();

    private CallListener messageListener = new CallListener() {
        @Override
        public void success(JsonResult result) {
            questionList = JsonUtil.jsonToList(result.getData(),Question[].class);
            handler.post(() -> {
                messageListAdapter.setDataSource(questionList);
            });
        }

        @Override
        public void failure(JsonResult result) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);

        //初始化view
        initView();
        //初始化点击事件
        initClickListener();

        userId = UserUtil.getUserId(this);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        HttpUtil.get(Request.URL + "/app/question/getQuestionList?userId=" + userId,messageListener);
    }

    private void initView() {
        //初始化顶部栏
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("消息中心");

        rv = findViewById(R.id.rv);
        messageListAdapter = new MessageListAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(messageListAdapter);
    }

    private void initClickListener(){
        back_img.setOnClickListener(v->{
            finish();
        });
        back_text.setOnClickListener(v->{
            finish();
        });


    }


}