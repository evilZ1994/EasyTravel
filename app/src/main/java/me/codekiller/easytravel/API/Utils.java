package me.codekiller.easytravel.API;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
    public static long appId = 1252662799;
    public static String ydAppKey = "6d9373dc4f2946f8";
    public static String appName = "EasyTravel";
    public static String host = "recognition.image.myqcloud.com";
    public static String typeImage = "multipart/form-data";
    public static String typeUrl = "application/json";
    public static String authorization = "Z5OFu4kZQ1kH6UxKSpfFqDmjgCFhPTEyNTI2NjI3OTkmYj1jb2Rla2lsbGVyLTEyNTI2NjI3OTkmaz1BS0lEeFZ4R09PMDFadlN1RU5GbnFpOFFhRW9MaHhrUHBCSEcmdD0xNTI2MDMwNDk5JmU9MTUyODYyMjQ5OSZyPTQ0OTg3NDkzNw==";

    public static String getImageRealPath(Context context, Uri imageUri){
        String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(index);
            cursor.close();
            return imagePath;
        } else {
            return null;
        }
    }

    public static String saveCrop(Context context, String fileName, Bitmap pic) {
        File file = new File(context.getExternalCacheDir(), "Crop");
        if (!file.exists()){
            file.mkdirs();
        }
        String filePath = file.getAbsolutePath() + "/" + fileName;
        return saveBitmap(filePath, pic);
    }

    public static String saveBitmap(String filePath, Bitmap pic) {
        FileOutputStream fos;

        File file = new File(filePath);
        try {
            if (!file.exists()) {
                Log.i("file path", filePath);
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
