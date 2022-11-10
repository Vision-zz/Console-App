package com.pitstop.Authentication.Manager.SessionAuthenticationManager;

import com.pitstop.Authentication.Database.AuthLevel;
import com.pitstop.Core.Models.Users.Employee;

public interface DBAuthTokenManager {
	String generateAuthToken(AuthLevel authLevel, Employee employee);
	void invalidateToken(String token);
}
