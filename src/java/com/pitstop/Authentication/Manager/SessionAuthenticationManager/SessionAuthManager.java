package com.pitstop.Authentication.Manager.SessionAuthenticationManager;

import java.util.UUID;

import com.pitstop.Authentication.Database.AuthLevel;
import com.pitstop.Authentication.Database.SessionDatabaseFunctions;
import com.pitstop.Core.Models.Users.Employee;

public class SessionAuthManager implements AuthLevelManager, DBAuthTokenManager, SessionAuthTokenManager {

	private SessionDatabaseFunctions database;
	private String currentToken = null;

	private static SessionAuthManager instance = null;

	private SessionAuthManager(SessionDatabaseFunctions database) {
		this.database = database;
	}

	public static SessionAuthManager getInstance(SessionDatabaseFunctions database) {
		if (instance == null)
			instance = new SessionAuthManager(database);
		return instance;
	}

	@Override
	public String generateAuthToken(AuthLevel authLevel, Employee employee) {
		String uuid = UUID.randomUUID().toString();
		database.addToken(uuid, authLevel, employee.getEmployeeID());
		return uuid;
	}

	@Override
	public void invalidateToken(String token) {
		database.invalidateToken(token);
	}

	@Override
	public boolean validateToken(String token) {
		AuthLevel authLevel = database.getAuthLevel(token);
		return !(authLevel.equals(AuthLevel.UNKNOWN));
	}

	@Override
	public boolean validateTokenAuthLevel(String token, AuthLevel authLevel) {
		AuthLevel dbAuthLevel = database.getAuthLevel(token);
		return authLevel.equals(dbAuthLevel);
	}

	@Override
	public void updateCurrentToken(String token) {
		this.currentToken = token;
	}

	@Override
	public void invalidateCurrentToken() {
		this.currentToken = null;
	}

	@Override
	public String getCurrentToken() {
		return this.currentToken;
	}

	@Override
	public boolean validateCurrentToken() {
		return validateToken(currentToken);
	}

	@Override
	public boolean validateCurrentTokenAuthLevel(AuthLevel authLevel) {
		return validateTokenAuthLevel(currentToken, authLevel);
	}

}
