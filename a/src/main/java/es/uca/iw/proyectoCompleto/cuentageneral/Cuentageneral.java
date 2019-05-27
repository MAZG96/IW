package es.uca.iw.proyectoCompleto.cuentageneral;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cuentageneral {
	private static final long serialVersionUID = -8883789651072229337L;

	@Id
	@GeneratedValue
	private Long id;

	private String cuentaBancaria;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return cuentaBancaria;
	}

	
	protected Cuentageneral() {
	}
	
	public Cuentageneral(String cuentaBancaria) {
		super();
		this.cuentaBancaria = cuentaBancaria;
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


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
}
