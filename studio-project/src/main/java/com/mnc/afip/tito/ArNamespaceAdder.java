package com.mnc.afip.tito;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class ArNamespaceAdder implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// Add "ar" namespace
		String request = eventContext.getMessageAsString();
		request = request.replaceAll("<", "<ar:");
		request = request.replaceAll("<ar:/", "</ar:");
		request = request.replaceAll("<ar:FECAESolicitar>", "<ar:FECAESolicitar xmlns:ar=\"http://ar.gov.afip.dif.FEV1/\">");
		request = request.replaceAll("<ar:FECompUltimoAutorizado>", "<ar:FECompUltimoAutorizado xmlns:ar=\"http://ar.gov.afip.dif.FEV1/\">");
		return request;
	}

}
