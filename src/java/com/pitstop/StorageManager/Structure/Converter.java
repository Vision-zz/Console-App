package com.pitstop.StorageManager.Structure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pitstop.StorageManager.Models.DataConverter;

public class Converter implements DataConverter<ParsedStorageData, String> {

	private final Gson gson;

	public Converter() {
		this.gson = new GsonBuilder().serializeNulls().create();
	}

	@Override
	public ParsedStorageData convertToAppData(String data) {
		return gson.fromJson(data, ParsedStorageData.class);
	}

	@Override
	public String convertToStorageData(ParsedStorageData data) {
		return gson.toJson(data);
	}

}
