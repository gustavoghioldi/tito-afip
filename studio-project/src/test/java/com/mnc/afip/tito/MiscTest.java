package com.mnc.afip.tito;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class MiscTest {

	@Test
	public void extractToken() {
        String authResponse = 
        		"\t<loginTicketResponse version=\"1\">\n" +
                "\t    <header>\n" +
                "\t        <source>CN=wsaahomo, O=AFIP, C=AR, SERIALNUMBER=CUIT 33693450239</source>\n" +
                "\t        <destination>C=ar, O=mnc, SERIALNUMBER=CUIT 20260792106, CN=mnc</destination>\n" +
                "\t        <uniqueId>2906078470</uniqueId>\n" +
                "\t        <generationTime>2015-06-19T14:09:22.139-03:00</generationTime>\n" +
                "\t        <expirationTime>2015-06-20T02:09:22.139-03:00</expirationTime>\n" +
                "\t    </header>\n" +
                "\t    <credentials>\n" +
                "\t        <token>PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8c3NvIHZlcnNpb249IjIuMCI+CiAgICA8aWQgdW5pcXVlX2lkPSIzNDc5MDEwNDM3IiBzcmM9IkNOPXdzYWFob21vLCBPPUFGSVAsIEM9QVIsIFNFUklBTE5VTUJFUj1DVUlUIDMzNjkzNDUwMjM5IiBnZW5fdGltZT0iMTQzNDczMzcwMiIgZXhwX3RpbWU9IjE0MzQ3NzY5NjIiIGRzdD0iQ049d3NmZSwgTz1BRklQLCBDPUFSIi8+CiAgICA8b3BlcmF0aW9uIHZhbHVlPSJncmFudGVkIiB0eXBlPSJsb2dpbiI+CiAgICAgICAgPGxvZ2luIHVpZD0iQz1hciwgTz1tbmMsIFNFUklBTE5VTUJFUj1DVUlUIDIwMjYwNzkyMTA2LCBDTj1tbmMiIHNlcnZpY2U9IndzZmUiIHJlZ21ldGhvZD0iMjIiIGVudGl0eT0iMzM2OTM0NTAyMzkiIGF1dGhtZXRob2Q9ImNtcyI+CiAgICAgICAgICAgIDxyZWxhdGlvbnM+CiAgICAgICAgICAgICAgICA8cmVsYXRpb24gcmVsdHlwZT0iNCIga2V5PSIyMDI2MDc5MjEwNiIvPgogICAgICAgICAgICA8L3JlbGF0aW9ucz4KICAgICAgICA8L2xvZ2luPgogICAgPC9vcGVyYXRpb24+Cjwvc3NvPgoK</token>\n" +
                "\t        <sign>nPoBd+aW55iD+ZFeEGyO31CHZyMV/phf1869JVJCxRW+2UaNe6eQ9Xz/Y92gAf9pfCwPUL6EVtE/0F2I5XaAYa6JZvmN2IFECqsQE8mEfRduc8/HLh0lORD1aLju4/6+adY77qWj0BXMCxq2xFRVvGtd5UTsOUzHwmusKCFpIno=</sign>\n" +
                "\t    </credentials>\n" +
                "\t</loginTicketResponse>\n";
		
        Pattern pattern = Pattern.compile("<token>(.*)</token>");
        Matcher matcher = pattern.matcher(authResponse);
        System.out.println(matcher.find());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
	}
	
	@Test
	public void parseDate() throws ParseException {
		System.out.println(AuthUtils.formatDate(new Date())); 
		// 2015-06-19T15:14:33-03:00
		// 2015-06-20T03:10:49.405-03:00
		String expirationTimeText = "2015-06-20T03:10:49.405-03:00";
		System.out.println(AuthUtils.parseDate(expirationTimeText));
	}

}
