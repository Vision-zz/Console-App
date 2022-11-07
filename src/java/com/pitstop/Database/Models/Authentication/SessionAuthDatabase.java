package com.pitstop.Database.Models.Authentication;

import java.util.HashMap;

public class SessionAuthDatabase implements SessionDatabaseFunctions{


	private final HashMap<String, AuthLevel> authDatabase;

	private static SessionAuthDatabase instance = null;

	private SessionAuthDatabase() {
		this.authDatabase = new HashMap<>();
	}

	public static final SessionAuthDatabase getInstance() {
		if (instance == null)
			instance = new SessionAuthDatabase();
		return instance;
	}

	@Override
	public void addToken(String token, AuthLevel authLevel) {
		authDatabase.put(token, authLevel);
	}

	@Override
	public void deleteToken(String token) {
		authDatabase.remove(token);
	}

	@Override
	public AuthLevel getTokenAuthLevel(String token) {
		if (!authDatabase.containsKey(token))
			return AuthLevel.UNKNOWN;
		return authDatabase.get(token);
	}

}
