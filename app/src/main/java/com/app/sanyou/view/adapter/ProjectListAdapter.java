package com.app.sanyou.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.entity.ProjectVo;
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

        holder.projectNameText.setText(projectVo.getProjectName());
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, ProjectDetailActivity.class);
            intent.putExtra("projectId",projectVo.getProjectId());
            intent.putExtra("projectName",projectVo.getProjectName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(dataSource == null) return 0;
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView projectNameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projectNameText = itemView.findViewById(R.id.projectName);
        }
    }
}
