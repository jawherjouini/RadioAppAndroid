package com.example.android.materialdesigncodelab.utils;

import android.app.Application;

import com.example.android.materialdesigncodelab.domains.RadioStation;

import java.util.List;

/**
 * Created by Jawher on 09/12/2015.
 */
public class RadioApplication extends Application {
    public static List<RadioStation> listRadios,listRadiosByCountry,listRadiosByTags,listRadiosByLang;
    public static RadioStation selectedRadio;
    public static String country="TUNISIA";
}
