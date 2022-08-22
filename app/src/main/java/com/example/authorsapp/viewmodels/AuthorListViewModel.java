package com.example.authorsapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.authorsapp.models.AuthorModel;
import com.example.authorsapp.repositories.AuthorRepository;
import com.example.authorsapp.util.Resource;

import java.util.List;


public class AuthorListViewModel extends ViewModel {

    private static final String TAG = "AuthorListViewModel";


    private MediatorLiveData<Resource<List<AuthorModel>>> authors = new MediatorLiveData<>();
    private AuthorRepository authorRepository;

    // query extras
    private int pageNumber;
    private boolean cancelRequest;
    private long requestStartTime;

    public void init(Application application){
        authorRepository = AuthorRepository.getInstance(application);
    }


    public LiveData<Resource<List<AuthorModel>>> getAuthors(){
        return authors;
    }
    public void getAllAuthors(int pageNumber){
            if(pageNumber == 0){
                pageNumber = 1;
            }
            this.pageNumber = pageNumber;
            executeGetAuthors();
    }

    public void nextPage(){
            pageNumber++;
            executeGetAuthors();
    }

    private void executeGetAuthors(){
        requestStartTime = System.currentTimeMillis();
        cancelRequest = false;
        final LiveData<Resource<List<AuthorModel>>> repositorySource = authorRepository.getAuthorsApi(pageNumber);
        authors.addSource(repositorySource, listResource -> {
            if(!cancelRequest){
                if(listResource != null){
                    if(listResource.status == Resource.Status.SUCCESS){
                        Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime) / 1000 + " seconds.");
                        Log.d(TAG, "onChanged: page number: " + pageNumber);
                        Log.d(TAG, "onChanged: " + listResource.data);

                        authors.removeSource(repositorySource);
                    }
                    else if(listResource.status == Resource.Status.ERROR){
                        Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime) / 1000 + " seconds.");

                        authors.removeSource(repositorySource);
                    }
                    authors.setValue(listResource);
                }
                else{
                    authors.removeSource(repositorySource);
                }
            }
            else{
                authors.removeSource(repositorySource);
            }
        });
    }



}
