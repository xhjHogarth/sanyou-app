package com.app.sanyou.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.entity.ContractData;

import java.util.List;

public class ProjectDetailAdapter extends RecyclerView.Adapter<ProjectDetailAdapter.ViewHolder> {

    private Context context;

    private List<ContractData> dataSource;

    public ProjectDetailAdapter(Context context) {
        this.context = context;
    }

    public void setDataSource(List<ContractData> dataSource){
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
        ContractData contractData = dataSource.get(position);

        if(position%2==1){
            holder.llItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
        holder.contractIdText.setText(contractData.getContractId());
        holder.contractNameText.setText(contractData.getContractName());
    }

    @Override
    public int getItemCount() {
        if(dataSource == null) return 0;
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout llItem;
        private TextView contractIdText;
        private TextView contractNameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llItem = itemView.findViewById(R.id.llItem);
            contractIdText = itemView.findViewById(R.id.contractId);
            contractNameText = itemView.findViewById(R.id.contractName);
        }
    }
}
