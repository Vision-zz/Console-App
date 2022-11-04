package com.pitstop.StorageManager.Models;

import java.io.IOException;

public interface StorageDataManager {
    public void loadData(StorageLoadTypes type);

    public void saveData() throws IOException;
}
