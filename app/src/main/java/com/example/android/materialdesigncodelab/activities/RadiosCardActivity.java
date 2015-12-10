package com.example.android.materialdesigncodelab.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.fragments.CardContentFragment;

public class RadiosCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radios_card);
        String extra = getIntent().getStringExtra("extra");
        Bundle bundle = new Bundle();
        bundle.putCharSequence("extra", extra);
        replaceFragment(new CardContentFragment(), bundle);
    }


    private void replaceFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}
