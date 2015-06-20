package com.mnc.afip.tito;

import java.util.List;

public class Factura {

	private FacturaHeader header;
	private List<FacturaItem> items;
	private FacturaTotal total;

	public Factura(FacturaHeader header, List<FacturaItem> items, FacturaTotal total) {
		this.header = header;
		this.items = items;
		this.total = total;
	}

	public FacturaHeader getHeader() {
		return header;
	}

	public void setHeader(FacturaHeader header) {
		this.header = header;
	}

	public List<FacturaItem> getItems() {
		return items;
	}

	public void setItems(List<FacturaItem> items) {
		this.items = items;
	}

	public FacturaTotal getTotal() {
		return total;
	}

	public void setTotal(FacturaTotal total) {
		this.total = total;
	}

}
