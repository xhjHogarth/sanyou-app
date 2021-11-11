package com.app.sanyou.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sanyou.R;
import com.app.sanyou.entity.IndustryData;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScanResultListAdapter extends BaseExpandableListAdapter{

    private Context context;

    private List<IndustryData> dataSource;

    public ScanResultListAdapter(Context context) {
        this.context = context;
    }

    public void setDataSource(List<IndustryData> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getGroupCount() {
        if(dataSource == null)
            return 0;
        return dataSource.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataSource.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataSource.get(groupPosition);
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

        GroupHolder groupHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.scan_result_list_group,null);
            groupHolder = new GroupHolder();
            groupHolder.tvGroupTime = convertView.findViewById(R.id.tvGroupTime);
            groupHolder.tvGroupTitle = convertView.findViewById(R.id.tvGroupTitle);
            groupHolder.ivElImg = convertView.findViewById(R.id.ivElImg);
            groupHolder.ivLeftGroupLine = convertView.findViewById(R.id.ivLeftGroupLine);
            groupHolder.llListHeader = convertView.findViewById(R.id.llListHeader);

            convertView.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder) convertView.getTag();
        }

        if(isExpanded){
            groupHolder.ivElImg.setImageResource(R.drawable.ic_item_expand);
            groupHolder.llListHeader.setBackgroundResource(R.drawable.list_top_bg_demo);
        }else{
            groupHolder.ivElImg.setImageResource(R.drawable.ic_item_close);
            groupHolder.llListHeader.setBackgroundResource(R.drawable.list_top_bg_close_demo);
        }
        IndustryData data = dataSource.get(groupPosition);
        //设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        if(data!=null && data.getDatatime() != null){
            groupHolder.tvGroupTime.setText(simpleDateFormat.format(data.getDatatime()));
        }else{
            groupHolder.tvGroupTime.setText("");
        }
        //设置标题
        groupHolder.tvGroupTitle.setText("第" + (dataSource.size()-groupPosition) + "次检测");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.scan_result_list_item,null);
            itemHolder = new ItemHolder();
            itemHolder.tvItemName = convertView.findViewById(R.id.tvItemName);
            itemHolder.ivLeftItemLine = convertView.findViewById(R.id.ivLeftItemLine);

            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ItemHolder) convertView.getTag();
        }

        IndustryData data = dataSource.get(groupPosition);
        if(data != null && data.getMax() != null)
            itemHolder.tvItemName.setText("平面度MAX: " + data.getMax());
        else
            itemHolder.tvItemName.setText("");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        public TextView tvGroupTime;
        public TextView tvGroupTitle;
        public ImageView ivElImg;
        public ImageView ivLeftGroupLine;
        public LinearLayout llListHeader;
    }

    class ItemHolder{
        public TextView tvItemName;
        public ImageView ivLeftItemLine;
    }
}
