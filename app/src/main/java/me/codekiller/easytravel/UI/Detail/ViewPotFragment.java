package me.codekiller.easytravel.UI.Detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import me.codekiller.easytravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPotFragment extends Fragment implements ViewPotContract.View{
    private WebView webView;

    private ViewPotContract.Presenter presenter;

    public ViewPotFragment() {
        // Required empty public constructor
    }

    public static ViewPotFragment newInstance() {
        return new ViewPotFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pot, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        String url = bundle.getString("url");
        presenter.loadViewPot(url);

        return view;
    }

    @Override
    public void showViewPot(String html) {
        webView.loadData(html, "text/html; charset=UTF-8", null);
    }

    @Override
    public void initViews(View view) {
        webView = view.findViewById(R.id.view_pot_web);
    }

    @Override
    public void setPresenter(ViewPotContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
