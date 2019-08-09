package com.guestlogixtest.carlostorres.rickandmortyapp.controller.remote;

import android.util.Log;

import com.guestlogixtest.carlostorres.rickandmortyapp.model.api.RestApiManager;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Data;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Controller {
    private static final String TAG = Controller.class.getSimpleName();
    private EpisodeCallbackListener episodeCallbackListener;
    private CharacterCallbackListener characterCallbackListener;
    private RestApiManager restApiManager;

    public Controller() {
        restApiManager = new RestApiManager();
    }

    public void setEpisodeCallbacks(EpisodeCallbackListener listener) {
        episodeCallbackListener = listener;
    }

    public void setCharacterCallbacks(CharacterCallbackListener listener) {
        characterCallbackListener = listener;
    }

    public void startEpisodeFetching() {
        restApiManager.getRickAndMortyApi().getEpisodes(new Callback<Data<Episode>>() {
            @Override
            public void success(Data<Episode> data, Response response) {
                Log.d(TAG, "JSON:: " + data);
                episodeCallbackListener.onFetchEpisodesProgress(data.getResults());
                episodeCallbackListener.onFetchEpisodesComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                episodeCallbackListener.onFetchEpisodesComplete();
            }
        });
    }

    public void startCharacterFetching(List<String> characters) {
        StringBuilder params = new StringBuilder();
        for (String url : characters) {
            params.append(url.split("/")[5].replace("\"", "") + ",");
        }
        restApiManager.getRickAndMortyApi().getCharacter(params.toString(), new Callback<List<Character>>() {
            @Override
            public void success(List<Character> data, Response response) {
                Log.d(TAG, "JSON:: " + data);
                characterCallbackListener.onFetchCharactersProgress(data);
                characterCallbackListener.onFetchCharactersComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                characterCallbackListener.onFetchCharactersComplete();
            }
        });
    }


    public interface EpisodeCallbackListener {
        void onFetchEpisodesProgress(List<Episode> episodes);

        void onFetchEpisodesComplete();
    }

    public interface CharacterCallbackListener {
        void onFetchCharactersProgress(List<Character> characterList);

        void onFetchCharactersComplete();
    }
}
