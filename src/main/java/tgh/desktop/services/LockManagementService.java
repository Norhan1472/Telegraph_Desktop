package tgh.desktop.services;

public interface LockManagementService {
    void acquireLock(Long id);
    void releaseLock(Long id);
}

