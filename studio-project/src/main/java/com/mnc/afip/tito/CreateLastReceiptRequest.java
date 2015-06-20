package com.mnc.afip.tito;

import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.mnc.afip.ws.Auth;
import com.mnc.afip.ws.FECompUltimoAutorizado;

public class CreateLastReceiptRequest implements Callable{

	private AuthTokenCache cacheInstance;
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		Factura factura = (Factura)eventContext.getMessage().getPayload();
		FECompUltimoAutorizado request = new FECompUltimoAutorizado();
		request.setAuth(getAuth(eventContext));
		request.setCbteTipo(Tito2AfipConverter.TIPO_COMPROBANTE_FACTURA_A);
		request.setPtoVta(Tito2AfipConverter.getPuntoDeVenta(factura));
		return request;
	}

	private Auth getAuth(MuleEventContext eventContext) {
		return getAuthTokenCache(eventContext.getMuleContext()).getAuthToken();
	}
	
	private AuthTokenCache getAuthTokenCache(MuleContext muleContext) {
		if(cacheInstance==null){
			cacheInstance = muleContext.getRegistry().get("authTokenCache");
		}
		return cacheInstance;
	}

}
