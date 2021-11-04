package com.app.sanyou.view.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.app.sanyou.view.adapter.LoadPictureAdapter;
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

    private String mPhotoPath;
    private Uri uriImage;
    private File mPhotoFile;

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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_question_feedback);

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
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();


            Question question = new Question();
            question.setTitle(questionTitle);
            question.setDescription(questionDesc);

            String json = new Gson().toJson(question);

            HttpUtil.post(Request.URL + "/app/question/addQuestion",json,feedbakListener);
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
                if(fileList.size() > 2){
                    showToast("一次最多上传2张图片!");
                }else{
                    selectPic();  //选择添加图片方法
                }
            }

            @Override
            public void delete(View view) {
                tvNum.setText((fileList.size()-1) + "/2");
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

//        final CharSequence[] items = {"相册","拍照"};
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("添加图片");
//        dialog.setItems(items, (dlg, which) -> {
//            if(which == 0){
//
//            }else{
//                try {
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                    mPhotoPath = getSDPath() + "/" + getPhotoFileName();
//                    mPhotoFile = new File(mPhotoPath);
//                    if(!mPhotoFile.exists()){
//                        mPhotoFile.createNewFile();
//                    }
//                    uriImage = FileProvider.getUriForFile(this,"com.app.sanyou.provider",mPhotoFile);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT,uriImage);
//                    startActivityForResult(intent,1);
//                }catch (Exception e){
//
//                }
//            }
//        }).create();
//        dialog.show();

    }

//    private String getSDPath(){
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//        if(sdCardExist){
//            sdDir = Environment.getExternalStorageDirectory();
//        }
//        return sdDir.toString();
//    }
//
//    private String getPhotoFileName(){
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
//        return sdf.format(date) + ".jpg";
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;
//            Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath,options);
//            if(bitmap != null){
//                if(uriImage != null){
//                    saveUritoFile(uriImage,1);
//                }
//                if(!bitmap.isRecycled()){
//                    bitmap.recycle();
//                }
//            }
//        }

        if(requestCode == 0){
            if(data != null){
                Uri uri = data.getData();
                saveUritoFile(uri,0);
            }
        }
    }

    private void saveUritoFile(Uri uriImage,int type){
        Bitmap photoBmp = null;
        if(uriImage != null){
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                photoBmp = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uriImage),null,options);
                File file = new File("");
//                if(type == 0){
//
//                }else{
//                    if(mPhotoFile != null)
//                        file = mPhotoFile;
//                }
                fileList.add(new LoadFileVo(file,false,photoBmp));
                tvNum.setText((fileList.size()-1) + "/2");

            }catch (Exception e){

            }
        }
    }

    private void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }



}