package org.example.model;

import java.util.List;

public class CreateSongRequest {
    String title;
    List<String> singers;

    public CreateSongRequest(String title, List<String> singers) {
        this.title = title;
        this.singers = singers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSingers() {
        return singers;
    }

    public void setSingers(List<String> singers) {
        this.singers = singers;
    }
}
