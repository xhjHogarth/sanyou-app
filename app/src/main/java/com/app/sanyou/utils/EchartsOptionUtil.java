package com.app.sanyou.utils;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;

import java.util.HashMap;
import java.util.List;

public class EchartsOptionUtil {

    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.title("折线图");
        option.legend("销量");
        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Line line = new Line();
        line.smooth(false).name("销量").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
        return option;
    }

    public static GsonOption getPieChartOptions(List<HashMap> mapList){
        GsonOption option = new GsonOption();

        option.title("检测数据实时统计");
        option.title().setX(X.center);

        option.tooltip().trigger(Trigger.item);
        option.tooltip().formatter("{b}: {c} ({d}%)");

        option.legend().orient(Orient.vertical);
        option.legend().setBottom("bottom");

        option.color("#FF6356","#FFAD00","#4CAE85");

        Pie pie = new Pie();
        pie.type(SeriesType.pie);
        pie.center("50%","60%").radius("55%");

        pie.itemStyle().emphasis().shadowBlur(10).shadowOffsetX(0).shadowColor("rgba(0, 0, 0, 0.5)");

        pie.setData(mapList);
        option.series(pie);

        return option;
    }


}
