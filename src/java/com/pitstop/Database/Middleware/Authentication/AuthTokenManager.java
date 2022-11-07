package com.pitstop.Database.Middleware.Authentication;

import com.pitstop.Database.Models.Authentication.AuthLevel;

public interface AuthTokenManager {
	String generateAuthToken(AuthLevel authLevel);
	void deleteAuthToken(String token);
}
