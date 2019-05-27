package es.uca.iw.proyectoCompleto.cuentageneral;

import org.springframework.data.repository.CrudRepository;

import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;

public interface CuentaGeneralRepository extends CrudRepository<Cuentageneral, Long>{
	
	public Cuentageneral findByCuentaBancaria(String cuentaBancaria);
}
