package com.example.authorsapp.persistence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.authorsapp.models.AuthorModel;

import java.util.List;

@Dao
public interface AuthorsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertAuthors(AuthorModel... author);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAuthor(AuthorModel author);

    @Query("Update authors SET name = :name, imageUrl = :image_url WHERE id = :id")
    void updateAuthor(String id,String name, String image_url);

    @Delete
    void delete(AuthorModel author);

    @Query("SELECT * FROM authors LIMIT (:pageNumber * 10) ")
    LiveData<List<AuthorModel>> getRecords(int pageNumber);

    @Query("SELECT EXISTS(SELECT * FROM authors WHERE id = :id)")
    boolean isRowIsExist(int id);
}
