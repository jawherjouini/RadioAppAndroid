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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.activities.RadiosCardActivity;
import com.example.android.materialdesigncodelab.utils.RadioApplication;

/**
 * Provides UI for the view with Tile.
 */
public class ByTagsFragment extends Fragment {

    public ByTagsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView list_title;
        String tag;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            list_title = (TextView) itemView.findViewById(R.id.tile_title);
            imageView = (ImageView) itemView.findViewById(R.id.tag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RadiosCardActivity.class).putExtra("extra", tag.toUpperCase());
                    context.startActivity(intent);
                }
            });
        }

    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Tiles in RecyclerView

        public ContentAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_tile, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.list_title.setText(RadioApplication.tags[position]);
            holder.tag = RadioApplication.tags[position];
            switch (position) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.a);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.b);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.c);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.d);
                    break;
                case 4:
                    holder.imageView.setImageResource(R.drawable.e);
                    break;
                case 5:
                    holder.imageView.setImageResource(R.drawable.f);
                    break;
                case 6:
                    holder.imageView.setImageResource(R.drawable.g);
                    break;
                case 7:
                    holder.imageView.setImageResource(R.drawable.h);
                    break;
                case 8:
                    holder.imageView.setImageResource(R.drawable.i);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return RadioApplication.tags.length;
        }
    }
}