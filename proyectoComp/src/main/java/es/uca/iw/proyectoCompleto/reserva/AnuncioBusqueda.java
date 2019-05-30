package es.uca.iw.proyectoCompleto.reserva;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoService;

@SpringView(name = AnuncioBusqueda.VIEW_NAME)
public class AnuncioBusqueda extends VerticalLayout implements View{
	public static final String VIEW_NAME = "anuncioBusqueda";
	private static final long serialVersionUID = 350041936893635659L;
	private Grid<Vehiculo> grid = new Grid<>(Vehiculo.class);
	private TextField filter = new TextField();
	
	@Autowired
	VehiculoService service;
		
	@PostConstruct
	void init() {
		Label titulo = new Label("Búsqueda avanzada de vehiculos");
		titulo.setStyleName("h2");
		addComponents(titulo);
		
		HorizontalLayout actions = new HorizontalLayout(titulo);
		addComponents(actions,filter, grid);

		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(1080, Unit.PIXELS);
		grid.setStyleName(ValoTheme.ACCORDION_BORDERLESS);
		grid.setColumns("matricula", "marca", "estado", "precio_dia", "numero_de_plazas", "gps");
		grid.getColumn("matricula").setCaption("matricula");
		grid.getColumn("marca").setCaption("Marca");
		grid.getColumn("estado").setCaption("Estado");
		grid.getColumn("precio_dia").setCaption("Precio/dia");
		grid.getColumn("numero_de_plazas").setCaption("Nº plazas");
		grid.getColumn("gps").setCaption("¿GPS?");
		

		filter.setPlaceholder("Filtrar por nombre");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listVehiculos(e.getValue()));
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			getUI().getNavigator().navigateTo(AnuncioReserva.VIEW_NAME + "/" + e.getValue().getId());
		});
		
		listVehiculos(null);
	}
	
	
	private void listVehiculos(String filtro) {
		if(StringUtils.isEmpty(filtro)) {
			grid.setItems(service.findAll());
		} else {
			grid.setItems(service.findByMatricula(filtro));
		}
	}
}
