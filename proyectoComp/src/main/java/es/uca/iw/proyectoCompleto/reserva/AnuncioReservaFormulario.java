package es.uca.iw.proyectoCompleto.reserva;

import java.awt.Checkbox;
import java.io.File;
import java.time.LocalDate;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoService;
import javafx.scene.control.RadioButton;

@SuppressWarnings("deprecation")
@SpringView(name = AnuncioReservaFormulario.VIEW_NAME)
public class AnuncioReservaFormulario extends VerticalLayout implements View{
	private static final long serialVersionUID = 350041936879535659L;
	public static final String VIEW_NAME = "AnuncioReservaFormulario";
	private final UserService usSer;
	private Reserva res = new Reserva();
	private final ReservaService resService;
	private final VehiculoService aptService;
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
	Label tipo_de_transmision = new Label();

	
	Button save = new Button("Confirmar", FontAwesome.REGISTERED);
	Button cancel = new Button("Cancelar",FontAwesome.LOCK);
	
	DateField fechaIni = new DateField("Fecha de inicio");
	DateField fechaFin = new DateField("Fecha de salida");
	TextField tarjeta = new TextField("Tarjeta de crédito");
	

	
	
	Binder<Reserva> binder = new Binder<>(Reserva.class);
	Binder<User> binder2 = new Binder<>(User.class);
	
	CssLayout acciones = new CssLayout(save, cancel);
	
	@Autowired
	public AnuncioReservaFormulario(ReservaService resService, UserService us, VehiculoService aptService) {
		this.resService = resService;
		this.usSer = us;
		this.aptService = aptService;
	}
	
	@PostConstruct
	void init() {
		VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		if(sesion.getContext().getAuthentication() != null)
			u = usSer.loadUserByUsername(sesion.getContext().getAuthentication().getName());
		HorizontalLayout horizontal = new HorizontalLayout();
		VerticalLayout infov = new VerticalLayout();
		VerticalLayout camposFormulario = new VerticalLayout();
		
		Label validationStatus = new Label();
        binder.setStatusLabel(validationStatus);		
        
		infov.addComponents(imagen, matricula, marca, estado, climatizador, gps, numero_de_plazas, tipo_de_transmision);
		camposFormulario.addComponents(validationStatus, fechaIni, fechaFin, tarjeta, acciones);
		horizontal.addComponents(infov, camposFormulario);
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
        }, "La fecha de salida debe ser superior a la fecha de llegada"));
		
		binder.withValidator(Validator.from(reserva -> {
			LocalDate hoy = LocalDate.now();
			int days = fechaIni.getValue().compareTo(hoy);
			if(days < 0) return false;
			else return true;
        }, "La fecha de llegada no puede ser anterior a la fecha de hoy"));
		
		save.setEnabled(false);
		
        save.addClickListener(
                event -> {
                	resService.save(res);
                	res.setPrecio();
                	res.setNumero(res.getId() * 13);
                	resService.save(res);
                	usSer.save2(u);
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
            
            matricula.setValue("Nombre del vehiculo: " + v.getMatricula());
        	marca.setValue("Dirección: " + v.getMarca());
        	estado.setValue("Ubicación: " + v.getEstado());
        	climatizador.setValue("Número de habitaciones: " + v.getClimatizador());
        	gps.setValue("Huéspedes: " + v.getGps());
        	numero_de_plazas.setValue("Precio por noche: " + v.getNumero_de_plazas() + "€");
        	tipo_de_transmision.setValue("Propietario/a: " + v.getUsuario().getFirstName()
        			+ " " + v.getUsuario().getLastName());
        	
        	//////////
        	res.setVehiculo(v);
    		res.setUsuario(u);
		}
	}
}