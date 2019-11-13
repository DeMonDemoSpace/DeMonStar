package com.demon.baseutil.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: DeMon.
 * Date: 2018/3/2.
 * Work:
 */

public class ImageUtil {
    public static final String BASE64_PNG = "data:image/png;base64,";
    public static final String BASE64_JPG = "data:image/jpeg;base64,";

    /**
     * 将Bitmap保存在本地
     *
     * @param bitmap
     */
    public static String saveBitmapImage(Context context, Bitmap bitmap, String fileName) {
        try {
            File dir = new File(FileUtil.getPath(context, "Image"));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName + System.currentTimeMillis() + ".png");
            if (file.exists()) {
                file.delete();
            }
            // 首先保存图片
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            // 把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            // 通知图库更新
            //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + dir.getCanonicalPath() + "/")));
            return file.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片路径转base64
     *
     * @param path
     * @return
     */
    public static String pathToBase64(String path) {
        String s = "";
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            s = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
            fis.close();
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * bitmap转Base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        String base64 = Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * 根据图片路径，压缩后转base64
     *
     * @param srcPath
     * @return
     */
    public static String compressAndBase64(String srcPath, float hh, float ww) {
        return bitmapToBase64(compressImage(srcPath, hh, ww));
    }


    /**
     * 比例压缩图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap compressImage(String srcPath, float hh, float ww) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //float hh = 600f;//这里设置高度为600f
        //float ww = 600f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImageQuality(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressImageQuality(String srcPath) {
        return compressImageQuality(BitmapFactory.decodeFile(srcPath));
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImageQuality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 80;
        while (baos.toByteArray().length / 1024 > 100) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            //必须保证100 > options >0;不断减小，针对大图缩短压缩时间
            if (options > 20) {
                options -= 20;
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        try {
            baos.flush();
            baos.close();
            isBm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 将文字转为Bitmap
     *
     * @param context   上下文
     * @param text      文字
     * @param textSize  字体大小
     * @param textColor 文字颜色
     * @param imgColor  图片颜色
     * @return
     */
    public static Bitmap textAsBitmap(Context context, String text, float textSize, int textColor, int imgColor) {
        TextPaint textPaint = new TextPaint();
        // textPaint.setARGB(0x31, 0x31, 0x31, 0);
        textPaint.setColor(context.getResources().getColor(textColor));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(text, textPaint, 450, Layout.Alignment.ALIGN_CENTER, 1.3f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20, layout.getHeight() + 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(10, 10);
        canvas.drawColor(context.getResources().getColor(imgColor));//绘制透明色
        layout.draw(canvas);
        return bitmap;
    }

    /**
     * 图片裁剪Fragment
     */
    public static void startPhotoZoom(Fragment fragment, Uri uri, String cropImgPath, int x, int y, int requestCode) {
        startPhotoZoom(fragment.getActivity(), uri, cropImgPath, x, y, requestCode);
    }

    /**
     * 图片裁剪
     *
     * @param activity
     * @param uri         裁剪前的图片Uri
     * @param cropImgPath 裁剪后保存的图片路径，外部传入后onActivityResult中直接使用，解决部分机型（如小米）不返回uri的问题
     * @param x           裁剪后的宽度，像素
     * @param y           裁剪后的高度，像素
     * @param requestCode
     */
    public static void startPhotoZoom(Activity activity, Uri uri, String cropImgPath, int x, int y, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        File file = new File(cropImgPath);
        Uri crop_uri = FileProviderUtil.getUriForFile(activity, file);
        if (Build.VERSION.SDK_INT < 24) {
            FileProviderUtil.grantPermissions(activity, intent, uri, true);
            FileProviderUtil.grantPermissions(activity, intent, crop_uri, true);
        }
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        //裁剪时是否保留图片的比例
        intent.putExtra("scale", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", x);
        intent.putExtra("aspectY", y);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", x);
        intent.putExtra("outputY", y);
        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data", false);
        //关闭人脸识别
        intent.putExtra("noFaceDetection", true); // no face detection
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());//保存格式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, crop_uri);
        activity.startActivityForResult(intent, requestCode);
    }
}
