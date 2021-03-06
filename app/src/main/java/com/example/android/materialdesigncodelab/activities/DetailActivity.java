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

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.fragments.MyDialogFragment;
import com.example.android.materialdesigncodelab.fragments.SavedDialogFragment;
import com.example.android.materialdesigncodelab.utils.RadioApplication;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity implements MyDialogFragment.NoticeDialogListener {

    ImageView image;
    TextView tags, language;
    TextView homepage, country;
    FloatingActionButton play, stop;

ProgressBar progressBar;
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private FloatingActionButton record;
    private MediaRecorder mRecorder = null;
    boolean mStartRecording = false;
 //   private MediaPlayer   mPlayer = null;

   private DetailActivity self;

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressBar=(ProgressBar)findViewById(R.id.pb);
        progressBar.setVisibility(View.GONE);
        if(!RadioApplication.isPortrait)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        self= this;
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
        record = (FloatingActionButton) findViewById(R.id.record);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mStartRecording){
                    DialogFragment newFragment = new MyDialogFragment();
                    newFragment.show(getFragmentManager(), "File Name");

                }else
                {
                    onRecord(false);
                    onPlay(true);
                    mStartRecording = false;
                    record.setImageResource(R.drawable.mic);
                    DialogFragment newFragment = new SavedDialogFragment();
                    newFragment.show(getFragmentManager(), "Saved File");


                }


            }
        });
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

new LongOperation().execute();
            }

        });

        stop = (FloatingActionButton) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
                if (RadioApplication.mPlayer != null && RadioApplication.mPlayer .isPlaying()) {
                    RadioApplication.mPlayer .stop();
                    RadioApplication.mPlayer=null;
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

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        RadioApplication.mPlayer  = new MediaPlayer();
        try {
            RadioApplication.mPlayer .setDataSource(mFileName);
            RadioApplication.mPlayer .prepare();
            RadioApplication.mPlayer .start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        RadioApplication.mPlayer .release();
        RadioApplication.mPlayer  = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    public void AudioRecordTest(String name) {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += name+".3gp";
        Log.e("filename",mFileName);
    }


    @Override
    public void onDialogPositiveClick(MyDialogFragment dialog) {
        Log.e("save","seif");
        if(!dialog.editText.getText().toString().isEmpty()){
            self.AudioRecordTest("/"+dialog.editText.getText().toString());
            record.setImageResource(R.drawable.recording);
            onRecord(true);

            mStartRecording = true;
        }else
            Toast.makeText(getApplicationContext(),"File name could not be empty",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDialogNegativeClick(MyDialogFragment dialog) {

    }

    private class LongOperation extends AsyncTask<String, Void, String> {
public boolean ok=false;
        @Override
        protected String doInBackground(String... params) {
            if(ok){

                if (RadioApplication.mPlayer != null && RadioApplication.mPlayer .isPlaying()) {
                    RadioApplication.mPlayer .stop();
                }
                RadioApplication.mPlayer = new MediaPlayer();
                RadioApplication.mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    RadioApplication.mPlayer.setDataSource(RadioApplication.selectedRadio.getUrl());
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "Url Incorrect", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(getApplicationContext(), "Url Incorrect", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "Url Incorrect!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    RadioApplication.mPlayer.prepare();
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "Url Incorrect", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Url Incorrect", Toast.LENGTH_LONG).show();
                }
                RadioApplication.mPlayer.start();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

        }

        @Override
        protected void onPreExecute() {
            if(isOnline()){
                progressBar.setVisibility(View.VISIBLE);
                ok=true;
                play.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);}
            else{

                new AlertDialog.Builder(DetailActivity.this)
                        .setTitle("Internet Configuration")
                        .setMessage("You must open internet connection")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }


    }
}
