package me.codekiller.easytravel.UI.Detail;

import me.codekiller.easytravel.base.BasePresenter;
import me.codekiller.easytravel.base.BaseView;

public interface ViewPotContract {
    interface Presenter extends BasePresenter {
        void loadViewPot(String url);
    }

    interface View extends BaseView<Presenter> {
        void showViewPot(String html);
    }
}
