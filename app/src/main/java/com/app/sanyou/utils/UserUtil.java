package com.app.sanyou.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserUtil {


    /**
     * 判断用户登录是否过期
     * @param context
     * @return
     */
    public static boolean isLoginExpired(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId",null);
        String userName = sharedPreferences.getString("username",null);
        if(StringUtil.isNull(userId) || StringUtil.isNull(userName)){
            return true;
        }
        return false;
    }

    /**
     * 退出登录，清除登录凭证
     * @param context
     */
    public static void loginOut(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("userId","");
        edit.putString("username","");
        edit.commit();
    }

    public static String getUserId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId",null);
        return StringUtil.isNull(userId) ? "" : userId;
    }

    public static String getUserName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);
        return StringUtil.isNull(username) ? "" : username;
    }
}
