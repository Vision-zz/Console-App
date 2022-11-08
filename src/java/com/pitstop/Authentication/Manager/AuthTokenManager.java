package com.pitstop.Authentication.Manager;

import com.pitstop.Authentication.Database.AuthLevel;
import com.pitstop.Core.Models.Users.Employee;

public interface AuthTokenManager {
	String generateAuthToken(AuthLevel authLevel, Employee employee);
	void invalidateToken(String token);
}
