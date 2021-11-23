package com.app.sanyou.view.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.LoadFileVo;
import com.app.sanyou.entity.Question;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.adapter.LoadPictureAdapter;
import com.app.sanyou.view.login.LoginActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuestionFeedbackActivity extends AppCompatActivity {

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private EditText question_title_text;
    private EditText question_desc_text;
    private Button feedback_btn;

    private RecyclerView rvPic;
    private TextView tvNum;

    private LoadPictureAdapter loadPictureAdapter;
    private List<LoadFileVo> fileList = new ArrayList<>();

    private String userId;

    private String mPhotoPath;
    private Uri uriImage;
    private File mPhotoFile;

    private CallListener feedbackListener = new CallListener() {

        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            Object obj = result.getData();
            QuestionFeedbackActivity.this.runOnUiThread(()->Toast.makeText(getApplicationContext(),"反馈成功!",Toast.LENGTH_SHORT).show());
            finish();
        }

        @Override
        public void failure(JsonResult result) {
            QuestionFeedbackActivity.this.runOnUiThread(()->Toast.makeText(getApplicationContext(),"反馈失败!",Toast.LENGTH_SHORT).show());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_question_feedback);

        userId = UserUtil.getUserId(this);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        //初始化View
        initView();
        //初始化点击事件
        initClickListener();
        //初始化适配器
        initAdapter();
    }


    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_text = findViewById(R.id.back_text);
        title_text = findViewById(R.id.title_text);
        title_text.setText("问题反馈");

        question_title_text = findViewById(R.id.question_title_text);
        question_desc_text = findViewById(R.id.question_desc_text);
        feedback_btn = findViewById(R.id.feedback_btn);

        rvPic = findViewById(R.id.rvPic);
        tvNum = findViewById(R.id.tvNum);
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
                Toast.makeText(getApplicationContext(),"标题/内容为空!",Toast.LENGTH_SHORT).show();


            Question question = new Question();
            question.setTitle(questionTitle);
            question.setDescription(questionDesc);
            question.setUserid(userId);

            //String json = new Gson().toJson(question);

            //HttpUtil.post(Request.URL + "/app/question/addQuestion",json,feedbackListener);
            List<File> list = new ArrayList<>();
            if(fileList.size() > 1){
                for(int i=1;i<fileList.size();i++){
                    list.add(fileList.get(i).getFile());
                }
            }
            HttpUtil.postMulti(Request.URL + "/app/question/addQuestion",question,list,feedbackListener);
        });
    }

    private void initAdapter(){
        fileList.add(new LoadFileVo());
        loadPictureAdapter = new LoadPictureAdapter(this,fileList);
        rvPic.setLayoutManager(new GridLayoutManager(this,3));
        rvPic.setAdapter(loadPictureAdapter);

        loadPictureAdapter.setListener(new LoadPictureAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position) {
                if(fileList.size() > 1){
                    showToast("最多上传1张图片!");
                }else{
                    selectPic();  //选择添加图片方法
                }
            }

            @Override
            public void delete(View view) {
                tvNum.setText((fileList.size()-1) + "/1");
            }
        });
    }

    private void selectPic(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            if(data != null){
                Uri uri = data.getData();
                saveUriToFile(uri,0);
            }
        }
    }

    @SuppressLint("Range")
    private void saveUriToFile(Uri uriImage, int type){
        Bitmap photoBmp = null;
        if(uriImage != null){
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                photoBmp = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uriImage),null,options);
                File file = new File("");
                if(type == 0){
                    if("content".equalsIgnoreCase(uriImage.getScheme())){
                        String filePath=null;
                        String[] projection = {MediaStore.Images.Media.DATA};
                        CursorLoader cursorLoader = new CursorLoader(this,uriImage,projection,null,null,null);
                        Cursor cursor = cursorLoader.loadInBackground();
                        if(cursor!=null) {
                            cursor.moveToFirst();
                            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
                            cursor.close();
                            file = new File(filePath);
                        }
                    }
                }
                fileList.add(new LoadFileVo(file,false,photoBmp));
                tvNum.setText((fileList.size()-1) + "/1");

            }catch (Exception e){

            }
        }
    }

    private void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }



}