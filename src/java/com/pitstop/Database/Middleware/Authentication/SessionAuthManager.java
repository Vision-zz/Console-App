package com.pitstop.Database.Middleware.Authentication;

import java.util.UUID;

import com.pitstop.Database.Models.Authentication.AuthLevel;
import com.pitstop.Database.Models.Authentication.SessionDatabaseFunctions;

public class SessionAuthManager implements AuthLevelManager, AuthTokenManager {

	private SessionDatabaseFunctions database;

	public SessionAuthManager(SessionDatabaseFunctions database) {
		this.database = database;
	}

	@Override
	public String generateAuthToken(AuthLevel authLevel) {
		String uuid = UUID.randomUUID().toString();
		database.addToken(uuid, authLevel);
		return uuid;
	}

	@Override
	public void deleteAuthToken(String token) {
		database.deleteToken(token);
	}

	@Override
	public boolean validateToken(String token) {
		AuthLevel authLevel = database.getTokenAuthLevel(token);
		return !(authLevel.equals(AuthLevel.UNKNOWN));
	}

	@Override
	public boolean validateTokenAuthLevel(String token, AuthLevel authLevel) {
		AuthLevel dbAuthLevel = database.getTokenAuthLevel(token);
		return authLevel.equals(dbAuthLevel);
	}

}
