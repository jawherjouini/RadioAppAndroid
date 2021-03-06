package com.example.android.materialdesigncodelab.utils;

import android.app.Application;
import android.media.MediaPlayer;

import com.example.android.materialdesigncodelab.domains.RadioStation;

import java.util.List;

public class RadioApplication extends Application {
    public static String[] languages = {"ALBANIAN",
            "ARABIC",
            "CHINESE",
            "DEUTSCH",
            "ENGLISH",
            "FRENCH",
            "ITALIAN",
            "RUSSIAN",
            "TURKISH"};

    public static String[] tags = {"80S", "90S", "ALTERNATIVE",
            "CLASSICAL",
            "DANCE",
            "ELECTRO",
            "HIPHOP",
            "ROCK",
            "TRANCE"};

    public static List<RadioStation> listRadios, listRadiosByCountry, listRadiosToShow, listFavoris;

    public static RadioStation selectedRadio;
    public static String country = "TUNISIA";
    public static MediaPlayer mPlayer;
    public static boolean isPortrait=true;

}
