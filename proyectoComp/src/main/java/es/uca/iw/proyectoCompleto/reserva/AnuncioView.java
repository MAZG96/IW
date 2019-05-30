package es.uca.iw.proyectoCompleto.reserva;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.vehiculos.Vehiculo;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoService;

@SpringView(name = AnuncioView.VIEW_NAME)
public class AnuncioView extends VerticalLayout implements View {
	private static final long serialVersionUID = 7389792879901938103L;
	public static final String VIEW_NAME = "";
	
	@Autowired
	UserService userSer;
	
	@Autowired
	VehiculoService vehiSer;
		
	@PostConstruct
	void init() {
		
		HorizontalLayout headerAnuncios = new HorizontalLayout();
		Label titulo = new Label("Vehiculos");
		titulo.setStyleName("h2");
		headerAnuncios.addComponent(titulo);		
		addComponent(headerAnuncios);
		anunciosAll();		
	}
	
	private void anunciosAll() {
		List<Vehiculo> vehiculos = vehiSer.findAll();
		for(Vehiculo v : vehiculos) {
			HorizontalLayout vehiculo = new HorizontalLayout();
			
			Image imagen = new Image();
			File file = new File(v.getGaleria());
			imagen.setVisible(true);
			imagen.setSource(new FileResource(file));
			imagen.setWidth(100, Unit.PIXELS);
			imagen.setHeight(100, Unit.PIXELS);
					
			Label lb1 = new Label ("Matricula: "+v.getMatricula() + ",");
			Label lb2 = new Label ("Marca: "+v.getMarca());
			Label lb3 = new Label ("Estado: "+v.getEstado());
			Label lb4 = new Label("Precio/dia: " + v.getPrecio_dia() + "€");
			Label lb5 = new Label ("GPS: "+ v.getGps());
			Label lb6 = new Label ("Climatizador: "+ v.getClimatizador());
			
			
			
			vehiculo.addComponent(imagen);
			vehiculo.addComponent(lb1);
			vehiculo.addComponent(lb2);
			vehiculo.addComponent(lb3);
			vehiculo.addComponent(lb4);
			vehiculo.addComponent(lb5);
			vehiculo.addComponent(lb6);
			
			addComponent(vehiculo);
			
			vehiculo.addLayoutClickListener(event -> {
				getUI().getNavigator().navigateTo(AnuncioReserva.VIEW_NAME + "/" + v.getId());
			});
			
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
