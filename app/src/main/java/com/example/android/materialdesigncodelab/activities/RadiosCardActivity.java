package com.example.android.materialdesigncodelab.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.domains.RadioStation;
import com.example.android.materialdesigncodelab.utils.InitiateSearch;
import com.example.android.materialdesigncodelab.utils.RadioApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RadiosCardActivity extends AppCompatActivity {
    private static Activity activity;
    private Toolbar toolbar;
    private Resources res;
    private InitiateSearch initiateSearch;
    private ImageView clearSearch;
    private View toolbar_shadow;
    private View view_search;
    public static EditText edit_text_search;
    private CardView card_search;
    private ImageView image_search_back;
    private String jsonFav;
    Boolean verif = false; //False veut dire que c'est un langage (pas une catégorie)
    private String extra;
    private Bundle bundle;
    public static Gson gson;
    public static RecyclerView recyclerView;
    public static ContentAdapter adapter;

    public void initial() {
        gson = new Gson();
        Type type = new TypeToken<List<RadioStation>>() {
        }.getType();
        jsonFav = this.getSharedPreferences("App", Context.MODE_PRIVATE).getString("fav", null);
        if (jsonFav != null) {
            RadioApplication.listFavoris = gson.fromJson(jsonFav, type);
        } else {
            RadioApplication.listFavoris = new ArrayList<>();
        }
        RadioApplication.listRadiosToShow = new ArrayList<>();
        if (!bundle.getString("extra").equals("")) extra = bundle.getString("extra");
        if (Arrays.asList(RadioApplication.tags).contains(extra))
            verif = true; //True veut dire que c'est un tag (catégorie)
        for (RadioStation item : RadioApplication.listRadios) {
            if ((verif && item.getTags().toUpperCase().contains(extra)) || (!verif && item.getLangua().toUpperCase().contains(extra))) {
                RadioApplication.listRadiosToShow.add(item);
            }
        }
        adapter = new ContentAdapter(RadioApplication.listRadiosToShow);
        swap(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radios_card);
        activity = this;
        initiateSearch = new InitiateSearch();
        res = this.getResources();
        extra = getIntent().getStringExtra("extra");
        bundle = new Bundle();
        bundle.putCharSequence("extra", extra);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(RadiosCardActivity.adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar_shadow = findViewById(R.id.toolbar_shadow);
        view_search = findViewById(R.id.view_search);
        edit_text_search = (EditText) findViewById(R.id.edit_text_search);
        card_search = (CardView) findViewById(R.id.card_search);
        image_search_back = (ImageView) findViewById(R.id.image_search_back);
        clearSearch = (ImageView) findViewById(R.id.clearSearch);
        initial();
        setTypeFace();
        initiateSearch();
        handleSearch();
    }

    private void handleSearch() {
        image_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch.handleToolBar(RadiosCardActivity.this, card_search, toolbar, view_search, edit_text_search);
                toolbar_shadow.setVisibility(View.VISIBLE);
            }
        });
        edit_text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO validate search
                    if (edit_text_search.getText().toString().trim().length() > 0) {
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edit_text_search.getWindowToken(), 0);
                        toolbar_shadow.setVisibility(View.GONE);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initiateSearch() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItem = item.getItemId();
                switch (menuItem) {
                    case R.id.action_search:
                        initiateSearch.handleToolBar(RadiosCardActivity.this, card_search, toolbar, view_search, edit_text_search);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        edit_text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().toUpperCase();
                if (edit_text_search.getText().toString().length() == 0) {
                    clearSearch.setImageResource(R.mipmap.ic_keyboard_voice);
                    initial();
                } else {
                    clearSearch.setImageResource(R.mipmap.ic_close);
                    //TODO search
                    List<RadioStation> list2 = new ArrayList<RadioStation>();
                    for (Iterator<RadioStation> it = RadioApplication.listRadiosToShow.iterator(); it.hasNext(); ) {
                        RadioStation item = it.next();
                        if (item.getName().toUpperCase().contains(str)) {
                            list2.add(item);
                        }
                    }
                    adapter = new ContentAdapter(list2);
                    swap(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_text_search.getText().toString().length() == 0) {
                    //TODO voice search
                    vocalSearch();
                } else {
                    edit_text_search.setText("");
                    ((InputMethodManager) RadiosCardActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
    }

    private void swap(ContentAdapter adapter) {
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    //TODO vocal
    public void vocalSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "talk now !");
        try {
            startActivityForResult(intent, 123);

        } catch (ActivityNotFoundException e) {
            Intent intentinstall = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.google.android.voicesearch"));
            startActivity(intentinstall);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            ArrayList<String> results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            edit_text_search.setText(results.get(0));

        } else {
            Log.e("vocal", "not ok");
        }
    }

    private void setTypeFace() {
        Typeface roboto_regular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        edit_text_search.setTypeface(roboto_regular);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView list_title, list_desc;
        ImageView list_avatar;
        Button button;
        ImageButton favoriteImageButton, shareImageButton;
        String url;
        List<RadioStation> listRadiosToShow;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(View itemView, final List<RadioStation> listRadiosToShow) {
            super(itemView);
            this.listRadiosToShow = listRadiosToShow;
            list_title = (TextView) itemView.findViewById(R.id.card_title);
            list_desc = (TextView) itemView.findViewById(R.id.card_text);
            list_avatar = (ImageView) itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    RadioApplication.selectedRadio = listRadiosToShow.get(getPosition());
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });

            // Adding Snackbar to Action Button inside card
            button = (Button) itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = listRadiosToShow.get(getPosition()).getHomepage();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(browserIntent);
                }
            });

            favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    if (!RadioApplication.listFavoris.contains(listRadiosToShow.get(getPosition()))) {
                        RadioApplication.listFavoris.add(listRadiosToShow.get(getPosition()));
                        String json = gson.toJson(RadioApplication.listFavoris);
                        activity.getSharedPreferences("App", activity.MODE_PRIVATE).edit().putString("fav", json).commit();
                        Snackbar.make(v, "Added to Favorite",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(v, "Already in favorites",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioApplication.selectedRadio = listRadiosToShow.get(getPosition());
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/html");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Radio FM share");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "I am listening to " + RadioApplication.selectedRadio.toString());
                    activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Card in RecyclerView.
        List<RadioStation> listRadiosToShow;

        public ContentAdapter() {
        }

        public ContentAdapter(List<RadioStation> list) {
            listRadiosToShow = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_card, parent, false);
            ViewHolder vh = new ViewHolder(v, listRadiosToShow);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RadioStation item = listRadiosToShow.get(position);
            RadioApplication.selectedRadio = item;
            holder.list_title.setText(item.getName());
            holder.list_desc.setText(item.getLangua() + " - " + item.getTags());
            try {
                if (item.getFavicon().contains(".ico")) throw new Exception();
                Picasso.with(activity).load(item.getFavicon())
                        .into(holder.list_avatar);
            } catch (Exception e) {
                holder.list_avatar.setImageResource(R.drawable.radiooooos);
                Log.e("exception", "favicon: " + e.getMessage());
            }

            //URL for the web browser
            if (!item.getHomepage().startsWith("http://") && !item.getHomepage().startsWith("https://"))
                item.setHomepage("http://" + item.getHomepage());
            holder.url = item.getHomepage();
        }

        @Override
        public int getItemCount() {
            return listRadiosToShow.size();
        }
    }
}
