package es.uca.iw.proyectoCompleto.vehiculos;

import java.io.File;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;



@SuppressWarnings("serial")
@SpringView(name = VehiculoView.VIEW_NAME)
public class VehiculoView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "VehiculoView";
	private final VehiculoService caracteristicas;
	private final UserService service;
	private final VehiculoEditor editor;
	final Grid<Vehiculo> grid;
	final TextField filtro;
	private final Button agregarNuevoBoton;
	VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();

	@Autowired
	public VehiculoView(VehiculoService caracteristicas, VehiculoEditor editor, UserService service) {
		this.caracteristicas = caracteristicas;
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Vehiculo.class);
		this.filtro = new TextField();
		this.agregarNuevoBoton = new Button("Nuevo Vehiculo");
	}

	@PostConstruct
	void init() {
		Label titulo = new Label("Vehiculos");
		titulo.setStyleName("h2");
		addComponent(titulo);
		

		
		filtro.setPlaceholder("Búsqueda por Matricula");
		HorizontalLayout acciones = new HorizontalLayout();	
		Responsive.makeResponsive(acciones);
		acciones.setSpacing(false);
		acciones.setMargin(false);
		acciones.addComponent(filtro);
		acciones.addComponent(agregarNuevoBoton);
		addComponent(acciones);	
		
			
		grid.setWidth("100%");
		grid.setColumns("matricula", "marca", "estado", "precio_dia", "numero_de_plazas", "gps");
		grid.getColumn("matricula").setCaption("matricula");
		grid.getColumn("marca").setCaption("Marca");
		grid.getColumn("estado").setCaption("Estado");
		grid.getColumn("precio_dia").setCaption("Precio/dia");
		grid.getColumn("numero_de_plazas").setCaption("Nº plazas");
		grid.getColumn("gps").setCaption("¿GPS?");
		
		
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
		filtro.addValueChangeListener(e -> listarVehiculos(e.getValue()));

		// Connect selected Cliente to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editarVehiculo(e.getValue());
		});

		// Instantiate and edit new Cliente the new button is clicked
		agregarNuevoBoton.addClickListener(e -> {
			if(service.loadUserByUsername(sesion.getContext().getAuthentication().getName()).getIs_Gestor())
				editor.editarVehiculo(new Vehiculo());
			else
				Notification.show("Debes ser Gestor para registrar un nuevo vehiculo", Type.ERROR_MESSAGE);
		});

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listarVehiculos(filtro.getValue());
		});

		VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		User u = new User(null, null);
				
		if(sesion.getContext().getAuthentication() != null)
			u = service.loadUserByUsername(sesion.getContext().getAuthentication().getName());
		
		
		// Initialize listing
		listarVehiculos(u);
	}


	void listarVehiculos(User u) {
		if (u != null) {
			grid.setItems(caracteristicas.findAll());
			
		}
	}
	
	void listarVehiculos(String texto) {
		VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		User u = service.loadUserByUsername(sesion.getContext().getAuthentication().getName());
		if (StringUtils.isEmpty(texto)) {
			grid.setItems(caracteristicas.findAll());
		}else {
			grid.setItems(caracteristicas.findByMatricula(texto));
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}