package com.example.authorsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "authors")
public class AuthorModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;


    @SerializedName("author")
    @ColumnInfo(name = "name")
    private String authorName;

    @SerializedName("download_url")
    @ColumnInfo(name = "imageUrl")
    private String imageURl;

    public AuthorModel(int id, String authorName, String imageURl) {
        this.id = id;
        this.authorName = authorName;
        this.imageURl = imageURl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getImageURl() {
        return imageURl;
    }

    protected AuthorModel(Parcel in) {
        id = in.readInt();
        authorName = in.readString();
        imageURl = in.readString();
    }

    public static final Creator<AuthorModel> CREATOR = new Creator<AuthorModel>() {
        @Override
        public AuthorModel createFromParcel(Parcel in) {
            return new AuthorModel(in);
        }

        @Override
        public AuthorModel[] newArray(int size) {
            return new AuthorModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(authorName);
        parcel.writeString(imageURl);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", authorname='" + authorName + '\'' +
                ", imageurl='" + imageURl +
                '}';
    }
}
