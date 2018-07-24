package me.codekiller.easytravel;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.youdao.sdk.app.YouDaoApplication;

import me.codekiller.easytravel.API.Utils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YouDaoApplication.init(this, Utils.ydAppKey);
        Fresco.initialize(this);
    }
}
