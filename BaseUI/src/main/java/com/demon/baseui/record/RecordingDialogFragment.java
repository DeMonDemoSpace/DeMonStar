package com.demon.baseui.record;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.demon.baseui.R;
import com.demon.baseutil.ToastUtil;
import com.demon.baseutil.file.FileUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 开始录音的 DialogFragment
 * <p>
 * Created by DeMon on 2018/7/19.
 */

public class RecordingDialogFragment extends DialogFragment {

    private static final String TAG = "RecordAudioDialogFragme";
    private long timeWhenPaused = 0;
    private boolean isRecording = false;
    private FloatingActionButton mFabRecord;
    private Chronometer mChronometerTime;
    private ImageView mIvClose;
    private Intent intent;
    private Listener mListener;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private String filePath, fileTime;
    private long startTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_recording, null);
        mChronometerTime = view.findViewById(R.id.record_audio_chronometer_time);
        mFabRecord = view.findViewById(R.id.record_audio_fab_record);
        mIvClose = view.findViewById(R.id.record_audio_iv_close);
        mFabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecording = !isRecording;
                onRecord(isRecording);
            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(getActivity(), "录音取消...");
                dismiss();
            }
        });
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    private void onRecord(boolean flag) {
        intent = new Intent(getActivity(), RecordingService.class);
        if (flag) {
            startTime = System.currentTimeMillis();
            fileTime = sdf.format(new Date());
            filePath = FileUtil.getPath(getActivity(), "Recording") + fileTime + ".amr";
            intent.putExtra("FilePath", filePath);
            mFabRecord.setImageResource(R.drawable.base_recording_stop);
            ToastUtil.showToast(getActivity(), "开始录音...");
            mChronometerTime.setBase(SystemClock.elapsedRealtime());
            mChronometerTime.start();
            getActivity().startService(intent);
        } else {
            mFabRecord.setImageResource(R.drawable.base_recording_start);
            mChronometerTime.stop();
            mListener.onFinish(filePath, fileTime, System.currentTimeMillis() - startTime);
            timeWhenPaused = 0;
            ToastUtil.showToast(getActivity(), "录音结束...");
            getActivity().stopService(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRecording) {
            getActivity().stopService(intent);
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }


    public interface Listener {
        void onFinish(String path, String time, long length);
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }
}
