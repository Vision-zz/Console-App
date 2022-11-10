package com.pitstop.Authentication.Manager.SessionAuthenticationManager;

import com.pitstop.Authentication.Database.AuthLevel;

public interface SessionAuthTokenManager {
	String getCurrentToken();
	void updateCurrentToken(String token);
	void invalidateCurrentToken();
	boolean validateCurrentToken();
	boolean validateCurrentTokenAuthLevel(AuthLevel authLevel);
}
