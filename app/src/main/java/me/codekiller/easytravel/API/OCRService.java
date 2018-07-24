package me.codekiller.easytravel.API;

import io.reactivex.Observable;
import me.codekiller.easytravel.bean.WordsResult;
import okhttp3.MultipartBody;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface OCRService {
    @Multipart
    @POST("ocr/general")
    Observable<WordsResult> generalOCR(@Header("Host") String host, @Header("Authorization") String authorization, @Part("appid") long appid, @Part MultipartBody.Part image);
}
