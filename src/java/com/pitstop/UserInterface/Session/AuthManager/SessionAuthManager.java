package com.pitstop.UserInterface.Session.AuthManager;

import java.util.UUID;

import com.pitstop.Authentication.Database.AuthLevel;
import com.pitstop.Authentication.Database.SessionDatabaseFunctions;
import com.pitstop.Authentication.Manager.AuthLevelManager;
import com.pitstop.Authentication.Manager.AuthTokenManager;
import com.pitstop.Core.Models.Users.Employee;

public class SessionAuthManager implements AuthLevelManager, AuthTokenManager {

	private SessionDatabaseFunctions database;
	
	public SessionAuthManager(SessionDatabaseFunctions database) {
		this.database = database;
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

}
