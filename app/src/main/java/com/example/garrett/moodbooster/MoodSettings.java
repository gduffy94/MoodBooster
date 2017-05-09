package com.example.garrett.moodbooster;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 5/5/2017.
 */

public class MoodSettings implements Serializable {
    public Boolean animals;
    public Boolean music;
    public Boolean nature;
    public Boolean recipes;
    public Boolean exercise;
    public Boolean family;
    public Boolean friends;
    public Boolean books;
    public Boolean quotes;
    public Boolean inspire;
    public Boolean travel;
    public Boolean funny;

    public MoodSettings() {
        // Default constructor required for calls to DataSnapshot.getValue(MoodSettings.class)
    }

    public MoodSettings(Boolean animals, Boolean music, Boolean nature, Boolean recipes, Boolean exercise, Boolean family,
        Boolean friends, Boolean books, Boolean quotes, Boolean inspire, Boolean travel, Boolean funny) {
        this.music = music;
        this.nature = nature;
        this.animals = animals;
        this.recipes = recipes;
        this.exercise = exercise;
        this.family = family;
        this.friends = friends;
        this.books = books;
        this.quotes = quotes;
        this.inspire = inspire;
        this.travel = travel;
        this.funny = funny;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("music", music);
        result.put("nature", nature);
        result.put("animals", animals);
        result.put("recipes", recipes);
        result.put("exercise", exercise);
        result.put("family", family);
        result.put("friends", friends);
        result.put("books", books);
        result.put("quotes", quotes);
        result.put("inspire", inspire);
        result.put("travel", travel);
        result.put("funny", funny);

        return result;
    }
}
