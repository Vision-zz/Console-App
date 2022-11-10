package com.pitstop.Authentication.Manager.AuthenticationManager;

import com.pitstop.Authentication.Model.AuthLevel;

public interface TokenLevelManager {
	boolean validateToken(String token);
	boolean validateTokenAuthLevel(String token, AuthLevel authLevel);
}
