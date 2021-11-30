package com.app.sanyou.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.entity.ProjectVo;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.view.viewpager.ProjectDetailActivity;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private List<ProjectVo> dataSource;

    private Context context;

    public void setDataSource(List<ProjectVo> dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public ProjectListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_project_item,parent,false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectVo projectVo = dataSource.get(position);

        String projectName = "";
        if(StringUtil.isNotNull(projectVo.getCustomProjectName()))
            projectName = projectVo.getCustomProjectName();
        else
            projectName = projectVo.getProjectName();

        holder.projectNameText.setText(projectName);

        String finalProjectName = projectName;
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, ProjectDetailActivity.class);
            intent.putExtra("projectId",projectVo.getProjectId());
            intent.putExtra("projectName", finalProjectName);
            intent.putExtra("position",position);
            context.startActivity(intent);
        });

        if(position == dataSource.size()-1){
            holder.ivSpilt.setVisibility(View.GONE);
        }
        if(position%4==0){
            holder.ivIcon.setImageResource(R.drawable.ic_project_img1);
        }else if(position%4==1){
            holder.ivIcon.setImageResource(R.drawable.ic_project_img2);
        }else if(position%4==2){
            holder.ivIcon.setImageResource(R.drawable.ic_project_img3);
        }else
            holder.ivIcon.setImageResource(R.drawable.ic_project_img4);
    }

    @Override
    public int getItemCount() {
        if(dataSource == null) return 0;
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView projectNameText;
        private ImageView ivIcon;
        private ImageView ivSpilt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projectNameText = itemView.findViewById(R.id.projectName);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            ivSpilt = itemView.findViewById(R.id.ivSpilt);
        }
    }
}
