package com.example.authorsapp.requests;

import androidx.lifecycle.LiveData;

import com.example.authorsapp.models.AuthorModel;
import com.example.authorsapp.requests.responses.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthorsApi {
    //Get All Authors
    @GET("/v2/list")
    LiveData<APIResponse<List<AuthorModel>>> getAuthorsList(
            @Query("page") int page,
            @Query("limit") int limit
    );
}
