package com.example.authorsapp.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.authorsapp.AppExecutors;
import com.example.authorsapp.models.AuthorModel;
import com.example.authorsapp.persistence.AuthorDatabase;
import com.example.authorsapp.persistence.AuthorsDao;
import com.example.authorsapp.requests.AuthorsService;
import com.example.authorsapp.requests.responses.APIResponse;
import com.example.authorsapp.util.NetworkBoundResource;
import com.example.authorsapp.util.Resource;

import java.util.List;

public class AuthorRepository {
    private static final String TAG = "AuthorRepository";

    private static AuthorRepository instance;
    private AuthorsDao authorsDao;

    public static AuthorRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AuthorRepository(context);
        }
        return instance;
    }

    private AuthorRepository(Context context) {
        authorsDao  = AuthorDatabase.getInstance(context).getAuthorsDao();
    }

    public LiveData<Resource<List<AuthorModel>>> getAuthorsApi(final int pageNumber){
        return new NetworkBoundResource<List<AuthorModel>, List<AuthorModel>>(AppExecutors.getInstance()){
            @Override
            protected void saveCallResult(@NonNull List<AuthorModel> item) {
                if(item.size() > 0 ){
                    Log.d(TAG, "saveCallResult: authors response: " + item.toString());

                    AuthorModel[] authors = new AuthorModel[item.size()];

                    int index = 0;
                    for(long rowid: authorsDao.insertAuthors((AuthorModel[]) (item.toArray(authors)))){
                        if(rowid == -1){
                            Log.d(TAG, "saveCallResult: CONFLICT... This author is already in the cache");
                            // if the author already exists...
                            // they will be erased
                            authorsDao.updateAuthor(
                                    String.valueOf(authors[index].getId()),
                                    authors[index].getAuthorName(),
                                    authors[index].getImageURl()
                            );
                        }
                        index++;
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<AuthorModel> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<AuthorModel>> loadFromDb() {
                return authorsDao.getRecords(pageNumber);
            }

            @NonNull
            @Override
            protected LiveData<APIResponse<List<AuthorModel>>> createCall() {
                return AuthorsService.getAuthorsApi().getAuthorsList(pageNumber,10);
            }
        }.getAsLiveData();
    }


}
