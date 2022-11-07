package com.pitstop.Database.Middleware.Authentication;

import com.pitstop.Database.Models.Authentication.AuthLevel;

public interface AuthLevelManager {
	boolean validateToken(String token);
	boolean validateTokenAuthLevel(String token, AuthLevel authLevel);
}
