package com.pitstop.StorageManager.Models;

public interface DataConverter<AppData, StorageData> {
	AppData convertToAppData(StorageData data);

	StorageData convertToStorageData(AppData data);
}
