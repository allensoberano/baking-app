package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    private int id;
    private String image;
    private String name;
    private String servings;
    private List<Ingredient> ingredients;
    private List<Step> steps;


    protected Recipe(Parcel in) {
        id = in.readInt();
        image = in.readString();
        name = in.readString();
        servings = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(servings);
    }
}
