package com.mnc.afip.tito;

import java.util.Date;

import com.mnc.afip.ws.Auth;

public class AuthTokenCache {

	private Auth authToken;
	private Date expireAt;
	
	public boolean isValid(){
		return authToken!=null && expireAt!=null && expireAt.after(new Date());
	}
	
	public Auth getAuthToken() {
		return authToken;
	}
	
	public void setAuthToken(Auth authToken, Date expireAt){
		this.authToken = authToken;
		this.expireAt = expireAt;
	}
	
	public void setAuthToken(String token, String sign, Long cuit, Date expireAt){
		Auth authToken = new Auth();
		authToken.setToken(token);
		authToken.setSign(sign);
		authToken.setCuit(cuit);
		setAuthToken(authToken, expireAt);
	}
}
