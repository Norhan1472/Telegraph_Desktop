package tgh.desktop.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

@Component
public class LockManager {
	private final Map<Long, ReentrantLock> locks = new ConcurrentHashMap<>();

    public void acquireLock(long rowId) {
        ReentrantLock lock = locks.computeIfAbsent(rowId, id -> new ReentrantLock());
        lock.lock();
    }

    public void releaseLock(long rowId) {
        ReentrantLock lock = locks.get(rowId);
        if (lock != null) {
            lock.unlock();
        }
    }
}
