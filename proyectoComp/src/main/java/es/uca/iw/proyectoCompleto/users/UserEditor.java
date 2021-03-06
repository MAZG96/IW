package es.uca.iw.proyectoCompleto.users;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
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

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout {

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
	PasswordField password = new PasswordField("Contraseña");
    PasswordField confirmPassword= new PasswordField("Confirmar Contraseña");
	TextField email = new TextField("Email");
	TextField addr = new TextField("Dirección");
	TextField tlf = new TextField("Tlf");
	TextArea bio = new TextArea("Biografía");
	CheckBox gestor = new CheckBox("Gestor");
	CheckBox gerente = new CheckBox("Gerente");
	DateField date = new DateField("Fecha de nacimiento");
	TextField foto = new TextField();
	
	
	

	/* Action buttons */
	Button save = new Button("Guardar", FontAwesome.SAVE);
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Borrar", FontAwesome.TRASH_O);

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	@Autowired
	public UserEditor(UserService service) {
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
		
		addComponents(validationStatus, firstName, lastName, username, password, confirmPassword, 
				email, addr, tlf, bio, gestor,gerente, date, upload, imagen, actions);

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
		
		binder.forField(date)
		.bind(User::getBirth, User::setBirth);
				
		binder.forField(password)
		.asRequired("Este campo no puede estar vacío")
		.bind("password");
				
		binder.forField(confirmPassword)
		.asRequired("Debe confirmar contraseña")
		.bind(User::getUsername, (person, password) -> {});
				
		binder.withValidator(Validator.from(user -> {
			return Objects.equals(password.getValue(),
                        confirmPassword.getValue());
        }, "La contraseña y su confirmación deben coincidir"));
		
		binder.forField(email)
		.asRequired("Este campo no puede estar vacío")
		.withValidator(new EmailValidator("Introduce un correo válido"))
		.bind(User::getEmail, User::setEmail);
		
		binder.forField(foto)
		.bind(User::getProfilePic, User::setProfilePic);
		
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.setEnabled(false);
		save.addClickListener(e -> service.save(user));
		delete.addClickListener(e -> service.delete(user));
		cancel.addClickListener(e -> editUser(user));
		setVisible(false);
		
			
		// Solo borra el admin
		delete.setEnabled(SecurityUtils.hasRole("ADMIN"));
		
		binder.addStatusChangeListener(
                event -> save.setEnabled(binder.isValid()));
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editUser(User c) {
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
		
		user.setIs_Gestor(gestor.getValue());
		user.setIs_Gerente(gerente.getValue());

		
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
		delete.addClickListener(e -> h.onChange());
}

}