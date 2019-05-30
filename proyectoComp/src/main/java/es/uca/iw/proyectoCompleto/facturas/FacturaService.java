package es.uca.iw.proyectoCompleto.facturas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {
	@Autowired
	private FacturaRepository repo;
	
	public List<Factura> findByTarjetaOrigen(String TarjetaOrigen){
		return repo.findByTarjetaOrigen(TarjetaOrigen);
	}
	
	public List<Factura> findByCuentaDestino(String CuentaDestino){
		return repo.findByCuentaDestino(CuentaDestino);
	}
	
	public Factura save(Factura factura) {
		return repo.save(factura);
	}

	public Factura findOne(Long arg0) {
		return repo.findOne(arg0);
	}

	public void delete(Factura arg0) {
		repo.delete(arg0);
	}


}