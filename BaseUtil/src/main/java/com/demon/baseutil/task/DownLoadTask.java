package com.demon.baseutil.task;

import android.content.Context;
import android.os.AsyncTask;

import com.demon.baseutil.file.FileUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: DeMon.
 * Date: 2018/1/26.
 * Work:
 */

public class DownLoadTask extends AsyncTask<String, Integer, byte[]> {
    private DownLoadListener l;
    private byte[] current = new byte[2 * 1024];
    private byte[] total;
    private boolean flag;
    private String name;
    private Context mContext;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public DownLoadTask(Context context, String name, DownLoadListener l) {
        this.mContext = context;
        this.name = name;
        this.l = l;
    }

    public interface DownLoadListener {
        void downloading(int value);

        void downloadCancel();

        void downloadStart();

        void downloadComplete(String filePath);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        l.downloadStart();
        flag = true;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(7676);
            http.setRequestProperty("Accept-Encoding", "identity");
            int length = http.getContentLength();
            total = new byte[length];
            int pointer = 0;
            InputStream is = http.getInputStream();
            int real = is.read(current);
            while (flag && real > 0) {
                for (int i = 0; i < real; i++) {
                    total[pointer + i] = current[i];
                }
                pointer += real;
                int progress = (int) ((double) pointer / length * 100);
                publishProgress(progress, pointer, length);
                real = is.read(current);
            }
            is.close();
            return total;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(byte[] result) {
        try {
            if (flag) {
                l.downloadComplete(resultToFile(result));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        l.downloading(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        l.downloadCancel();
    }

    private String resultToFile(byte[] result) throws IOException {
        if (result == null || result.length == 0) {
            return null;
        }
        String filePath = FileUtil.getPath(mContext, "File");
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, name);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(result);
        bos.close();
        fos.close();
        return file.getAbsolutePath();
    }
}
