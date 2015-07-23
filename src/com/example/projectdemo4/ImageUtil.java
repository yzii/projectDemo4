package com.example.projectdemo4;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;



import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
   
public class ImageUtil { 
    private static final String SDCARD_CACHE_IMG_PATH = Environment 
            .getExternalStorageDirectory().getPath() + "/itcast/images/"; 
   
    /**
     * 保存图片到SD卡
     * @param imagePath
     * @param buffer
     * @throws IOException
     */ 
    public static void saveImage(String imagePath, byte[] buffer) 
            throws IOException { 
        File f = new File(imagePath); 
        if (f.exists()) { 
            return; 
        } else { 
            File parentFile = f.getParentFile(); 
            if (!parentFile.exists()) { 
                parentFile.mkdirs(); 
            } 
            f.createNewFile(); 
            FileOutputStream fos = new FileOutputStream(imagePath); 
            fos.write(buffer); 
            fos.flush(); 
            fos.close(); 
        } 
    } 
       
    /**
     * 从SD卡加载图片
     * @param imagePath
     * @return
     */ 
    public static Bitmap getImageFromLocal(String imagePath){ 
        File file = new File(imagePath); 
        if(file.exists()){ 
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath); 
            file.setLastModified(System.currentTimeMillis()); 
            return bitmap; 
        } 
            return null; 
    } 
       
    /**
     * Bitmap转换到Byte[]
     * @param bm
     * @return
     */ 
    public static byte[] bitmap2Bytes(Bitmap bm){    
        ByteArrayOutputStream bas = new ByteArrayOutputStream();      
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bas);      
        return bas.toByteArray();    
       }   
       
    /**
     * 从本地或者服务端加载图片
     * @return
     * @throws IOException 
     */ 
 /*   public static Bitmap loadImage(final String imagePath,final String imgUrl,final ImageCallback callback) { 
        Bitmap bitmap = getImageFromLocal(imagePath); 
        if(bitmap != null){ 
            return bitmap; 
        }else{//从网上加载 
            final Handler handler = new Handler(){ 
                @Override 
                public void handleMessage(Message msg) { 
                    if(msg.obj!=null){ 
                        Bitmap bitmap = (Bitmap) msg.obj; 
                        callback.loadImage(bitmap, imagePath); 
                    } 
                } 
            }; 
               
            Runnable runnable = new Runnable() { 
                @Override 
                public void run() { 
                    try { 
                        URL url = new URL(imgUrl); 
                        Log.e("图片加载", imgUrl); 
                        URLConnection conn = url.openConnection(); 
                        conn.connect(); 
                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(),8192) ; 
                        Bitmap bitmap = BitmapFactory.decodeStream(bis); 
                        //保存文件到sd卡 
                        saveImage(imagePath,bitmap2Bytes(bitmap)); 
                        Message msg = handler.obtainMessage(); 
                        msg.obj = bitmap; 
                        handler.sendMessage(msg); 
                    } catch (IOException e) { 
                        Log.e(ImageUtil.class.getName(), "保存图片到本地存储卡出错！"); 
                    } 
                } 
            }; 
            ThreadPoolManager.getInstance().addTask(runnable); 
        } 
        return null; 
    } */
   
    // 返回图片存到sd卡的路径 
    public static String getCacheImgPath() { 
        return SDCARD_CACHE_IMG_PATH; 
    } 
   
    public static String md5(String paramString) { 
        String returnStr; 
        try { 
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5"); 
            localMessageDigest.update(paramString.getBytes()); 
            returnStr = byteToHexString(localMessageDigest.digest()); 
            return returnStr; 
        } catch (Exception e) { 
            return paramString; 
        } 
    } 
   
    /**
     * 将指定byte数组转换成16进制字符串
     * 
     * @param b
     * @return
     */ 
    public static String byteToHexString(byte[] b) { 
        StringBuffer hexString = new StringBuffer(); 
        for (int i = 0; i < b.length; i++) { 
            String hex = Integer.toHexString(b[i] & 0xFF); 
            if (hex.length() == 1) { 
                hex = '0' + hex; 
            } 
            hexString.append(hex.toUpperCase()); 
        } 
        return hexString.toString(); 
    } 
    
 
        /**
         * 转换图片成圆形
         * @param bitmap 传入Bitmap对象
         * @return
         */
        public static Bitmap toRoundBitmap(Bitmap bitmap) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                float roundPx;
                float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
                if (width <= height) {
                        roundPx = width / 2;
                        top = 0;
                        bottom = width;
                        left = 0;
                        right = width;
                        height = width;
                        dst_left = 0;
                        dst_top = 0;
                        dst_right = width;
                        dst_bottom = width;
                } else {
                        roundPx = height / 2;
                        float clip = (width - height) / 2;
                        left = clip;
                        right = width - clip;
                        top = 0;
                        bottom = height;
                        width = height;
                        dst_left = 0;
                        dst_top = 0;
                        dst_right = height;
                        dst_bottom = height;
                }
                
                Bitmap output = Bitmap. createBitmap(width,
                                height, Config. ARGB_8888 );
                Canvas canvas = new Canvas(output);
                
                final int color = 0xff424242;
                final Paint paint = new Paint();
                final Rect src = new Rect(( int)left, ( int )top, ( int)right, (int )bottom);
                final Rect dst = new Rect(( int)dst_left, ( int )dst_top, (int )dst_right, ( int)dst_bottom);
                final RectF rectF = new RectF(dst);

                paint.setAntiAlias( true );
                
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                paint.setXfermode( new PorterDuffXfermode(Mode. SRC_IN));
                canvas.drawBitmap(bitmap, src, dst, paint);
                return output;
        }
       
   

    
    
    
    /**
     * 
     * @author Mathew
     *
     */ 
    public interface ImageCallback{ 
        public void loadImage(Bitmap bitmap,String imagePath); 
    } 
       
}