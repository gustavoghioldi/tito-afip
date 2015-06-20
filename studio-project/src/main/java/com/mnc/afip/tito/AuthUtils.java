package com.mnc.afip.tito;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.mule.util.Base64;

public class AuthUtils {

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"; 
	private static final String DATE_PARSE = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"; 

	public static String create_LoginTicketRequest (String SignerDN, String dstDN, String service, Long TicketTime) {

		String LoginTicketRequest_xml;

		Date GenTime = new Date();
		GregorianCalendar gentime = new GregorianCalendar();
		gentime.add(Calendar.SECOND, -10);
		GregorianCalendar exptime = new GregorianCalendar();
		String UniqueId = new Long(GenTime.getTime() / 1000).toString();
		
		exptime.setTime(new Date(GenTime.getTime()+TicketTime));
		
		LoginTicketRequest_xml = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
			+"<loginTicketRequest version=\"1.0\">"
			+"<header>"
			+"<source>" + SignerDN + "</source>"
			+"<destination>" + dstDN + "</destination>"
			+"<uniqueId>" + UniqueId + "</uniqueId>"
			+"<generationTime>" + formatDate(gentime) + "</generationTime>"
			+"<expirationTime>" + formatDate(exptime) + "</expirationTime>"
			+"</header>"
			+"<service>" + service + "</service>"
			+"</loginTicketRequest>";
		
		return LoginTicketRequest_xml;
	}
	
	public static String formatDate(GregorianCalendar calendar) {
		return formatDate(calendar.getTime());
	}

	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(date);
	}
	
	public static Date parseDate(String text) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat(DATE_PARSE);
		return parser.parse(text);
	}

	public static String create_cms (String p12file, String p12pass, String signer, String dstDN, String service, Long TicketTime) {

		PrivateKey pKey = null;
		X509Certificate pCertificate = null;
		byte [] asn1_cms = null;
		CertStore cstore = null;
		String LoginTicketRequest_xml;
		String SignerDN = null;

		//
		// Manage Keys & Certificates
		//
		try {
			// Create a keystore using keys from the pkcs#12 p12file
			KeyStore ks = KeyStore.getInstance("pkcs12");
			FileInputStream p12stream = new FileInputStream ( p12file ) ;
			ks.load(p12stream, p12pass.toCharArray());
			p12stream.close();

			// Get Certificate & Private key from KeyStore
			pKey = (PrivateKey) ks.getKey(signer, p12pass.toCharArray());
			pCertificate = (X509Certificate)ks.getCertificate(signer);
			SignerDN = pCertificate.getSubjectDN().toString();
			//SignerDN = "cn=mnc,o=mnc,c=ar,serialNumber=CUIT 20260792106";

			// Create a list of Certificates to include in the final CMS
			ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>();
			certList.add(pCertificate);

			if (Security.getProvider("BC") == null) {
				Security.addProvider(new BouncyCastleProvider());
			}

			cstore = CertStore.getInstance("Collection", new CollectionCertStoreParameters (certList), "BC");
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 

		//
		// Create XML Message
		// 
		LoginTicketRequest_xml = create_LoginTicketRequest(SignerDN, dstDN, service, TicketTime);
		
		//
		// Create CMS Message
		//
		try {
			// Create a new empty CMS Message
			CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

			// Add a Signer to the Message
			gen.addSigner(pKey, pCertificate, CMSSignedDataGenerator.DIGEST_SHA1);

			// Add the Certificate to the Message
      		gen.addCertificatesAndCRLs(cstore);

			// Add the data (XML) to the Message
			CMSProcessable data = new CMSProcessableByteArray(LoginTicketRequest_xml.getBytes("UTF-8"));

			// Add a Sign of the Data to the Message
			CMSSignedData signed = gen.generate(data, true, "BC");	

			// 
			asn1_cms = signed.getEncoded();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	
		try {
			return Base64.encodeBytes (asn1_cms);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String aaa(String encoded) throws Exception {
		byte[] decoded = Base64.decode(encoded);
		CMSSignedData signedData = new CMSSignedData(decoded);
		CMSProcessableByteArray aux = (CMSProcessableByteArray) signedData.getSignedContent();
		byte[] content = (byte[])aux.getContent();
		return new String(content, "UTF-8");
	}
	
}
