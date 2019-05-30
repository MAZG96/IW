package es.uca.iw.proyectoCompleto.account;

import java.io.File;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringView(name = AccountView.VIEW_NAME)
@SuppressWarnings("serial")
public class AccountView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "AccountView";
	private AccountEditor editor;
	private Grid<User> grid;
	VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
	
	private final UserService service;
	
	@Autowired
	public AccountView(UserService service, AccountEditor editor) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(User.class);
	}

	
	@PostConstruct
	void init() {
			
		Label description = new Label("Haz click en tus datos para editarlos");
		description.setStyleName("h3");
		
		HorizontalLayout misdatos;
		
		String foto = service.loadUserByUsername(sesion.getContext().getAuthentication().getName()).getProfilePic();
		
		if(foto != null) {
			final Image imagen = new Image();
			imagen.setVisible(true);
			File file = new File(foto);
			imagen.setSource(new FileResource(file));
			imagen.setWidth(200, Unit.PIXELS);
			imagen.setHeight(200, Unit.PIXELS);
			
			misdatos = new HorizontalLayout(imagen);
			
		} else {
			Label nullpic = new Label("Aún no tienes ninguna foto de perfil");
			nullpic.setStyleName("h3");
			
			misdatos = new HorizontalLayout(nullpic);
		}
		
		Label property;
		
		if(service.loadUserByUsername(sesion.getContext().getAuthentication().getName()).getIs_Gestor()) {
			property = new Label("Gestor");
		} else if(service.loadUserByUsername(sesion.getContext().getAuthentication().getName()).getIs_Gerente()) {
			property = new Label("Gerente");
		}else {
			property = new Label("Usuario");
		}
			

		addComponents(misdatos, property, description, grid, editor);
		

		grid.setWidth("100%");
		grid.setHeight(100, Unit.PIXELS);
		grid.setColumns("firstName", "lastName", "username", "email", "addr", "tlf", "bio");
		grid.getColumn("firstName").setCaption("Nombre");
		grid.getColumn("lastName").setCaption("Apellidos");
		grid.getColumn("username").setCaption("Usuario");
		grid.getColumn("addr").setCaption("Dirección");
		grid.getColumn("tlf").setCaption("Teléfono");
		grid.getColumn("bio").setCaption("Biografía");
		// Hook logic to components

		// Connect selected User to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editAccount(e.getValue());
		});

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listUser();
		});

		// Initialize listing
		listUser();

	}


	private void listUser() {
		grid.setItems(service.loadUserByUsername(sesion.getContext().getAuthentication().getName()));
		
		//grid.setItems(service.findByLastNameStartsWithIgnoreCase());
	}
	
	/*public Image convertByteToImage(final byte[] image) {
		 StreamSource streamSource = new StreamResource.StreamSource() {
	         public InputStream getStream()
	         {
	            return (image == null) ? null : new ByteArrayInputStream(image);
	         }
	      };

	      return new Image(null, new StreamResource(streamSource, "streamedSourceFromByteArray"));
	}*/
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
