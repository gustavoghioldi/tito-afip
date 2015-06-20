package com.mnc.afip.tito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class TitoFacturaReaderUsingMaps implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		String facturaText = eventContext.getMessage().getPayloadAsString(); 
//		System.out.println("------------------------------------------------------");
//		System.out.println(facturaText);
//		System.out.println("------------------------------------------------------");
//		return eventContext.getMessage().getPayload();
		Map<String, Object> parsedFactura = parseFactura(facturaText); 
		return parsedFactura;
	}

	private Map<String, Object> parseFactura(String facturaText) throws IOException {
		Scanner scanner = new Scanner(facturaText);

		// Process Header Parts
		Map<String, String> headerParts = new HashMap<String, String>();
		
		// Discard "C" starting line
		takeNextNonEmptyLine(scanner);

		extractFromBrackets(takeNextNonEmptyLine(scanner), "CI"   , headerParts);
		extractFromBrackets(takeNextNonEmptyLine(scanner), "FECHA", headerParts);
		
		String line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line, "CLIENTE"  , headerParts, 0, -1);
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line, "DIRECCION", headerParts,  0, 45);
		extractFromSegment(line, "TELEFONO" , headerParts, 45, 70);
		extractFromSegment(line, "CP"       , headerParts, 70, -1);
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line, "LOCALIDAD", headerParts, 0, 50);
		extractFromSegment(line, "CUIT"     , headerParts, 50, -1);

		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line, "TIPO-CUIT", headerParts,  0, -1);

		line = takeNextNonEmptyLine(scanner);
		extractFromSegment (line, "CONDICION", headerParts, 0, 60);
		extractFromBrackets(line, "REMITO"   , headerParts);
		
		extractFromBrackets(takeNextNonEmptyLine(scanner), "O-COMPRA", headerParts);

		// Discard items header
		takeNextNonEmptyLine(scanner);
		takeNextLine(scanner);
		
		// Process items
		List<Map<String, String>> itemsParts = new ArrayList<Map<String, String>>();
		while(!(line=takeNextLine(scanner)).trim().isEmpty()) {
			parseItem(line, itemsParts);
		}
		
		// Discard Total header
		takeNextNonEmptyLine(scanner);
		takeNextLine(scanner);

		Map<String, String> totalParts = new HashMap<String, String>();
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line, "SUBTOTAL-1", totalParts,  0, 13);
		extractFromSegment(line, "DESCUENTO" , totalParts, 13, 22);
		extractFromSegment(line, "SUBTOTAL-2", totalParts, 22, 37);
		extractFromSegment(line, "IVA-1"     , totalParts, 37, 52);
		extractFromSegment(line, "IVA-2"     , totalParts, 52, 67);
		extractFromSegment(line, "TOTAL"     , totalParts, 67, -1);
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line, "EN-LETRAS" , totalParts,  0, -1);
		
		scanner.close();
		
		Map<String, Object> factura = new HashMap<String, Object>();
		factura.put("HEADER", headerParts);
		factura.put("ITEMS" , itemsParts);
		factura.put("TOTAL" , totalParts);
		
		return factura;
	}
	
	private void parseItem(String line, List<Map<String, String>> itemsParts) {
		Map<String, String> itemParts = new HashMap<String, String>();
		extractFromSegment (line, "CODIGO"       , itemParts,  0, 13);
		extractFromSegment (line, "CANTIDAD"     , itemParts, 13, 19);
		extractFromSegment (line, "DESCRIPCION"  , itemParts, 19, 51);
		extractFromSegment (line, "PRECIOxUNIDAD", itemParts, 51, 66);
		extractFromSegment (line, "IMPORTE"      , itemParts, 66, -1);
		itemsParts.add(itemParts);
	}

	private String takeNextNonEmptyLine(Scanner scanner) {
		String line;
		while((line=takeNextLine(scanner))!=null){
			  if( !line.trim().isEmpty() ) {
				  break;
			  }
		}
		return line;
	}

	private String takeNextLine(Scanner scanner) {
		return scanner.hasNextLine() ? scanner.nextLine().replaceAll("\t", "  ") : null; 
	}

	private void extractFromBrackets(String line, String partName, Map<String, String> parts) {
		int startPos = line.indexOf('[')+1;
		int endPos   = line.indexOf(']');
		parts.put(partName, line.substring(startPos, endPos).trim());
	}
	
	private void extractFromSegment(String line, String partName, Map<String, String> parts, int segmentFrom, int segmentTo) {
		String part = (segmentTo == -1) ? line.substring(segmentFrom) : line.substring(segmentFrom, segmentTo);
		parts.put(partName, part.trim());
	}
	

}
