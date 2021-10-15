package com.app.sanyou.view.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.sanyou.utils.StringUtil;
import com.github.abel533.echarts.json.GsonOption;

public class EchartView extends WebView {


    public EchartView(@NonNull Context context) {
        this(context,null);
    }

    public EchartView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public EchartView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        loadUrl("file:///android_asset/echarts.html");
    }

    /**
     * Android使用Echarts有两种方式，一种是使用Java代码生成GsonOption对象，将Echarts的所以属性通过Java代码生成
     * 使用下面的refreshEchartsWithOption(GsonOption option)方法
     * 另一种方式是将Echarts的样式都写在xml文件中，只需要将数据传过去就行
     * 使用下面的refreshEchartsWithOption(String dataJson)方法
     * @param option
     */
    public void refreshEchartsWithOption(String chartType,GsonOption option){
        if(option == null)
            return;

        String optionString = option.toString();
        String call = "";

        if("实时数据".equals(chartType)){
            call = "javascript:loadPieChart('" + optionString + "')";
        }else if("历史数据".equals(chartType)){

        }else if("周期数据".equals(chartType)){

        }else if("正太分布".equals(chartType)){

        }

        if(StringUtil.isNull(call))
            return;
        loadUrl(call);
    }

    public void refreshEchartsWithOption(String chartType,String dataJson){
        if(dataJson == null)
            return;

        String call = "";
        if("实时数据".equals(chartType)){
            call = "javascript:loadPieChart('" + dataJson + "')";
        }else if("历史数据".equals(chartType)){
            call = "javascript:loadHistoryLineChart('" + dataJson + "')";
        }else if("周期数据".equals(chartType)){
            call = "javascript:loadCycleLineChart('" + dataJson + "')";
        }else if("正太分布".equals(chartType)){
            call = "javascript:loadNDLineChart('" + dataJson + "')";
        }

        if(StringUtil.isNull(call))
            return;
        loadUrl(call);
    }
}
