package com.guestlogixtest.carlostorres.rickandmortyapp.model.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.guestlogixtest.carlostorres.rickandmortyapp.R;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.Holder>
{
    private List<Episode> episodes;
    private EpisodeClickListener episodeClickListener;

    public EpisodesAdapter(List<Episode> episodes, EpisodeClickListener episodeClickListener)
    {
        this.episodeClickListener = episodeClickListener;
        this.episodes = episodes;
    }

    public void addAllEpisodes(List<Episode> episodes)
    {
        this.episodes.clear();
        this.episodes.addAll(episodes);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        final Episode currentEpisode = episodes.get(position);
        holder.mName.setText(currentEpisode.getName());
        holder.mdate.setText(currentEpisode.getAirDate());
        holder.mEpisode.setText("Episode: " + currentEpisode.getEpisode());

        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                episodeClickListener.episodeClicked(currentEpisode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        private TextView mName, mdate, mEpisode;
        private ConstraintLayout mItem;

        public Holder(View itemView)
        {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mdate = itemView.findViewById(R.id.date);
            mEpisode = itemView.findViewById(R.id.episode);
            mItem = itemView.findViewById(R.id.layout);
        }
    }

    public interface EpisodeClickListener {
        void episodeClicked(Episode episode);
    }
}


