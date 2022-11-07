package com.pitstop.Database.Models.Authentication;

public interface SessionDatabaseFunctions {
	public void addToken(String token, AuthLevel authLevel);
	public void deleteToken(String token);
	public AuthLevel getTokenAuthLevel(String token);
}
