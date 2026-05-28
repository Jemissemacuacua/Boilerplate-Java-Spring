package com.app.builderplante.core.middleware;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private static final int MAX_REQUESTS = 20;
    private static final long WINDOW_MS = 10_000;

    private final Map<String, RequestCounter> counters = new ConcurrentHashMap<>();

    public boolean isAllowed(String ip) {
        long now = Instant.now().toEpochMilli();
        counters.merge(ip, new RequestCounter(now, 1), (existing, newVal) -> {
            if (now - existing.windowStart > WINDOW_MS) {
                return new RequestCounter(now, 1);
            }
            existing.count++;
            return existing;
        });
        return counters.get(ip).count <= MAX_REQUESTS;
    }

    private static class RequestCounter {
        long windowStart;
        int count;

        RequestCounter(long windowStart, int count) {
            this.windowStart = windowStart;
            this.count = count;
        }
    }
}
