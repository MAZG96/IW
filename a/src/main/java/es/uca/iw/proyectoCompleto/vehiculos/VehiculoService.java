package es.uca.iw.proyectoCompleto.vehiculos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.users.User;


@Service
public class VehiculoService{

	@Autowired
	private VehiculoRepository repo;


	public Vehiculo loadVehiculoByVehiculomatricula(String Vehiculomatricula) {

		Vehiculo Vehiculo = repo.findByMatriculaStartsWithIgnoreCase(Vehiculomatricula);
		return Vehiculo;
	}
	
	public List<Vehiculo> findByUser(@Param("usuario")User usuario){
		return repo.findByUser(usuario);
	}
	
	public List<Vehiculo> findByMatricula(@Param("matricula")String matricula, @Param("usuario") User usuario){
		return repo.findByMatricula(matricula, usuario);
	}
	
	public List<Vehiculo> findByMatricula(@Param("matricula")String matricula){
		return repo.findByMatricula(matricula);
	}

	public Vehiculo save(Vehiculo Vehiculo) {
		return repo.save(Vehiculo);
	}

	public Vehiculo findOne(Long arg0) {
		return repo.findOne(arg0);
	}

	public void delete(Vehiculo arg0) {
		repo.delete(arg0);
	}

	public List<Vehiculo> findAll() {
		return (List<Vehiculo>) repo.findAll();
	}
	
	public List<Vehiculo> findByMarca(String marca){
		return repo.findByMarca(marca);
	}

	public List<Vehiculo> findByUserVehiculo(@Param("usuario")User usuario){
		return repo.findByUserVehiculo(usuario);
	}
}