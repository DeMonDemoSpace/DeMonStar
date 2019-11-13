package com.demon.baseframe.handler;


import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Author: DeMon.
 * Date: 2018/1/23.
 * Work:
 */

public class FHandler extends Handler {

    private WeakReference<Fragment> mActivityWeakReference;
    private WeakReference<IfHandler> mIfHandler;

    public FHandler(Fragment fragment, IfHandler ifHandler) {
        this.mActivityWeakReference = new WeakReference<>(fragment);
        this.mIfHandler = new WeakReference<>(ifHandler);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mActivityWeakReference != null && mIfHandler != null) {
            Fragment fragment = mActivityWeakReference.get();
            IfHandler handler = mIfHandler.get();
            handler.initHandlerMessage(fragment, msg);
        }

    }

    public interface IfHandler {
        void initHandlerMessage(Fragment fragment, Message msg);
    }
}
