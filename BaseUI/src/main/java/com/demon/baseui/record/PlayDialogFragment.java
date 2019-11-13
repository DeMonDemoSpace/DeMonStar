package com.demon.baseui.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.demon.baseui.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 播放录音的 DialogFragment
 * <p>
 * Created by DeMon on 2018/7/19.
 */

public class PlayDialogFragment extends DialogFragment {
    private static final String TAG = "PlayDialogFragment";

    public static final String FILE_PATH = "FilePath";
    public static final String FILE_TIME = "FileTime";


    private Handler mHandler = new Handler();

    private MediaPlayer mMediaPlayer = null;

    private SeekBar mSeekBar = null;
    private FloatingActionButton mPlayButton = null;
    private TextView mCurrentProgressTextView = null;
    private TextView mFileNameTextView = null;
    private TextView mFileLengthTextView = null;
    private ImageView mIvClose;
    private boolean isPlaying = false;
    private String filePath;
    private long fileTime;
    long minutes = 0;
    long seconds = 0;
    private static long mFileLength = 0;

    public static PlayDialogFragment newInstance(String filePath, long fileTime) {
        PlayDialogFragment f = new PlayDialogFragment();
        Bundle b = new Bundle();
        b.putString(FILE_PATH, "file://" + filePath);
        b.putLong(FILE_TIME, fileTime);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePath = getArguments().getString(FILE_PATH, "");
        fileTime = getArguments().getLong(FILE_TIME, 0);
        long itemDuration = fileTime;
        mFileLength = itemDuration;
        minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration) - TimeUnit.MINUTES.toSeconds(minutes);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_play, null);

        mFileNameTextView = view.findViewById(R.id.file_name_text_view);
        mFileLengthTextView = view.findViewById(R.id.file_length_text_view);
        mCurrentProgressTextView = view.findViewById(R.id.current_progress_text_view);
        mIvClose = view.findViewById(R.id.iv_close);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSeekBar = view.findViewById(R.id.seekbar);
        ColorFilter filter = new LightingColorFilter(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
        mSeekBar.getProgressDrawable().setColorFilter(filter);
        mSeekBar.getThumb().setColorFilter(filter);
        mFileLengthTextView.setText(String.valueOf(mFileLength));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(progress);
                    mHandler.removeCallbacks(mRunnable);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mMediaPlayer.getCurrentPosition())
                            - TimeUnit.MINUTES.toSeconds(minutes);
                    mCurrentProgressTextView.setText(String.format("%02d:%02d", minutes, seconds));

                    updateSeekBar();

                } else if (mMediaPlayer == null && fromUser) {
                    prepareMediaPlayerFromPoint(progress);
                    updateSeekBar();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mMediaPlayer != null) {
                    mHandler.removeCallbacks(mRunnable);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mMediaPlayer != null) {
                    mHandler.removeCallbacks(mRunnable);
                    mMediaPlayer.seekTo(seekBar.getProgress());

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mMediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes);
                    mCurrentProgressTextView.setText(String.format("%02d:%02d", minutes, seconds));
                    updateSeekBar();
                }
            }
        });

        mPlayButton = view.findViewById(R.id.fab_play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(isPlaying);
                isPlaying = !isPlaying;
            }
        });
        File file = new File(filePath);
        mFileNameTextView.setText(file.getName());
        mFileLengthTextView.setText(String.format("%02d:%02d", minutes, seconds));

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (mMediaPlayer != null) {
            stopPlaying();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer != null) {
            stopPlaying();
        }
    }

    private void onPlay(boolean isPlaying) {
        if (!isPlaying) {
            if (mMediaPlayer == null) {
                startPlaying();
            } else {
                resumePlaying();
            }

        } else {
            pausePlaying();
        }
    }

    private void startPlaying() {
        mPlayButton.setImageResource(R.drawable.base_record_pause);
        mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mSeekBar.setMax(mMediaPlayer.getDuration());

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });
        updateSeekBar();
    }

    private void prepareMediaPlayerFromPoint(int progress) {
        mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            mMediaPlayer.seekTo(progress);

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        //keep screen on while playing audio
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void pausePlaying() {
        if (mMediaPlayer != null) {
            mPlayButton.setImageResource(R.drawable.base_record_play);
            mHandler.removeCallbacks(mRunnable);
            mMediaPlayer.pause();
        }
    }

    private void resumePlaying() {
        if (mMediaPlayer != null) {
            mPlayButton.setImageResource(R.drawable.base_record_pause);
            mHandler.removeCallbacks(mRunnable);
            mMediaPlayer.start();
            updateSeekBar();
        }
    }

    private void stopPlaying() {
        if (mMediaPlayer != null) {
            mPlayButton.setImageResource(R.drawable.base_record_play);
            mHandler.removeCallbacks(mRunnable);
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;

            mSeekBar.setProgress(mSeekBar.getMax());
            isPlaying = !isPlaying;

            mCurrentProgressTextView.setText(mFileLengthTextView.getText());
            mSeekBar.setProgress(mSeekBar.getMax());
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null) {

                int mCurrentPosition = mMediaPlayer.getCurrentPosition();
                mSeekBar.setProgress(mCurrentPosition);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition)
                        - TimeUnit.MINUTES.toSeconds(minutes);
                mCurrentProgressTextView.setText(String.format("%02d:%02d", minutes, seconds));

                updateSeekBar();
            }
        }
    };

    private void updateSeekBar() {
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

    }
}
