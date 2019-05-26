package es.uca.iw.proyectoCompleto.cuentageneral;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoRepository;

@Service
public class CuentaGeneralService {
	
	@Autowired
	private CuentaGeneralRepository repo;
	
	public Cuentageneral save(Cuentageneral cuenta) {
		return repo.save(cuenta);
	}
	
	public Cuentageneral findByCuentaBancaria(String cuentaBancaria) {
		return repo.findByCuentaBancaria(cuentaBancaria);
	}
}
