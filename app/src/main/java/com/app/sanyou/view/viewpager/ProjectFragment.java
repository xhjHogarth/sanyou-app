package com.app.sanyou.view.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.ProjectVo;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.JsonUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.adapter.ProjectAdapter;
import com.app.sanyou.view.login.LoginActivity;
import com.google.gson.Gson;

import java.util.List;

public class ProjectFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ProjectAdapter projectAdapter;
    private Context context;

    private List<ProjectVo> projectList;
    private String userId;

    private Handler handler = new Handler();

    public static ProjectFragment getInstance(Context context){
        ProjectFragment projectFragment = new ProjectFragment();
        projectFragment.context = context;
        return projectFragment;
    }

    CallListener projectListListener = new CallListener() {
        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            projectList = JsonUtil.jsonToList(result.getData(),ProjectVo[].class);
            handler.post(() -> {
                projectAdapter = new ProjectAdapter(context,projectList);
                expandableListView.setAdapter(projectAdapter);
            });
        }

        @Override
        public void failure(JsonResult result) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_fragment,null);

        expandableListView = view.findViewById(R.id.elv);

        userId = UserUtil.getUserId(context);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(context);
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
        HttpUtil.get(Request.URL + "/app/project/getProjectList?userId=" + userId,projectListListener);

        return view;
    }
}
