package trabajo_ipc.controllers;

import com.sun.javafx.scene.control.skin.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;

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
    private BooleanProperty validRepassword;
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
    @FXML
    private PasswordField field_contraseña2;
    
    
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
    @FXML
    private Label contraseña2_incorrecto;
    
    
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
        validRepassword = new SimpleBooleanProperty(false);
        validEmail = new SimpleBooleanProperty(false);
        equalPasswords = new SimpleBooleanProperty(false);
        
        validName.setValue(Boolean.FALSE);
        validSurname.setValue(Boolean.FALSE);
        validNickname.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        validRepassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        
        field_correo.focusedProperty().addListener((observable, oldValue, newValue)->{
        if(!newValue){//focusLost
           checkEditEmail();
        }
    }); 
        
        BooleanBinding validFields = Bindings.and(validEmail, validPassword)
                 .and(equalPasswords);
         

    } 
        
    private void checkEditEmail() {
            if(!Utils.checkEmail(field_correo.getText())){
                manageError(correo_incorrecto, field_correo, validEmail);
            }else{
                manageError(correo_incorrecto, field_correo, validEmail);
            }
    }
    private void checkEquals(){
        if(field_contraseña.textProperty().getValueSafe().compareTo(field_contraseña2));
    }


    private void introducir_name(InputMethodEvent event) {
        String name = field_name.getText();
    }

    private void introducir_surname(InputMethodEvent event) {
        String surname = field_surname.getText();
    }

    private void introducir_nickname(InputMethodEvent event) {
        String nickname = field_nickname.getText();
    }

    private void introducir_password(InputMethodEvent event) {
        String password = field_password.getText();
    }
    
    private void introducir_repassword(InputMethodEvent event) {
        String repassword = field_repassword.getText();
    }

    private void introducir_mail(InputMethodEvent event) {
        String mail = field_mail.getText();
    }

    @FXML
    private void pulsar_login(ActionEvent event) {
        
    }

    @FXML
    private void pulsar_cancelar(ActionEvent event) {
    }

  
}