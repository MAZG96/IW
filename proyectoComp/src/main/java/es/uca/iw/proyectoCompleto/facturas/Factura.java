package es.uca.iw.proyectoCompleto.facturas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.transaction.annotation.Transactional;

import es.uca.iw.proyectoCompleto.reserva.Reserva;

@Transactional
@Entity
public class Factura {
	private static final long serialVersionUID = -8883789651072229337L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	@Column(length = 34)
	private String tarjetaOrigen;
	
	@Column(length = 34)
	private String cuentaDestino;

	private double cantidad;
	
	String tipo;

	@ManyToOne
	private Reserva reserva;
	public Factura() {}
	public Factura(String tarjetaOrigen, String cuentaDestino, double cantidad,String tipo) {
		super();
		this.tarjetaOrigen = tarjetaOrigen;
		this.cuentaDestino = cuentaDestino;
		this.cantidad = cantidad;
		this.tipo=tipo;
	}

	/**
	 * @return the tarjetaOrigen
	 */
	public String getTarjetaOrigen() {
		return tarjetaOrigen;
	}

	/**
	 * @param tarjetaOrigen the tarjetaOrigen to set
	 */
	public void setTarjetaOrigen(String tarjetaOrigen) {
		this.tarjetaOrigen = tarjetaOrigen;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the cuentaDestino
	 */
	public String getCuentaDestino() {
		return cuentaDestino;
	}

	/**
	 * @param cuentaDestino the cuentaDestino to set
	 */
	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	/**
	 * @return the cantidad
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	
}
