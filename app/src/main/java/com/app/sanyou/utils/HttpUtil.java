package com.app.sanyou.utils;

import androidx.annotation.NonNull;

import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.ReqMediaType;
import com.app.sanyou.constants.ResponseStatus;
import com.app.sanyou.entity.Question;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    private static final String TAG = "HttpUtil";
    private static volatile OkHttpClient client;

    private HttpUtil(){}

    private static OkHttpClient getClient(){
        if(client == null){
            synchronized (HttpUtil.class){
                client = new OkHttpClient().newBuilder().build();
            }
        }
        return client;
    }

    /**
     * Get请求
     * @param url 请求地址
     */
    public static void get(String url, CallListener listener){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        if(client == null)  client = getClient();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                JsonResult jsonResult = new JsonResult();
                jsonResult.setStatus(ResponseStatus.ERROR);
                jsonResult.setMsg(e.getMessage());
                if(listener != null){
                    listener.failure(jsonResult);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                JsonResult jsonResult = gson.fromJson(json,JsonResult.class);
                if(listener != null){
                    if(jsonResult.getStatus() == ResponseStatus.SUCCESS){
                        listener.success(jsonResult);
                    }else{
                        listener.failure(jsonResult);
                    }
                }
            }
        });
    }

    /**
     * Post请求
     * @param url 请求地址
     * @param postData 请求参数
     * @param listener 请求之后执行操作的监听器
     */
    public static void post(String url, String postData, CallListener listener){
        RequestBody requestBody = RequestBody.create(postData, ReqMediaType.JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        if(client == null)  client = getClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                JsonResult jsonResult = new JsonResult();
                jsonResult.setStatus(ResponseStatus.ERROR);
                jsonResult.setMsg(e.getMessage());
                if(listener != null){
                    listener.failure(jsonResult);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                JsonResult jsonResult = gson.fromJson(json,JsonResult.class);
                if(listener != null){
                    if(jsonResult.getStatus() == ResponseStatus.SUCCESS){
                        listener.success(jsonResult);
                    }else{
                        listener.failure(jsonResult);
                    }
                }
            }
        });
    }

    public static void postMulti(String url, Question question, List<File> fileList, CallListener listener){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("title", question.getTitle());
        builder.addFormDataPart("description", question.getDescription());
        builder.addFormDataPart("userId", question.getUserid());
        for (File file : fileList) {
            builder.addFormDataPart("images",
                    file.getName(),
                    RequestBody.create(MediaType.parse(MediaTypeUtil.getMimeType(file.getName())),file));
        }
        MultipartBody build = builder.setType(MultipartBody.FORM).build();
        Request request = new Request.Builder()
                .url(url)
                .post(build).build();

        if(client == null)  client = getClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                JsonResult jsonResult = new JsonResult();
                jsonResult.setStatus(ResponseStatus.ERROR);
                jsonResult.setMsg(e.getMessage());
                if(listener != null){
                    listener.failure(jsonResult);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                JsonResult jsonResult = gson.fromJson(json,JsonResult.class);
                if(listener != null){
                    if(jsonResult.getStatus() == ResponseStatus.SUCCESS){
                        listener.success(jsonResult);
                    }else{
                        listener.failure(jsonResult);
                    }
                }
            }
        });
    }

    public static void postMulti(String url, String userId, File file, CallListener listener){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("userId", userId);
        builder.addFormDataPart("image",
                file.getName(),
                RequestBody.create(MediaType.parse(MediaTypeUtil.getMimeType(file.getName())),file));

        MultipartBody build = builder.setType(MultipartBody.FORM).build();
        Request request = new Request.Builder()
                .url(url)
                .post(build).build();

        if(client == null)  client = getClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                JsonResult jsonResult = new JsonResult();
                jsonResult.setStatus(ResponseStatus.ERROR);
                jsonResult.setMsg(e.getMessage());
                if(listener != null){
                    listener.failure(jsonResult);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                JsonResult jsonResult = gson.fromJson(json,JsonResult.class);
                if(listener != null){
                    if(jsonResult.getStatus() == ResponseStatus.SUCCESS){
                        listener.success(jsonResult);
                    }else{
                        listener.failure(jsonResult);
                    }
                }
            }
        });
    }

}
