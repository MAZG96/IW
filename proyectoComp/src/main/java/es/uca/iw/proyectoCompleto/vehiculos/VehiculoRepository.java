package es.uca.iw.proyectoCompleto.vehiculos;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.uca.iw.proyectoCompleto.users.User;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Long> {
	
	public Vehiculo findByMatriculaStartsWithIgnoreCase(String matricula);
	
	@Query("Select a from Vehiculo a where a.matricula like %:matricula% and a.usuario = :usuario")
	public List<Vehiculo> findByMatricula(@Param("matricula")String matricula,@Param("usuario") User usuario);
	
	@Query("Select a from Vehiculo a where a.matricula like %:matricula%")
	public List<Vehiculo> findByMatricula(@Param("matricula")String matricula);
	
	public Vehiculo findById(Long id);
	
	@Query("Select a from Vehiculo a where a.usuario = :usuario")
	List<Vehiculo> findByUserVehiculo(@Param("usuario")User usuario);
	
	@Query("Select a from Vehiculo a where a.marca like %:marca%")
	public List<Vehiculo> findByMarca(@Param("marca")String marca);
	
	@Query("Select a from Vehiculo a where a.usuario = :usuario")
	public List<Vehiculo> findByUser(@Param("usuario")User usuario);
}