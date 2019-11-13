package com.demon.baseui.gilde;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class GlideDownLoad implements Runnable {
    private String url;
    private Context context;
    private Listener listener;

    public interface Listener {

        void onSuccess(File file);

        void onFailed();
    }


    public GlideDownLoad(Context context, String url, Listener listener) {
        this.url = url;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            FutureTarget<File> target = Glide.with(context)
                    .asFile()
                    .load(url)
                    .submit();
            File file = target.get();
            listener.onSuccess(file);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailed();
        }
    }

}