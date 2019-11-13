package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.demon.baseui.R;
import com.demon.baseui.activity.PreviewImgActivity;
import com.demon.baseui.gilde.GlideUtil;

/**
 * @author DeMonnnnnn
 * @date 2019/10/15
 * @email 757454343@qq.com
 * @description
 */
public class UploadImageView extends ConstraintLayout {
    public final int NORMAL = 0;//正常模式
    public final int PREVIEW = 1;//预览模式
    private int model = 0;
    private TextView tvTips;
    private RelativeLayout addLayout;
    private ImageView addImgView, delImgView;
    private AddImgListener addListener;
    private Context mContext;
    private String url = "";
    private String path;

    public UploadImageView(Context context) {
        this(context, null);
    }

    public UploadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        final View view = LayoutInflater.from(context).inflate(R.layout.widget_add_image_view, this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UploadImageView);
        addLayout = findViewById(R.id.addLayout);
        delImgView = findViewById(R.id.ivAddDel);
        addImgView = findViewById(R.id.ivAddImg);
        tvTips = findViewById(R.id.tvImgTips);
        String tips = typedArray.getString(R.styleable.UploadImageView_uiv_tips);
        tvTips.setText(TextUtils.isEmpty(tips) ? "添加图片" : tips);
        addLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addListener != null) {
                    addListener.addImg(view);
                }
            }
        });
        delImgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delUploadImageUrl();
            }
        });
        addImgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url.startsWith("http")) {
                    mContext.startActivity(PreviewImgActivity.newIntent(mContext, 0, url));
                } else {
                    mContext.startActivity(PreviewImgActivity.newIntent(mContext, 1, url));
                }
            }
        });
        typedArray.recycle();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public void setModel(int model, String url) {
        this.model = model;
        setUploadImageUrl(url);
    }

    public void setUploadImageUrl(String url) {
        if (TextUtils.isEmpty(url)) return;
        this.url = url;
        addLayout.setVisibility(GONE);
        addImgView.setVisibility(VISIBLE);
        GlideUtil.setImage(mContext, url, addImgView);
        if (model == NORMAL) {
            delImgView.setVisibility(VISIBLE);
        } else {
            delImgView.setVisibility(GONE);
        }
    }

    public void delUploadImageUrl() {
        url = "";
        path = "";
        addLayout.setVisibility(VISIBLE);
        addImgView.setVisibility(GONE);
        delImgView.setVisibility(GONE);
    }


    public void setAddImgListener(AddImgListener listener) {
        this.addListener = listener;
    }


    public interface AddImgListener {
        void addImg(View view);
    }

    public String getUrl() {
        return url;
    }


}
