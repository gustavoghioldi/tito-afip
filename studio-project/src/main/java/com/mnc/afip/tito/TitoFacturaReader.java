package com.mnc.afip.tito;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.util.BeanUtils;

public class TitoFacturaReader implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		String facturaText = eventContext.getMessage().getPayloadAsString(); 
		return parseFactura(facturaText);
	}

	private Factura parseFactura(String facturaText) throws Exception {
		Scanner scanner = new Scanner(facturaText);
		
		// Process Header Parts
		FacturaHeader header = new FacturaHeader();
		
		// Discard "C" starting line
		takeNextNonEmptyLine(scanner);

		extractFromBrackets(takeNextNonEmptyLine(scanner), header, "comprobanteInterno");
		extractFromBrackets(takeNextNonEmptyLine(scanner), header, "fecha");
		
		String line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line,  0, -1, header, "cliente");
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line,  0, 45, header, "direccion");
		extractFromSegment(line, 45, 70, header, "telefono");
		extractFromSegment(line, 70, -1, header, "codigoPostal");
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line,  0, 50, header, "localidad");
		extractFromSegment(line, 50, -1, header, "cuit");

		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line,  0, -1, header, "tipoCuit");

		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line,  0, 60, header, "condicion");
		extractFromBrackets(line, header, "remito");
		
		extractFromBrackets(takeNextNonEmptyLine(scanner), header, "ordenCompra");

		// Discard items header
		takeNextNonEmptyLine(scanner);
		takeNextLine(scanner);
		
		// Process items
		List<FacturaItem> items = new ArrayList<FacturaItem>();
		while(!(line=takeNextLine(scanner)).trim().isEmpty()) {
			items.add(parseItem(line));
		}
		
		// Discard Total header
		takeNextNonEmptyLine(scanner);
		takeNextLine(scanner);

		FacturaTotal total = new FacturaTotal();
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line,  0, 13, total, "subTotal1");
		extractFromSegment(line, 13, 22, total, "descuento");
		extractFromSegment(line, 22, 37, total, "subTotal2");
		extractFromSegment(line, 37, 52, total, "iva1");
		extractFromSegment(line, 52, 67, total, "iva2");
		extractFromSegment(line, 67, -1, total, "total");
		
		line = takeNextNonEmptyLine(scanner);
		extractFromSegment(line, 0, -1, total, "enLetras");
		
		scanner.close();
		
		return new Factura(header, items, total);
	}
	
	private FacturaItem parseItem(String line) throws IllegalAccessException, InvocationTargetException {
		FacturaItem item = new FacturaItem();;
		extractFromSegment (line,  0, 13, item, "codigo");
		extractFromSegment (line, 13, 19, item, "cantidad");
		extractFromSegment (line, 19, 51, item, "descripcion");
		extractFromSegment (line, 51, 66, item, "precioUnitario");
		extractFromSegment (line, 66, -1, item, "importe");
		return item;
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

	private void extractFromBrackets(String line, Object bean, String propertyName) throws IllegalAccessException, InvocationTargetException {
		int startPos = line.indexOf('[')+1;
		int endPos   = line.indexOf(']');
		BeanUtils.setProperty(bean, propertyName, line.substring(startPos, endPos).trim());
	}
	
	private void extractFromSegment(String line, int segmentFrom, int segmentTo, Object bean, String propertyName) throws IllegalAccessException, InvocationTargetException {
		String part = (segmentTo == -1) ? line.substring(segmentFrom) : line.substring(segmentFrom, segmentTo);
		BeanUtils.setProperty(bean, propertyName, part.trim());
	}
	

}
