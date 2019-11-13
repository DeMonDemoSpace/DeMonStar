package com.demon.baseutil.task;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author DeMon
 * @date 2018/8/14
 * @description
 */
public class HeartbeatTask {

    private Timer timer;
    private TimerTask task;
    private OnScheduleListener mListener;

    public HeartbeatTask() {
        timer = new Timer();
    }

    public HeartbeatTask(OnScheduleListener mListener) {
        timer = new Timer();
        this.mListener = mListener;
    }

    public void start(long delay, long period) {
        task = new TimerTask() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onSchedule();
                }
            }
        };
        timer.schedule(task, delay, period);
    }

    public void exit() {
        if (task != null) {
            task.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    public interface OnScheduleListener {
        void onSchedule();
    }

    public void setOnScheduleListener(OnScheduleListener listener) {
        this.mListener = listener;
    }
}
