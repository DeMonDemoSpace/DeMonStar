package com.demon.baseui.scroll;

/**
 * @author DeMon
 * @date 2018/7/25
 * @description 继承MoveGestureDetector实现一个滑动方向的监听
 */

public class SlidingDirectionMonitor implements MoveGestureDetector.OnMoveGestureListener {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 0;
    public static final int UP = 1;
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;
    private SidleListener listener;
    private int multiple = 1;

    public SlidingDirectionMonitor(SidleListener listener) {
        this.listener = listener;
    }

    public SlidingDirectionMonitor(int multiple, SidleListener listener) {
        this.listener = listener;
        this.multiple = multiple;
    }

    @Override
    public boolean onMoveBegin(MoveGestureDetector detector) {
        //当手指按下的时候
        x1 = detector.getMoveX();
        y1 = detector.getMoveY();
        return true;
    }

    @Override
    public boolean onMove(MoveGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onMoveEnd(MoveGestureDetector detector) {
        //当手指离开的时候
        x2 = detector.getMoveX();
        y2 = detector.getMoveY();
        if (x1 - x2 > multiple * 100) { //左滑
            listener.SidleLeftOrRight(LEFT);
        } else if (x2 - x1 > multiple * 100) { //右滑
            listener.SidleLeftOrRight(RIGHT);
        }

        if (y1 - y2 > multiple * 100) {//下滑
            listener.SidleUpOrDown(DOWN);
        } else if (y2 - y1 > multiple * 100) { //上滑
            listener.SidleUpOrDown(UP);
        }
        return true;
    }

    public interface SidleListener {
        void SidleLeftOrRight(int x);

        void SidleUpOrDown(int y);
    }

    public static class SimpleSidleListener implements SidleListener {

        @Override
        public void SidleLeftOrRight(int x) {

        }

        @Override
        public void SidleUpOrDown(int y) {

        }
    }
}
