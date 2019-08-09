package com.guestlogixtest.carlostorres.rickandmortyapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guestlogixtest.carlostorres.rickandmortyapp.R;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.local.CharacterRepository;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.local.RickAndMortyDatabase;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.remote.Controller;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.adapter.CharactersAdapter;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements Controller.CharacterCallbackListener, CharactersAdapter.CharacterClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Character> characterList = new ArrayList<>();
    private CharactersAdapter charactersAdapter = new CharactersAdapter(characterList, this);
    private Controller controller;
    private Episode episode;
    private int position;
    private CharacterRepository characterRepository;
    private List<Character> savedCharacters;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(Episode episode, int position) {
        ListFragment contentFragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable("episode", episode);
        args.putInt("position", position);
        contentFragment.setArguments(args);
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        controller = new Controller();
        controller.setCharacterCallbacks(ListFragment.this);

        configViews(view);

        episode = (Episode) getArguments().getSerializable("episode");
        position = getArguments().getInt("position");
        characterRepository = new CharacterRepository(RickAndMortyDatabase.getDatabase(getActivity().getApplicationContext()).characterDao());

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        controller.startCharacterFetching(episode.getCharacters());
        super.onResume();
    }

    private void configViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.list);
        swipeRefreshLayout = view.findViewById(R.id.swipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(charactersAdapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                controller.startCharacterFetching(episode.getCharacters());
            }
        });
    }

    @Override
    public void onFetchCharactersProgress(List<Character> characters) {
        List<Character> filteredList = new ArrayList<>();
        for (Character character : characters) {
            savedCharacters = characterRepository.charactersById(character.getId().toString(), episode.getEpisode());
            if (savedCharacters.size() > 0) {
                character.setStatus(savedCharacters.get(0).getStatus());
            }
            if (position == 1 && character.getStatus().equals("Dead")){
                filteredList.add(character);
            } else if (position == 0 && !character.getStatus().equals("Dead")){
                filteredList.add(character);
            }
        }

        charactersAdapter.addAllEpisodes(filteredList);
    }

    @Override
    public void onFetchCharactersComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void characterClicked(Character character) {
        Intent i = new Intent(getActivity(), CharacterDetailActivity.class);
        i.putExtra("character", character);
        i.putExtra("episode", episode);
        startActivity(i);
    }
}
