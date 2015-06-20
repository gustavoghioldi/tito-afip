package com.mnc.afip.tito;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class CreateLoginTicketRequest implements Callable{

	private static final String CMS_CONTENT = "###";
	private static final String LOGIN_CMS = 
	"<wsaa:loginCms xmlns:wsaa=\"http://wsaa.view.sua.dvadac.desein.afip.gov\"><wsaa:in0>" + CMS_CONTENT + "</wsaa:in0></wsaa:loginCms>";
	
	private static final String DST_DN = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239";
	private static final String SERVICE = "wsfe";
	private static final Long TICKET_TIME = 30 * 60 * 1000L;
	   
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		String p12file = eventContext.getMessage().getProperty("p12File", PropertyScope.SESSION);
		String p12pass = eventContext.getMessage().getProperty("p12pass", PropertyScope.SESSION);
		String signer = eventContext.getMessage().getProperty("signer", PropertyScope.SESSION);
		String cms = AuthUtils.create_cms(p12file, p12pass, signer, DST_DN, SERVICE, TICKET_TIME); 
		return LOGIN_CMS.replace(CMS_CONTENT, cms);
	}

}
