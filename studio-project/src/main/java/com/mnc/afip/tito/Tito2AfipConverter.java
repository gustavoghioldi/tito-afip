package com.mnc.afip.tito;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.mule.api.annotations.ContainsTransformerMethods;
import org.mule.api.annotations.Transformer;

import com.mnc.afip.ws.AlicIva;
import com.mnc.afip.ws.Auth;
import com.mnc.afip.ws.FECAEDetRequest;
import com.mnc.afip.ws.FECAESolicitar;
import com.mnc.afip.ws.FeCAEReq;
import com.mnc.afip.ws.FeCabReq;

@ContainsTransformerMethods
public class Tito2AfipConverter {

	private static final NumberFormat DOUBLE_GERMAN_FORMAT = NumberFormat.getNumberInstance(Locale.GERMAN);

	public static final int TIPO_COMPROBANTE_FACTURA_A = 1;
	
	private static final int CONCEPTO_PRODUCTOS = 1;
	
	private static final int TIPO_DOCUMENTO_CUIT = 80;

	private static final String MONEDA_PESOS = "PES";

	private static final double COTIZACION_PESOS = 1.0;
	
	private static final short IVA_21 = 5;
	private static final short IVA_10_5 = 4;
	
	private AuthTokenCache authTokenCache;
	
	@Transformer
	public FECAESolicitar facturaToFECAESolicitar(final Factura factura) {
		FECAESolicitar FECAESolicitar = new FECAESolicitar();
		
		FeCAEReq FeCAEReq = new FeCAEReq();
		
		FeCabReq FeCabReq = new FeCabReq();
		FeCabReq.setCantReg(1);
		FeCabReq.setPtoVta(getPuntoDeVenta(factura));
		FeCabReq.setCbteTipo(TIPO_COMPROBANTE_FACTURA_A);
		FeCAEReq.setFeCabReq(FeCabReq);
		
		FECAEDetRequest det = new FECAEDetRequest();
		det.setConcepto(CONCEPTO_PRODUCTOS);
		det.setDocTipo(TIPO_DOCUMENTO_CUIT);
		det.setDocNro(getCuit(factura));
		long nroComprobante = getNumeroDeComprobante(factura);
		det.setCbteDesde(nroComprobante);
		det.setCbteHasta(nroComprobante);
		det.setCbteFch(getFecha(factura));
		det.setImpTotal(getImporteTotal(factura));
		det.setImpTotConc(0);
		det.setImpNeto(getImporteNeto(factura));
		det.setImpIVA(getImporteIva(factura));
		det.setMonId(MONEDA_PESOS);
		det.setMonCotiz(COTIZACION_PESOS);
		
		List<AlicIva> Iva = new ArrayList<>();
		addIfNotNull(Iva, createAlicIva21(factura));
		addIfNotNull(Iva, createAlicIva105(factura));
		det.setIva(Iva);
		
		List<FECAEDetRequest> dets = new ArrayList<>();
		dets.add(det);
		FeCAEReq.setFeDetReq(dets);

		FECAESolicitar.setFeCAEReq(FeCAEReq);
		
		FECAESolicitar.setAuth(authTokenCache.getAuthToken());
		
		return FECAESolicitar;
	}
	
	private <T> void addIfNotNull(List<T> lista, T item) {
		if (item!=null) {
			lista.add(item);
		}
	}
	
	private AlicIva createAlicIva105(final Factura factura) {
		double iva = parseDouble(factura.getTotal().getIva2());
		if (iva!=0) {
			AlicIva AlicIva = new AlicIva();
			AlicIva.setId(IVA_10_5);
			AlicIva.setBaseImp(parseDouble(factura.getTotal().getSubTotal1()));
			AlicIva.setImporte(iva);
			return AlicIva;
		}
		return null;
	}

	private AlicIva createAlicIva21(final Factura factura) {
		double iva = parseDouble(factura.getTotal().getIva1());
		if (iva!=0) {
			AlicIva AlicIva = new AlicIva();
			AlicIva.setId(IVA_21);
			AlicIva.setBaseImp(parseDouble(factura.getTotal().getSubTotal2()));
			AlicIva.setImporte(iva);
			return AlicIva;
		}
		return null;
	}

	private double getImporteIva(Factura factura) {
		return parseDouble(factura.getTotal().getIva1()) + parseDouble(factura.getTotal().getIva2());
	}

	private double getImporteNeto(Factura factura) {
		return parseDouble(factura.getTotal().getSubTotal2());
	}

	private double getImporteTotal(Factura factura) {
		return parseDouble(factura.getTotal().getTotal());
	}

	public static int getPuntoDeVenta(Factura factura) {
		return parseInt(factura.getHeader().getComprobanteInterno().split("-")[0]);
	}
	
	private long getNumeroDeComprobante(Factura factura) {
//		return parseInt(factura.getHeader().getComprobanteInterno().split("-")[1]);
		return factura.getHeader().getNroCbte();
	}
	
	private long getCuit(Factura factura) {
		return parseLong(factura.getHeader().getCuit().replaceAll("-", ""));
	}

	private String getFecha(Factura factura) {
		String[] dateParts = factura.getHeader().getFecha().split("-"); 
		return dateParts[2]+dateParts[1]+dateParts[0];
	}
	
	private static int parseInt(String value){
		if( value==null || value.isEmpty() ){
			return 0;
		}
		return Integer.parseInt(value);
	}

	private long parseLong(String value){
		if( value==null || value.isEmpty() ){
			return 0;
		}
		return Long.parseLong(value);
	}

	private double parseDouble(String value){
		if( value==null || value.isEmpty() ){
			return 0;
		}
		try {
			return DOUBLE_GERMAN_FORMAT.parse(value).doubleValue();
		} catch (ParseException e) {
			return 0;
		}
	}

	public AuthTokenCache getAuthTokenCache() {
		return authTokenCache;
	}

	public void setAuthTokenCache(AuthTokenCache authTokenCache) {
		this.authTokenCache = authTokenCache;
	}

}
