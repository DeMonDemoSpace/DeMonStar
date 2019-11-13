package com.demon.baseui.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demon.baseui.R;
import com.demon.baseui.dialog.CustomDialog;
import com.demon.baseui.dialog.WeChatDialog;
import com.demon.baseui.dialog.listener.EtDialogListener;
import com.demon.baseui.dialog.listener.ListListener;
import com.demon.baseui.dialog.listener.NoListener;
import com.demon.baseui.dialog.listener.YesListener;
import com.demon.baseui.list.adapter.DataAdapter;
import com.demon.baseui.list.holder.DataViewHolder;

import java.util.List;

/**
 * @author DeMon
 * @date 2017/11/9
 * @description Dialog 工具类
 */

public class DialogUtil {

    /**
     * 根据title和自定义的view生成dialog
     * 并实现确认的回调
     *
     * @param context 所属的Context
     * @param view    自定义的view
     */
    public static Dialog viewDialog(Context context, String title, View view, final YesListener listener) {
        WeChatDialog dialog = new WeChatDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitleText(title);
        }
        if (view != null) {
            dialog.setCustomView(view);
        }
        dialog.setLeftText(R.string.base_negative).setRightText(R.string.base_positive);
        dialog.setDoubleListener(new WeChatDialog.DoubleListener() {
            @Override
            public void onLeftClick(Dialog dialog) {

            }

            @Override
            public void onRightClick(Dialog dialog) {
                if (listener != null) {
                    listener.doYes(dialog);
                }
            }
        });
        dialog.show();
        return dialog;
    }


    /**
     * 完全自定义View的Dialog
     *
     * @param context 所属的Context
     * @param view    自定义的view
     */
    public static CustomDialog getViewDialog(Context context, String title, View view) {
        CustomDialog dialog = new CustomDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitleText(title);
        }
        if (view != null) {
            dialog.setCustomView(view);
        }
        return dialog;
    }

    /**
     * 根据title和ListView生成dialog
     *
     * @param context 所属的Context
     */
    public static void listViewDialog(Context context, String title, DataAdapter adapter) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCanceledOnTouchOutside(true);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitleText(title);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        RecyclerView rvData = view.findViewById(R.id.rv_data);
        rvData.setLayoutManager(new LinearLayoutManager(context));
        rvData.setAdapter(adapter);
        dialog.setCustomView(view);
        dialog.show();
    }


    /**
     * @param context  上下文
     * @param title    输入框标题
     * @param text     输入框显示的内容
     * @param hint     hint提示
     * @param type
     * @param listener 监听
     */
    public static void editViewDialog(final Context context, String title, final String text, String hint, int type, final EtDialogListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
        final EditText et_text = view.findViewById(R.id.et_text);
        et_text.setHint(hint);
        et_text.setInputType(type);
        if (!TextUtils.isEmpty(text)) {
            et_text.setText(text);
        }
        viewDialog(context, title, view, new YesListener() {
            @Override
            public void doYes(Dialog dialog) {
                String content = et_text.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(context, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listener != null) {
                    listener.doSure(content);
                }
            }
        });
    }

    /**
     * 显示一个string list的Dialog
     *
     * @param context
     * @param list
     * @param listener
     */
    public static void stringListDialog(Context context, String title, final List<String> list, final ListListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        final CustomDialog dialog = getViewDialog(context, title, view);
        dialog.setCanceledOnTouchOutside(true);
        RecyclerView rvData = view.findViewById(R.id.rv_data);
        rvData.setLayoutManager(new LinearLayoutManager(context));
        DataAdapter<String> adapter = new DataAdapter<String>(context, R.layout.list_text_start, list) {
            @Override
            public void convert(DataViewHolder holder, final int position, final String s) {
                holder.setText(R.id.tv_text, s);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        if (listener != null) {
                            listener.doPick(position, s);
                        }
                    }
                });
                View line = holder.getView(R.id.line);
                if (position == list.size() - 1) {
                    line.setVisibility(View.GONE);
                } else {
                    line.setVisibility(View.VISIBLE);
                }
            }
        };
        rvData.setAdapter(adapter);
        dialog.show();
    }


    /**
     * 权限申请的Dialog
     *
     * @param activity
     * @param text
     */
    public static void PermissionDialog(final Activity activity, String text, final NoListener listener) {
        WeChatDialog dialog = new WeChatDialog(activity, Gravity.LEFT);
        dialog.setCancelable(false);
        dialog.setTitleText(activity.getString(R.string.base_permission));
        dialog.setContentText(text);
        dialog.setRightText(R.string.base_setting);
        dialog.setDoubleListener(new WeChatDialog.DoubleListener() {
            @Override
            public void onLeftClick(Dialog dialog) {
                if (listener != null) {
                    listener.doNo(dialog);
                }
            }

            @Override
            public void onRightClick(Dialog dialog) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                activity.startActivity(intent);
            }
        }).show();
    }

    /**
     * 网络设置的Dialog
     *
     * @param activity
     */
    public static void NetWorkDialog(final Activity activity, final NoListener listener) {
        WeChatDialog dialog = new WeChatDialog(activity);
        dialog.setCancelable(false);
        dialog.setTitleText("网络设置");
        dialog.setContentText("网络不可用，请检查网络设置!");
        dialog.setRightText(R.string.base_setting);
        dialog.setDoubleListener(new WeChatDialog.DoubleListener() {
            @Override
            public void onLeftClick(Dialog dialog) {
                if (listener != null) {
                    listener.doNo(dialog);
                }
            }

            @Override
            public void onRightClick(Dialog dialog) {
                //跳转到设置网络界面
                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        }).show();
    }


    public static WeChatDialog showDialog(Context context, CharSequence content, final YesListener listener) {
        WeChatDialog dialog = new WeChatDialog(context).setShowTitle(true).setContentStr(content).setDoubleListener(new WeChatDialog.DoubleListener() {
            @Override
            public void onLeftClick(Dialog dialog) {

            }

            @Override
            public void onRightClick(Dialog dialog) {
                if (listener != null) {
                    listener.doYes(dialog);
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static WeChatDialog showDialog(Context context, CharSequence content) {
        WeChatDialog dialog = new WeChatDialog(context).setShowTitle(true).setContentStr(content).setSingle(true);
        dialog.show();
        return dialog;
    }

    public static WeChatDialog showSingleDialog(Context context, CharSequence content, final YesListener listener) {
        WeChatDialog dialog = new WeChatDialog(context).setShowTitle(true).setContentStr(content).setSingle(true).setSingleListener(new WeChatDialog.SingleListener() {
            @Override
            public void onSingleClick(Dialog dialog) {
                if (listener != null) {
                    listener.doYes(dialog);
                }
            }
        });
        dialog.show();
        return dialog;
    }
}
