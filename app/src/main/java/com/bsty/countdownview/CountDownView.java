package com.bsty.countdownview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by bsty on 7/7/16.
 */
public class CountDownView extends Drawable {
    private static final String TAG = "CountDownView";
    private final static int PROGRESS_FACTOR = -360;
    private Paint mPaint;
    private Paint textPaint;
    private RectF mArcRect;
    private float radius;

    //当前进度条进度
    private float progress;
    //进度条颜色
    private int ringColor;
    //进度条宽度
    private int ringWidth;
    //倒计时数字
    private int showNumber;
    //字符串颜色
    private int textColor;
    //数字颜色
    private int numColor;

    private String showText = "正在支付";

    private int MAX_DOTS_COUNT = 3;

    Paint numPaint;


    //小圆点数量
    private int circularCount;

    private Paint dotPaint;

    private static final float PERCENT_CIRCLER_TO_HEIGHT = 3 / 14f;//半径占父控件高度的比例

    public CountDownView(int ringWidth, int ringColor, int showNumber, int textColor, int numColor) {
        mPaint = new Paint();
        numPaint = new Paint();
        mArcRect = new RectF();

        this.ringWidth = ringWidth;
        this.ringColor = ringColor;
        this.showNumber = showNumber;
        this.textColor = textColor;
        this.numColor = numColor;
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        drawRing(bounds, canvas);

        drawNum(canvas);
        drawText(bounds, canvas);
        drawDots(canvas);
    }

    /**
     * 画圆环
     *
     * @param bounds
     * @param canvas
     */
    private void drawRing(Rect bounds, Canvas canvas) {
        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();

        radius = size * PERCENT_CIRCLER_TO_HEIGHT;

        mPaint.setColor(ringColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(ringWidth);


        float cirX = bounds.centerX();
        float cirY = bounds.centerY() * (27 / 35f);

        canvas.translate(cirX, cirY);

        mArcRect.set(-radius, -radius, radius, radius);
        canvas.drawArc(mArcRect, -90, progress, false, mPaint);
    }

    /**
     * 画倒计时数字
     *
     * @param canvas
     */
    private void drawNum(Canvas canvas) {
        float textSize = radius * 0.75f * 1.5f;
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(textColor);
        mPaint.setStrokeWidth(ringWidth / 2);
        mPaint.setStyle(Paint.Style.FILL);
        float numX = 0;
        float numY = -(mPaint.descent() + mPaint.ascent()) / 2;
        canvas.drawText(Integer.toString(showNumber), numX, numY, mPaint);
    }

    /**
     * 画字符串
     *
     * @param canvas
     */
    private void drawText(Rect bounds, Canvas canvas) {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(ringWidth / 2);
        float textSize = radius * 0.4f;
        textPaint.setTextSize(textSize);
        float textX = 0;
        float textY = radius * 1.8f - (textPaint.descent() + textPaint.ascent()) / 2;
//        Log.d(TAG, "textY= " + textY);
        canvas.drawText(showText, textX, textY, textPaint);
    }

    /**
     * 画小圆点
     *
     * @param canvas
     */
    private void drawDots(Canvas canvas) {
        Rect textBound = new Rect();
        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(textColor);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setStrokeWidth(ringWidth / 2);

        textPaint.getTextBounds(showText, 0, showText.length(), textBound);
        float dotWidth = textBound.width() / showText.length();

        /**
         * 三个圆点占用宽度为一个字符所占宽度,设置每个圆点间隔为直径,第一个间距为一个半径,所以半径的计算方法为
         * 半径 = 一个字符宽度 / ((2*圆点个数-1)+1)
         */

        float cirRadius = dotWidth / ((2f * MAX_DOTS_COUNT - 1f) * 2f + 1f);
        float dotX = textBound.width() / 2;
        float dotY = radius * 1.8f - (textPaint.descent() + textPaint.ascent()) * 0.4f;
//        Log.d(TAG, "dotX= " + dotX + "\n" + "dotY=" + dotY);
        for (int i = 0; i < 4 * circularCount - 1; i += 4) {
            canvas.drawCircle(dotX + cirRadius * (i + 2), dotY, cirRadius, dotPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return mPaint.getAlpha();
    }

    public int getCircularCount() {
        return circularCount;
    }

    public void setCircularCount(int circularCount) {
        this.circularCount = circularCount;
        invalidateSelf();
    }

    public int getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(int showNumber) {
        this.showNumber = showNumber;
        invalidateSelf();
    }

    public float getProgress() {
        return progress / PROGRESS_FACTOR;
    }

    public void setProgress(float progress) {
        this.progress = progress * PROGRESS_FACTOR;
        invalidateSelf();

    }
}
