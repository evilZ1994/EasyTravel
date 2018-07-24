package me.codekiller.easytravel.UI.Detail;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import me.codekiller.easytravel.R;

public class ViewPotActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pot);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        ViewPotFragment viewPotFragment = (ViewPotFragment) getSupportFragmentManager().findFragmentById(R.id.view_pot_container);
        if (viewPotFragment == null) {
            viewPotFragment = ViewPotFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            viewPotFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.view_pot_container, viewPotFragment);
            transaction.commit();
        }
        ViewPotPresenter potPresenter = new ViewPotPresenter(this, viewPotFragment);
    }
}
