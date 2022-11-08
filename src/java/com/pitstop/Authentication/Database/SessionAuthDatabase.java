package com.pitstop.Authentication.Database;

import java.util.HashMap;

public class SessionAuthDatabase implements SessionDatabaseFunctions {

	private final HashMap<String, AuthLevel> authTokenLevelMap;
	private final HashMap<String, String> authEmployeeTokenMap;

	private static SessionAuthDatabase instance = null;

	private SessionAuthDatabase() {
		this.authTokenLevelMap = new HashMap<>();
		this.authEmployeeTokenMap = new HashMap<>();
	}

	public static final SessionAuthDatabase getInstance() {
		if (instance == null)
			instance = new SessionAuthDatabase();
		return instance;
	}

	@Override
	public void addToken(String token, AuthLevel authLevel, String employeeID) {
		authTokenLevelMap.put(token, authLevel);
		authEmployeeTokenMap.put(employeeID, token);
	}

	@Override
	public void invalidateToken(String token) {
		authTokenLevelMap.remove(token);
		authEmployeeTokenMap.remove(token);
	}

	@Override
	public AuthLevel getAuthLevel(String token) {
		if (!authTokenLevelMap.containsKey(token))
			return AuthLevel.UNKNOWN;
		return authTokenLevelMap.get(token);
	}

	@Override
	public String getEmployeeToken(String employeeID) {
		return authEmployeeTokenMap.get(employeeID);
	}

}
