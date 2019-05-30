package es.uca.iw.proyectoCompleto.security;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;


@SpringView(name = RegisterScreen.VIEW_NAME)
@SuppressWarnings("serial")
public class RegisterScreen extends FormLayout implements View{
	public static final String VIEW_NAME = "registerScreen";
	@Autowired
	private final UserService service;
	
	Button save = new Button("Registrarse", FontAwesome.REGISTERED);
	Button cancel = new Button("Cancelar",FontAwesome.LOCK);

	TextField firstName = new TextField("Nombre");
	TextField lastName = new TextField("Apellidos");
	TextField username = new TextField("Usuario");
    PasswordField password = new PasswordField("Contraseña");
    PasswordField confirmPassword= new PasswordField("Confirmar Contraseña");
    TextField email = new TextField("Email");
	TextField addr = new TextField("Dirección");
	TextField tlf = new TextField("Tlf");
	TextArea bio = new TextArea("Biografía");
	DateField date = new DateField("Fecha de nacimiento");
	TextField tarjeta = new TextField("Tarjeta");
	TextField cuentabancaria = new TextField("Cuenta Bancaria");
	
	Binder<User> binder = new Binder<>(User.class);
	
	CssLayout actions = new CssLayout(save, cancel);
	
	@Autowired
	public RegisterScreen(UserService service) {
		this.service = service;
	}
	
	@PostConstruct
	void init() {
		this.setSpacing(false);
		this.setMargin(false);
		this.setCaption("Registro en Alquicoche");
		
		this.save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		Label validationStatus = new Label();
        binder.setStatusLabel(validationStatus);
		
    	addComponents(firstName, lastName, username, password, confirmPassword, email, addr, tlf,
    			bio, date,tarjeta,cuentabancaria, actions);
    	binder.bindInstanceFields(this);
    	
    	binder.forField(firstName)
		.asRequired("Este campo no puede estar vacío")
		.bind(User::getFirstName, User::setFirstName);
		
		binder.forField(lastName)
		.asRequired("Este campo no puede estar vacío")
		.bind(User::getLastName, User::setLastName);
		
		binder.forField(username)
		.asRequired("Este campo no puede estar vacío")
		.bind(User::getUsername, User::setUsername);
		
		binder.forField(password)
		.asRequired("Este campo no puede estar vacío")
		.bind("password");
		
		binder.forField(tlf)
		.asRequired("Este campo no puede estar vacío")
		.bind("tlf");
		
		binder.forField(tarjeta)
		.asRequired("Este campo no puede estar vacío")
		.withValidator(new StringLengthValidator("Este campo debe ser una cadena entre 16 y 32 caracteres", 16, 34))
		.bind(User::getTarjeta, User::setTarjeta);
		
		binder.forField(cuentabancaria)
		.asRequired("Este campo no puede estar vacío")
		.withValidator(new StringLengthValidator("numero de cuenta tiene 20 caracteres", 20, 20))
		.bind(User::getCuentaBancaria, User::setCuentaBancaria);
		
		binder.forField(email)
		.asRequired("Este campo no puede estar vacío")
		.withValidator(new EmailValidator("Introduce un correo válido"))
		.bind(User::getEmail, User::setEmail);

		binder.forField(date)
		.withValidator(edad->{
			LocalDate hoy = LocalDate.now();
        	long dias = ChronoUnit.DAYS.between(hoy,date.getValue());
        	if(dias<6570) {
        		return true;
        	}else{return false;}},"debe ser mayor de 18 años")
		.bind(User::getBirth, User::setBirth);
		
        binder.withValidator(Validator.from(user -> {
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                return true;
            } else {
                return Objects.equals(password.getValue(),
                        confirmPassword.getValue());
            }
        }, "La contraseña y su confirmación deben coincidir"));
        
        
                
        binder.setBean(new User("", ""));
        
        save.setEnabled(false);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        
        save.addClickListener(
                event ->{ service.save(binder.getBean());
                	getUI().getPage().reload();});
        
        
        
        binder.addStatusChangeListener(
                event -> save.setEnabled(binder.isValid()));
		
	}
	
	private boolean isRegistered(User user) {
		boolean isRegistered = false;
		if((service.findByEmail(user.getEmail()) == null) && 
				(service.loadUserByUsername(user.getUsername()) == null)){
			service.save(user);
			isRegistered = true;
		}
		return isRegistered;
	}
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
