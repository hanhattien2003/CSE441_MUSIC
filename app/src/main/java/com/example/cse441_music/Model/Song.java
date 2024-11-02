package com.example.cse441_music.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Song implements Parcelable {
    private String id;
    private String name;
    private String artistName;
    private String albumName;
    private String audioUrl;
    private String imageUrl;
    private String audiodownload;
    private int audiodownload_allowed;  // 0 nếu không cho phép download


    public Song() {
        this.audiodownload = "";
        this.audiodownload_allowed = 0;
    }

    public Song(String id, String name, String artistName, String audioUrl, String imageUrl,
                String albumName, String audiodownload, int audiodownload_allowed) {
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.albumName = albumName;
        this.audiodownload = audiodownload;
        this.audiodownload_allowed = audiodownload_allowed;
    }

    public Song(String id, String name, String artistName, String audioUrl, String imageUrl,
                String albumName) {
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.albumName = albumName;
    }

    protected Song(Parcel in) {
        id = in.readString();
        name = in.readString();
        artistName = in.readString();
        albumName = in.readString();
        audioUrl = in.readString();
        imageUrl = in.readString();
        audiodownload = in.readString();
        audiodownload_allowed = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAudiodownload() {
        return audiodownload;
    }

    public int getAudiodownload_allowed() {
        return audiodownload_allowed;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(artistName);
        parcel.writeString(albumName);
        parcel.writeString(audioUrl);
        parcel.writeString(imageUrl);
        parcel.writeString(audiodownload);
        parcel.writeInt(audiodownload_allowed);
    }
}
