package com.map.hunter.drawer;
/* Copyright 2020 Thomas Schneider
 *
 * This file is a part of OpenMultiMaps
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * OpenMultiMaps is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with OpenMultiMaps; if not,
 * see <http://www.gnu.org/licenses>. */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.map.hunter.databinding.DrawerContributorsBinding;
import com.map.hunter.entity.Contributor;


public class ContributorsDrawer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Contributor> contributors;

    public ContributorsDrawer(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DrawerContributorsBinding itemBinding = DrawerContributorsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final Contributor contributor = contributors.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.binding.mainTitle.setText(contributor.getMain_title());
        holder.binding.project.setText(contributor.getProject());
        holder.binding.mapUrl.setText(contributor.getMap_url());
        holder.binding.sourceCode.setText(contributor.getSource_code());
        holder.binding.maintainer.setText(contributor.getMaintainer());
        holder.binding.donations.setText(contributor.getDonations());
        if( contributor.isHasTitle()) {
            holder.binding.mainTitle.setVisibility(View.VISIBLE);
        }else{
            holder.binding.mainTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        DrawerContributorsBinding binding;
        ViewHolder(DrawerContributorsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
