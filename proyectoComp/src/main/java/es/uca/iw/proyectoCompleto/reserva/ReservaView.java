package es.uca.iw.proyectoCompleto.reserva;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;


@SuppressWarnings("serial")
@SpringView(name = ReservaView.VIEW_NAME)
public class ReservaView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "reservaView";
	private final ReservaRepository caracteristicas;
	private ReservaService resService;
	private final UserService service;
	private ReservaEditor editor;
	final Grid<Reserva> grid;
	final TextField filtro;

	@Autowired
	public ReservaView(ReservaService resService, ReservaRepository caracteristicas, ReservaEditor editor, UserService service) {
		this.caracteristicas = caracteristicas;
		this.resService = resService;
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Reserva.class);
		this.filtro = new TextField();
	}

	@PostConstruct
	void init() {
		Label titulo = new Label("Reserva");
		titulo.setStyleName("h2");
		addComponent(titulo);
		
		filtro.setPlaceholder("Búsqueda por vehiculo");
		filtro.setWidth("250px");
		HorizontalLayout acciones = new HorizontalLayout();	
		Responsive.makeResponsive(acciones);
		acciones.setSpacing(false);
		acciones.setMargin(false);
		acciones.addComponent(filtro);
		addComponent(acciones);	
		
		grid.setWidth("100%");
		grid.setColumns("numero", "fechaini", "fechafin", "precio", "vehiculo","isCancelada");
		grid.getColumn("numero").setCaption("Nº Reserva");
		grid.getColumn("fechaini").setCaption("Dia inicio");
		grid.getColumn("fechafin").setCaption("Dia final");
		grid.getColumn("precio").setCaption("Precio");
		grid.getColumn("vehiculo").setCaption("Vehiculo");
		grid.getColumn("isCancelada").setCaption("¿Cancelada?");
		
		editor.setWidth("100%");
		
		HorizontalLayout contenido = new HorizontalLayout();
		Responsive.makeResponsive(contenido);
		contenido.setSpacing(false);
		contenido.setMargin(false);
		contenido.setSizeFull();
		
		contenido.addComponent(grid);
		contenido.addComponent(editor);
		contenido.setExpandRatio(grid, 0.7f);
		contenido.setExpandRatio(editor, 0.3f);
		addComponent(contenido);
		
		// Replace listing with filtered content when user changes filtro
		filtro.setValueChangeMode(ValueChangeMode.LAZY);
		filtro.addValueChangeListener(e -> listarReservas(e.getValue()));
		
		// Connect selected User to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			if(!e.getValue().isCancelada()) {
				editor.editarReserva(e.getValue());
			}else {
				Notification.show("Reserva ya finalizada");
			}
		});

		User u = new User(null, null);
		//VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		if(sesion.getContext().getAuthentication() != null)
			u = service.loadUserByUsername(sesion.getContext().getAuthentication().getName());
		
		
		// Initialize listing
		listarReservas(u);
	}


	void listarReservas(User u) {
		if (u!=null) {
			if(u.getIs_Gestor() || u.isAdmin()) { //Si es gestor ve todas las reservas
				grid.setItems((Collection<Reserva>) caracteristicas.findAll());
			}else{ //si es un usuario normal ve solo las suyas
				grid.setItems((Collection<Reserva>) caracteristicas.findByUser(u));
			}
			
		}
	}
	VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
	void listarReservas(String texto) {
		if (StringUtils.isEmpty(texto)) {
			User u = service.loadUserByUsername(sesion.getContext().getAuthentication().getName());
			grid.setItems((Collection<Reserva>) caracteristicas.findAll());
		}else {
			User u = service.loadUserByUsername(sesion.getContext().getAuthentication().getName());
			grid.setItems(resService.findByVehiculofindByNombre(texto, u));
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}