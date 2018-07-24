package me.codekiller.easytravel.UI.Result;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;

import java.util.ArrayList;
import java.util.List;

import me.codekiller.easytravel.R;
import me.codekiller.easytravel.bean.ViewPotItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment implements ResultContract.View{
    private ResultContract.Presenter presenter;

    private AppCompatTextView sourceText;
    private AppCompatTextView translateText;
    private RecyclerView recyclerView;

    private ViewPotRecyclerAdapter viewPotRecyclerAdapter;
    private List<ViewPotItem> viewPotItems;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(){
        return new ResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        String words = bundle.getString("words");

        Language from = LanguageUtils.getLangByName("自动");
        Language to = LanguageUtils.getLangByName("中文");
        presenter.translate(from, to, words);

        return view;
    }

    @Override
    public void initViews(View view) {
        sourceText = view.findViewById(R.id.source_text);
        translateText = view.findViewById(R.id.translate_text);

        recyclerView = view.findViewById(R.id.view_pot_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        viewPotItems = new ArrayList<>();
        viewPotRecyclerAdapter = new ViewPotRecyclerAdapter(getContext(), viewPotItems);
        recyclerView.setAdapter(viewPotRecyclerAdapter);
    }

    @Override
    public void setPresenter(ResultContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onTranslateResult(Translate translate, String source) {
        //翻译结果回调没在主线程中
        List<String> translations = translate.getTranslations();
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : translations){
            stringBuilder.append(s);
        }
        String result = stringBuilder.toString();

        sourceText.setText(source);
        translateText.setText(result);

        presenter.searchViewPot(result);
    }

    @Override
    public void onTranslateError(TranslateErrorCode errorCode) {

    }

    @Override
    public void onViewPotResult(List<ViewPotItem> items) {
        viewPotItems.addAll(items);
        viewPotRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewPotError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
