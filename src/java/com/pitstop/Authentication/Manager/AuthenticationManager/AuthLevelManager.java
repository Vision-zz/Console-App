package com.pitstop.Authentication.Manager.AuthenticationManager;

import com.pitstop.Authentication.Model.AuthLevel;

public interface AuthLevelManager {
	String getCurrentToken();
	void updateCurrentToken(String token);
	void invalidateCurrentToken();
	boolean validateCurrentToken();
	boolean validateCurrentTokenAuthLevel(AuthLevel authLevel);
}
