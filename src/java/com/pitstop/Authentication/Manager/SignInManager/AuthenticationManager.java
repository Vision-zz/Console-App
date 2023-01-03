package com.pitstop.Authentication.Manager.SignInManager;

import com.pitstop.Authentication.Model.AuthenticationStatus;

public interface AuthenticationManager {
	AuthenticationStatus authenticate(String username, String password);
}
