package es.uca.iw.proyectoCompleto.facturas;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = FacturaView.VIEW_NAME)
public class FacturaView extends VerticalLayout implements View{
	public static final String VIEW_NAME = "FacturaView";
	private FacturaService factSer;
	private final FacturaRepository factRepo;
	final Grid<Factura> grid;
	final TextField filtro;

	@Autowired
	public FacturaView(FacturaService ser,FacturaRepository factRepo) {	
		this.factRepo=factRepo;
		this.factSer=ser;
		this.grid = new Grid<>(Factura.class);
		this.filtro = new TextField();
	}

	@PostConstruct
	void init() {
		Label titulo = new Label("Factura");
		titulo.setStyleName("h2");
		
		addComponent(titulo);
		
		List<Factura> facturas = (List<Factura>) factRepo.findAll();
		
		
		float total_dinero=0.0f;
		float canceladas = 0.0f;
		float fianzas = 0.0f;
		for(int i=0;i<facturas.size();i++) {
			if(facturas.get(i).getTipo().compareTo("reserva")==0) { //reservas pagadas
				total_dinero+=facturas.get(i).getCantidad();
			}else if(facturas.get(i).getTipo().compareTo("cancelacion")==0){ //devoluciones
				canceladas+=facturas.get(i).getCantidad();
				total_dinero-=facturas.get(i).getCantidad();
			}else {
				fianzas+=facturas.get(i).getCantidad();
				total_dinero-=facturas.get(i).getCantidad();
			}
		}
		Label dinero_total = new Label("Ganancias Totales: "+total_dinero+"€");
		Label devolucionf = new Label("Dinero devuelto por fianzas: "+fianzas+"€");
		Label devolucionc = new Label("Dinero devuelto por cancelacion de reservas: "+canceladas+"€");
		titulo.setStyleName("h2");
		// Initialize listing
		addComponents(dinero_total,devolucionf,devolucionc);
		
		
		HorizontalLayout acciones = new HorizontalLayout();	
		Responsive.makeResponsive(acciones);
		acciones.setSpacing(false);
		acciones.setMargin(false);
		addComponent(acciones);	
		
		grid.setWidth("100%");
		grid.setColumns("cantidad", "cuentaDestino", "tarjetaOrigen","tipo");
		grid.getColumn("cantidad").setCaption("Cantidad(€)");
		grid.getColumn("cuentaDestino").setCaption("Cuenta Destino");
		grid.getColumn("tarjetaOrigen").setCaption("Cuenta Origen");
		grid.getColumn("tipo").setCaption("Tipo");
		
		
		
		HorizontalLayout contenido = new HorizontalLayout();
		Responsive.makeResponsive(contenido);
		contenido.setSpacing(false);
		contenido.setMargin(false);
		contenido.setSizeFull();
		
		contenido.addComponent(grid);	
		contenido.setExpandRatio(grid, 0.7f);
		addComponent(contenido);

		listarFacturas();
	}



	void listarFacturas() {
		grid.setItems((Collection<Factura>)factRepo.findAll());
	}
	

	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
