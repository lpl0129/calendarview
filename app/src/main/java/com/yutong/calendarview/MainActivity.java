package com.yutong.calendarview;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateSelectedListener, CalendarView.OnViewChangeListener {
    @Bind(R.id.calendarView)
    CalendarView mCalendarView;
    @Bind(R.id.tv_year_month)
    TextView tv_year_month;
    @Bind(R.id.today_iv)
    ImageView today_iv;
    @Bind(R.id.listview)
    ListView listview;
    int imY, imM, imD;
    ListAdapter adapter;
    String[] ls=new String[]{"数据1","数据2","数据3","数据4","数据5","数据6","数据7",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnViewChangeListener(this);
        tv_year_month.setText(String.valueOf(mCalendarView.getCurYear()) + "年" + mCalendarView.getCurMonth() + "月");
        today_iv = (ImageView) findViewById(R.id.today_iv);
        // Color.TRANSPARENT;
        initData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        String[] ymd = str.split("-");
        imY = Integer.valueOf(ymd[0]);
        imM = Integer.valueOf(ymd[1]);
        imD = Integer.valueOf(ymd[2]);
        adapter=new ListAdapter(this,ls);
        listview.setAdapter(adapter);
    }

    protected void initData() {
        final List<Calendar> schemes = new ArrayList<>();
        final int year = mCalendarView.getCurYear();
        final int month = mCalendarView.getCurMonth();
        //这里的颜色是小圆点的颜色
        schemes.add(getSchemeCalendar(year, month, 3, 0xffd5d5d5, "课"));
        schemes.add(getSchemeCalendar(year, month, 6, 0xffd5d5d5, "课"));
        schemes.add(getSchemeCalendar(year, month, 9, 0xffd5d5d5, "课"));
        schemes.add(getSchemeCalendar(year, month, 13, 0xffd5d5d5, "课"));
        schemes.add(getSchemeCalendar(year, month, 14, 0xffd5d5d5, "课"));
        schemes.add(getSchemeCalendar(year, month, 15, 0xffd5d5d5, "课"));
        schemes.add(getSchemeCalendar(year, month, 18, 0xffd5d5d5, "课"));
        schemes.add(getSchemeCalendar(year, month, 25, 0xffd5d5d5, "课"));
        mCalendarView.setSchemeDate(schemes);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        if (calendar.getYear() == imY && calendar.getMonth() == imM && calendar.getDay() == imD) {//当前选中的是不是今天
            today_iv.setVisibility(View.GONE);
        } else {
            today_iv.setVisibility(View.VISIBLE);
        }
        tv_year_month.setText(String.valueOf(calendar.getYear()) + "年" + calendar.getMonth() + "月");
        //在这里处理日历与历史listview的数据联动
    }

    @Override
    public void onViewChange(boolean isMonthView) {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.today_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.today_iv:
                mCalendarView.scrollToCurrent();
                break;

        }
    }
}
