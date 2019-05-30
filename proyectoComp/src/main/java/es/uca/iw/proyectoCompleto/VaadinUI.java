package es.uca.iw.proyectoCompleto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import es.uca.iw.proyectoCompleto.security.AccessDeniedView;
import es.uca.iw.proyectoCompleto.security.ErrorView;
import es.uca.iw.proyectoCompleto.security.LoginScreen;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringUI
public class VaadinUI extends UI {

	private static final long serialVersionUID = -5809833958946342598L;

	@Autowired
	SpringViewProvider viewProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
    MainScreen mainScreen;
	
	@Override
	protected void init(VaadinRequest request) {

	   	this.getUI().getNavigator().setErrorView(ErrorView.class);
		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		
		
		showMainScreen();
		
		

	}

	
	private void showMainScreen() {
		int rol = -1;
		if(SecurityUtils.hasRole("ADMIN"))
			rol = 0;
		mainScreen.setAuth(rol);
		
		if(SecurityUtils.hasRole("USER") || SecurityUtils.hasRole("ADMIN"))
			mainScreen.navigationBar.addComponent(mainScreen.logoutButton);
					
		setContent(mainScreen);
	}
	
}