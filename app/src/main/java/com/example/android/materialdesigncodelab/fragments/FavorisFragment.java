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

package com.example.android.materialdesigncodelab.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.activities.DetailActivity;
import com.example.android.materialdesigncodelab.domains.RadioStation;
import com.example.android.materialdesigncodelab.utils.RadioApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides UI for the view with Cards.
 */
public class FavorisFragment extends Fragment {
    static Activity activity;
    private String jsonFav;
    private static Gson gson;
    public static ContentAdapter adapter;

    public static void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        activity = getActivity();
        gson = new Gson();
        Type type = new TypeToken<List<RadioStation>>() {
        }.getType();
        jsonFav = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE).getString("fav", null);
        if (jsonFav != null) {
            RadioApplication.listFavoris = gson.fromJson(jsonFav, type);
        } else {
            RadioApplication.listFavoris = new ArrayList<>();
        }

        adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView list_title, list_desc;
        ImageView list_avatar;
        Button button;
        ImageButton favoriteImageButton, shareImageButton;
        String url;

        public ViewHolder(View itemView) {
            super(itemView);
            list_title = (TextView) itemView.findViewById(R.id.card_title);
            list_desc = (TextView) itemView.findViewById(R.id.card_text);
            list_avatar = (ImageView) itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    RadioApplication.selectedRadio = RadioApplication.listFavoris.get(getPosition());
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });

            // Adding Snackbar to Action Button inside card
            button = (Button) itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = RadioApplication.listFavoris.get(getPosition()).getHomepage();
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
                    RadioApplication.listFavoris.remove(RadioApplication.listFavoris.get(getPosition()));
                    String json = gson.toJson(RadioApplication.listFavoris);
                    activity.getSharedPreferences("App", activity.MODE_PRIVATE).edit().putString("fav", json).commit();
                    refresh();
                    Snackbar.make(v, "Removed from Favorite",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioApplication.selectedRadio = RadioApplication.listFavoris.get(getPosition());
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

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_card, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RadioStation item = RadioApplication.listFavoris.get(position);
            RadioApplication.selectedRadio = item;
            holder.list_title.setText(item.getName());

            //TODO change message
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
            return RadioApplication.listFavoris.size();
        }
    }
}
