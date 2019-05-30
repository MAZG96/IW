package es.uca.iw.proyectoCompleto.reserva;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoService;

@SpringView(name = AnuncioReserva.VIEW_NAME)
public class AnuncioReserva extends VerticalLayout implements View{
	private static final long serialVersionUID = 350041936879535659L;
	public static final String VIEW_NAME = "AnuncioReserva";
	private final VehiculoService aptService;
	ViewChangeListener.ViewChangeEvent event;
	Vehiculo v;
	Image imagen = new Image();
	Label matricula = new Label();
	Label marca = new Label();
	Label estado = new Label();
	Label climatizador = new Label();
	Label gps = new Label();
	Label precioL = new Label();
	Label propietario = new Label();
	
	@Autowired
	public AnuncioReserva(VehiculoService aptService) {
		this.aptService = aptService;
	}
	
	@PostConstruct
	void init() {
		Button reservar = new Button("Reservar");
		// Configure and style components
		setSpacing(true);
		reservar.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		reservar.setStyleName(ValoTheme.BUTTON_PRIMARY);
				
		addComponents(imagen, matricula, marca, estado, climatizador, gps, precioL, propietario, reservar);
		
		// Connect selected Reserva to editor or hide if none is selected
		reservar.addClickListener(e -> {
			if(SecurityUtils.isLoggedIn()) {
				getUI().getNavigator().navigateTo(AnuncioReservaView.VIEW_NAME + "/" + v.getId());
			}
			else {
				Notification.show("Inica sesión para acceder a todas las funcionalidades", Type.ERROR_MESSAGE);
				getUI().getNavigator().navigateTo(LoginScreen.VIEW_NAME);
			}
		});
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
            
            matricula.setValue("Matricula del vehiculo: " + v.getMatricula());
        	marca.setValue("Marca: " + v.getMarca());
        	estado.setValue("Estado: " + v.getEstado());
        	climatizador.setValue("Climatizador: " + v.getClimatizador());
        	gps.setValue("Numero de plazas: " + v.getNumero_de_plazas());
        	
        	
		}
	}
}