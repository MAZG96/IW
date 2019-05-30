package es.uca.iw.proyectoCompleto.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;

@Entity
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8883789651072229337L;

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;

	@Column(unique = true, length = 255)
	private String username;

	private String password;

	private String email;

	private String addr;

	@Column(length = 12)
	private String tlf;

	@Column(length = 525)
	private String bio;

	private String profilePic;

	private LocalDate birth;

	private boolean isAdmin = false;
	
	@Column(length = 34)
	private String tarjeta;
	
	@Column(length = 34)
	private String cuentaBancaria;
	
	private boolean is_Gerente;
	
	private boolean is_Gestor;
	

	protected User() {
	}

	public User(String firstName, String lastName, String username, String password, String email, String addr,
			String tlf, String bio, String profilePic, LocalDate birth, boolean isAdmin, String tarjeta,
			String cuentaBancaria,boolean is_Gestor,boolean is_Gerente) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.addr = addr;
		this.tlf = tlf;
		this.bio = bio;
		this.profilePic = profilePic;
		this.birth = birth;
		this.isAdmin = isAdmin;
		this.tarjeta = tarjeta;
		this.cuentaBancaria = cuentaBancaria;
		this.is_Gestor=is_Gestor;
		this.is_Gerente=is_Gerente;
	}

	public boolean getIs_Gerente() {
		return is_Gerente;
	}

	public void setIs_Gerente(boolean is_Gerente) {
		this.is_Gerente = is_Gerente;
	}

	public boolean getIs_Gestor() {
		return is_Gestor;
	}

	public void setIs_Gestor(boolean is_Gestor) {
		this.is_Gestor = is_Gestor;
	}

	public User(String firstName, String lastName, String username) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}

	public User(String firstName, String lastName) {
		this(firstName, lastName, firstName);
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	/**
	 * @return the cuentaBancaria
	 */
	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	/**
	 * @param cuentaBancaria the cuentaBancaria to set
	 */
	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	@Override
	public String toString() {
		return getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		// list.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
		VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		if (isAdmin)
			list.add(new SimpleGrantedAuthority("ADMIN"));
		else {
			list.add(new SimpleGrantedAuthority("USER"));
		}
			
		return list;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	/**
	 * @return the birth
	 */
	public LocalDate getBirth() {
		return birth;
	}

	/**
	 * @param birth
	 *            the birth to set
	 */
	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin
	 *            the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}