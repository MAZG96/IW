package es.uca.iw.proyectoCompleto.reserva;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.facturas.Factura;
import es.uca.iw.proyectoCompleto.facturas.FacturaService;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.cuentageneral.CuentaGeneralService;
import es.uca.iw.proyectoCompleto.cuentageneral.Cuentageneral;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoService;

@SuppressWarnings("deprecation")
@SpringView(name = AnuncioReservaView.VIEW_NAME)
public class AnuncioReservaView extends VerticalLayout implements View{
	private static final long serialVersionUID = 350041936879535659L;
	public static final String VIEW_NAME = "anuncioReservaView";
	private final UserService usSer;
	private Factura tran=new Factura();
	private Reserva res = new Reserva();
	private final CuentaGeneralService cuentaService;
	private final FacturaService tranService;
	private final ReservaService resService;
	private final ReservaRepository resRepo;
	private final VehiculoService aptService;
	final Grid<Reserva> grid;
	private User u = new User("", "");
	ViewChangeListener.ViewChangeEvent event;
	Vehiculo v;
	Image imagen = new Image();
	Label matricula = new Label();
	Label marca = new Label();
	Label estado = new Label();
	Label climatizador = new Label();
	Label gps = new Label();
	Label numero_de_plazas = new Label();
	Label precio_dia = new Label();
	Label condiciones_seguro = new Label("El seguro cuesta el 20% del alquiler");
	Label condiciones_fianza = new Label("La fianza es del 50% del coste");

	
	Button save = new Button("Confirmar", FontAwesome.REGISTERED);
	Button cancel = new Button("Cancelar",FontAwesome.LOCK);
	
	DateField fechaIni = new DateField("Fecha de inicio");
	DateField fechaFin = new DateField("Fecha de salida");
	TextField tarjeta = new TextField("Tarjeta de crédito");
	CheckBox seguro = new CheckBox("Seguro");
	
	
	Binder<Reserva> binder = new Binder<>(Reserva.class);
	Binder<User> binder2 = new Binder<>(User.class);
	
	CssLayout acciones = new CssLayout(save, cancel);
	
	@Autowired
	public AnuncioReservaView(ReservaService resService, UserService us, VehiculoService aptService,FacturaService tranService,CuentaGeneralService cuenta,ReservaRepository r) {
		this.resService = resService;
		this.usSer = us;
		this.aptService = aptService;
		this.tranService=tranService;
		this.cuentaService=cuenta;
		this.grid = new Grid<>(Reserva.class);
		this.resRepo=r;
	}
	
	@PostConstruct
	void init() {
		VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		if(sesion.getContext().getAuthentication() != null)
			u = usSer.loadUserByUsername(sesion.getContext().getAuthentication().getName());
		HorizontalLayout horizontal = new HorizontalLayout();
		VerticalLayout infov = new VerticalLayout();
		VerticalLayout camposFormulario = new VerticalLayout();
		VerticalLayout reservascoche = new VerticalLayout();
		Label re = new Label("Reservas realizadas");
		re.setStyleName("h2");
		Label validationStatus = new Label();
        binder.setStatusLabel(validationStatus);		
        
		infov.addComponents(imagen, matricula, marca, estado, climatizador, gps, numero_de_plazas,precio_dia);
		camposFormulario.addComponents(validationStatus, fechaIni, fechaFin, tarjeta,seguro, acciones,condiciones_seguro,condiciones_fianza);
		reservascoche.addComponent(re);
		
		
		
		grid.setWidth("100%");
		grid.setColumns("fechaini", "fechafin");
		grid.getColumn("fechaini").setCaption("Dia inicio");
		grid.getColumn("fechafin").setCaption("Dia final");
		
		reservascoche.addComponent(grid);
		horizontal.addComponents(infov, camposFormulario,reservascoche);
		addComponent(horizontal);
		
		
		binder.setBean(res);
		binder2.setBean(u);
		
		binder.forField(fechaIni)
			.asRequired("Este campo no puede estar vacío")
			.bind(Reserva::getFechaini, Reserva::setFechaini);
		
		binder.forField(fechaFin)
			.asRequired("Este campo no puede estar vacío")
			.bind(Reserva::getFechafin, Reserva::setFechafin);
		
		binder2.forField(tarjeta)
			.asRequired("Este campo no puede estar vacío")
			.withValidator(new StringLengthValidator("Este campo debe ser una cadena entre 16 y 32 caracteres", 16, 34))
			.bind(User::getTarjeta, User::setTarjeta);
		
		binder.withValidator(Validator.from(reserva -> {
			int days = fechaFin.getValue().compareTo(fechaIni.getValue());
			if(days == 0) return false;
			else return true;
        }, "La reserva debe ser al menos de 1 día"));
		
		binder.withValidator(Validator.from(reserva -> {
			int days = fechaFin.getValue().compareTo(fechaIni.getValue());
			if(days < 0) return false;
			else return true;
        }, "La fecha de salida incorrecta"))
		.withValidator(Validator.from(reserva -> {
			List<Reserva> reservas = resService.findByVehiculo(res.getVehiculo());
			for(int i=0;i<reservas.size();i++) {
				if(fechaFin.getValue().isBefore(reservas.get(i).getFechaini()) || fechaFin.getValue().isAfter(reservas.get(i).getFechafin())) {
					return true;
				}else {
					return false;
				}
			}
			return true;			
        }, "Fecha Final esta ocupada"))
		.withValidator(Validator.from(reserva -> {
			List<Reserva> reservas = resService.findByVehiculo(res.getVehiculo());
			for(int i=0;i<reservas.size();i++) {
				if(reservas.get(i).getFechaini().isAfter(fechaIni.getValue()) && reservas.get(i).getFechafin().isBefore(fechaFin.getValue())) {
					return false;
				}else {
					return true;
				}
			}
			return true;			
        }, "Fecha Ocupada"));
		
		binder.withValidator(Validator.from(reserva -> {
			LocalDate hoy = LocalDate.now();
			int days = fechaIni.getValue().compareTo(hoy);
			if(days < 0) return false;
			else return true;
        }, "La fecha inicial incorrecta"))
		.withValidator(Validator.from(reserva -> {
			List<Reserva> reservas = resRepo.findByVehiculo(res.getVehiculo());
			for(int i=0;i<reservas.size();i++) {
				if(fechaIni.getValue().isBefore(reservas.get(i).getFechaini()) || fechaIni.getValue().isAfter(reservas.get(i).getFechafin())) {
					return true;
				}else {
					return false;
				}
			}
			return true;			
        }, "Fecha Inicial esta ocupada"))
		.withValidator(Validator.from(reserva -> {
			String estado = aptService.findOne(res.getVehiculo().getId()).getEstado();
			if(v.getEstado().compareTo("disponible")==0) return true;
			else return false;
        }, "Coche no disponible"));
		
		seguro.addValueChangeListener(event -> {
			res.setSeguro(true);
		});
		
		save.setEnabled(false);
		
        save.addClickListener(
                event -> {
                	Cuentageneral cuenta = cuentaService.findByCuentaBancaria("ES7620770024003102575766");
                	long dias = ChronoUnit.DAYS.between(fechaIni.getValue(),fechaFin.getValue());
                	System.out.println("dias "+dias);
                	resService.save(res);
                	res.setPrecio((float)aptService.findOne(res.getVehiculo().getId()).getPrecio_dia()*dias);  //precio = preciodia*dias
                	res.setNumero(res.getId() * 13);
                	if(res.isSeguro()) { //Si activa el seguro 20% del precio pagado
                		float precio_seguro=res.getPrecio();
                		precio_seguro=(float)(precio_seguro+(precio_seguro*0.2));
                		res.setPrecio(precio_seguro);
                	}else { //fianza de la reserva 50%
                		float precio_seguro=res.getPrecio();
                		precio_seguro=(float)(precio_seguro+(precio_seguro*0.5));
                		res.setPrecio(precio_seguro);
                	}
                	resService.save(res);
                	usSer.save2(u);
                	tran.setTipo("reserva");
                	tran.setTarjetaOrigen(tarjeta.getValue());
                	tran.setCuentaDestino(cuenta.getCuentaBancaria()); //1111111 la cuenta destino de la empresa Alquicoche
                	tran.setCantidad(res.getPrecio()); //cantidad = preciodia*dias
                	tran.setReserva(res);
                	tranService.save(tran);
                	
                	getUI().getNavigator().navigateTo(ReservaView.VIEW_NAME);
        });
        
        
        
        cancel.addClickListener(
                event -> {
                	getUI().getNavigator().navigateTo(AnuncioReserva.VIEW_NAME + "/" + v.getId());
        });
        
        binder.addStatusChangeListener(
                event -> save.setEnabled(binder.isValid() && binder2.isValid()));
        binder2.addStatusChangeListener(
                event -> save.setEnabled(binder.isValid() && binder2.isValid()));		
		
	}
	
	public interface ChangeHandler {
		void onChange();
	}
	
	void listarReservas(Reserva res) {
		if (res.getVehiculo()!=null) {		
			grid.setItems((Collection<Reserva>) resRepo.findByVehiculo(res.getVehiculo()));			
		}
	}
		
	
	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null) {
			String[] msgs = event.getParameters().split("/");
			Long id = Long.parseLong(msgs[0]);
			v = aptService.findOne(id);
			
			
			String foto = aptService.findOne(id).getGaleria();
			
            if(foto != null) {
    			imagen.setVisible(true);
    			File file = new File(foto);
    			imagen.setSource(new FileResource(file));
    			imagen.setWidth(200, Unit.PIXELS);
    			imagen.setHeight(200, Unit.PIXELS);
    		}
            
            matricula.setValue("Matricula: " + v.getMatricula());
        	marca.setValue("Marca: " + v.getMarca());
        	estado.setValue("Estado: " + v.getEstado());
        	numero_de_plazas.setValue("Número de plazas: " + v.getNumero_de_plazas());
        	gps.setValue("GPS: " + v.getGps());
        	precio_dia.setValue("Precio/día: " + v.getPrecio_dia() + "€");
        
        	
        	//////////
        	res.setVehiculo(v);
    		res.setUsuario(u);
    		listarReservas(res);
		}
	}
}