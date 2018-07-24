package me.codekiller.easytravel.UI.Result;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.codekiller.easytravel.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String words = intent.getStringExtra("words");

        ResultFragment resultFragment = (ResultFragment)getSupportFragmentManager().findFragmentById(R.id.result_container);
        if (resultFragment == null){
            resultFragment = ResultFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("words", words);
            resultFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.result_container, resultFragment);
            transaction.commit();
        }
        ResultPresenter presenter = new ResultPresenter(this, resultFragment);
    }
}
