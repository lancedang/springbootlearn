package com.example.oauth2demowithpage.controller;

import lombok.Data;

@Data
public class UserProfile
{
	private String name;
	private String email;

	//Setters and getters

	@Override
	public String toString() {
		return "UserProfile [name=" + name + ", email=" + email + "]";
	}
}