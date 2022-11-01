package com.pitstop.Database.Models;

import java.io.IOException;

import com.pitstop.Database.Middleware.Storage.StorageParseable;

public interface DBStorageLoadable {
    public void loadData(StorageParseable parser);
    public void saveData(StorageParseable parser) throws IOException;
}