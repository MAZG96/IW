package es.uca.iw.proyectoCompleto.facturas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import es.uca.iw.proyectoCompleto.users.User;

public interface FacturaRepository extends CrudRepository<Factura, Long> {
	
	public List<Factura> findByTarjetaOrigen(String TarjetaOrigen);
	
	public List<Factura> findByCuentaDestino(String CuentaDestino);
}