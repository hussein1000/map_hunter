package com.map.hunter.drawer;

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