/**
 * 
 */
package es.uca.iw.proyectoCompleto.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
		
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = repo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
	
	public User save(User user) {
		
		
				
		user.setPassword(passwordEncoder.encode(user.getPassword() != null ? user.getPassword() : "default"));
		
		return repo.save(user);
	}
	
	public User save2(User user) {
		return repo.save(user);
	}
	

	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public List<User> findByLastNameStartsWithIgnoreCase(String lastName) {
		return repo.findByLastNameStartsWithIgnoreCase(lastName);
	}

	public User findOne(Long arg0) {
		return repo.findOne(arg0);
	}

	public void delete(User arg0) {
		repo.delete(arg0);
	}

	public List<User> findAll() {
		return repo.findAll();
	}

}
