package com.app.sanyou.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.entity.LoadFileVo;

import java.util.List;

public class LoadPictureAdapter extends RecyclerView.Adapter<LoadPictureAdapter.MyViewHolder>{

    private Context context;
    private List<LoadFileVo> fileList;
    private int pictureNum = 2;

    public LoadPictureAdapter(Context context,List<LoadFileVo> fileList){
        this.context = context;
        this.fileList = fileList;
    }

    public LoadPictureAdapter(Context context, List<LoadFileVo> fileList, int pictureNum) {
        this.context = context;
        this.fileList = fileList;
        this.pictureNum = pictureNum;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.picture_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position == 0 && fileList.get(position).getBitmap() == null){
            holder.ivPic.setImageResource(R.drawable.ic_pick);
            holder.ivPic.setOnClickListener(v->{
                listener.click(v,position);
            });
            holder.ivDel.setVisibility(View.INVISIBLE);
        }else{
            holder.ivPic.setImageBitmap(fileList.get(position).getBitmap());
            holder.ivDel.setVisibility(View.VISIBLE);
        }

        holder.ivDel.setOnClickListener(v->{
            if(fileList.get(position).isUpload()){
                Toast.makeText(context,"该图片已上传!",Toast.LENGTH_SHORT).show();
            }else{
                fileList.remove(position);
                if(fileList.size() == pictureNum-1 && fileList.get(0).getBitmap()!=null){
                    fileList.add(0,new LoadFileVo());
                }
                notifyDataSetChanged();
                listener.delete(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(fileList == null) return 0;
        return fileList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPic;
        private ImageView ivDel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPic = itemView.findViewById(R.id.ivPic);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }

    public interface OnItemClickListener {

          void click(View view, int position);

          void delete(View view);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
