package me.codekiller.easytravel.UI.Result;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.codekiller.easytravel.API.Utils;
import me.codekiller.easytravel.bean.ViewPotItem;
import me.codekiller.easytravel.model.ViewPotsManager;

public class ResultPresenter implements ResultContract.Presenter {
    private Context context;
    private ResultContract.View view;

    public ResultPresenter(Context context, ResultContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void translate(Language from, Language to, String content) {
        TranslateParameters tParams = new TranslateParameters.Builder()
                .source(Utils.appName)
                .from(from)
                .to(to)
                .build();
        Translator translator = Translator.getInstance(tParams);
        translator.lookup(content, "requestId", new TranslateListener() {
            @Override
            public void onError(TranslateErrorCode translateErrorCode, String s) {
                view.onTranslateError(translateErrorCode);
            }

            @Override
            public void onResult(final Translate translate, final String s, String s1) {
                //回调线程没在主线程中
                ((ResultActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.onTranslateResult(translate, s);
                    }
                });
            }

            @Override
            public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

            }
        });
    }

    @Override
    public void searchViewPot(String key) {
        ViewPotsManager manager = ViewPotsManager.getInstance();
        Observer<List<ViewPotItem>> observer = new Observer<List<ViewPotItem>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ViewPotItem> items) {
                view.onViewPotResult(items);
            }

            @Override
            public void onError(Throwable e) {
                view.onViewPotError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        manager.searchViewPots(observer, key);
    }
}
