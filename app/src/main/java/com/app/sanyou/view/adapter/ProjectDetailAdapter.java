package com.app.sanyou.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.entity.OrderVo;

import java.util.List;

public class ProjectDetailAdapter extends RecyclerView.Adapter<ProjectDetailAdapter.ViewHolder> {

    private Context context;

    private List<OrderVo> dataSource;

    public ProjectDetailAdapter(Context context) {
        this.context = context;
    }

    public void setDataSource(List<OrderVo> dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.project_contract_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderVo order = dataSource.get(position);

//        if(position%2==1){
//            holder.llItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
//        }
        holder.orderCodeText.setText(order.getOrderCode());
        holder.orderNameText.setText(order.getOrderName());
        if(position == dataSource.size()-1)
            holder.ivSpilt.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if(dataSource == null) return 0;
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout llItem;
        private TextView orderCodeText;
        private TextView orderNameText;
        private ImageView ivSpilt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llItem = itemView.findViewById(R.id.llItem);
            orderCodeText = itemView.findViewById(R.id.contractId);
            orderNameText = itemView.findViewById(R.id.contractName);
            ivSpilt = itemView.findViewById(R.id.ivSpilt);
        }
    }
}
