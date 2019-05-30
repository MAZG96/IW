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
		//addComponent(new Label("This is the welcome page"));
		
		HorizontalLayout headerAnuncios = new HorizontalLayout();
		Label titulo = new Label("Vehiculos");
		titulo.setStyleName("h2");
		headerAnuncios.addComponent(titulo);
		
		addComponent(headerAnuncios);
		
		anunciosAll();
		
	}
	
	private void anunciosAll() {
		List<Vehiculo> vehiculos = vehiSer.findAll();
		System.out.println(vehiculos.get(0).getMatricula());
		for(Vehiculo v : vehiculos) {
			System.out.println(vehiculos.size());
			HorizontalLayout vehiculo = new HorizontalLayout();
			
			Image imagen = new Image();
			File file = new File(v.getGaleria());
			imagen.setVisible(true);
			imagen.setSource(new FileResource(file));
			imagen.setWidth(100, Unit.PIXELS);
			imagen.setHeight(100, Unit.PIXELS);
					
			Label ap1 = new Label ("Matricula: "+v.getMatricula() + ",");
			Label ap2 = new Label ("Marca: "+v.getMarca());
			Label ap3 = new Label ("Estado: "+v.getEstado());
			Label ap4 = new Label("Precio/dia: " + v.getPrecio_dia() + "€");
			Label ap5 = new Label ("GPS: "+ v.getGps());
			Label ap6 = new Label ("Climatizador: "+ v.getClimatizador());
			
			
			
			vehiculo.addComponent(imagen);
			vehiculo.addComponent(ap1);
			vehiculo.addComponent(ap2);
			vehiculo.addComponent(ap3);
			vehiculo.addComponent(ap4);
			vehiculo.addComponent(ap5);
			vehiculo.addComponent(ap6);
			
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
