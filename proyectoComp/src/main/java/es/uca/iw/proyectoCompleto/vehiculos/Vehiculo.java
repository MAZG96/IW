package es.uca.iw.proyectoCompleto.vehiculos;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.vaadin.ui.HorizontalLayout;

import es.uca.iw.proyectoCompleto.users.User;


@Entity
@Table(name = "vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT a FROM Vehiculo a")
    , @NamedQuery(name = "Vehiculo.findById", query = "SELECT a FROM Vehiculo a WHERE a.id = :id")
    , @NamedQuery(name = "Vehiculo.findByMatricula", query = "SELECT a FROM Vehiculo a WHERE a.matricula = :matricula")
    , @NamedQuery(name = "Vehiculo.findByMarca", query = "SELECT a FROM Vehiculo a WHERE a.marca = :marca")
    , @NamedQuery(name = "Vehiculo.findByEstado", query = "SELECT a FROM Vehiculo a WHERE a.estado = :estado")
    , @NamedQuery(name = "Vehiculo.findByClimatizador", query = "SELECT a FROM Vehiculo a WHERE a.climatizador = :climatizador")
    , @NamedQuery(name = "Vehiculo.findByGps", query = "SELECT a FROM Vehiculo a WHERE a.gps = :gps")})
public class Vehiculo implements Serializable {

    public int getPrecio_dia() {
		return precio_dia;
	}

	public void setPrecio_dia(int precio_dia) {
		this.precio_dia = precio_dia;
	}

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
	@Column(unique = true, length = 255,name = "matricula")
    private String matricula;
    
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    
    @Basic(optional = false)
    @Column(name = "climatizador")
    private int climatizador;

    @Basic(optional = false)
    @Column(name = "gps")
    private int gps;
    
    @Basic(optional = false)
    @Column(name = "numero_de_plazas")
    private int numero_de_plazas;
    
    @Basic(optional = false)
    @Column(name = "tipo_transmision")
    private String tipo_transmision;
    
    @Basic(optional = false)
    @Column(name = "carroceria")
    private String carroceria;
    
    //fecha salida y entrada
    
    @Basic(optional = false)
    @Column(name = "precio_dia")
    private int precio_dia;
    
    @Basic(optional = false)
    @Column(name = "oficina")
    private String oficina;
    
    @Basic(optional = false)
    @Column(name = "disponibilidad_ini")
    private LocalDate disponibilidad_ini;
	
    @Basic(optional = false)
    @Column(name = "disponibilidad_fin")
    private LocalDate disponibilidad_fin;
    

	@ManyToOne
    private User usuario;
    
 	private String galeria;

    public Vehiculo() {
    }

    public Vehiculo(Long id) {
        this.id = id;
    }

    public Vehiculo(Long id, String matricula, String marca, String estado, int climatizador, int gps,
    		int numero_de_plazas,String tipo_transmision,int precio_dia,String oficina,String carroceria,String galeria,LocalDate fechaini, LocalDate fechafin) {
        
    	this.id = id;
        this.precio_dia=precio_dia;
    	this.matricula = matricula;
        this.marca = marca;
        this.estado = estado;
        this.climatizador = climatizador;
        this.gps = gps;
        this.numero_de_plazas = numero_de_plazas;
        this.tipo_transmision = tipo_transmision;
        this.oficina=oficina;
        this.carroceria=carroceria;
        this.galeria=galeria;
        this.disponibilidad_ini=fechaini;
        this.disponibilidad_fin=fechafin;      
    }


	public LocalDate getDisponibilidad_ini() {
		return disponibilidad_ini;
	}

	public void setDisponibilidad_ini(LocalDate disponibilidad_ini) {
		this.disponibilidad_ini = disponibilidad_ini;
	}

	public LocalDate getDisponibilidad_fin() {
		return disponibilidad_fin;
	}
	
	

	public void setDisponibilidad_fin(LocalDate disponibilidad_fin) {
		this.disponibilidad_fin = disponibilidad_fin;
	}

	public Long getId() {
		return id;
	}

	public String getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getClimatizador() {
		return climatizador;
	}

	public void setClimatizador(int climatizador) {
		this.climatizador = climatizador;
	}

	public int getGps() {
		return gps;
	}

	public void setGps(int gps) {
		this.gps = gps;
	}

	public String getTipo_transmision() {
		return tipo_transmision;
	}

	public void setTipo_transmision(String tipo_transmision) {
		this.tipo_transmision = tipo_transmision;
	}

	public int getNumero_de_plazas() {
		return numero_de_plazas;
	}

	public void setNumero_de_plazas(int numero_de_plazas) {
		this.numero_de_plazas = numero_de_plazas;
	}
	
	public String getGaleria() {
        return galeria;
    }

    public void setGaleria(String galeria) {
        this.galeria = galeria;
    }

    public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return matricula;
    }
    
}