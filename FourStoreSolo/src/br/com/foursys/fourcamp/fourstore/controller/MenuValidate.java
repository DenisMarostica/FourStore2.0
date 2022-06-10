package br.com.foursys.fourcamp.fourstore.controller;

public class MenuValidate {

	int password = 1234;
	String login = "admin@admin";
	
	
	public Boolean ValidateLogin(String loginTyped ,int passwordTyped){
	
	if(loginTyped.equals(login) && passwordTyped == password) {
		
		return true;
	
	}else {
		return false;
	}
	
	}

}
