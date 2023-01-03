package com.pitstop.Authentication.Database;

import java.util.HashMap;

import com.pitstop.Authentication.Model.AuthLevel;

public class AuthenticationDatabase implements AuthDatabaseFunctions {

	private final HashMap<String, AuthLevel> authTokenLevelMap;

	private static AuthenticationDatabase instance = null;

	private AuthenticationDatabase() {
		this.authTokenLevelMap = new HashMap<>();
	}

	public static final AuthenticationDatabase getInstance() {
		if (instance == null)
			instance = new AuthenticationDatabase();
		return instance;
	}

	@Override
	public void addToken(String token, AuthLevel authLevel, String employeeID) {
		authTokenLevelMap.put(token, authLevel);
	}

	@Override
	public void invalidateToken(String token) {
		authTokenLevelMap.remove(token);
	}

	@Override
	public AuthLevel getAuthLevel(String token) {
		if (!authTokenLevelMap.containsKey(token))
			return AuthLevel.UNKNOWN;
		return authTokenLevelMap.get(token);
	}

}
