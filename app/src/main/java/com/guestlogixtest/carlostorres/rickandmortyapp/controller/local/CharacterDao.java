package com.guestlogixtest.carlostorres.rickandmortyapp.controller.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;

import java.util.List;

@Dao
public interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Character character);


    @Query("SELECT * from Character")
    List<Character> getAllCharacters();

    @Query("SELECT * from Character WHERE id == :id AND episodeDead = :episode")
    List<Character> getCharacterById(String id, String episode);

    @Query("DELETE FROM Character")
    void deleteAll();
}
