package es.uca.iw.proyectoCompleto;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.account.AccountView;
import es.uca.iw.proyectoCompleto.facturas.FacturaView;
import es.uca.iw.proyectoCompleto.reserva.AnuncioBusqueda;
import es.uca.iw.proyectoCompleto.reserva.AnuncioView;
import es.uca.iw.proyectoCompleto.reserva.ReservaView;
import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.RegisterScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.vehiculos.VehiculoView;

@SuppressWarnings("deprecation")
@SpringViewDisplay
public class MainScreen extends VerticalLayout implements ViewDisplay {

	private static final long serialVersionUID = 4315357433431309546L;
	private Panel springViewDisplay;
	final CssLayout navigationBar = new CssLayout();
	final Button logoutButton = new Button("Cerrar sesión", event -> logout());
	VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		
	@Autowired
	UserService userSer;
	
	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@PostConstruct
	public void init() {
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		
		HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");
		
		Button iconic = new Button(null, event -> {
			getUI().getPage().reload();
			});
		iconic.setVisible(true);
		File file = new File("src/webapp/VAADIN/img/escudo.png");
		iconic.setIcon(new FileResource(file));
		iconic.setWidth("400");
		iconic.setHeight("160");
		iconic.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		header.addComponent(iconic);
		
		
		
		
		Label titulo = new Label("ALQUICOCHE");
		titulo.setStyleName("h1");
		header.addComponent(titulo);
		header.setComponentAlignment(titulo, Alignment.MIDDLE_RIGHT);
		header.setComponentAlignment(iconic, Alignment.MIDDLE_LEFT);
		
		
		
		Button login = new Button("Inicia sesión", FontAwesome.USER);
		login.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		Button register = new Button("Registrarse",FontAwesome.USER_PLUS);
		login.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		VerticalLayout sesioncitas = new VerticalLayout();
		sesioncitas.addComponents(login, register);
		sesioncitas.setComponentAlignment(login, Alignment.TOP_RIGHT);
		sesioncitas.setComponentAlignment(register, Alignment.TOP_RIGHT);
				
		//Boton de busqueda
		HorizontalLayout busqueda = new HorizontalLayout();
		busqueda.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		busqueda.setWidth("100%");
		Button lupa = new Button("Buscar");
		lupa.setIcon(VaadinIcons.SEARCH);
		lupa.addClickListener(e -> getUI().getNavigator().navigateTo(AnuncioBusqueda.VIEW_NAME));	
		busqueda.addComponent(lupa);
		
		header.setHeight("175px");
		
		root.addComponents(header, busqueda);
		
		logoutButton.setStyleName(ValoTheme.BUTTON_LINK);

		// Creamos la barra de navegación
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Inicio", AnuncioView.VIEW_NAME));
		HorizontalLayout nav = new HorizontalLayout();
		nav.addComponent(navigationBar);
		nav.addComponent(lupa);
		root.addComponent(nav);
		Label u;
		if(!SecurityUtils.isLoggedIn()) { //USUARIO INVITADO
			register.addClickListener(e -> getUI().getNavigator().navigateTo(RegisterScreen.VIEW_NAME));
			login.addClickListener(e -> getUI().getNavigator().navigateTo(LoginScreen.VIEW_NAME));
			header.setWidth("100%");
			header.addComponents(sesioncitas);
		}
		else if(userSer.loadUserByUsername(sesion.getContext().getAuthentication().getName()).getIs_Gestor()){ //GESTOR
			navigationBar.addComponent(createNavigationButton("Coches", VehiculoView.VIEW_NAME));//MODIFICAR COCHES GENERAL
			navigationBar.addComponent(createNavigationButton("Reservas", ReservaView.VIEW_NAME)); //MODIFICAR RESERVAS GENERAL
			navigationBar.addComponent(createNavigationButton("Editar perfil", AccountView.VIEW_NAME));
			navigationBar.addComponent(logoutButton);
			u = new Label("Usuario: "+userSer.loadUserByUsername(sesion.getContext().getAuthentication().getName()));
			u.setStyleName("h2");
			header.addComponent(u);
			header.setComponentAlignment(u, Alignment.MIDDLE_RIGHT);
		}else if(userSer.loadUserByUsername(sesion.getContext().getAuthentication().getName()).getIs_Gerente()){ //GERENTE
			navigationBar.addComponent(createNavigationButton("Coches", VehiculoView.VIEW_NAME));
			navigationBar.addComponent(createNavigationButton("Reservas", ReservaView.VIEW_NAME)); //MODIFICAR RESERVAS GENERAL
			navigationBar.addComponent(createNavigationButton("Editar perfil", AccountView.VIEW_NAME));
			navigationBar.addComponent(createNavigationButton("Facturación", FacturaView.VIEW_NAME));
			navigationBar.addComponent(logoutButton);

			u = new Label("Usuario: "+userSer.loadUserByUsername(sesion.getContext().getAuthentication().getName()));
			u.setStyleName("h2");
			header.addComponent(u);
			header.setComponentAlignment(u, Alignment.MIDDLE_RIGHT);
		}else{ //USUARIO
			navigationBar.addComponent(createNavigationButton("Mis reservas", ReservaView.VIEW_NAME));
			navigationBar.addComponent(createNavigationButton("Editar perfil", AccountView.VIEW_NAME));
			u = new Label("Usuario: "+userSer.loadUserByUsername(sesion.getContext().getAuthentication().getName()));
			navigationBar.addComponent(logoutButton);
			u.setStyleName("h2");
			header.addComponent(u);
			header.setComponentAlignment(u, Alignment.MIDDLE_RIGHT);
		}
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		addComponent(root);
	}
	
	public void setAuth()
	{
		Bienvenido(); 
	}
	
	private void Bienvenido() 
	{
		VaadinSessionSecurityContextHolderStrategy sesion = new VaadinSessionSecurityContextHolderStrategy();
		
		
		if(sesion.getContext().getAuthentication() != null) {
			User u = userSer.loadUserByUsername(sesion.getContext().getAuthentication().getName());
			Notification.show("Bienvenido " + u.getFirstName(), Type.WARNING_MESSAGE);
		}
			
		else 
			Notification.show("Bienvenido", Type.WARNING_MESSAGE);
	}
	

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);

		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}
		
	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
}