package com.member.portal.exception;

public class MemberNotFoundException extends RuntimeException {
	public MemberNotFoundException(Long id) {
		super("Could not find employee " + id);

	}

}
