package com.app.sanyou.view.viewpager;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.app.sanyou.R;
import com.app.sanyou.common.CallListener;
import com.app.sanyou.common.JsonResult;
import com.app.sanyou.constants.Request;
import com.app.sanyou.entity.Equipment;
import com.app.sanyou.entity.IndustryDataVo;
import com.app.sanyou.utils.DateUtils;
import com.app.sanyou.utils.EchartsOptionUtil;
import com.app.sanyou.utils.HttpUtil;
import com.app.sanyou.utils.JsonUtil;
import com.app.sanyou.utils.StringUtil;
import com.app.sanyou.utils.UserUtil;
import com.app.sanyou.view.login.LoginActivity;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "DataFragment";
    private EchartView chart;

    private Spinner echartsSpinner;
    private Spinner equipmentsSpinner;
    private TextView dateText;
    private Button searchBtn;

    private Context context;
    private FragmentManager fragmentManager;

    private String userId;
    private List<Equipment> equipmentList;

    /**
     * 实时数据  历史数据  周期数据  正太分布
     */
    private String chartType;
    private String equipmentNo;
    private Date startTime;
    private Date endTime;

    private List<IndustryDataVo> chartDataList;

    private Handler handler = new Handler();

    public static DataFragment getInstance(Context context, FragmentManager fragmentManager){
        DataFragment dataFragment = new DataFragment();
        dataFragment.context = context;
        dataFragment.fragmentManager = fragmentManager;
        return dataFragment;
    }

    private CallListener equipmentListener = new CallListener() {

        Gson gson = new Gson();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void success(JsonResult result) {
            equipmentList = JsonUtil.jsonToList(result.getData(),Equipment[].class);
            String[] equipments = equipmentList.stream().map(t -> t.getEquipNo()).collect(Collectors.toList()).toArray(new String[0]);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, equipments);
            handler.post(() -> {
                equipmentsSpinner.setAdapter(adapter);
            });
        }

        @Override
        public void failure(JsonResult result) {
            Log.d(TAG, "failure: " + gson.toJson(result));
            Looper.prepare();
            Toast.makeText(context,result.getMsg(),Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    };

    private CallListener echartsListener = new CallListener() {

        Gson gson = new Gson();

        @Override
        public void success(JsonResult result) {
            Log.d(TAG, "success: " + gson.toJson(result));
            chartDataList = JsonUtil.jsonToList(result.getData(),IndustryDataVo[].class);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    refreshChart(chartType);
                }
            });
        }

        @Override
        public void failure(JsonResult result) {
            Log.d(TAG, "failure: " + gson.toJson(result));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_fragment,null);

        //初始化view
        initView(view);

        //判断用户登录是否过期
        userId = UserUtil.getUserId(context);
        if(StringUtil.isNull(userId)){
            UserUtil.loginOut(context);
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }

        //加载用户拥有权限的设备数据
        HttpUtil.get(Request.URL + "/equipment/getUserEquip?userId=" + userId,equipmentListener);

        dateText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show(fragmentManager, "Datepickerdialog");
        });

        searchBtn.setOnClickListener(v -> {
            chartType = echartsSpinner.getSelectedItem().toString();
            equipmentNo = equipmentsSpinner.getSelectedItem().toString();
            if(startTime == null || endTime == null){
                Toast.makeText(context,"请选择日期!",Toast.LENGTH_SHORT).show();
            }else{
                Map<String,String> postData = new HashMap<>();
                postData.put("lineno",equipmentNo);
                postData.put("userId",userId);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                postData.put("startTime",sdf.format(startTime));
                postData.put("endTime",sdf.format(endTime));
                Gson gson = new Gson();
                String json = gson.toJson(postData);

                String url = "";
                if("实时数据".equals(chartType)){
                    url = "/industryData/getPieChart";
                }else if("历史数据".equals(chartType)){
                    url = "/industryData/getLineChart";
                }else if("周期数据".equals(chartType)){
                    url = "/industryData/getCycleLineChart";
                }else if("正太分布".equals(chartType)){
                    url = "/industryData/getNormalLineChart";
                }
                HttpUtil.post(Request.URL + url, json, echartsListener);
            }
        });

        chart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        return view;
    }

    private void initView(View view){
        echartsSpinner = view.findViewById(R.id.echarts_spinner);
        equipmentsSpinner = view.findViewById(R.id.equipments_spinner);
        dateText = view.findViewById(R.id.date_text);
        searchBtn = view.findViewById(R.id.search_btn);
        chart = view.findViewById(R.id.chart);
    }

    private void refreshChart(String chartType){
        Gson gson = new Gson();

        if("实时数据".equals(chartType)){
            List<HashMap> mapList = new ArrayList();
            for (IndustryDataVo industryDataVo : chartDataList) {
                HashMap map = new HashMap();
                map.put("name",industryDataVo.getName());
                map.put("value",industryDataVo.getValue());
                mapList.add(map);
            }

            String json = gson.toJson(mapList);

            //第一种展示Echarts图表的方式
            chart.refreshEchartsWithOption(chartType,EchartsOptionUtil.getPieChartOptions(mapList));
            //第二种展示Echarts图表的方式
            //chart.refreshEchartsWithOption(chartType,json);
        }else if("历史数据".equals(chartType)){
            if(chartDataList == null)
                return;

            String json = gson.toJson(chartDataList);
            chart.refreshEchartsWithOption(chartType,json);
        }else if("周期数据".equals(chartType)){
            if(chartDataList == null)
                return;

            String json = gson.toJson(chartDataList);
            chart.refreshEchartsWithOption(chartType,json);
        }else if("正太分布".equals(chartType)){
            if(chartDataList == null)
                return;

            String json = gson.toJson(chartDataList);
            chart.refreshEchartsWithOption(chartType,json);
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Calendar startCalendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
        startTime = startCalendar.getTime();
        Calendar endCalendar = new GregorianCalendar(yearEnd,monthOfYearEnd,dayOfMonthEnd);
        endTime = endCalendar.getTime();

        //开始时间的时间戳
        String timeStart = DateUtils.getTimeStart("" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
        //结束时间的时间戳
        String timeEnd = DateUtils.getTimeEnd("" + yearEnd + "年" + (monthOfYearEnd + 1) + "月" + dayOfMonthEnd + "日");

        String dateStr = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日" + " - " +
                yearEnd + "年" + (monthOfYearEnd + 1) + "月" + dayOfMonthEnd + "日";

        dateText.setText(dateStr);

        try {
            if (Long.valueOf(timeStart) > Long.valueOf(timeEnd)) {
                Toast.makeText(context, "结束时间不能小于开始时间", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(context, "选择时间的有误", Toast.LENGTH_SHORT).show();
        }
    }
}
