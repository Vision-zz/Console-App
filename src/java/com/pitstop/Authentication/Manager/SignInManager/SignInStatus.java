package com.pitstop.Authentication.Manager.SignInManager;

import com.pitstop.Core.Models.Users.Employee;

public class SignInStatus {
	public final boolean isProcessFailed;
	public final String token;
	public final String reason;
	public final Employee employee;

	public SignInStatus(boolean isProcessFailed, String token, String reason, Employee employee) {
		this.isProcessFailed = isProcessFailed;
		this.token = token;
		this.reason = reason;
		this.employee = employee;
	}

}
