package com.example.authorsapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import  com.example.authorsapp.R;


public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView authorNameTextView;
    ImageView imageView;

    // Click Listener
    OnAuthorListener onAuthorListener;

    public AuthorViewHolder(@NonNull View itemView, OnAuthorListener onAuthorListener) {
        super(itemView);

        this.onAuthorListener = onAuthorListener;
        authorNameTextView = itemView.findViewById(R.id.author_name);
        imageView = itemView.findViewById(R.id.image_url);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onAuthorListener.onAuthorClick(getAdapterPosition());
    }
}
