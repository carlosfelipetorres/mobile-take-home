package com.guestlogixtest.carlostorres.rickandmortyapp.model.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.guestlogixtest.carlostorres.rickandmortyapp.R;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.Holder>
{
    private List<Character> characters;
    private CharacterClickListener characterClickListener;

    public CharactersAdapter(List<Character> characters, CharacterClickListener characterClickListener)
    {
        this.characterClickListener = characterClickListener;
        this.characters = characters;
    }

    public void addAllEpisodes(List<Character> characters)
    {
        this.characters.clear();
        this.characters.addAll(characters);
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
        final Character currentCharacter = characters.get(position);
        holder.mName.setText(currentCharacter.getName());
        holder.mdate.setText(currentCharacter.getSpecies());
        holder.mEpisode.setText("Status: " + currentCharacter.getStatus());

        Glide.with(holder.itemView.getContext()).load(currentCharacter.getImage()).into(holder.mImage);
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                characterClickListener.characterClicked(currentCharacter);
            }
        });

        if (currentCharacter.getStatus().equals("Dead")){
            holder.mImageDead.setVisibility(View.VISIBLE);
            holder.mItem.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        } else {
            holder.mImageDead.setVisibility(View.GONE);
            holder.mItem.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        private TextView mName, mdate, mEpisode;
        private ImageView mImage, mImageDead;
        private ConstraintLayout mItem;

        public Holder(View itemView)
        {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mdate = itemView.findViewById(R.id.date);
            mEpisode = itemView.findViewById(R.id.episode);
            mItem = itemView.findViewById(R.id.layout);
            mImage = itemView.findViewById(R.id.image);
            mImageDead = itemView.findViewById(R.id.imageDead);
        }
    }

    public interface CharacterClickListener {
        void characterClicked(Character character);
    }
}


