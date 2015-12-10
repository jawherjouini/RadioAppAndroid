package com.example.android.materialdesigncodelab.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.domains.RadioStation;
import com.example.android.materialdesigncodelab.utils.IAsyncTask;
import com.example.android.materialdesigncodelab.utils.RadioApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by Seif on 08/12/2015.
 */
public class SplashScreen extends Activity implements IAsyncTask {

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        gson = new Gson();

        if (getCountry(this) != null) {
            RadioApplication.country = getCountry(this);
            Log.e("country", RadioApplication.country);
        }

        String serverURL = "http://www.radio-browser.info/webservice/json/stations";

        // Use AsyncTask execute Method To Prevent ANR Problem
        new LongOperation().execute(serverURL);

    }

    public static String getCountry(Context c) {
        TelephonyManager manager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        HashMap countryLookupMap = countryLookupMap = new HashMap();
        countryLookupMap.put("AD", "Andorra");
        countryLookupMap.put("AE", "United Arab Emirates");
        countryLookupMap.put("AF", "Afghanistan");
        countryLookupMap.put("AG", "Antigua and Barbuda");
        countryLookupMap.put("AI", "Anguilla");
        countryLookupMap.put("AL", "Albania");
        countryLookupMap.put("AM", "Armenia");
        countryLookupMap.put("AN", "Netherlands Antilles");
        countryLookupMap.put("AO", "Angola");
        countryLookupMap.put("AQ", "Antarctica");
        countryLookupMap.put("AR", "Argentina");
        countryLookupMap.put("AS", "American Samoa");
        countryLookupMap.put("AT", "Austria");
        countryLookupMap.put("AU", "Australia");
        countryLookupMap.put("AW", "Aruba");
        countryLookupMap.put("AZ", "Azerbaijan");
        countryLookupMap.put("BA", "Bosnia and Herzegovina");
        countryLookupMap.put("BB", "Barbados");
        countryLookupMap.put("BD", "Bangladesh");
        countryLookupMap.put("BE", "Belgium");
        countryLookupMap.put("BF", "Burkina Faso");
        countryLookupMap.put("BG", "Bulgaria");
        countryLookupMap.put("BH", "Bahrain");
        countryLookupMap.put("BI", "Burundi");
        countryLookupMap.put("BJ", "Benin");
        countryLookupMap.put("BM", "Bermuda");
        countryLookupMap.put("BN", "Brunei");
        countryLookupMap.put("BO", "Bolivia");
        countryLookupMap.put("BR", "Brazil");
        countryLookupMap.put("BS", "Bahamas");
        countryLookupMap.put("BT", "Bhutan");
        countryLookupMap.put("BV", "Bouvet Island");
        countryLookupMap.put("BW", "Botswana");
        countryLookupMap.put("BY", "Belarus");
        countryLookupMap.put("BZ", "Belize");
        countryLookupMap.put("CA", "Canada");
        countryLookupMap.put("CC", "Cocos (Keeling) Islands");
        countryLookupMap.put("CD", "Congo, The Democratic Republic of the");
        countryLookupMap.put("CF", "Central African Republic");
        countryLookupMap.put("CG", "Congo");
        countryLookupMap.put("CH", "Switzerland");
        countryLookupMap.put("CI", "Côte d?Ivoire");
        countryLookupMap.put("CK", "Cook Islands");
        countryLookupMap.put("CL", "Chile");
        countryLookupMap.put("CM", "Cameroon");
        countryLookupMap.put("CN", "China");
        countryLookupMap.put("CO", "Colombia");
        countryLookupMap.put("CR", "Costa Rica");
        countryLookupMap.put("CU", "Cuba");
        countryLookupMap.put("CV", "Cape Verde");
        countryLookupMap.put("CX", "Christmas Island");
        countryLookupMap.put("CY", "Cyprus");
        countryLookupMap.put("CZ", "Czech Republic");
        countryLookupMap.put("DE", "Germany");
        countryLookupMap.put("DJ", "Djibouti");
        countryLookupMap.put("DK", "Denmark");
        countryLookupMap.put("DM", "Dominica");
        countryLookupMap.put("DO", "Dominican Republic");
        countryLookupMap.put("DZ", "Algeria");
        countryLookupMap.put("EC", "Ecuador");
        countryLookupMap.put("EE", "Estonia");
        countryLookupMap.put("EG", "Egypt");
        countryLookupMap.put("EH", "Western Sahara");
        countryLookupMap.put("ER", "Eritrea");
        countryLookupMap.put("ES", "Spain");
        countryLookupMap.put("ET", "Ethiopia");
        countryLookupMap.put("FI", "Finland");
        countryLookupMap.put("FJ", "Fiji Islands");
        countryLookupMap.put("FK", "Falkland Islands");
        countryLookupMap.put("FM", "Micronesia, Federated States of");
        countryLookupMap.put("FO", "Faroe Islands");
        countryLookupMap.put("FR", "France");
        countryLookupMap.put("GA", "Gabon");
        countryLookupMap.put("GB", "United Kingdom");
        countryLookupMap.put("GD", "Grenada");
        countryLookupMap.put("GE", "Georgia");
        countryLookupMap.put("GF", "French Guiana");
        countryLookupMap.put("GH", "Ghana");
        countryLookupMap.put("GI", "Gibraltar");
        countryLookupMap.put("GL", "Greenland");
        countryLookupMap.put("GM", "Gambia");
        countryLookupMap.put("GN", "Guinea");
        countryLookupMap.put("GP", "Guadeloupe");
        countryLookupMap.put("GQ", "Equatorial Guinea");
        countryLookupMap.put("GR", "Greece");
        countryLookupMap.put("GS", "South Georgia and the South Sandwich Islands");
        countryLookupMap.put("GT", "Guatemala");
        countryLookupMap.put("GU", "Guam");
        countryLookupMap.put("GW", "Guinea-Bissau");
        countryLookupMap.put("GY", "Guyana");
        countryLookupMap.put("HK", "Hong Kong");
        countryLookupMap.put("HM", "Heard Island and McDonald Islands");
        countryLookupMap.put("HN", "Honduras");
        countryLookupMap.put("HR", "Croatia");
        countryLookupMap.put("HT", "Haiti");
        countryLookupMap.put("HU", "Hungary");
        countryLookupMap.put("ID", "Indonesia");
        countryLookupMap.put("IE", "Ireland");
        countryLookupMap.put("IL", "Israel");
        countryLookupMap.put("IN", "India");
        countryLookupMap.put("IO", "British Indian Ocean Territory");
        countryLookupMap.put("IQ", "Iraq");
        countryLookupMap.put("IR", "Iran");
        countryLookupMap.put("IS", "Iceland");
        countryLookupMap.put("IT", "Italy");
        countryLookupMap.put("JM", "Jamaica");
        countryLookupMap.put("JO", "Jordan");
        countryLookupMap.put("JP", "Japan");
        countryLookupMap.put("KE", "Kenya");
        countryLookupMap.put("KG", "Kyrgyzstan");
        countryLookupMap.put("KH", "Cambodia");
        countryLookupMap.put("KI", "Kiribati");
        countryLookupMap.put("KM", "Comoros");
        countryLookupMap.put("KN", "Saint Kitts and Nevis");
        countryLookupMap.put("KP", "North Korea");
        countryLookupMap.put("KR", "South Korea");
        countryLookupMap.put("KW", "Kuwait");
        countryLookupMap.put("KY", "Cayman Islands");
        countryLookupMap.put("KZ", "Kazakstan");
        countryLookupMap.put("LA", "Laos");
        countryLookupMap.put("LB", "Lebanon");
        countryLookupMap.put("LC", "Saint Lucia");
        countryLookupMap.put("LI", "Liechtenstein");
        countryLookupMap.put("LK", "Sri Lanka");
        countryLookupMap.put("LR", "Liberia");
        countryLookupMap.put("LS", "Lesotho");
        countryLookupMap.put("LT", "Lithuania");
        countryLookupMap.put("LU", "Luxembourg");
        countryLookupMap.put("LV", "Latvia");
        countryLookupMap.put("LY", "Libyan Arab Jamahiriya");
        countryLookupMap.put("MA", "Morocco");
        countryLookupMap.put("MC", "Monaco");
        countryLookupMap.put("MD", "Moldova");
        countryLookupMap.put("MG", "Madagascar");
        countryLookupMap.put("MH", "Marshall Islands");
        countryLookupMap.put("MK", "Macedonia");
        countryLookupMap.put("ML", "Mali");
        countryLookupMap.put("MM", "Myanmar");
        countryLookupMap.put("MN", "Mongolia");
        countryLookupMap.put("MO", "Macao");
        countryLookupMap.put("MP", "Northern Mariana Islands");
        countryLookupMap.put("MQ", "Martinique");
        countryLookupMap.put("MR", "Mauritania");
        countryLookupMap.put("MS", "Montserrat");
        countryLookupMap.put("MT", "Malta");
        countryLookupMap.put("MU", "Mauritius");
        countryLookupMap.put("MV", "Maldives");
        countryLookupMap.put("MW", "Malawi");
        countryLookupMap.put("MX", "Mexico");
        countryLookupMap.put("MY", "Malaysia");
        countryLookupMap.put("MZ", "Mozambique");
        countryLookupMap.put("NA", "Namibia");
        countryLookupMap.put("NC", "New Caledonia");
        countryLookupMap.put("NE", "Niger");
        countryLookupMap.put("NF", "Norfolk Island");
        countryLookupMap.put("NG", "Nigeria");
        countryLookupMap.put("NI", "Nicaragua");
        countryLookupMap.put("NL", "Netherlands");
        countryLookupMap.put("NO", "Norway");
        countryLookupMap.put("NP", "Nepal");
        countryLookupMap.put("NR", "Nauru");
        countryLookupMap.put("NU", "Niue");
        countryLookupMap.put("NZ", "New Zealand");
        countryLookupMap.put("OM", "Oman");
        countryLookupMap.put("PA", "Panama");
        countryLookupMap.put("PE", "Peru");
        countryLookupMap.put("PF", "French Polynesia");
        countryLookupMap.put("PG", "Papua New Guinea");
        countryLookupMap.put("PH", "Philippines");
        countryLookupMap.put("PK", "Pakistan");
        countryLookupMap.put("PL", "Poland");
        countryLookupMap.put("PM", "Saint Pierre and Miquelon");
        countryLookupMap.put("PN", "Pitcairn");
        countryLookupMap.put("PR", "Puerto Rico");
        countryLookupMap.put("PS", "Palestine");
        countryLookupMap.put("PT", "Portugal");
        countryLookupMap.put("PW", "Palau");
        countryLookupMap.put("PY", "Paraguay");
        countryLookupMap.put("QA", "Qatar");
        countryLookupMap.put("RE", "Réunion");
        countryLookupMap.put("RO", "Romania");
        countryLookupMap.put("RU", "Russian Federation");
        countryLookupMap.put("RW", "Rwanda");
        countryLookupMap.put("SA", "Saudi Arabia");
        countryLookupMap.put("SB", "Solomon Islands");
        countryLookupMap.put("SC", "Seychelles");
        countryLookupMap.put("SD", "Sudan");
        countryLookupMap.put("SE", "Sweden");
        countryLookupMap.put("SG", "Singapore");
        countryLookupMap.put("SH", "Saint Helena");
        countryLookupMap.put("SI", "Slovenia");
        countryLookupMap.put("SJ", "Svalbard and Jan Mayen");
        countryLookupMap.put("SK", "Slovakia");
        countryLookupMap.put("SL", "Sierra Leone");
        countryLookupMap.put("SM", "San Marino");
        countryLookupMap.put("SN", "Senegal");
        countryLookupMap.put("SO", "Somalia");
        countryLookupMap.put("SR", "Suriname");
        countryLookupMap.put("ST", "Sao Tome and Principe");
        countryLookupMap.put("SV", "El Salvador");
        countryLookupMap.put("SY", "Syria");
        countryLookupMap.put("SZ", "Swaziland");
        countryLookupMap.put("TC", "Turks and Caicos Islands");
        countryLookupMap.put("TD", "Chad");
        countryLookupMap.put("TF", "French Southern territories");
        countryLookupMap.put("TG", "Togo");
        countryLookupMap.put("TH", "Thailand");
        countryLookupMap.put("TJ", "Tajikistan");
        countryLookupMap.put("TK", "Tokelau");
        countryLookupMap.put("TM", "Turkmenistan");
        countryLookupMap.put("TN", "Tunisia");
        countryLookupMap.put("TO", "Tonga");
        countryLookupMap.put("TP", "East Timor");
        countryLookupMap.put("TR", "Turkey");
        countryLookupMap.put("TT", "Trinidad and Tobago");
        countryLookupMap.put("TV", "Tuvalu");
        countryLookupMap.put("TW", "Taiwan");
        countryLookupMap.put("TZ", "Tanzania");
        countryLookupMap.put("UA", "Ukraine");
        countryLookupMap.put("UG", "Uganda");
        countryLookupMap.put("UM", "United States Minor Outlying Islands");
        countryLookupMap.put("US", "United States");
        countryLookupMap.put("UY", "Uruguay");
        countryLookupMap.put("UZ", "Uzbekistan");
        countryLookupMap.put("VA", "Holy See (Vatican City State)");
        countryLookupMap.put("VC", "Saint Vincent and the Grenadines");
        countryLookupMap.put("VE", "Venezuela");
        countryLookupMap.put("VG", "Virgin Islands, British");
        countryLookupMap.put("VI", "Virgin Islands, U.S.");
        countryLookupMap.put("VN", "Vietnam");
        countryLookupMap.put("VU", "Vanuatu");
        countryLookupMap.put("WF", "Wallis and Futuna");
        countryLookupMap.put("WS", "Samoa");
        countryLookupMap.put("YE", "Yemen");
        countryLookupMap.put("YT", "Mayotte");
        countryLookupMap.put("YU", "Yugoslavia");
        countryLookupMap.put("ZA", "South Africa");
        countryLookupMap.put("ZM", "Zambia");
        countryLookupMap.put("ZW", "Zimbabwe");
        return countryLookupMap.get(manager.getNetworkCountryIso().toUpperCase()).toString().toUpperCase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void IAmFinished(ArrayList<RadioStation> arrayList) {
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        // Required initialization

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SplashScreen.this);
        String data = "";

        List<RadioStation> radios;

        private IAsyncTask asyncTaskListener;

        public LongOperation(IAsyncTask asyncTaskListener) {
            this.asyncTaskListener = asyncTaskListener;
        }

        public LongOperation() {

        }

        protected void onPreExecute() {
            //Start Progress Dialog (Message)
            Dialog.setMessage("Please wait..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            /************ Make Post Call To Web Server ***********/
            BufferedReader reader = null;
            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL(urls[0]);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                // Append Server Response To Content String
                Content = sb.toString();
            } catch (Exception ex) {
                Error = ex.getMessage();
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                }
            }

            /*****************************************************/
            return null;
        }

        protected void onPostExecute(Void unused) {
            radios = null;

            if (Error != null) {
                //TODO
                Log.e("JSON Error", Error);
            } else {
                /****************** Start Parse Response JSON Data *************/
                try {

                    JSONArray jsonMainNode = new JSONArray(Content);

                    /*********** Process each JSON Node ************/
                    radios = new ArrayList<>();
                    RadioApplication.listRadiosByCountry = new ArrayList<>();
                    int lengthJsonArr = jsonMainNode.length();
                    for (int i = 0; i < lengthJsonArr; i++) {
                        /****** Get Object for each JSON node.***********/
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        /******* Fetch node values **********/
                        String id = jsonChildNode.optString("id").toString();
                        String name = jsonChildNode.optString("name").toString();
                        String url = jsonChildNode.optString("url").toString();
                        String homepage = jsonChildNode.optString("homepage").toString();
                        String favicon = jsonChildNode.optString("favicon").toString();
                        String tags = jsonChildNode.optString("tags").toString();
                        String country = jsonChildNode.optString("country").toString();
                        String langua = jsonChildNode.optString("language").toString();
                        String votes = jsonChildNode.optString("votes").toString();
                        Boolean fav = false;

                        RadioStation radioStation = new RadioStation(id, url, name, homepage, favicon, tags, country, votes, fav, langua);
                        radios.add(radioStation);
                        if (country.toUpperCase().equals(RadioApplication.country)) {
                            RadioApplication.listRadiosByCountry.add(radioStation);
                        }
                    }
                    /****************** End Parse Response JSON Data *************/

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            if (RadioApplication.listRadiosByCountry == null) {
                Type type = new TypeToken<List<RadioStation>>() {
                }.getType();
                String json = getSharedPreferences("App", MODE_PRIVATE).getString("listcountry", null);
                RadioApplication.listRadiosByCountry = gson.fromJson(json, type);
            } else {
                String json = gson.toJson(RadioApplication.listRadiosByCountry);
                getSharedPreferences("App", MODE_PRIVATE).edit().putString("listcountry", json).commit();
            }

            if (radios == null) {
                Type type = new TypeToken<List<RadioStation>>() {
                }.getType();
                String json = getSharedPreferences("App", MODE_PRIVATE).getString("list", null);
                radios = gson.fromJson(json, type);
                Toast.makeText(getApplicationContext(), "Offline mode", Toast.LENGTH_LONG).show();
            } else {
                String json = gson.toJson(radios);
                getSharedPreferences("App", MODE_PRIVATE).edit().putString("list", json).commit();
            }

            // Close progress dialog
            Dialog.dismiss();

            RadioApplication.listRadios = radios;
            if (RadioApplication.listRadios != null && RadioApplication.listRadiosByCountry != null) {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        }

    }
}
