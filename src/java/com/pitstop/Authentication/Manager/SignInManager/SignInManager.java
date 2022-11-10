package com.pitstop.Authentication.Manager.SignInManager;

public interface SignInManager {
	SignInStatus signIn(String username, String password);
}
