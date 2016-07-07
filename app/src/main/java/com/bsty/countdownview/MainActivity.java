package com.bsty.countdownview;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private CountDownDialogFragment countDownDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.id_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                if (countDownDialogFragment != null) {
                    getFragmentManager().beginTransaction().remove(countDownDialogFragment);
                }
                countDownDialogFragment = new CountDownDialogFragment();
                countDownDialogFragment.show(getFragmentManager(), "countDownDialog");
            }
        });

    }

}
