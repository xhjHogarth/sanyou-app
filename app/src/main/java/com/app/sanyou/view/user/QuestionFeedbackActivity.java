package com.app.sanyou.view.user;

import android.os.Bundle;
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
import com.app.sanyou.entity.Question;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.StringUtil;
import com.google.gson.Gson;

public class QuestionFeedbackActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private EditText question_title_text;
    private EditText question_desc_text;
    private Button feedback_btn;

    private CallListener feedbakListener = new CallListener() {

        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            Object obj = result.getData();
            finish();
        }

        @Override
        public void failure(JsonResult result) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_feedback);

        //初始化View
        initView();
        //初始化点击事件
        initClickListener();

    }


    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("问题反馈");

        question_title_text = findViewById(R.id.question_title_text);
        question_desc_text = findViewById(R.id.question_desc_text);
        feedback_btn = findViewById(R.id.feedback_btn);
    }


    private void initClickListener() {
        //返回
        back_img.setOnClickListener(v->{
            finish();
        });
        back_text.setOnClickListener(v->{
            finish();
        });

        //问题反馈
        feedback_btn.setOnClickListener(v->{
            String questionTitle = question_title_text.getText().toString();
            String questionDesc = question_desc_text.getText().toString();

            if(StringUtil.isNull(questionTitle) || StringUtil.isNull(questionDesc))
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();


            Question question = new Question();
            question.setTitle(questionTitle);
            question.setDescription(questionDesc);

            String json = new Gson().toJson(question);

            HttpUtil.post(Request.URL + "/app/question/addQuestion",json,feedbakListener);
        });
    }

}