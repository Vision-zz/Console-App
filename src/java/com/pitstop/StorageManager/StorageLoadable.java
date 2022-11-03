package com.pitstop.StorageManager;

import java.io.IOException;

public interface StorageLoadable {
    public void loadData(StorageLoadTypes type);
    public void saveData(StorageLoadTypes types) throws IOException;
}