package es.uca.iw.proyectoCompleto.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.account.AccountView;
import es.uca.iw.proyectoCompleto.security.RegisterScreen;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserManagementView;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.users.UserView;


@SpringView(name = LoginScreen.VIEW_NAME)
public class LoginScreen extends VerticalLayout implements View{
	public static final String VIEW_NAME = "loginScreen";
	//private LoginCallback callback;
	
	//public LoginScreen(LoginCallback callback) {
		//this.callback = callback;
	@Autowired
	AuthenticationManager authenticationManager;
    	
	@PostConstruct
	void init() {
		setMargin(true);
        setSpacing(true);

        TextField username = new TextField("Nombre de usuario");
        addComponent(username);

        PasswordField password = new PasswordField("Contraseña");
        addComponent(password);
        
        Button login = new Button("Iniciar de sesión", evt -> {
            String pword = password.getValue();
            password.setValue("");
            if (!login(username.getValue(), pword)) {
                Notification.show("Error de inicio de sesión");
                username.focus();
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addComponent(login);
	}
	

	private boolean login(String username, String password) {
		try {
			Authentication token = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			// Reinitialize the session to protect against session fixation
			// attacks. This does not work with websocket communication.
			VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
			SecurityContextHolder.getContext().setAuthentication(token);
			
			// Show the main UI
			getUI().getPage().reload();
			return true;
		} catch (AuthenticationException ex) {
			return false;
		}
	}
    
    /**
  	 * 
  	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
