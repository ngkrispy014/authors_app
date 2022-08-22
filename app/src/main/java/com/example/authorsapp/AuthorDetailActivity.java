package com.example.authorsapp;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.authorsapp.models.AuthorModel;

public class AuthorDetailActivity extends AppCompatActivity {
    private TextView nameText;
    private ImageView imageView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.author_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        nameText = findViewById(R.id.selected_author_name);
        imageView = findViewById(R.id.author_details_image);

        Intent i = getIntent();
        AuthorModel authorModel = getIntent().getExtras().getParcelable("author");

        Glide.with(this).load(authorModel.getImageURl()).placeholder(R.drawable.placeholder).into(imageView);
        nameText.setText(authorModel.getAuthorName());
    }


}