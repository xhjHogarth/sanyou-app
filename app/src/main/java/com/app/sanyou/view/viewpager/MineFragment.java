package com.app.sanyou.view.viewpager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.sanyou.R;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.app.sanyou.view.user.UserInfoActivity;
import com.google.gson.Gson;

import java.util.Objects;

public class MineFragment extends Fragment {

    public static final String CONTEXT = "context";
    private Button signOutBtn;
    private Context context;
    private ImageView imageView;

    public static MineFragment getInstance(Context context){
        MineFragment mineFragment = new MineFragment();
        mineFragment.context = context;
        return mineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment,null);

        signOutBtn = view.findViewById(R.id.sign_out_btn);
        signOutBtn.setOnClickListener(v -> {
            UserUtil.loginOut(context);

            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        });

        imageView = view.findViewById(R.id.user_info_setting);
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

        return view;
    }
}
