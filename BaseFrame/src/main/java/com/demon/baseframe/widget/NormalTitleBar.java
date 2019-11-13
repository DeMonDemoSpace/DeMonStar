package com.demon.baseframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.demon.baseframe.R;

/**
 * @author DeMon
 * @date 2019/7/11
 * @email 757454343@qq.com
 * @description
 */
public class NormalTitleBar extends FrameLayout {
    private int leftImg;
    private Boolean isClickRightText;
    private int rightInitImg;
    private int rightClickImg;
    private View view;
    private TextView tvLeft, tvTitle, tvRight;
    private ImageView imageRight;

    public NormalTitleBar(Context context) {
        this(context, null);
    }

    public NormalTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.widget_normal_title, this);
        tvLeft = findViewById(R.id.tvLeft);
        tvTitle = findViewById(R.id.tvTitle);
        tvRight = findViewById(R.id.tvRight);
        imageRight = findViewById(R.id.imageRight);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NormalTitleBar);
        setTitleTextColor(typedArray.getColor(R.styleable.NormalTitleBar_ntb_textColor, getResources().getColor(R.color.white)));
        setLeftText(typedArray.getString(R.styleable.NormalTitleBar_ntb_leftText));
        setLeftIamge(typedArray.getResourceId(R.styleable.NormalTitleBar_ntb_leftImg, R.mipmap.base_back));
        isShowLeft(typedArray.getBoolean(R.styleable.NormalTitleBar_ntb_isLeftShow, true));
        isShowLeftImage(typedArray.getBoolean(R.styleable.NormalTitleBar_ntb_isLeftShowImg, true));
        setTitleText(typedArray.getString(R.styleable.NormalTitleBar_ntb_title));
        setRightText(typedArray.getString(R.styleable.NormalTitleBar_ntb_rightText));
        rightInitImg = typedArray.getResourceId(R.styleable.NormalTitleBar_ntb_rightTextInitImg, 0);
        rightClickImg = typedArray.getResourceId(R.styleable.NormalTitleBar_ntb_rightTextClickImg, 0);
        setRightTextImg(rightInitImg, rightClickImg);
        setRightImage(typedArray.getResourceId(R.styleable.NormalTitleBar_ntb_rightImg, 0));
        typedArray.recycle();
    }


    /**
     * 统一标题栏字体颜色
     */
    public void setTitleTextColor(int id) {
        tvLeft.setTextColor(id);
        tvTitle.setTextColor(id);
        tvRight.setTextColor(id);
    }

    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    public void setLeftTextListener(final OnClickListener listener) {
        if (listener != null) {
            tvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(view);
                }
            });
        }
    }

    public void setLeftIamge(int img) {
        this.leftImg = img;
        if (img != 0) {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), img, null);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvLeft.setCompoundDrawables(drawable, null, null, null);
        }
    }

    public void isShowLeft(Boolean flag) {
        if (flag) {
            tvLeft.setVisibility(View.VISIBLE);
        } else {
            tvLeft.setVisibility(View.GONE);
        }

    }

    public void isShowLeftImage(Boolean flag) {
        if (flag) {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), leftImg, null);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvLeft.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvLeft.setCompoundDrawables(null, null, null, null);
        }

    }

    public void setTitleText(String text) {
        tvTitle.setText(text);
    }

    public void setRightText(String text) {
        if (TextUtils.isEmpty(text)) return;
        tvRight.setText(text);
        tvRight.setVisibility(View.VISIBLE);
        imageRight.setVisibility(View.GONE);
    }

    public void setRightTextListener(final OnClickListener listener) {
        if (listener != null) {
            tvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClickRightText = !isClickRightText;
                    setRightTextImg(isClickRightText);
                    listener.onClick(view);
                }
            });
        }
    }

    public void setRightTextImg(int initImg, int clickImg) {
        this.rightInitImg = initImg;
        this.rightClickImg = clickImg;
        //初始img与点击img一致
        if (rightClickImg == 0) {
            rightClickImg = rightInitImg;
        }
        tvRight.setVisibility(View.VISIBLE);
        imageRight.setVisibility(View.GONE);
        setRightTextImg(false);
    }

    public void setRightTextImg(Boolean flag) {
        if (rightInitImg != 0 && rightClickImg != 0) {
            if (!flag) {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), rightInitImg, null);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvRight.setCompoundDrawables(null, null, drawable, null);
            } else {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), rightClickImg, null);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvRight.setCompoundDrawables(null, null, drawable, null);
            }
        } else {
            tvRight.setCompoundDrawables(null, null, null, null);
        }
    }

    public void setRightImage(int img) {
        if (img == 0) return;
        imageRight.setImageResource(img);
        tvRight.setVisibility(View.GONE);
        imageRight.setVisibility(View.VISIBLE);
    }

    public void setRightImageListener(final OnClickListener listener) {
        if (listener != null) {
            imageRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(view);
                }
            });
        }
    }
}
