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
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


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

			VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
			SecurityContextHolder.getContext().setAuthentication(token);

			getUI().getPage().reload();
			return true;
		} catch (AuthenticationException ex) {
			return false;
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
