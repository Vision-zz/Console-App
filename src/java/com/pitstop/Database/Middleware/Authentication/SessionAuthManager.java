package com.pitstop.Database.Middleware.Authentication;

import com.pitstop.Database.Models.Authentication.AuthLevel;
import com.pitstop.Database.Models.Authentication.SessionDatabaseFunctions;

public class SessionAuthManager implements AuthLevelManager, AuthTokenManager {

	private SessionDatabaseFunctions database;

	SessionAuthManager(SessionDatabaseFunctions database) {
		this.database = database;
	}

	@Override
	public String generateAuthToken(AuthLevel authLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAuthToken(String token) {
		database.deleteToken(token);
	}

	@Override
	public boolean validateToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateTokenAuthLevel(String token, AuthLevel authLevel) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
