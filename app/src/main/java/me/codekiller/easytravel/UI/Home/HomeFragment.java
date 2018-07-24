package me.codekiller.easytravel.UI.Home;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.codekiller.easytravel.API.Utils;
import me.codekiller.easytravel.BuildConfig;
import me.codekiller.easytravel.R;
import me.codekiller.easytravel.UI.Result.ResultActivity;
import me.codekiller.easytravel.bean.ViewPotItem;
import me.codekiller.easytravel.bean.WordsResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View{
    private HomeContract.Presenter presenter;

    private AppCompatImageView cameraBtn;
    private AppCompatEditText searchInput;
    private RecyclerView recyclerView;

    private List<ViewPotItem> items;
    private RecyclerAdapter adapter;

    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int CROP_REQUEST_CODE = 100;

    private Context context;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();

        initViews(view);

        //获取热门景点
        presenter.getHotViewPot();

        return view;
    }

    @Override
    public void initViews(View view) {
        cameraBtn = view.findViewById(R.id.camera_btn);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(HomeFragment.this)
                        .choose(MimeType.ofAll()) //图片类型
                        .maxSelectable(1) //最大可选择数量
                        .capture(true) //选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "me.codekiller.easytravel.fileprovider")) //param1:true表示拍照存储在公有目录，false是私有目录
                        .imageEngine(new GlideEngine()) //图片加载引擎
                        .theme(R.style.MyPickerTheme)
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });

        searchInput = view.findViewById(R.id.search_input);
        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    //当软键盘里的搜索按钮按下后
                    String words = searchInput.getEditableText().toString();
                    if (words.trim().length() > 0){
                        jumpToResultPage(words);
                    }
                }
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 3;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        items = new ArrayList<>();
        adapter = new RecyclerAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);
    }

    private void jumpToResultPage(String words) {
        Intent intent = new Intent(getContext(), ResultActivity.class);
        intent.putExtra("words", words);
        startActivity(intent);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK){
            String imgPath = Matisse.obtainPathResult(data).get(0);
            Log.i("image path", imgPath);
            //跳转到图片裁剪
            jumpToCropImage(imgPath);
        } else if (requestCode == CROP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap pic = extras.getParcelable("data");
                String path = Utils.saveCrop(context,"crop-" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date()) + ".jpeg", pic);
                presenter.generaOCR(path);
            }
        }
    }

    private void jumpToCropImage(String imgPath) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //对intent授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider",new File(imgPath));
        } else {
            uri = Uri.fromFile(new File(imgPath));
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @Override
    public void resultOK(List<WordsResult.DataBean.ItemsBean> items) {
        StringBuilder builder = new StringBuilder();
        for (WordsResult.DataBean.ItemsBean item : items){
            builder.append(item.getItemstring());
            builder.append(" ");
        }
        jumpToResultPage(builder.toString());
    }

    @Override
    public void resultError(String msg) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onHotViewOk(List<ViewPotItem> items) {
        this.items.addAll(items);
        adapter.notifyDataSetChanged();
    }
}
