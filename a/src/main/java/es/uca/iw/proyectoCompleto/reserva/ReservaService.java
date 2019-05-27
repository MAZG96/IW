package es.uca.iw.proyectoCompleto.reserva;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserRepository;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoRepository;

@Service
public class ReservaService {

	@Autowired
	private final VehiculoRepository vehiculo;
	private final ReservaRepository res;
	private final UserRepository usuario;
	
	@Autowired
	public ReservaService(VehiculoRepository vehiculo, ReservaRepository res, UserRepository u) 
	{ 
		this.vehiculo = vehiculo;
		this.res = res;
		this.usuario = u;
	}
	
	public List<Reserva> findByVehiculofindByNombre(@Param("matricula")String nombre, @Param("usuario") User usuario){
		return res.findByVehiculofindByMatricula(nombre, usuario);
	}
	
	
	List<Reserva> findByVehiculo(@Param("vehiculo")Vehiculo vehiculo){
		return res.findByVehiculo(vehiculo);
	}
	
	List<Reserva> findByUser(@Param("usuario")User usuario){
		return res.findByUser(usuario);
	}
	
	public Reserva save(Reserva Reserva) {
		return res.save(Reserva);
	}

	public Reserva findOne(Long arg0) {
		return res.findOne(arg0);
	}

	public void delete(Reserva arg0) {
		res.delete(arg0);
	}

	

	//public void generarPDF()
	
	
	
}