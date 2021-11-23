package com.app.sanyou.view.viewpager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.UserImage;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.app.sanyou.view.user.CollectRecordActivity;
import com.app.sanyou.view.user.HistoryRecordActivity;
import com.app.sanyou.view.user.MessageCenterActivity;
import com.app.sanyou.view.user.QuestionFeedbackActivity;
import com.app.sanyou.view.user.UserInfoActivity;
import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MineFragment extends Fragment {

    public static final String CONTEXT = "context";
    private Button signOutBtn;
    private Context context;

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private ImageView ivMyImg;
    private TextView txUsername;
    private TextView txId;

    private LinearLayout info_detail_ll;
    private LinearLayout collect_ll;
    private LinearLayout history_ll;
    private LinearLayout help_ll;
    private LinearLayout message_ll;

    private String userId="";
    private String username="";
    private String uil = Request.URL + "/images/" + "";
    private String imageName = "";

    public static MineFragment getInstance(Context context){
        MineFragment mineFragment = new MineFragment();
        mineFragment.context = context;
        return mineFragment;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 200:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ivMyImg.setImageBitmap(bitmap);
            }
        }
    };

    private CallListener getImageListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            UserImage userImage = gson.fromJson(gson.toJson(result.getData()),UserImage.class);
            if(userImage!=null && StringUtil.isNotNull(userImage.getFilename())) {
                imageName = userImage.getFilename();
                updateImage();
            }
        }

        @Override
        public void failure(JsonResult result) {

        }
    };

    private CallListener uploadImageListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            UserImage userImage = gson.fromJson(gson.toJson(result.getData()),UserImage.class);
            if(userImage!=null && StringUtil.isNotNull(userImage.getFilename())){
                imageName = userImage.getFilename();
                updateImage();
            }
        }

        @Override
        public void failure(JsonResult result) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment,null);

        userId = UserUtil.getUserId(context);
        username = UserUtil.getUserName(context);

        initView(view);
        initClickListener();

        HttpUtil.get(Request.URL + "/app/userImage/getUserImage?userId=" + userId, getImageListener);

        return view;
    }


    private void initView(View view){
        signOutBtn = view.findViewById(R.id.sign_out_btn);
        //初始化顶部栏
        back_img = view.findViewById(R.id.back_img);
        back_text = view.findViewById(R.id.back_text);
        title_text = view.findViewById(R.id.title_text);
        //隐藏返回按钮和文字
        back_img.setVisibility(View.GONE);
        back_text.setVisibility(View.GONE);
        title_text.setText("个人中心");

        ivMyImg = view.findViewById(R.id.ivMyImg);
        txUsername = view.findViewById(R.id.txUsername);
        txId = view.findViewById(R.id.txId);

        txUsername.setText("昵称:" + username);
        txId.setText("ID:" + userId);

        ivMyImg.setOnClickListener(v->{
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },1);
            }

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,0);
        });

        info_detail_ll = view.findViewById(R.id.info_detail_ll);
        collect_ll = view.findViewById(R.id.collect_ll);
        history_ll = view.findViewById(R.id.history_ll);
        help_ll = view.findViewById(R.id.help_ll);
        message_ll = view.findViewById(R.id.message_ll);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                photoBmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uriImage),null,options);
                File file = null;
                if(type == 0){
                    if("content".equalsIgnoreCase(uriImage.getScheme())){
                        String filePath=null;
                        String[] projection = {MediaStore.Images.Media.DATA};
                        CursorLoader cursorLoader = new CursorLoader(context,uriImage,projection,null,null,null);
                        Cursor cursor = cursorLoader.loadInBackground();
                        if(cursor!=null) {
                            cursor.moveToFirst();
                            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
                            cursor.close();
                            file = new File(filePath);
                        }
                    }
                }
                //ivMyImg.setImageBitmap(photoBmp);
                if(file!=null){
                    HttpUtil.postMulti(Request.URL + "/app/userImage/upload",userId,file,uploadImageListener);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void initClickListener() {
        //退出登录
        signOutBtn.setOnClickListener(v -> {
            UserUtil.loginOut(context);

            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        });


        //个人信息设置
        info_detail_ll.setOnClickListener(v -> {

            boolean loginExpired = UserUtil.isLoginExpired(context);

            Intent intent;
            if(loginExpired){
                intent = new Intent(context, LoginActivity.class);
            }else{
                intent = new Intent(context, UserInfoActivity.class);
            }
            startActivity(intent);
        });

        //跳转到我的收藏
        collect_ll.setOnClickListener(v->{
            Intent intent = new Intent(context, CollectRecordActivity.class);
            startActivity(intent);
        });

        //跳转到历史记录
        history_ll.setOnClickListener(v->{
            Intent intent = new Intent(context, HistoryRecordActivity.class);
            startActivity(intent);
        });

        //跳转到问题反馈
        help_ll.setOnClickListener(v->{
            Intent intent = new Intent(context, QuestionFeedbackActivity.class);
            startActivity(intent);
        });

        //跳转到消息中心
        message_ll.setOnClickListener(v->{
            Intent intent = new Intent(context, MessageCenterActivity.class);
            startActivity(intent);
        });
    }

    private void updateImage(){
        new Thread(){
            @Override
            public void run() {
                try {
                    if(StringUtil.isNotNull(imageName)){
                        URL url = new URL(uil + imageName);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(10000);
                        int code = connection.getResponseCode();
                        if(code == 200){
                            InputStream inputStream = connection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            Message msg = Message.obtain();
                            msg.obj = bitmap;
                            msg.what = 200;
                            handler.sendMessage(msg);
                            inputStream.close();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
