package trabajo_ipc.controllers;

import com.sun.javafx.logging.Logger;
import com.sun.javafx.logging.PlatformLogger.Level;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;

public class FXML_registroController implements Initializable {

    private BooleanProperty validName;
    private BooleanProperty validSurname;
    private BooleanProperty validNickname;
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;

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
    private Button button_login;
    @FXML
    private Button button_cancel;
    @FXML
    private Label nombre_incorrecto;
    @FXML
    private Label nombreusuario_incorrecto;
    @FXML
    private Label correo_incorrecto;
    @FXML
    private Label contraseña_incorrecto;
    @FXML
    private Label apellido_incorrecto;
    @FXML
    private Hyperlink iniciar_link;
    @FXML
    private ImageView avatarid;
    @FXML
    private Label titulo;
    
    private Boolean edit = false;
    
    private Stage stage;
    
    private boolean register;
    
    private Utils utils = new Utils();

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validName = new SimpleBooleanProperty(false);
        validSurname = new SimpleBooleanProperty(false);
        validNickname = new SimpleBooleanProperty(false);
        validPassword = new SimpleBooleanProperty(false);
        validEmail = new SimpleBooleanProperty(false);
        
        register = true;
        try {
            Acount.getInstance().getLoggedUser().getName();
            iniciar_link.setVisible(false);
            button_login.setText("Actualizar");
            titulo.setText("Modificar perfil");
        } catch (Exception e) {
            register = false;
            
            field_nombreusuario.textProperty().addListener((observable, oldValue, newValue) -> {
                new Thread(() -> {
                    boolean exists;
                    try {
                        exists = Acount.getInstance().existsLogin(newValue);
                        Platform.runLater(() -> {
                                if (exists) {
                                    nombreusuario_incorrecto.setText("Nickname ya esta en uso");
                                } else {
                                    nombreusuario_incorrecto.setText("");
                                }
                        });
                    } catch (AcountDAOException | IOException ex) {}
                   }).start(); 
            });     
        }       
    }

    private static Boolean checkEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            return false;
        }
        return true;
    }

    private void checkEditEmail() {
        if (checkEmail(field_correo.getText())) {
            manageCorrect(correo_incorrecto, field_correo, validEmail);
        } else {
            manageError(correo_incorrecto, field_correo, validEmail);
        }
    }

    private void manageError(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, textField);
        textField.requestFocus();
    }

    private void manageCorrect(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, textField);
    }

    private void hideErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.setVisible(false);
        textField.setStyle("");
    }

    private void showErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.setVisible(true);
        textField.setStyle("-fx-background-color: #FCE5E0");
    }

    public boolean containsSpaces(String texto) {
        return texto.contains(" ");
    }

    @FXML
    private void pulsar_registrarse(ActionEvent event) throws IOException, AcountDAOException {
        String nombre = field_nombre.getText();
        String apellidos = field_apellidos.getText();
        String nombreUsuario = field_nombreusuario.getText();
        String correo = field_correo.getText();
        String contraseña = field_contraseña.getText();
        Image avatar = avatarid.getImage();
        LocalDate fecha = LocalDate.now();

        boolean valid = true;
        
        try {
            Acount.getInstance().getLoggedUser().getName();
        } catch (Exception ex) {
            if (Acount.getInstance().existsLogin(nombreUsuario)) {
                nombreusuario_incorrecto.setText("El nombre de usuario ya esta en uso.");
                nombreusuario_incorrecto.setVisible(true);
                valid = false;
            }
        }

        if (nombre.isBlank()) {
            nombre_incorrecto.setText("El nombre no puede estar en blanco.");
            nombre_incorrecto.setVisible(true);
            valid = false;
        } else {
            nombre_incorrecto.setVisible(false);
        }

        if (apellidos.isBlank()) {
            apellido_incorrecto.setText("Los apellidos no pueden estar en blanco.");
            apellido_incorrecto.setVisible(true);
            valid = false;
        } else {
            apellido_incorrecto.setVisible(false);
        }

        if (nombreUsuario.isBlank()) {
            nombreusuario_incorrecto.setText("El nombre de usuario no puede estar en blanco.");
            nombreusuario_incorrecto.setVisible(true);
            valid = false;
        } else {
            nombreusuario_incorrecto.setVisible(false);
        }

        if (correo.isBlank()) {
            correo_incorrecto.setText("Correo no puede estar en blanco.");
            correo_incorrecto.setVisible(true);
            valid = false;
        } else {
            correo_incorrecto.setVisible(false);
        }

        if (contraseña.isBlank()) {
            contraseña_incorrecto.setText("La contraseña no puede estar en blanco.");
            contraseña_incorrecto.setVisible(true);
            valid = false;
        } else {
            contraseña_incorrecto.setVisible(false);
        }
        
        if (containsSpaces(nombreUsuario)) {
            nombreusuario_incorrecto.setText("El nombre de usuario contiene espacios.");
            nombreusuario_incorrecto.setVisible(true);
            valid = false;
        }
        
        if (!checkEmail(correo)) {
            correo_incorrecto.setText("El correo no es correcto.");
            correo_incorrecto.setVisible(true);
            valid = false;
        }
        if (containsSpaces(contraseña)) {
            contraseña_incorrecto.setText("La contraseña no puede contener espacios.");
            contraseña_incorrecto.setVisible(true);
            valid = false;
        }

        if (valid) {
            try {
                if(!edit){
                    
                    String emailMessage;
                    String tituloEmail;
                    
                    if(register){
                        emailMessage = "Su usuario " + nombreUsuario + " ha actualizado su información del perfil, si no ha sido usted, por favor, contáctenos al numero +34 111 222 333";
                        tituloEmail = "Mi dinerillo: Actualización de usuario";
                    } else {
                        emailMessage = "El usuario " + nombreUsuario + " se ha registrado con éxito en 'Mi dinerillo', muchas gracias por confiar en nuestra aplicación.";
                        tituloEmail = "Mi dinerillo: Registro de usuario";
                    }
                    
                    Acount.getInstance().registerUser(nombre, apellidos, correo, nombreUsuario, contraseña, avatar, fecha);
                    
                    utils.sendEmail(field_correo.getText(), field_nombre.getText(), tituloEmail, emailMessage);
                    
                    FXMLLoader cargarRegistro = new FXMLLoader(getClass().getResource("/resources/fxml/inicio.fxml"));
                    Parent root = cargarRegistro.load();

                    Stage stage = new Stage();
                    Stage stageinicial = (Stage) button_cancel.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                    stageinicial.close();
                    
                } else {
                    Acount.getInstance().getLoggedUser().setName(nombre);
                    Acount.getInstance().getLoggedUser().setSurname(apellidos);
                    Acount.getInstance().getLoggedUser().setEmail(correo);
                    Acount.getInstance().getLoggedUser().setPassword(contraseña);
                    Acount.getInstance().getLoggedUser().setImage(avatar);
                    
                    FXMLLoader cargarRegistro = new FXMLLoader(getClass().getResource("/resources/fxml/FXMLDocument.fxml"));
                    Parent root = cargarRegistro.load();

                    Stage stage = new Stage();
                    Stage stageinicial = (Stage) button_cancel.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                    stageinicial.close();
                
                }
                    // Navegar a la siguiente escena

            } catch (AcountDAOException e) {
                if (e.getMessage().contains("UNIQUE constraint failed: user.nickName")) {
                    nombreusuario_incorrecto.setText("El nombre de usuario ya existe.");
                    nombreusuario_incorrecto.setVisible(true);
                } else {
                    // Mostrar un alerta genérica de error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No se pudo registrar el usuario");
                    alert.setContentText("Ocurrió un error durante el registro. Por favor, inténtalo de nuevo.");
                    alert.showAndWait();
                }
            }
        }
        
    }
      
    @FXML
    private void pulsar_cancelar(ActionEvent event) {
        Stage stage = (Stage) button_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void iniciar_sesion(MouseEvent event) throws IOException {
        FXMLLoader cargarRegistro = new FXMLLoader(getClass().getResource("/resources/fxml/inicio.fxml"));
        Parent root = cargarRegistro.load();

        Stage stage = new Stage();
        Stage stageinicial = (Stage) button_cancel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        stageinicial.close();
    }

    @FXML
    private void cambiar_avatar(MouseEvent event) {
        FileChooser fichero = new FileChooser();
        fichero.setTitle("Seleccionar imagen");
        fichero.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg")
        );

        File selectedFile = fichero.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            Image imagen = new Image(selectedFile.toURI().toString());
            
            avatarid.setFitHeight(100); 
            avatarid.setFitWidth(100);  
            
            avatarid.setImage(imagen);
            avatarid.getStyleClass().add("blue-border"); // Aplica la clase CSS

        }
    }
    
    public void editRegister(){
        try{
            field_nombre.setText(Acount.getInstance().getLoggedUser().getName());
            field_apellidos.setText(Acount.getInstance().getLoggedUser().getSurname());
            field_correo.setText(Acount.getInstance().getLoggedUser().getEmail());
            field_nombreusuario.setText(Acount.getInstance().getLoggedUser().getNickName());
            avatarid.setImage(Acount.getInstance().getLoggedUser().getImage());
            edit = true;
            
        }
        catch(Exception e){}
    }
        
    public void setStage(Stage sta) {
        stage = sta;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
    }
}
