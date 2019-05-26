 package es.uca.iw.proyectoCompleto.reserva;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;

import java.util.List;

public interface ReservaRepository extends CrudRepository<Reserva, Long> {
	
	Reserva findByNumero(Long numero);
	
	public Reserva findById(Long id);
	
	@Query("Select a from Reserva a where a.usuario = :usuario")
	List<Reserva> findByUserReserva(@Param("usuario")User usuario);
	
	@Query("Select a from Reserva a where a.vehiculo = :vehiculo")
	List<Reserva> findByVehiculo(@Param("vehiculo")Vehiculo vehiculo);
	
	@Query("Select r from Reserva r where r.vehiculo in (Select a from Vehiculo a where a.matricula like %:matricula%) "
			+ "and r.usuario = :usuario")
	List<Reserva> findByVehiculofindByMatricula(@Param("matricula")String matricula, @Param("usuario") User usuario);
	
	@Query("Select r from Reserva r where r.usuario = :usuario order by r.fechafin desc")
	List<Reserva> findByUser(@Param("usuario")User usuario);

}