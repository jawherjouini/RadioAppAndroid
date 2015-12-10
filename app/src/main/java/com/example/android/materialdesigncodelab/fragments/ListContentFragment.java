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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.activities.DetailActivity;
import com.example.android.materialdesigncodelab.domains.RadioStation;
import com.example.android.materialdesigncodelab.utils.RadioApplication;
import com.squareup.picasso.Picasso;

/**
 * Provides UI for the view with List.
 */
public class ListContentFragment extends Fragment {

    static Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        activity = getActivity();
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView list_title, list_desc;
        ImageView list_avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            list_title = (TextView) itemView.findViewById(R.id.list_title);
            list_desc = (TextView) itemView.findViewById(R.id.list_desc);
            list_avatar = (ImageView) itemView.findViewById(R.id.list_avatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    RadioApplication.selectedRadio = RadioApplication.listRadiosByCountry.get(getPosition());
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        public ContentAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //Log.e("item", RadioApplication.listRadiosByCountry.get(position).toString());
            RadioStation item = RadioApplication.listRadiosByCountry.get(position);
            holder.list_title.setText(item.getName());
            holder.list_desc.setText(item.getHomepage());
            try {
                if (item.getFavicon().contains(".ico")) throw new Exception();
                Picasso.with(activity).load(item.getFavicon())
                        .into(holder.list_avatar);
            } catch (Exception e) {
                holder.list_avatar.setImageResource(R.drawable.radiooooos);
                Log.e("exception", "favicon: " + e.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return RadioApplication.listRadiosByCountry.size();
        }
    }

}
