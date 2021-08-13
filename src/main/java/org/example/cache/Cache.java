package org.example.cache;

import org.example.gen.Song;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    Map<Integer, Song> cache = new HashMap<>();
    private static Cache instance;
    private Cache() {

    }

    public static Cache getInstance() {
        if (instance == null) {
            return new Cache();
        }
        return instance;
    }

    public Map<Integer, Song> getCache() {
        return cache;
    }

    public boolean isContainKey(int id) {
        if (cache.containsKey(id)) {
            return true;
        }
        return false;
    }

    public Song get(int id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        return null;
    }

    public int add(Song song) {
        if (!cache.containsKey(song.getId())) {
            cache.put(song.getId(), song);
        }
        return song.getId();
    }

    public int remove(int key) {
        if (cache.containsKey(key)) {
            cache.remove(key);
            return key;
        }
        return -1;
    }
}
