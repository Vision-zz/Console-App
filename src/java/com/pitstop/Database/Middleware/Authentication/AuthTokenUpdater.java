package com.pitstop.Database.Middleware.Authentication;

public interface AuthTokenUpdater {
	void setAuthToken(String token);
	void removeAuthToken(String token);
}
