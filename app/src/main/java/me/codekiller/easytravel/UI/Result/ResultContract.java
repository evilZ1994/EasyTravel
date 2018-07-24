package me.codekiller.easytravel.UI.Result;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;

import java.util.List;

import me.codekiller.easytravel.base.BasePresenter;
import me.codekiller.easytravel.base.BaseView;
import me.codekiller.easytravel.bean.ViewPotItem;

public interface ResultContract {
    interface Presenter extends BasePresenter{
        void translate(Language from, Language to, String content);

        void searchViewPot(String key);
    }

    interface View extends BaseView<Presenter>{
        void onTranslateResult(Translate translate, String source);

        void onTranslateError(TranslateErrorCode errorCode);

        void onViewPotResult(List<ViewPotItem> items);

        void onViewPotError(String msg);
    }
}
