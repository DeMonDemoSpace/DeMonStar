package com.demon.baseui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2018/6/26
 * @description 倒计时按钮
 */
public class TimingButton extends AppCompatButton {
    private int total, interval;
    private String psText, countText;
    private String initText;

    public TimingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义属性，并赋值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimingButton);
        total = typedArray.getInteger(R.styleable.TimingButton_tb_totalTime, 60000);
        interval = typedArray.getInteger(R.styleable.TimingButton_tb_timeInterval, 1000);
        psText = typedArray.getString(R.styleable.TimingButton_tb_psText);
        countText = typedArray.getString(R.styleable.TimingButton_tb_countText);
        initText = getText().toString();
        typedArray.recycle();
    }


    public void start() {
        TimeCount time = new TimeCount(total, interval);
        time.start();
    }

    public class TimeCount extends CountDownTimer {
        private long countDownInterval;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            setEnabled(true);
            if (TextUtils.isEmpty(psText)) {
                setText(initText);
            } else {
                setText(psText);
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            setEnabled(false);
            if (TextUtils.isEmpty(countText)) {
                countText = "";
            }
            setText(countText + "（" + millisUntilFinished / countDownInterval + "秒）");
        }
    }
}
