package me.codekiller.easytravel.UI.Home;

import android.net.Uri;

import java.util.List;

import me.codekiller.easytravel.base.BasePresenter;
import me.codekiller.easytravel.base.BaseView;
import me.codekiller.easytravel.bean.ViewPotItem;
import me.codekiller.easytravel.bean.WordsResult;

public interface HomeContract {
    interface Presenter extends BasePresenter{
        void generaOCR(String imagePath);

        void getHotViewPot();
    }

    interface View extends BaseView<Presenter>{
        void resultOK(List<WordsResult.DataBean.ItemsBean> items);

        void resultError(String msg);

        void onFinish();

        void onHotViewOk(List<ViewPotItem> items);
    }
}
