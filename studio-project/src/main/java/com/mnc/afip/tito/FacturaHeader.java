package com.mnc.afip.tito;

public class FacturaHeader {

	private String comprobanteInterno;
	private String fecha;
	private String cliente;
	private String direccion;
	private String telefono;
	private String codigoPostal;
	private String localidad;
	private String cuit;
	private String tipoCuit;
	private String condicion;
	private String remito;
	private String ordenCompra;
	
	private long nroCbte;
	
	public String getComprobanteInterno() {
		return comprobanteInterno;
	}
	public void setComprobanteInterno(String comprobanteInterno) {
		this.comprobanteInterno = comprobanteInterno;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public String getTipoCuit() {
		return tipoCuit;
	}
	public void setTipoCuit(String tipoCuit) {
		this.tipoCuit = tipoCuit;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public String getRemito() {
		return remito;
	}
	public void setRemito(String remito) {
		this.remito = remito;
	}
	public String getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	public long getNroCbte() {
		return nroCbte;
	}
	public void setNroCbte(long nroCbte) {
		this.nroCbte = nroCbte;
	}
}
