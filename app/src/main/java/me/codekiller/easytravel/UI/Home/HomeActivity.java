package me.codekiller.easytravel.UI.Home;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.codekiller.easytravel.R;
import me.codekiller.easytravel.UI.Home.HomeFragment;
import me.codekiller.easytravel.UI.Home.HomePresenter;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = (HomeFragment)getSupportFragmentManager().findFragmentById(R.id.container);
        if (homeFragment == null){
            homeFragment = HomeFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, homeFragment);
            transaction.commit();
        }
        HomePresenter homePresenter = new HomePresenter(this, homeFragment);
    }
}
