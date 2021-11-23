package com.app.sanyou.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sanyou.R;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.Question;
import com.app.sanyou.utils.StringUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private List<Question> dataSource;

    private Context context;

    private String uil = Request.URL + "/images/";

    public void setDataSource(List<Question> dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public MessageListAdapter(Context context){
        this.context = context;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 200:
                    Map<String,Object> map = (Map<String, Object>) msg.obj;
                    Bitmap bitmap = (Bitmap) map.get("bitmap");
                    ImageView iv = (ImageView) map.get("iv");
                    iv.setImageBitmap(bitmap);
            }
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = dataSource.get(position);

        if(question!=null){
            //设置截图
            if(StringUtil.isNotNull(question.getUrl()))
                updateImage(question.getUrl(),holder.ivImg);
            //设置标题
            if(StringUtil.isNotNull(question.getTitle()))
                holder.tvTitle.setText(question.getTitle());
            else
                holder.tvTitle.setText("");
            //设置内容
            if(StringUtil.isNotNull(question.getDescription()))
                holder.tvContent.setText(question.getDescription());
            else
                holder.tvContent.setText("");
            //设置是否处理的图片
            if(question.getIsHandled()!=null){
                if(question.getIsHandled()==1){
                    holder.ivHandleImg.setImageResource(R.drawable.ic_handled);
                }else{
                    holder.ivHandleImg.setImageResource(R.drawable.ic_not_handled);
                }
            }
            //如果是最后一条数据,隐藏分割线
            if(position == dataSource.size()-1)
                holder.ivSpilt.setVisibility(View.GONE);
        }
    }

    private void updateImage(String imageName,ImageView imageView){
        new Thread(){
            @Override
            public void run() {
                try {
                    if(StringUtil.isNotNull(imageName)){
                        URL url = new URL(uil + imageName);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(10000);
                        int code = connection.getResponseCode();
                        if(code == 200){
                            InputStream inputStream = connection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            Message msg = Message.obtain();
                            Map<String,Object> map = new HashMap<>();
                            map.put("bitmap",bitmap);
                            map.put("iv",imageView);
                            msg.obj = map;
                            msg.what = 200;
                            handler.sendMessage(msg);
                            inputStream.close();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        if(dataSource == null)
            return 0;
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImg;
        private TextView tvTitle;
        private TextView tvContent;
        private ImageView ivHandleImg;
        private ImageView ivSpilt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImg = itemView.findViewById(R.id.ivImg);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivHandleImg = itemView.findViewById(R.id.ivHandleImg);
            ivSpilt = itemView.findViewById(R.id.ivSpilt);
        }
    }
}
