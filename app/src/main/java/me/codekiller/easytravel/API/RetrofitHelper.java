package me.codekiller.easytravel.API;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static String ocrBaseUrl = "http://recognition.image.myqcloud.com/";

    private static OkHttpClient okHttpClient;

    private static OCRService ocrService;

    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("Retrofit Log", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(15*1000, TimeUnit.MILLISECONDS)
                    .readTimeout(15*1000, TimeUnit.MILLISECONDS)
                    .writeTimeout(15*1000, TimeUnit.MILLISECONDS)
                    .build();
        }

        return okHttpClient;
    }

    public static OCRService getOcrService(){
        if(ocrService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(ocrBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            ocrService = retrofit.create(OCRService.class);
        }

        return ocrService;
    }

}
