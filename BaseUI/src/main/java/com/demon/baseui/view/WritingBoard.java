package com.demon.baseui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.demon.baseui.R;
import com.demon.baseui.list.adapter.SpinnerAdapter;
import com.demon.baseui.list.holder.DataViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * @author D&LL
 * @date 2018/7/4
 * @description
 */
public class WritingBoard extends ConstraintLayout implements View.OnClickListener {
    private Spinner spTextSize, spColor;
    private TextView tv_undo, tv_restore, tv_clear, tv_save;
    private PaintView mPaintView;
    private Context mContext;
    private Listener listener;
    private boolean hasLines;

    public WritingBoard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.widget_writing_board, this);
        spTextSize = findViewById(R.id.sp_text_size);
        spColor = findViewById(R.id.sp_color);

        String[] sizeArray = mContext.getResources().getStringArray(R.array.base_text_size);
        List<String> sizeList = Arrays.asList(sizeArray);
        SpinnerAdapter<String> adapter1 = new SpinnerAdapter<String>(mContext, R.layout.list_wb_spinner, sizeList) {
            @Override
            public void convert(DataViewHolder holder, final int position, String s) {
                holder.setText(R.id.tv_text, s);
            }
        };
        spTextSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPaintView.setPaintSize((position + 1) * 2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTextSize.setAdapter(adapter1);
        String[] colorArray = mContext.getResources().getStringArray(R.array.base_color);
        List<String> colorList = Arrays.asList(colorArray);
        SpinnerAdapter<String> adapter2 = new SpinnerAdapter<String>(mContext, R.layout.list_wb_spinner, colorList) {
            @Override
            public void convert(DataViewHolder holder, final int position, String s) {
                holder.setText(R.id.tv_text, s);
            }
        };
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mPaintView.setPaintColor(Color.BLACK);
                } else if (position == 1) {
                    mPaintView.setPaintColor(Color.RED);
                } else if (position == 2) {
                    mPaintView.setPaintColor(Color.YELLOW);
                } else if (position == 3) {
                    mPaintView.setPaintColor(Color.BLUE);
                } else if (position == 4) {
                    mPaintView.setPaintColor(Color.GREEN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spColor.setAdapter(adapter2);

        tv_undo = findViewById(R.id.tv_undo);
        tv_restore = findViewById(R.id.tv_restore);
        tv_clear = findViewById(R.id.tv_clear);
        tv_save = findViewById(R.id.tv_save);
        mPaintView = findViewById(R.id.paintView);
        tv_undo.setOnClickListener(this);
        tv_restore.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_undo) {
            mPaintView.undo();
        } else if (i == R.id.tv_restore) {
            mPaintView.redo();
        } else if (i == R.id.tv_clear) {
            //mPaintView.clearAll();
            if (listener != null) {
                listener.clear(mPaintView);
            }
        } else if (i == R.id.tv_save) {
            //mPaintView.saveImg(FileUtil.getPath(mContext, "Image"), System.currentTimeMillis() + ".png");
            if (listener != null) {
                listener.save(mPaintView);
            }
        }
    }


    public interface Listener {
        void clear(PaintView paintView);

        void save(PaintView paintView);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setHasLines(boolean hasLines) {
        this.hasLines = hasLines;
        mPaintView.setHasLines(hasLines);
    }
}
