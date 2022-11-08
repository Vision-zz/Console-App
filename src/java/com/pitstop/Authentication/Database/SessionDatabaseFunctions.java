package com.pitstop.Authentication.Database;

public interface SessionDatabaseFunctions {
	public void addToken(String token, AuthLevel authLevel, String employeeID);
	public void invalidateToken(String token);
	public AuthLevel getAuthLevel(String token);
	public String getEmployeeToken(String employeeID);
}
