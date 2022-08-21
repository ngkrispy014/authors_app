package com.example.authorsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.authorsapp.models.AuthorModel;

import java.util.List;

import com.example.authorsapp.R;

public class AuthorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AuthorModel> mAuthorList;
    private OnAuthorListener onAuthorListener;

    public AuthorListAdapter(OnAuthorListener onAuthorListener) {
        this.onAuthorListener = onAuthorListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_item, parent, false);

        return new AuthorViewHolder(view, onAuthorListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((AuthorViewHolder) holder).authorNameTextView.setText(mAuthorList.get(i).getAuthorName());
        Glide.with(holder.itemView.getContext()).load(mAuthorList.get(i).getImageURl())
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.placeholder)
                .into(((AuthorViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        if (mAuthorList != null) {
            return this.mAuthorList.size();
        }
        return 0;

    }

    public void setAuthorList(List<AuthorModel> mAuthors) {
        this.mAuthorList = mAuthors;
        notifyDataSetChanged();
    }

    public AuthorModel getSelectedAuthor(int position) {
        return mAuthorList.get(position);
    }

}
