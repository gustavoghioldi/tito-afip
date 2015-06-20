package com.mnc.afip.tito;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.mule.module.json.JsonData;

public class ParseLoginTicketResponse implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		JsonData jsonData = (JsonData) eventContext.getMessage().getPayload();
		String xmlCmsResponse = jsonData.get("loginCmsResponse/loginCmsReturn").asText();
		
		String token = getTagContent(xmlCmsResponse, "token");
		String sign  = getTagContent(xmlCmsResponse, "sign");
		String expirationTimeText = getTagContent(xmlCmsResponse, "expirationTime");
		Date expirationTime = AuthUtils.parseDate(expirationTimeText);
		
		eventContext.getMessage().setProperty("token", token, PropertyScope.SESSION);
		eventContext.getMessage().setProperty("sign", sign, PropertyScope.SESSION);
		eventContext.getMessage().setProperty("expirationTime", expirationTime, PropertyScope.SESSION);
	
		return xmlCmsResponse;
	}
	
	private String getTagContent(String xml, String tagName ) {
        Pattern pattern = Pattern.compile("<" + tagName + ">(.*)</" + tagName + ">");
        Matcher matcher = pattern.matcher(xml);
        if( matcher.find() && matcher.groupCount()>0 ) {
        	return matcher.group(1);
        }
        return null;
	}

}
