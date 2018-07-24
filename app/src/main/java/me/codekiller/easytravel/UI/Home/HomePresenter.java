package me.codekiller.easytravel.UI.Home;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.codekiller.easytravel.API.RetrofitHelper;
import me.codekiller.easytravel.API.Utils;
import me.codekiller.easytravel.UI.Home.HomeContract;
import me.codekiller.easytravel.bean.ViewPotItem;
import me.codekiller.easytravel.bean.WordsResult;
import me.codekiller.easytravel.model.ViewPotsManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HomePresenter implements HomeContract.Presenter {
    private Context context;
    private HomeContract.View view;

    public HomePresenter(Context context, HomeContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void generaOCR(String imagePath) {
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
            Observable<WordsResult> result = RetrofitHelper.getOcrService().generalOCR(Utils.host, Utils.authorization, Utils.appId, image);
            result.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<WordsResult>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(WordsResult wordsResult) {
                            if (wordsResult.getCode() == 0) {
                                view.resultOK(wordsResult.getData().getItems());
                            } else {
                                view.resultError(wordsResult.getMessage());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void getHotViewPot() {
        Observer<List<ViewPotItem>> observer = new Observer<List<ViewPotItem>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ViewPotItem> items) {
                ViewPotItem title = new ViewPotItem();
                title.setName("热门景点");
                items.add(0, title);
                view.onHotViewOk(items);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        ViewPotsManager.getInstance().getHotViewPots(observer);
    }
}
