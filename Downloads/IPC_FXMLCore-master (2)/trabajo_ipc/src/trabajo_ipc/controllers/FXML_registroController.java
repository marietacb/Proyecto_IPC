package trabajo_ipc.controllers;

import com.sun.javafx.scene.control.skin.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author Dynabook
 */
public class FXML_registroController implements Initializable {
    
    private BooleanProperty validName;
    private BooleanProperty validSurname;
    private BooleanProperty validNickname;
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;

    private final int EQUALS = 0;
    
 
    //DONDE SE ESCRIBE
    @FXML
    private Text Registro;
    @FXML
    private TextField field_nombre;
    @FXML
    private TextField field_apellidos;
    @FXML
    private TextField field_nombreusuario;
    @FXML
    private TextField field_correo;
    @FXML
    private PasswordField field_contraseña;
    
    
    //LOS BOTONES
    @FXML
    private Button button_login;
    @FXML
    private Button button_cancel;
    
    
    //LOS ERRORES EN ROJO
    @FXML
    private Label nombre_incorrecto;
    @FXML
    private Label nombreusuario_incorrecto;
    @FXML
    private Label correo_incorrecto;
    @FXML
    private Label contraseña_incorrecto;
    private ImageView imagen_perfil;
    @FXML
    private Label apellido_incorrecto;
    @FXML
    private Hyperlink iniciar_link;
    
    private int fecha;
    @FXML
    private Button avatar;
    
    
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
 
    }
    
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }
    
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        validName = new SimpleBooleanProperty(false);
        validSurname = new SimpleBooleanProperty(false);
        validNickname = new SimpleBooleanProperty(false);
        validPassword = new SimpleBooleanProperty(false);
        validEmail = new SimpleBooleanProperty(false);
        
        validName.setValue(Boolean.FALSE);
        validSurname.setValue(Boolean.FALSE);
        validNickname.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        
        field_correo.focusedProperty().addListener((observable, oldValue, newValue)->{
        if(!newValue){//focusLost
           //checkEditEmail();
        }
    }); 
        
    } 
    
    public static  Boolean checkEmail (String email){   
        if(email == null){
            return false;
        }return true;
    }
        

    private void checkEditEmail() {
            if(checkEmail(field_correo.getText())){
                manageError(correo_incorrecto, field_correo, validEmail);
            }else{
                manageError(correo_incorrecto, field_correo, validEmail);
            }
    }
    public boolean CompobarEspacios(String texto, int index){
  
        for(int i=0; i<texto.length();i++){
            if(texto.charAt(i) ==  ' '){return true;}
            else{return false;}
        }
        return false;
    }
    
    private void introducir_name(InputMethodEvent event) {
        String name = field_nombre.getText();
    }

    private void introducir_surname(InputMethodEvent event) {
        String surname = field_apellidos.getText();
    }

    private void introducir_nickname(InputMethodEvent event) {
       String nickname = field_nombreusuario.getText();
    }

    private void introducir_password(InputMethodEvent event) {
       String password = field_contraseña.getText();
    }
    
    private void introducir_mail(InputMethodEvent event) {
        String mail = field_correo.getText();
    }


    @FXML
    private void pulsar_registrarse(ActionEvent event) throws AcountDAOException, IOException {
        String nombre = field_nombre.getText();
        String apellidos = field_apellidos.getText();
        String nombreUsuario = field_nombreusuario.getText();
        String correo = field_correo.getText();
        String contraseña = field_contraseña.getText();
        Image foto_perfil = imagen_perfil.getImage();
        
        LocalDate fecha = LocalDate.now();

        if(CompobarEspacios(nombreUsuario,nombreUsuario.length())==true){
            nombre_incorrecto.visibleProperty().set(true);
        }
        if(CompobarEspacios(correo,correo.length())==true){
            correo_incorrecto.visibleProperty().set(true);
        }
        if(CompobarEspacios(contraseña,contraseña.length())==true){
            contraseña_incorrecto.visibleProperty().set(true);
        }
            
        
        if(nombre.isBlank()){
            nombre_incorrecto.visibleProperty().set(true);
        }
        if(apellidos.isBlank()){
            apellido_incorrecto.visibleProperty().set(true);
        }
        if(nombreUsuario.isBlank()){
            nombreusuario_incorrecto.visibleProperty().set(true);
        }
        if(correo.isBlank()){
            correo_incorrecto.visibleProperty().set(true);
        }
        if(contraseña.isBlank()){
            contraseña_incorrecto.visibleProperty().set(true);
        }
        
        boolean isOK= Acount.getInstance().registerUser(nombre, apellidos, correo, nombreUsuario, contraseña, foto_perfil, fecha);
        if(isOK){
            //MANDARLO A LA SIGUIENTE ESCENA
        }else{
            System.err.println("NO SE HA GUARDADO BIEN");
        }
    }

    @FXML
    private void pulsar_cancelar(ActionEvent event) {
        Stage stage = (Stage) button_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void iniciar_sesion(MouseEvent event) throws IOException {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/inicio.fxml"));
       Parent root = cargarRegistro.load();
       
       Stage stage = new Stage();
       Stage stageinicial = (Stage) button_cancel.getScene().getWindow();
       stage.setScene(new Scene(root));
       stage.show();
       stageinicial.close();
    }

    @FXML
    private void cambiar_avatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        
        // Filtro para limitar a solo archivos de imagen
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

    }

}
