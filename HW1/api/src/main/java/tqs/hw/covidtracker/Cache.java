package tqs.hw.covidtracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Cache<T> {
    
    private int timeToLive;

    private class Entry {
        private int ttl;
        private T value;

        public Entry(T value, int ttl) {
            this.value = value;
            this.ttl = ttl;
        }

        public T getValue() {
            return value;
        }

        public int getRemainingTimeToLive() {
            return ttl;
        }

        public void setTimeToLive() {
            
        }
    }


    private long hits;
    private long misses;
    private Map<String, Entry> cache;

    public Cache(int timeToLive) {
        hits = 0;
        misses = 0;
        this.timeToLive = timeToLive;
        cache = new HashMap<>();
    }

    public long getHits() {
        return hits;
    }

    public long getMisses() {
        return misses;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void add(String key, T value) {
        
    }

    public Optional<T> get(String key) {
        return null;
    }


}
