package com.jewong.bakingapp.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jewong.bakingapp.data.Ingredient;
import com.jewong.bakingapp.data.Step;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("unused")
public class DataConverter {

    @TypeConverter
    public String writeIngredientList(List<Ingredient> list) {
        if (list == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public List<Ingredient> readIngredientList(String json) {
        if (json == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public String writeStepList(List<Step> list) {
        if (list == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public List<Step> readStepList(String json) {
        if (json == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
