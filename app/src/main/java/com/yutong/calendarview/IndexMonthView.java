package com.yutong.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.text.SimpleDateFormat;
import java.util.Date;



public class IndexMonthView extends MonthView {
    private Paint mSchemeBasicPaint = new Paint();
    private int mPadding;
    private int mH, mW;
    private int imY, imM, imD;

    public IndexMonthView(Context context) {
        super(context);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setColor(0xffd5d5d5);
        mSchemeBasicPaint.setFakeBoldText(true);
        mPadding = dipToPx(getContext(), 4);
        mH = dipToPx(getContext(), 2);
        mW = dipToPx(getContext(), 8);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        String[] ymd = str.split("-");
        imY = Integer.valueOf(ymd[0]);
        imM = Integer.valueOf(ymd[1]);
        imD = Integer.valueOf(ymd[2]);
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
       /* mSelectedPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x + mPadding, y + mPadding, x + mItemWidth - mPadding, y + mItemHeight - mPadding, mSelectedPaint);*/
        mSelectedPaint.setStyle(calendar.isCurrentDay() ? Paint.Style.FILL : Paint.Style.STROKE);
        float l1 = ((x + mItemWidth - mPadding) - (x + mPadding)) / 2;
        float cx = (x + mPadding) + l1;
        float l2 = ((y + mItemHeight - mPadding) - (y + mPadding)) / 2;
        float cy = (y + mPadding) + l2;
        canvas.drawCircle(cx, cy - Constant.OFFSET, Constant.ROUND, mSelectedPaint);
        onDrawText(canvas, calendar, x, y, hasScheme, calendar.isCurrentDay());
        return true;
    }

    /**
     * onDrawSelected
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        mSchemeBasicPaint.setColor(calendar.getSchemeColor());
        //矩形标记
        if (!Constant.ROUND_SQUARE) {
            canvas.drawRect(x + mItemWidth / 2 - mW / 2,
                    y + mItemHeight - mH * 2 - mPadding,
                    x + mItemWidth / 2 + mW / 2,
                    y + mItemHeight - mH - mPadding, mSchemeBasicPaint);
        } else {
            //圆点标记
            int l1 = ((x + mItemWidth / 2 + mW / 2) - (x + mItemWidth / 2 - mW / 2)) / 2;
            int zx = (x + mItemWidth / 2 - mW / 2) + l1;
            int l2 = ((y + mItemHeight - mH - mPadding) - (y + mItemHeight - mH * 2 - mPadding)) / 2;
            int zy = (y + mItemHeight - mH * 2 - mPadding) + l2;
            canvas.drawCircle(zx, zy, Constant.DOT, mSchemeBasicPaint);
        }
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 6;
        if (isSelected) {
            mCurDayTextPaint.setColor(getResources().getColor(R.color.today_select));
        } else {
            if (imY < calendar.getYear()) {
                mOtherMonthTextPaint.setColor(getResources().getColor(R.color.yesterday));
            } else if (imY > calendar.getYear()) {
                mOtherMonthTextPaint.setColor(getResources().getColor(R.color.tomorrow));
            } else {
                if (imM < calendar.getMonth()) {
                    mOtherMonthTextPaint.setColor(getResources().getColor(R.color.yesterday));
                } else if (imM > calendar.getMonth()) {
                    mOtherMonthTextPaint.setColor(getResources().getColor(R.color.tomorrow));
                } else {
                    if (imD < calendar.getDay()) {
                        mOtherMonthTextPaint.setColor(getResources().getColor(R.color.yesterday));
                    } else if (imD > calendar.getDay()) {
                        mOtherMonthTextPaint.setColor(getResources().getColor(R.color.tomorrow));
                    }
                }
            }
            // System.out.println(str+"   "++" "+calendar.getMonth()+"  "+calendar.getDay());
            mCurDayTextPaint.setColor(getResources().getColor(R.color.today_unselected));
        }
        if (hasScheme) {
           /* canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);*/
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint : mOtherMonthTextPaint);

           /* //农历信息
           canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
                            mCurMonthLunarTextPaint);*/

        } else {
           /* canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);*/
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint : mOtherMonthTextPaint);
            //农历信息
            // canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
        }

    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
