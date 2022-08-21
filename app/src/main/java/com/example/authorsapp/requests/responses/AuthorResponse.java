package com.example.authorsapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorResponse {
    @SerializedName("id")
    @Expose
    private int author_id;

    @SerializedName("author")
    @Expose
    private String authorName;

    @SerializedName("download_url")
    @Expose
    private String imageURl;

    public AuthorResponse(int author_id, String authorName, String imageURl) {
        this.author_id = author_id;
        this.authorName = authorName;
        this.imageURl = imageURl;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }
}
