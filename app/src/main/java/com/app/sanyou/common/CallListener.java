package com.app.sanyou.common;

/**
 * 请求成功或失败执行的操作
 */
public interface CallListener {

    /**
     * 请求成功之后的操作
     * @param result 请求成功返回的json对象
     */
    void success(JsonResult result);

    /**
     * 请求失败之后的操作
     * @param result 请求失败返回的json对象
     */
    void failure(JsonResult result);
}
