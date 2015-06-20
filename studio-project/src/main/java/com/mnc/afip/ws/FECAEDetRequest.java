package com.mnc.afip.ws;

import java.util.List;

public class FECAEDetRequest {

	private int Concepto;
	private int DocTipo;
	private long DocNro;
	private long CbteDesde;
	private long CbteHasta;
	private String CbteFch;
	private double ImpTotal;
	private double ImpTotConc;
	private double ImpNeto;
	private double ImpOpEx;
	private double ImpTrib;
	private double ImpIVA;
	private String FchServDesde;
	private String FchServHasta;
	private String FchVtoPago;
	private String MonId;
	private double MonCotiz;
	
	private List<CbteAsoc> CbtesAsoc;
	private List<Tributo> Tributos;
	private List<AlicIva> Iva;
	private List<Opcional> Opcionales;
	
	public int getConcepto() {
		return Concepto;
	}
	public void setConcepto(int concepto) {
		Concepto = concepto;
	}
	public int getDocTipo() {
		return DocTipo;
	}
	public void setDocTipo(int docTipo) {
		DocTipo = docTipo;
	}
	public long getDocNro() {
		return DocNro;
	}
	public void setDocNro(long docNro) {
		DocNro = docNro;
	}
	public long getCbteDesde() {
		return CbteDesde;
	}
	public void setCbteDesde(long cbteDesde) {
		CbteDesde = cbteDesde;
	}
	public long getCbteHasta() {
		return CbteHasta;
	}
	public void setCbteHasta(long cbteHasta) {
		CbteHasta = cbteHasta;
	}
	public String getCbteFch() {
		return CbteFch;
	}
	public void setCbteFch(String cbteFch) {
		CbteFch = cbteFch;
	}
	public double getImpTotal() {
		return ImpTotal;
	}
	public void setImpTotal(double impTotal) {
		ImpTotal = impTotal;
	}
	public double getImpTotConc() {
		return ImpTotConc;
	}
	public void setImpTotConc(double impTotConc) {
		ImpTotConc = impTotConc;
	}
	public double getImpNeto() {
		return ImpNeto;
	}
	public void setImpNeto(double impNeto) {
		ImpNeto = impNeto;
	}
	public double getImpOpEx() {
		return ImpOpEx;
	}
	public void setImpOpEx(double impOpEx) {
		ImpOpEx = impOpEx;
	}
	public double getImpTrib() {
		return ImpTrib;
	}
	public void setImpTrib(double impTrib) {
		ImpTrib = impTrib;
	}
	public double getImpIVA() {
		return ImpIVA;
	}
	public void setImpIVA(double impIVA) {
		ImpIVA = impIVA;
	}
	public String getFchServDesde() {
		return FchServDesde;
	}
	public void setFchServDesde(String fchServDesde) {
		FchServDesde = fchServDesde;
	}
	public String getFchServHasta() {
		return FchServHasta;
	}
	public void setFchServHasta(String fchServHasta) {
		FchServHasta = fchServHasta;
	}
	public String getFchVtoPago() {
		return FchVtoPago;
	}
	public void setFchVtoPago(String fchVtoPago) {
		FchVtoPago = fchVtoPago;
	}
	public String getMonId() {
		return MonId;
	}
	public void setMonId(String monId) {
		MonId = monId;
	}
	public double getMonCotiz() {
		return MonCotiz;
	}
	public void setMonCotiz(double monCotiz) {
		MonCotiz = monCotiz;
	}
	public List<CbteAsoc> getCbtesAsoc() {
		return CbtesAsoc;
	}
	public void setCbtesAsoc(List<CbteAsoc> cbtesAsoc) {
		CbtesAsoc = cbtesAsoc;
	}
	public List<Tributo> getTributos() {
		return Tributos;
	}
	public void setTributos(List<Tributo> tributos) {
		Tributos = tributos;
	}
	public List<AlicIva> getIva() {
		return Iva;
	}
	public void setIva(List<AlicIva> iva) {
		Iva = iva;
	}
	public List<Opcional> getOpcionales() {
		return Opcionales;
	}
	public void setOpcionales(List<Opcional> opcionales) {
		Opcionales = opcionales;
	}

}
