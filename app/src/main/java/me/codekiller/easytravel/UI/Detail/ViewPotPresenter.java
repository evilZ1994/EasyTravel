package me.codekiller.easytravel.UI.Detail;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.codekiller.easytravel.model.ViewPotsManager;

public class ViewPotPresenter implements ViewPotContract.Presenter {
    private Context context;
    private ViewPotContract.View view;

    public ViewPotPresenter(Context context, ViewPotContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadViewPot(String url) {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                view.showViewPot(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        ViewPotsManager.getInstance().loadViewPot(observer, url);
    }
}
