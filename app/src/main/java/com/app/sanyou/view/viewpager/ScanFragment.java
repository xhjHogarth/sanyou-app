package com.app.sanyou.view.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.app.sanyou.zxing.android.CaptureActivity;

public class ScanFragment extends Fragment {

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    private TextView search_btn;
    private EditText query_text;
    private ImageView ivDelete;

    private Button btn_scan;
    private TextView tv_scanResult;

    private Context context;
    private TabViewPagerActivity tabViewPagerActivity;

    private String scanCode;

    private Handler handler = new Handler();

    private CallListener checkExistListener = new CallListener() {
        @Override
        public void success(JsonResult result) {
            Intent intent = new Intent(context, ScanResultActivity.class);
            intent.putExtra("scanCode",scanCode);
            intent.putExtra("tag","1");
            startActivity(intent);
        }

        @Override
        public void failure(JsonResult result) {
            getActivity().runOnUiThread(()->Toast.makeText(context,"阴极板不存在!",Toast.LENGTH_SHORT).show());
        }
    };

    public static ScanFragment getInstance(Context context,TabViewPagerActivity tabViewPagerActivity) {
        ScanFragment scanFragment = new ScanFragment();
        scanFragment.context = context;
        scanFragment.tabViewPagerActivity = tabViewPagerActivity;
        return scanFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_fragment,null);

        //初始化view
        initView(view);
        //初始化点击事件
        initClickListener();

        return view;
    }

    /**
     * 初始化view
     */
    private void initView(View view){
//        tv_scanResult = view.findViewById(R.id.tv_scanResult);
//        btn_scan = view.findViewById(R.id.btn_scan);

        search_btn = view.findViewById(R.id.search_btn);
        query_text = view.findViewById(R.id.query_text);
        ivDelete = view.findViewById(R.id.ivDelete);
    }

    /**
     * 初始化点击事件
     */
    private void initClickListener(){
        //扫码按钮点击事件
//        btn_scan.setOnClickListener(v -> {
//            //动态权限申请
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(tabViewPagerActivity, new String[]{Manifest.permission.CAMERA}, 1);
//            } else {
//                goScan();
//            }
//        });

        //设置删除图片的点击事件
        ivDelete.setOnClickListener(v->{
            query_text.setText("");
            ivDelete.setVisibility(View.GONE);
        });

        //EditText添加监听
        query_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                    ivDelete.setVisibility(View.GONE);
                else{
                    ivDelete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //搜索按钮的点击事件
        search_btn.setOnClickListener(v->{
            scanCode = query_text.getText().toString();
            if(StringUtil.isNull(scanCode)){
                Toast.makeText(context,"阴极板编码不能为空!",Toast.LENGTH_SHORT).show();
            }else{
                String userId = UserUtil.getUserId(context);
                if(StringUtil.isNull(userId)){
                    UserUtil.loginOut(context);
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }

                //判断阴极板是否存在
                HttpUtil.get(Request.URL + "/app/product/checkExist?code=" + scanCode,checkExistListener);
            }
        });
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan(){
        Intent intent = new Intent(context, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    Toast.makeText(context, "你拒绝了权限申请，可能无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                tv_scanResult.setText("你扫描到的内容是：" + content);
            }
        }
    }

}
