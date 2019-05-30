package es.uca.iw.proyectoCompleto.reserva;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;

@Entity
public class Reserva {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	private Long numero;
	
	private LocalDate fechaini;
	
	private LocalDate fechafin;
	
	private String problema;
	
	private Float precio;
	
	private boolean isCancelada = false;
	
	private boolean seguro = false;
	
	@ManyToOne
	private User usuario;
	
	@ManyToOne
	private Vehiculo vehiculo;
	
	protected Reserva() {}
	
	public Reserva(Long numero, LocalDate fechaini, LocalDate fechafin, Float precio, boolean isCancelada,String problema,boolean s) { 
		this.numero = numero;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.precio = precio;
		this.isCancelada = isCancelada;
		this.problema=problema;
		this.seguro=s;
	}
	
	public Reserva(Long numero, LocalDate fechaini, LocalDate fechafin, boolean isCancelada) { 
		this.numero = numero;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.isCancelada = isCancelada;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public boolean isCancelada() {
		return isCancelada;
	}

	public void setCancelada(boolean isCancelada) {
		this.isCancelada = isCancelada;
	}

	public boolean isSeguro() {
		return seguro;
	}

	public void setSeguro(boolean seguro) {
		this.seguro = seguro;
	}

	public Long getId() {
		return id;
	}
	
	public Long getNumero() {
		return numero;
	}
	
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public LocalDate getFechaini() {
		return fechaini;
	}
	
	public void setFechaini(LocalDate fechaini) { 
		this.fechaini = fechaini;
	}
	
	public LocalDate getFechafin() { 
		return fechafin;
	}
	
	public String getStringisConfirmed() {
		return this.isCancelada ? "Cancelada" :"Aceptada";
	}
	
	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}
	
	public Float getPrecio() {
		return precio;
	}
	
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	
	public void setPrecio() {
		LocalDate now = fechaini;
		float price = 0.0f;
		
		while(fechafin.compareTo(now) > 0) {
			if(now.getMonthValue() == 7 || now.getMonthValue() == 8 || now.getMonthValue() == 12)
				price += vehiculo.getClimatizador() * 1.40; //Incremento 40%
			else
				price += vehiculo.getClimatizador();
			
			now = now.plusDays(1);
		}
		
		this.precio = price;
	}
	
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	public Long getUsuarioId() {
		return usuario.getId();
	}
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo  vehiculo) {
		this.vehiculo =  vehiculo;
	}
	
	public Long getVehiculoId() {
		return vehiculo.getId();
	}
	
	public boolean getisCancelada() {
		return isCancelada;
	}
	
	public void setisCancelada(boolean isCancelada) {
		this.isCancelada = isCancelada;
	}
	
	@Override
	public String toString() {
		return "Reserva [id=" + id + ", numero=" + numero + ", fechaini=" + fechaini +
				", fechafin=" + fechafin + ", usuario=" + usuario.getFirstName() + " " + usuario.getLastName() + 
				", vehiculo=" + vehiculo.getId() +
				"]";
	}
}