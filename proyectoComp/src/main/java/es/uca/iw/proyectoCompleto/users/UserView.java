/**
 * 
 */
package es.uca.iw.proyectoCompleto.users;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.vehiculos.VehiculoRepository;


@SpringView(name = UserView.VIEW_NAME)
public class UserView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "userView";

	private Grid<User> grid;
	private TextField filter;
	
	
	private final UserService service;
	private final VehiculoRepository repoApar;

	@Autowired
	public UserView(UserService service, UserEditor editor, VehiculoRepository serviceApar) {
		this.service = service;
		this.repoApar = serviceApar;
		this.grid = new Grid<>(User.class);
		this.filter = new TextField();
	    
	}

	
	@PostConstruct
	void init() {
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter);
		
		addComponents(actions, grid);

		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(1080, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName", "email", "addr", "tlf", "bio", "birth");
		grid.getColumn("firstName").setCaption("Nombre");
		grid.getColumn("lastName").setCaption("Apellidos");
		grid.getColumn("addr").setCaption("Dirección");
		grid.getColumn("birth").setCaption("Fecha nacimiento");

		filter.setPlaceholder("Filtrar por apellidos");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listUsers(e.getValue()));

		// Initialize listing
		listUsers(null);

	}

	private void listUsers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {			
			grid.setItems(service.findAll());
		} else {
			grid.setItems(service.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
