package es.uca.iw.proyectoCompleto.reserva;


import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Collection;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.VaadinUI;
import es.uca.iw.proyectoCompleto.cuentageneral.Cuentageneral;
import es.uca.iw.proyectoCompleto.cuentageneral.CuentaGeneralService;
import es.uca.iw.proyectoCompleto.facturas.Factura;
import es.uca.iw.proyectoCompleto.facturas.FacturaService;
import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserRepository;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoService;


@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class ReservaEditor extends VerticalLayout{
	
	private final UserRepository usre;
	Factura f = new Factura();
	private Reserva res;
	private Long id;
	private final UserService uSer;
	private final ReservaService resService;
	private final FacturaService tranSer;
	private final CuentaGeneralService CuentaService;
	private User proper;
	private String properAccount;
	private LocalDate fechaINICIO;
	private LocalDate fechaFIN;
	private Binder<Cuentageneral> binder = new Binder<>(Cuentageneral.class);
	
	/* Fields to edit properties in vehiculo entity */
	TextField problema = new TextField("Incidencias");
	Label title = new Label("Estado de la reserva");
	Button eliminar = new Button("Cancelar reserva");
	//Boton para salir del editor
	Button x = new Button("Atrás");
	Button finalizar = new Button("Finalizar reserva");
	Label stat = new Label();
	Button showCar = new Button("Ver vehiculo");
	HorizontalLayout acciones = new HorizontalLayout(eliminar, x);
	
	@Autowired
	public ReservaEditor(ReservaService resService, UserService us, FacturaService tranSer, 
			CuentaGeneralService CuentaService,UserRepository user){
		this.resService = resService;
		this.uSer = us;
		this.tranSer = tranSer;
		this.CuentaService = CuentaService;	
		this.usre=user;
		VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		if(uSer.loadUserByUsername(sesion.getContext().getAuthentication().getName()).getIs_Gestor()){
		addComponents(title, stat, showCar,problema,finalizar);
		}else {
			addComponents(title, stat, acciones, showCar);
		}
		
		showCar.setStyleName(ValoTheme.BUTTON_TINY);
		showCar.setIcon(VaadinIcons.HOME_O);
		
		showCar.addClickListener(e -> {getUI().getNavigator().navigateTo(AnuncioReserva.VIEW_NAME + "/" + id);});
		
		// Configure and style components
		setSpacing(true);
		//acciones.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		title.setStyleName(ValoTheme.LABEL_BOLD);
		eliminar.setStyleName(ValoTheme.BUTTON_DANGER);
		eliminar.setIcon(VaadinIcons.WARNING);
		eliminar.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		x.setStyleName(ValoTheme.BUTTON_FRIENDLY);

		// wire action buttons to cancelar reserva y salir
		eliminar.addClickListener(e -> {
			//Politica de cancelacion
			User u = uSer.loadUserByUsername(sesion.getContext().getAuthentication().getName());
			
			
			
			Factura factura;
			double precioYo;
			
			Cuentageneral cuenta = CuentaService.findByCuentaBancaria("ES7620770024003102575766");
			
			if(LocalDate.now().isAfter(this.fechaFIN)) //COMPRUEBA QUE NO SEA ANTIGUA LA RESERVA
				Notification.show("Esta reserva no se puede cancelar. ¡Es antigua!", Type.ERROR_MESSAGE);
			else if(LocalDate.now().isAfter(this.fechaINICIO.minusDays(15))) { 
						//80% del totaal
						precioYo = (res.getPrecio()*0.8);
						
						
						//reserva cancelada
						res.setisCancelada(true);
						
						resService.save(res);
						binder.setBean(cuenta);
						CuentaService.save(cuenta);
						
						factura = new Factura(cuenta.getCuentaBancaria(),res.getUsuario().getCuentaBancaria(), precioYo,"cancelacion");
						factura.setReserva(res);
						tranSer.save(factura);
						
					}else{
						//integro
						//reserva cancelada
						res.setisCancelada(true);
						precioYo = res.getPrecio()*0.2;

						
						binder.setBean(cuenta);
						CuentaService.save(cuenta);
						
						factura = new Factura(cuenta.getCuentaBancaria(),res.getUsuario().getCuentaBancaria(),precioYo,"cancelacion");
						factura.setReserva(res);
						tranSer.save(factura);	
					}
				getUI().getPage().reload();
				});
		setVisible(false);
		
		
		x.addClickListener(e ->{
			editarReserva(res);
	    	getUI().getPage().reload();
		});
		
		finalizar.addClickListener(e -> {	
			Cuentageneral cuenta = CuentaService.findByCuentaBancaria("ES7620770024003102575766");
			if(LocalDate.now().isAfter(this.fechaFIN)) { //COMPRUEBA QUE NO SEA ANTIGUA LA RESERVA
				if(!res.isSeguro() && problema.getValue().compareTo("")==0) { //sino tiene seguro y no ha sufrido alguna incidencia se devuelve la fianza
					 
					f.setTipo("fianza");
                	f.setTarjetaOrigen(cuenta.getCuentaBancaria());
                	f.setCuentaDestino(res.getUsuario().getCuentaBancaria()); //1111111 la cuenta destino de la empresa Alquicoche
                	
                	f.setCantidad(res.getPrecio()*0.5f); //cantidad = preciodia*dias
                	f.setReserva(res);
					tranSer.save(f);
				}
				res.setCancelada(true); //cerramos la reserva
				res.setProblema(problema.getValue());
				resService.save(res);
				getUI().getPage().reload();	
			}else {
				Notification.show("Esta reserva no se puede finalizar, aún esta en curso", Type.ERROR_MESSAGE);

			}
		});
		
	
	}
	
	public final void editarReserva(Reserva c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		
		final boolean persisted = c.getId() != null;
		
		if (persisted)
			res = resService.findOne(c.getId());
		else 
			res = c;
				
		x.setVisible(persisted);
				
		//binder.setBean(res);
		this.proper = c.getVehiculo().getUsuario();
		this.properAccount = proper.getCuentaBancaria();
		this.fechaINICIO = c.getFechaini();
		this.fechaFIN = c.getFechafin();
		this.id = c.getVehiculoId();
		

		
		setVisible(true);

		// A hack to ensure the whole form is visible
		eliminar.focus();
		// Select all text in nombre field automatically
		//nombre.selectAll();
	}	
	
}