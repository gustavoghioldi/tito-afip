package com.mnc.afip.ws;

public class Auth {
	private String Token;
	private String Sign;
	private long Cuit;
	
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	public long getCuit() {
		return Cuit;
	}
	public void setCuit(long cuit) {
		Cuit = cuit;
	}
}
