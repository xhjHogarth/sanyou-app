package com.app.sanyou.view.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.sanyou.R;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.app.sanyou.view.user.CollectRecordActivity;
import com.app.sanyou.view.user.HistoryRecordActivity;
import com.app.sanyou.view.user.QuestionFeedbackActivity;
import com.app.sanyou.view.user.UserInfoActivity;

public class MineFragment extends Fragment {

    public static final String CONTEXT = "context";
    private Button signOutBtn;
    private Context context;
    private ImageView imageView;

    private ImageView back_img;
    private TextView back_text;
    private TextView title_text;

    private LinearLayout collect_ll;
    private LinearLayout history_ll;
    private LinearLayout help_ll;

    public static MineFragment getInstance(Context context){
        MineFragment mineFragment = new MineFragment();
        mineFragment.context = context;
        return mineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment,null);

        initView(view);
        initClickListener();

        return view;
    }


    private void initView(View view){
        signOutBtn = view.findViewById(R.id.sign_out_btn);
        imageView = view.findViewById(R.id.user_info_setting);
        //初始化顶部栏
        back_img = view.findViewById(R.id.back_img);
        back_text = view.findViewById(R.id.back_text);
        title_text = view.findViewById(R.id.title_text);
        //隐藏返回按钮和文字
        back_img.setVisibility(View.GONE);
        back_text.setVisibility(View.GONE);
        title_text.setText("个人中心");

        collect_ll = view.findViewById(R.id.collect_ll);
        history_ll = view.findViewById(R.id.history_ll);
        help_ll = view.findViewById(R.id.help_ll);
    }

    private void initClickListener() {
        //退出登录
        signOutBtn.setOnClickListener(v -> {
            UserUtil.loginOut(context);

            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        });



        //个人信息设置
        imageView.setOnClickListener(v -> {

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
    }
}
