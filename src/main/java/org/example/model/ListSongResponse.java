package org.example.model;

import org.example.gen.Song;

import java.util.List;

public class ListSongResponse {
    int returnCode;
    List<Song> songList;
    String message;

    public ListSongResponse(int returnCode, List<Song> songList, String message) {
        this.returnCode = returnCode;
        this.songList = songList;
        this.message = message;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
