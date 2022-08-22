package com.example.authorsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.authorsapp.adapters.AuthorListAdapter;
import com.example.authorsapp.adapters.OnAuthorListener;
import com.example.authorsapp.models.AuthorModel;
import com.example.authorsapp.util.Resource;
import com.example.authorsapp.viewmodels.AuthorListViewModel;
import com.google.gson.Gson;

import java.util.List;

public class AuthorListActivity extends AppCompatActivity implements OnAuthorListener {
    //RecyclerView Adapter
    private AuthorListAdapter authorListAdapter;
    private static final String TAG = "AuthorListActivity";

    //ViewModel
    private AuthorListViewModel authorListViewModel;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

        authorListViewModel =new ViewModelProvider(this).get(AuthorListViewModel.class);
        authorListViewModel.init(this.getApplication());

        ObserveAnyChange();
        ConfigureRecyclerView();
        getAuthorList(1,10);
    }


    private void ConfigureRecyclerView() {
        authorListAdapter = new AuthorListAdapter(this) ;

        recyclerView.setAdapter(authorListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    // we need to display the next search results on the next page
                    showProgressView();
                    authorListViewModel.nextPage();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void getAuthorList(int page, int limit) {
        authorListViewModel.getAllAuthors(page);
    }

    private void ObserveAnyChange() {
        authorListViewModel.getAuthors().observe(this, new Observer<Resource<List<AuthorModel>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<AuthorModel>> listResource) {
                if (listResource != null) {
                    Log.d(TAG, "onChanged: status: " + listResource.status);

                    if (listResource.data != null) {
                        switch (listResource.status) {

                            case ERROR: {
                                Log.e(TAG, "onChanged: cannot refresh the cache.");
                                Log.e(TAG, "onChanged: ERROR message: " + listResource.message);
                                Log.e(TAG, "onChanged: status: ERROR, #authors: " + listResource.data.size());
                                hideProgressView();
                                authorListAdapter.setAuthorList(listResource.data);
                                Toast.makeText(AuthorListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();

                                break;
                            }

                            case SUCCESS: {
                                hideProgressView();

                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS, #Authors: " + listResource.data.size());
                                authorListAdapter.setAuthorList(listResource.data);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }




    @Override
    public void onAuthorClick(int position) {
        Intent intent = new Intent(this, AuthorDetailActivity.class);


        Gson gson = new Gson();
        String authorJson = gson.toJson(authorListAdapter.getSelectedAuthor(position));

        AuthorModel authorModel = authorListAdapter.getSelectedAuthor(position);
        intent.putExtra("author", (authorModel));

        startActivity(intent);
    }

        void showProgressView() {
            progressBar.setVisibility(View.VISIBLE);
        }

        void hideProgressView() {
            progressBar.setVisibility(View.INVISIBLE);
        }


}