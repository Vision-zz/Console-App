package com.pitstop.Authentication.Manager.SessionAuthenticationManager;

import com.pitstop.Authentication.Database.AuthLevel;

public interface AuthLevelManager {
	boolean validateToken(String token);
	boolean validateTokenAuthLevel(String token, AuthLevel authLevel);
}
