package tqs.hw.covidtracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

public class Cache<T> {
    
    private int timeToLive;
    private int intervalTimer = 500;  // how long in milliseconds between each cleanup step

    private class CacheEntry {
        private int ttl;
        private T value;

        public CacheEntry(T value, int ttl) {
            this.value = value;
            this.ttl = ttl;
        }

        public T getValue() {
            return value;
        }

        public int getRemainingTimeToLive() {
            return ttl;
        }

        public void setTimeToLive(int ttl) {
            this.ttl = ttl;
        }
    }

    private long requests;
    private long hits;
    private long misses;
    private Map<String, CacheEntry> cache;

    private boolean running = true;

    /**
     * @param timeToLive How long entries remain in memory in milliseconds
     */
    public Cache(int timeToLive) {
        hits = 0;
        misses = 0;
        this.timeToLive = timeToLive;
        cache = new HashMap<>();

        Thread t = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(intervalTimer);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                cleanup();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public long getRequests() {
        return requests;
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
        synchronized (cache) {
            cache.put(key, new CacheEntry(value, timeToLive));
        }   
    }

    public Optional<T> get(String key) {
        requests++;
        synchronized (cache) {
            if (cache.containsKey(key)) {
                cache.get(key).setTimeToLive(timeToLive);
                hits++;
                return Optional.of(cache.get(key).getValue());
            }
            else {
                misses++;
                return Optional.empty();
            }
        }
    }

    private void cleanup() {
        ArrayList<String> toRemove = new ArrayList<>();

        synchronized (cache) {
            Iterator<Entry<String, CacheEntry>> itr = cache.entrySet().iterator();
            
            while (itr.hasNext()) {
                Entry<String, CacheEntry> entry = itr.next();
                entry.getValue().setTimeToLive(entry.getValue().getRemainingTimeToLive() - intervalTimer);
                if (entry.getValue().getRemainingTimeToLive() <= 0)
                    toRemove.add(entry.getKey());
            }
        }

        for (String key : toRemove) {
            synchronized (cache) {
                cache.remove(key);
            }
            Thread.yield();
        }
    }

}
