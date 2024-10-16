package com.example.cse441_music.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

//lop ket qua
public class DeezerResponse {
    @SerializedName("data")
    private List<Song> data;

    public List<Song> getData() {
        return data;
    }

    public void setData(List<Song> data) {
        this.data = data;
    }
}