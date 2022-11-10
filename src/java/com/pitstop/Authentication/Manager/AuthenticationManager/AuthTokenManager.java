package com.pitstop.Authentication.Manager.AuthenticationManager;

import com.pitstop.Authentication.Model.AuthLevel;
import com.pitstop.Core.Models.Users.Employee;

public interface AuthTokenManager {
	String generateAuthToken(AuthLevel authLevel, Employee employee);

	void invalidateToken(String token);
}
