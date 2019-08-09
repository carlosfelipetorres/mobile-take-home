package com.guestlogixtest.carlostorres.rickandmortyapp.controller.local;

import android.support.annotation.WorkerThread;

import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterRepository {

    CharacterDao characterDao;

    public CharacterRepository(CharacterDao characterDao) {
        this.characterDao = characterDao;
    }

    public List<Character> allCharacters() {
        if (this.characterDao != null) return this.characterDao.getAllCharacters();
        else return new ArrayList<>();
    }

    @WorkerThread
    public List<Character> charactersById(String id, String episode) {
        return characterDao.getCharacterById(id, episode);
    }

    @WorkerThread
    public void insert(Character character) {
        characterDao.insert(character);
    }

    @WorkerThread
    public void deleteCharacter() {
        characterDao.deleteAll();
    }
}
