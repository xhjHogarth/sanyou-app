package com.app.sanyou.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sanyou.R;
import com.app.sanyou.entity.ProjectVo;
import com.app.sanyou.view.viewpager.ProjectDetailActivity;

import java.util.List;

public class ProjectAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {

    private Context context;
    private List<ProjectVo> projectList;


    public void setDatasource(List<ProjectVo> projectList){
        this.projectList = projectList;
    }


    public ProjectAdapter(Context context,List<ProjectVo> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        if (projectList == null)
            return 0;
        return projectList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(projectList == null || groupPosition >= projectList.size()
                || projectList.get(groupPosition).getContractDataList() == null)
            return 0;
        return projectList.get(groupPosition).getContractDataList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(projectList == null)
            return null;
        return projectList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder parentHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.project_list_item_level1,null);
            parentHolder = new ParentHolder();
            parentHolder.projectNameText = convertView.findViewById(R.id.projectName);
            convertView.setTag(parentHolder);
        }else{
            parentHolder = (ParentHolder) convertView.getTag();
        }
        String projectName = projectList.get(groupPosition).getProjectName();
        parentHolder.projectNameText.setText(projectName==null?"":projectName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.project_list_item_level2,null);
            childHolder = new ChildHolder();
            childHolder.itemRow = convertView.findViewById(R.id.item_row);
            childHolder.contractNameText = convertView.findViewById(R.id.contractName);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildHolder) convertView.getTag();
        }
        String contractName = projectList.get(groupPosition).getContractDataList().get(childPosition).getContractName();
        childHolder.contractNameText.setText(contractName == null?"":contractName);

        childHolder.itemRow.setOnClickListener(v -> {
            String projectId = projectList.get(groupPosition).getProjectId();
            String contractId = projectList.get(groupPosition).getContractDataList().get(childPosition).getContractId();
            Intent intent = new Intent(context, ProjectDetailActivity.class);
            intent.putExtra("projectId",projectId);
            intent.putExtra("contractId",contractId);
            context.startActivity(intent);
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    class ParentHolder{
        TextView projectNameText;
    }

    class ChildHolder{
        LinearLayout itemRow;
        TextView contractNameText;
    }
}
