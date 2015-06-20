package com.mnc.afip.ws;

public class Tributo {

	private short Id;
	private String Desc;
	private double BaseImp;
	private double Alic;
	private double Importe;
	
	public short getId() {
		return Id;
	}
	public void setId(short id) {
		Id = id;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public double getBaseImp() {
		return BaseImp;
	}
	public void setBaseImp(double baseImp) {
		BaseImp = baseImp;
	}
	public double getAlic() {
		return Alic;
	}
	public void setAlic(double alic) {
		Alic = alic;
	}
	public double getImporte() {
		return Importe;
	}
	public void setImporte(double importe) {
		Importe = importe;
	}

}
