package com.demon.baseframe.handler;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author DeMon
 * @date 2018/1/10
 * @description 针对Handler内存泄漏进行封装
 * 使用接口调用处理message
 */

public class IHandler extends Handler {
    /**
     * WeakReference存放存入的Activity，
     * 这样在Activity结束回收的时候WeakReference不会阻止系统进行回收操作，
     * 能有效的避免因为handler引起的内存泄露风险
     */
    private WeakReference<Activity> mActivityWeakReference;
    private WeakReference<IfHandler> mIfHandler;

    public IHandler(Activity activity, IfHandler ifHandler) {
        this.mActivityWeakReference = new WeakReference<>(activity);
        this.mIfHandler = new WeakReference<>(ifHandler);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mActivityWeakReference != null && mIfHandler != null) {
            Activity activity = mActivityWeakReference.get();
            IfHandler handler = mIfHandler.get();
            handler.initHandlerMessage(activity, msg);
        }

    }

    public interface IfHandler {
        void initHandlerMessage(Activity activity, Message msg);
    }
}
