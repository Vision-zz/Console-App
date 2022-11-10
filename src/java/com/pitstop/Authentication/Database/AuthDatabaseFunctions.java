package com.pitstop.Authentication.Database;

import com.pitstop.Authentication.Model.AuthLevel;

public interface AuthDatabaseFunctions {
	public void addToken(String token, AuthLevel authLevel, String employeeID);
	public void invalidateToken(String token);
	public AuthLevel getAuthLevel(String token);
}
