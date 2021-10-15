package com.app.sanyou.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtil {

    public static Gson gson = new Gson();

    public static <T> List<T> jsonToList(Object object, Class<T[]> clazz){
        String json = gson.toJson(object);
        T[] list = gson.fromJson(json,clazz);
        return list == null ? new ArrayList<>() : Arrays.asList(list);
    }
}
