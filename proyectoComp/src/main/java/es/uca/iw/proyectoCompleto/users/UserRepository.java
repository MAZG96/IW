package es.uca.iw.proyectoCompleto.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findByLastNameStartsWithIgnoreCase(String lastName);
	
	public User findByUsername(String username);
	
	
	public User findByEmail(String email);
}

