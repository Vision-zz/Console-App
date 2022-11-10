package com.pitstop.Authentication.Model;

import com.pitstop.Core.Models.Users.Employee;

public abstract class AuthenticationStatus {
	public static final class Success extends AuthenticationStatus {
		public final String token;
		public final Employee employee;

		public Success(String token, Employee employee) {
			this.token = token;
			this.employee = employee;
		}
	}

	public static final class Fail extends AuthenticationStatus {
		public final String reason;

		public Fail(String reason) {
			this.reason = reason;
		}
	}
}