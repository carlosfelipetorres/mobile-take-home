package com.guestlogixtest.carlostorres.rickandmortyapp.controller.remote;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Data;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Controller {
    private EpisodeCallbackListener episodeCallbackListener;
    private CharacterCallbackListener characterCallbackListener;

    public Controller() {}

    public void setEpisodeCallbacks(EpisodeCallbackListener listener) {
        episodeCallbackListener = listener;
    }

    public void setCharacterCallbacks(CharacterCallbackListener listener) {
        characterCallbackListener = listener;
    }

    public void startEpisodeFetching() {
        new RetrieveEpisodeData().execute();
    }

    public void startCharacterFetching(List<String> characters) {
        StringBuilder params = new StringBuilder();
        for (String url : characters) {
            params.append(url.split("/")[5] + ",");
        }
        new RetrieveCharacterData().execute(params.toString());
    }


    public interface EpisodeCallbackListener {
        void onFetchEpisodesProgress(List<Episode> episodes);

        void onFetchEpisodesComplete();
    }

    public interface CharacterCallbackListener {
        void onFetchCharactersProgress(List<Character> characterList);

        void onFetchCharactersComplete();
    }

    private class RetrieveEpisodeData extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private String API_URL = "https://rickandmortyapi.com/api";

        protected void onPreExecute() {}

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL(API_URL + "/episode");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            Log.i("INFO", response);
            Type listType = new TypeToken<Data<Episode>>(){}.getType();
            Data<Episode> data = new Gson().fromJson(response, listType);

            episodeCallbackListener.onFetchEpisodesProgress(data.getResults());
            episodeCallbackListener.onFetchEpisodesComplete();
        }
    }

    private class RetrieveCharacterData extends AsyncTask<String, Void, String> {

        private Exception exception;
        private String API_URL = "https://rickandmortyapi.com/api";

        protected void onPreExecute() {}

        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(API_URL + "/character/" + urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            Log.i("INFO", response);
            Type listType = new TypeToken<List<Character>>(){}.getType();
            List<Character> data = new Gson().fromJson(response, listType);

            characterCallbackListener.onFetchCharactersProgress(data);
            characterCallbackListener.onFetchCharactersComplete();
        }
    }
}
