package me.codekiller.easytravel.model;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.codekiller.easytravel.bean.ViewPotItem;

public class ViewPotsManager {
    private static ViewPotsManager manager;
    private static String viewPotUrl = "https://jingdian.supfree.net/";
    private static String searchM = "search.asp";

    public static ViewPotsManager getInstance() {
        if (manager == null) {
            manager = new ViewPotsManager();
        }

        return manager;
    }

    public void searchViewPots(Observer<List<ViewPotItem>> observer, final String key) {
        Observable.create(new ObservableOnSubscribe<List<ViewPotItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ViewPotItem>> emitter) throws Exception {
                List<ViewPotItem> items = new ArrayList<>();
                String enKey = URLEncoder.encode(key, "GB2312");
                String url = viewPotUrl + searchM + "?key=" + enKey;
                Document document = Jsoup.connect(url).get();
                Elements uls = document.getElementsByClass("oul tul");
                if (uls.size() > 0) {
                    Element ul = uls.first();
                    Elements lists = ul.getElementsByTag("li");
                    for (Element li : lists) {
                        Element a = li.getElementsByTag("a").first();
                        Element img = a.getElementsByTag("img").first();
                        ViewPotItem item = new ViewPotItem();
                        item.setName(a.text());
                        item.setUrl(viewPotUrl + a.attr("href"));
                        String imgUrl = img.attr("src");
                        if (imgUrl.contains("nopic")) {
                            item.setImgUrl("http://upyun.nidhogg.cn/img/load_fail.png");
                        } else {
                            item.setImgUrl(imgUrl);
                        }

                        items.add(item);
                    }

                    emitter.onNext(items);
                } else {
                    emitter.onError(new Throwable("View pot not found"));
                }

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getHotViewPots(Observer<List<ViewPotItem>> observer) {
        Observable.create(new ObservableOnSubscribe<List<ViewPotItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ViewPotItem>> emitter) throws Exception {
                List<ViewPotItem> items = new ArrayList<>();
                Document document = Jsoup.connect(viewPotUrl).get();
                Elements uls = document.getElementsByClass("oul tuls");
                if (uls.size() > 0) {
                    Element ul = uls.first();
                    Elements lists = ul.getElementsByTag("li");
                    for (Element li : lists) {
                        Element a = li.getElementsByTag("a").first();
                        Element img = a.getElementsByTag("img").first();
                        ViewPotItem item = new ViewPotItem();
                        item.setName(a.text());
                        item.setUrl(viewPotUrl + a.attr("href"));
                        String imgUrl = img.attr("src");
                        if (imgUrl.contains("nopic")) {
                            item.setImgUrl("http://upyun.nidhogg.cn/img/load_fail.png");
                        } else {
                            item.setImgUrl(imgUrl);
                        }

                        items.add(item);
                    }

                    emitter.onNext(items);
                } else {
                    emitter.onError(new Throwable("View pot not found"));
                }

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadViewPot(Observer<String> observer, final String url) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Document document = Jsoup.connect(url).get();

                Elements cdivs = document.getElementsByClass("cdiv");
                if (cdivs.size() > 1) {
                    Element cdiv = cdivs.get(1);
                    //清除<a>标签链接
                    Elements atags = cdiv.getElementsByTag("a");
                    for (Element atag : atags) {
                        atag.removeAttr("href");
                    }
                    if (cdivs.size() > 2) {
                        Element gallery = cdivs.get(2);
                        Elements divs = gallery.getElementsByTag("div");
                        for (Element div : divs) {
                            div.after("<br>");
                        }
                        //清除<a>标签链接
                        Elements atags2 = gallery.getElementsByTag("a");
                        for (Element atag : atags2) {
                            atag.removeAttr("href");
                        }
                        emitter.onNext(cdiv.html() + gallery.html());
                    } else {
                        emitter.onNext(cdiv.html());
                    }
                } else {
                    emitter.onNext("暂无信息");
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
