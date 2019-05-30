package es.uca.iw.proyectoCompleto.vehiculos;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class VehiculoEditor extends VerticalLayout{
	
private final VehiculoService service;
private final UserService userservice;
	

	private Vehiculo veh;

	/* Fields to edit properties in Vehiculo entity */
	
	Label title = new Label("Nuevo Vehiculo");
	TextField matricula = new TextField("Matricula");
	TextField marca = new TextField("Marca");
	TextField estado = new TextField("Estado");
	TextField climatizador = new TextField("Climatizador");
	TextField gps = new TextField("Gps");
	TextField numero_de_plazas = new TextField("Numero de plazas");
	TextField tipo_transmision = new TextField("Tipo de transmision");
	TextField carroceria = new TextField("carroceria");
	TextField precio_dia = new TextField("precio_dia");
	TextField oficina = new TextField("oficina");
	TextField galeria = new TextField("galeria");
	DateField fechaIni = new DateField("Fecha de inicio");
	DateField fechaFin = new DateField("Fecha de salida");
	

	/* Action buttons */
	Button guardar = new Button("Guardar");
	Button cancelar = new Button("Cancelar");
	Button borrar = new Button("Borrar");
	CssLayout acciones = new CssLayout(guardar, cancelar, borrar);

	Binder<Vehiculo> binder = new Binder<>(Vehiculo.class);
	
	

	@Autowired
	public VehiculoEditor(VehiculoService caracteristicas, UserService us) {
		
		service = caracteristicas;
		userservice=us;
		
		final Image imagen = new Image();
		class ImageUploader implements Receiver, SucceededListener {
			public File file;
			public OutputStream receiveUpload(String filename, String mimeType){
				FileOutputStream fos = null;
				try{
					file = new File("src/webapp/VAADIN/img/"+filename);
					fos = new FileOutputStream(file);
				}catch(final java.io.FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "Error al subir el fichero");
					return null;
				}
				return fos;
			}
	
			public void uploadSucceeded(SucceededEvent event) {
				imagen.setVisible(true);
				imagen.setSource(new FileResource(file));
				imagen.setWidth(200, Unit.PIXELS);
				imagen.setHeight(200, Unit.PIXELS);
				galeria.setValue(file.toString());
			}
		};
		
		ImageUploader receiver = new ImageUploader();
		Upload upload = new Upload("Subir fichero", receiver);
		upload.setImmediateMode(true);
		upload.addSucceededListener(receiver);	
		galeria.setVisible(false);
		


		Label validationStatus = new Label();
        binder.setStatusLabel(validationStatus);
		
		matricula.setMaxLength(32);
		marca.setMaxLength(32);
		estado.setMaxLength(32);
		climatizador.setMaxLength(32);
		gps.setMaxLength(8);
		numero_de_plazas.setMaxLength(8);
		tipo_transmision.setMaxLength(32);
		carroceria.setMaxLength(32);
		precio_dia.setMaxLength(8);
		oficina.setMaxLength(32);
		
		
		addComponents(title, matricula, marca, estado, climatizador, gps, numero_de_plazas,tipo_transmision,carroceria,precio_dia,fechaIni,fechaFin,oficina,upload, imagen, acciones);

		
		System.out.println(galeria.getValue());
		binder.forField(galeria)
		.bind(Vehiculo::getGaleria, Vehiculo::setGaleria);
		
		binder.forField(matricula)
		.asRequired("No puede estar vacío")
		.withValidator(valor -> service.findByMatricula(matricula.getValue()).size()==0,"matricula ya existe")
		.bind(Vehiculo::getMatricula, Vehiculo::setMatricula);
		
		binder.forField(marca)
		.asRequired("No puede estar vacío")
		.withValidator(new StringLengthValidator("Este campo debe ser una cadena entre 4 y 128 caracteres", 4, 32))
		.bind(Vehiculo::getMarca, Vehiculo::setMarca);
		
		binder.forField(estado)
		.asRequired("No puede estar vacío")
		.withValidator((Validator.from(reserva -> {
			if(estado.getValue().compareTo("disponible")==0 || estado.getValue().compareTo("reservado")==0 || estado.getValue().compareTo("averiado")==0) {return true;}
			else {return false;}
        }, "Estado: disponible / reservado / averiado")))
		.bind(Vehiculo::getEstado, Vehiculo::setEstado);
		
		binder.forField(numero_de_plazas)
		  .asRequired("No puede estar vacío")
		  .withConverter(new StringToIntegerConverter("Por favor introduce un número de plazas"))
		  .bind("numero_de_plazas");

		binder.forField(climatizador)
		  .withNullRepresentation("")
		  .asRequired("No puede estar vacío")
		  .withConverter(new StringToIntegerConverter("Por favor introduce un número entero"))
		  .bind("climatizador");
		
		binder.forField(gps)
		  .withNullRepresentation("")
		  .asRequired("No puede estar vacío")
		  .withConverter(
		    new StringToIntegerConverter("Por favor introduce un número entero"))
		  .bind("gps");
		
		binder.forField(precio_dia)
		  .withNullRepresentation("")
		  .asRequired("No puede estar vacío")
		  .withConverter(
		    new StringToIntegerConverter("Por favor introduce un número entero"))
		  .bind("precio_dia");
		
		binder.forField(oficina)
		.asRequired("No puede estar vacío")
		.withValidator(new StringLengthValidator("Este campo debe ser una cadena entre 4 y 128 caracteres", 4, 128))
		.bind(Vehiculo::getOficina, Vehiculo::setOficina);
		
		binder.forField(tipo_transmision)
		.asRequired("No puede estar vacío")
		.withValidator(new StringLengthValidator("Este campo debe ser una cadena entre 4 y 128 caracteres", 4, 128))
		.bind(Vehiculo::getTipo_transmision, Vehiculo::setTipo_transmision);
		binder.forField(fechaIni)
		.asRequired("Este campo no puede estar vacío")
		.bind(Vehiculo::getDisponibilidad_ini, Vehiculo::setDisponibilidad_ini);
	
		binder.forField(fechaFin)
		.asRequired("Este campo no puede estar vacío")
		.withValidator(Validator.from(reserva -> {
			int days = fechaFin.getValue().compareTo(fechaIni.getValue());
			if(days == 0) return false;
			else return true;
        }, "La disponibilidad debe ser al menos de 1 día"))
		.bind(Vehiculo::getDisponibilidad_fin, Vehiculo::setDisponibilidad_fin);
	
		binder.forField(carroceria)
		.asRequired("No puede estar vacío")
		.withValidator(new StringLengthValidator("Este campo debe ser una cadena entre 4 y 128 caracteres", 4, 128))
		.bind(Vehiculo::getCarroceria, Vehiculo::setCarroceria);		
	
		setSpacing(true);
		acciones.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		guardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		guardar.setClickShortcut(ShortcutAction.KeyCode.ENTER);		
		
		guardar.addClickListener(e -> {
			
			VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
			User u = userservice.loadUserByUsername(sesion.getContext().getAuthentication().getName());
			
			if(binder.isValid()) {
				
				
				binder.setBean(veh);
				veh.setMatricula(matricula.getValue());
				veh.setMarca(marca.getValue());
				veh.setEstado(estado.getValue());
				veh.setClimatizador(Integer.valueOf(climatizador.getValue()));
				veh.setGps(Integer.valueOf(gps.getValue()));
				veh.setNumero_de_plazas(Integer.valueOf(numero_de_plazas.getValue()));
				veh.setTipo_transmision(tipo_transmision.getValue());
				veh.setDisponibilidad_ini(fechaIni.getValue());
				veh.setDisponibilidad_fin(fechaFin.getValue());
				veh.setCarroceria(carroceria.getValue());
				veh.setPrecio_dia(Integer.valueOf(precio_dia.getValue()));
				veh.setOficina(oficina.getValue());
				veh.setGaleria(galeria.getValue());
				veh.setUsuario(u);
				caracteristicas.save(veh);
				
				getUI().getPage().reload();
				
			}else
				mostrarNotificacion(new Notification("Algunos campos del formulario deben corregirse"));
		});
		borrar.addClickListener(e -> caracteristicas.delete(veh));
		cancelar.addClickListener(e -> editarVehiculo(veh));
		setVisible(false);
	}
	
	private void mostrarNotificacion(Notification notification) {
        notification.setDelayMsec(1500);
        notification.show(Page.getCurrent());
    }

	public interface ChangeHandler {
		void onChange();
	}

	public final void editarVehiculo(Vehiculo c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		
		final boolean persisted = c.getId() != null;
		
		if (persisted)
			veh = service.findOne(c.getId());
		else 
			veh = c;
		
		cancelar.setVisible(persisted);

		binder.setBean(veh);

		setVisible(true);

		guardar.focus();
		matricula.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		guardar.addClickListener(e -> {
			if(binder.isValid())
				h.onChange();
		});
		borrar.addClickListener(e -> h.onChange());
	}
	
}