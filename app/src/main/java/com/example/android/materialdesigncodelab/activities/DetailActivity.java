/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialdesigncodelab.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.utils.RadioApplication;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    ImageView image;
    TextView tags, language;
    TextView homepage, country;
    FloatingActionButton play, stop;
    private MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(RadioApplication.selectedRadio.getName());

        image = (ImageView) findViewById(R.id.image);
        language = (TextView) findViewById(R.id.langua);
        tags = (TextView) findViewById(R.id.tags);
        homepage = (TextView) findViewById(R.id.homepage);
        country = (TextView) findViewById(R.id.country);


        language.setText(RadioApplication.selectedRadio.getLangua());
        tags.setText(RadioApplication.selectedRadio.getTags());
        homepage.setText(RadioApplication.selectedRadio.getHomepage());
        country.setText(RadioApplication.selectedRadio.getCountry());

        try {
            if (RadioApplication.selectedRadio.getFavicon().contains(".ico")) throw new Exception();
            Picasso.with(this).load(RadioApplication.selectedRadio.getFavicon())
                    .into(image);
        } catch (Exception e) {
            image.setImageResource(R.drawable.radiooooos);
            Log.e("exception", "favicon: " + e.getMessage());
        }

        // Adding Floating Action Button to bottom right of main view
        play = (FloatingActionButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                mPlayer = new MediaPlayer();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mPlayer.setDataSource(RadioApplication.selectedRadio.getUrl());
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mPlayer.prepare();
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                }
                mPlayer.start();
            }
        });

        stop = (FloatingActionButton) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
                if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
