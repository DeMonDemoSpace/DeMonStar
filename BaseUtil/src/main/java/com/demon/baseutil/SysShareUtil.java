package com.demon.baseutil;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;

import com.demon.baseutil.file.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeMon
 * @date 2019/8/8
 * @email 757454343@qq.com
 * @description
 */
public class SysShareUtil {

    /**
     * 调用系统分享
     *
     * @return
     */
    public static void shareSysAll(Activity activity, Bitmap bitmap) {
        //由Bitmap得到uri
        //Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, null, null));
        Uri imageUri = Uri.parse(ImageUtil.saveBitmapImage(activity, bitmap, "sys_share.jpg"));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, "选择分享方式"));
    }

    /**
     * 调用系统分享分享到Tencent
     *
     * @param activity
     * @param bitmap
     */
    public static void shareTencent(Activity activity, Bitmap bitmap) {
        Uri uri = Uri.parse(ImageUtil.saveBitmapImage(activity, bitmap, "sys_share"));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> resInfo = pm.queryIntentActivities(intent, 0);
        if (resInfo.isEmpty()) {
            ToastUtil.showErrorToast(activity, "没有可以分享的应用");
            return;
        }
        List<Intent> targetIntents = new ArrayList<>();
        for (ResolveInfo resolveInfo : resInfo) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo.packageName.contains("com.tencent.mm") || activityInfo.packageName.contains("com.tencent.mobileqq") || activityInfo.packageName.contains("com.tencent.tim")) {
                //过滤掉qq，tim，微信收藏
                if (resolveInfo.loadLabel(pm).toString().contains("收藏")) {
                    continue;
                }
                Intent target = new Intent();
                target.setAction(Intent.ACTION_SEND);
                target.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                target.putExtra(Intent.EXTRA_STREAM, uri);
                target.setType("image/*");//必须设置，否则选定分享类型后不能跳转界面
                targetIntents.add(new LabeledIntent(target, activityInfo.packageName, resolveInfo.loadLabel(pm), resolveInfo.icon));
            }
        }
        if (targetIntents.size() <= 0) {
            ToastUtil.showErrorToast(activity, "没有可以分享的应用");
            return;
        }
        Intent chooser = Intent.createChooser(targetIntents.remove(targetIntents.size() - 1), "选择分享方式");
        if (chooser == null) return;
        LabeledIntent[] labeledIntents = targetIntents.toArray(new LabeledIntent[targetIntents.size()]);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, labeledIntents);
        activity.startActivity(chooser);
    }

}
