package com.mnc.afip.tito;

import org.junit.Test;

public class AuthUtilsTest {

	@Test
	public void testCreate_cms_test_no_afip() {
		String p12file = "/Users/admin/_marcos/projects/afip/certificates/test-no-afip/server.pfx";
		String p12pass = "123456";
		String signer = "mnc test certificate"; // Es lo que va en el campo name en el último paso de la creación del certificdo
		String dstDN = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239";
		String service = "wsfe";
		long ticketTime = 30*60*1000;
		String result = AuthUtils.create_cms(p12file, p12pass, signer, dstDN, service, ticketTime);
		System.out.println(result);
	}
	
	@Test
	public void testCreate_cms_mnc() throws Exception {
		String p12file = "/Users/admin/_marcos/projects/afip/certificates/mnc/mnc.pfx";
		String p12pass = "123456";
		String signer = "mnc";
		String dstDN = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239";
		String service = "wsfe";
		long ticketTime = 30*60*1000;
		String result = AuthUtils.create_cms(p12file, p12pass, signer, dstDN, service, ticketTime);
		System.out.println(result);
		System.out.println(AuthUtils.aaa(result));
	}
	
	@Test
	public void test_create_LoginTicketRequest_mnc() {
		String signer = "mnc";
		String dstDN = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239";
		String service = "wsfe";
		long ticketTime = System.currentTimeMillis();
		String result = AuthUtils.create_LoginTicketRequest(signer, dstDN, service, ticketTime);
		System.out.println(result);
		
	}
	
	// ---------
	// https://sites.google.com/site/facturaelectronicax/Home/version-full/como-usar/certificado-digital/certificados-resumen/guia-certificado-digital
	// openssl genrsa -out privada 1024
	// openssl req -new -key privada -subj "/C=AR/O=mnc/CN=mnc/serialNumber=CUIT 20260792106" -out pedido
	// ---------
	
	
	// http://superuser.com/questions/73979/how-to-easily-create-a-ssl-certificate-and-configure-it-in-apache2-in-mac-os-x
	// http://stackoverflow.com/questions/20445365/create-pkcs12-file-with-self-signed-certificate-via-openssl-in-windows-for-my-a
	
	// openssl genrsa -des3 -out server.key 1024
	// openssl req -new -key server.key -out server.csr
	// openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
	// openssl pkcs12 -keypbe PBE-SHA1-3DES -certpbe PBE-SHA1-3DES -export -in server.crt -inkey server.key -out server.pfx -name "mnc test certificate"
	
	
	

}
