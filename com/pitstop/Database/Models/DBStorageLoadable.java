package com.pitstop.Database.Models;

import java.io.IOException;

import com.pitstop.Database.Middleware.Storage.StorageParseable;

public interface DBStorageLoadable {
    public void loadDataToDB(StorageParseable parser);
    public void saveDataFromDB(StorageParseable parser) throws IOException;
}