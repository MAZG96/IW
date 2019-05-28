package es.uca.iw.proyectoCompleto.account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.users.UserEditor.ChangeHandler;

@SpringComponent
@UIScope
@SuppressWarnings("serial")
public class AccountEditor extends VerticalLayout{
	private final UserService service;
	/**
	 * The currently edited user
	 */
	private User user;

	private Binder<User> binder = new Binder<>(User.class);

	
	/* Fields to edit properties in User entity */
	TextField firstName = new TextField("Nombre");
	TextField lastName = new TextField("Apellidos");
	TextField username = new TextField("Usuario");
	PasswordField passwd = new PasswordField("Contraseña");
    PasswordField confirmPasswd= new PasswordField("Confirmar Contraseña");
	TextField email = new TextField("Email");
	TextField addr = new TextField("Dirección");
	TextField tlf = new TextField("Tlf");
	TextArea bio = new TextArea("Biografía");
	TextField foto = new TextField("Foto de perfil");
	TextField bank = new TextField("Cuenta bancaria");
	
	

	/* Action buttons */
	Button save = new Button("Guardar", FontAwesome.SAVE);
	Button cancel = new Button("Cancelar");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel);


	@Autowired
	public AccountEditor(UserService service) {
		this.service = service;
		
		final Image imagen = new Image();
		class ImageUploader implements Receiver, SucceededListener {
			public File file;
			public OutputStream receiveUpload(String filename, String mimeType){
				FileOutputStream fos = null;
				try{
					file = new File("src/webapp/VAADIN/img/"+filename);
					fos = new FileOutputStream(file);
				}catch(final java.io.FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "Error al subir la imagen");
					return null;
				}
				return fos;
			}
	
			public void uploadSucceeded(SucceededEvent event) {
				imagen.setVisible(true);
				imagen.setSource(new FileResource(file));
				imagen.setWidth(200, Unit.PIXELS);
				imagen.setHeight(200, Unit.PIXELS);
				foto.setValue(file.toString());
			}
		};
		
		ImageUploader receiver = new ImageUploader();
		Upload upload = new Upload("Subir foto", receiver);
		upload.setImmediateMode(true);
		upload.addSucceededListener(receiver);
		foto.setVisible(false);
		
		Label validationStatus = new Label();
        binder.setStatusLabel(validationStatus);
				
		addComponents(validationStatus, firstName, lastName, username, passwd, confirmPasswd, 
				email, addr, tlf, bio, upload, imagen, bank, actions);

		// bind using naming convention
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
		
		binder.forField(passwd)
		.asRequired("Este campo no puede estar vacío")
		.bind(User::getPassword, User::setPassword);
		
		binder.forField(confirmPasswd)
		.asRequired("Debe confirmar contraseña")
		.bind(User::getUsername, (person, password) -> {});
				
		binder.withValidator(Validator.from(user -> {
			return Objects.equals(passwd.getValue(),
                        confirmPasswd.getValue());
        }, "La contraseña y su confirmación deben coincidir"));
		
		binder.forField(email)
		.asRequired("Este campo no puede estar vacío")
		.withValidator(new EmailValidator("Introduce un correo válido"))
		.bind(User::getEmail, User::setEmail);
		
		binder.forField(foto)
		.bind(User::getProfilePic, User::setProfilePic);
        
		binder.forField(bank)
		.asRequired("Este campo no puede estar vacío")
		.withValidator(new StringLengthValidator("La cuenta bancaria debe ser entre 16 y 34 caracteres", 16, 34))
		.bind(User::getCuentaBancaria, User::setCuentaBancaria);
		
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.setEnabled(false);
		save.addClickListener(e -> {
			service.save(user);
        	getUI().getPage().reload();
		});
		cancel.addClickListener(e -> {
			editAccount(user);;
        	getUI().getPage().reload();
		});
		setVisible(false);
		
		binder.addStatusChangeListener(
                event -> save.setEnabled(binder.isValid()));
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editAccount(User c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			user = service.findOne(c.getId());
		}
		else {
			user = c;
		}
		cancel.setVisible(persisted);

		// Bind user properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(user);
		
		setVisible(true);
		
		
		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		firstName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
	}
}
