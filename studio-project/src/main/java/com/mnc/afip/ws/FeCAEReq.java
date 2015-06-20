package com.mnc.afip.ws;

import java.util.List;

public class FeCAEReq {
	
	private FeCabReq FeCabReq;
	private List<FECAEDetRequest> FeDetReq;
	
	public FeCabReq getFeCabReq() {
		return FeCabReq;
	}
	public void setFeCabReq(FeCabReq feCabReq) {
		FeCabReq = feCabReq;
	}
	public List<FECAEDetRequest> getFeDetReq() {
		return FeDetReq;
	}
	public void setFeDetReq(List<FECAEDetRequest> feDetReq) {
		FeDetReq = feDetReq;
	}
}
