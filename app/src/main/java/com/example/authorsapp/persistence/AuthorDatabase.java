package com.example.authorsapp.persistence;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.authorsapp.models.AuthorModel;

@Database(entities = {AuthorModel.class}, version = 4)

public abstract class AuthorDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "authors_db";

    private static AuthorDatabase instance;

    public static AuthorDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AuthorDatabase.class,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract AuthorsDao getAuthorsDao();

}

