/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import model.Acount;
import model.AcountDAO;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */
public class PerfilUsuarioController implements Initializable {

    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidosText;
    @FXML
    private TextField emailText;
    @FXML
    private ImageView fotoImage;
    @FXML
    private TextField nickname;
    @FXML
    private Button guardar;
    @FXML
    private Button cancelar;
    @FXML
    private Label errorNombre;
    @FXML
    private Label errorApellidos;
    @FXML
    private Label errorEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            nombreText.setText(Acount.getInstance().getLoggedUser().getName());
            apellidosText.setText(Acount.getInstance().getLoggedUser().getSurname());
            emailText.setText(Acount.getInstance().getLoggedUser().getEmail());
            nickname.setText(Acount.getInstance().getLoggedUser().getNickName());
            fotoImage.setImage(Acount.getInstance().getLoggedUser().getImage());
            
            guardar.setDisable(true);
            cancelar.setDisable(true);
            
        }
        catch(Exception e){}
    }    

    @FXML
    private void cambiarFoto(MouseEvent event) {
        FileChooser ficheroSel = new FileChooser();    //seleccionador de archivos
        ficheroSel.setTitle("Abrir imagen");   
        ficheroSel.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg")); //formate de imagen
        
        File seleccionado = ficheroSel.showOpenDialog( 
        ((Node)event.getSource()).getScene().getWindow());
        if (ficheroSel != null) {
            Image imagen = new Image(seleccionado.toURI().toString());
            fotoImage.setImage(imagen);
        } 
        
        //activamos botones para hacer cambios
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }

    @FXML
    private void pulsarGuardar(ActionEvent event) throws Exception{  
        
        boolean error = true;
        errorNombre.setVisible(false);
        errorApellidos.setVisible(false);
        errorEmail.setVisible(false);

        //si el nombre introducido esta mal
        if(!nombreText.getText().chars().noneMatch(Character::isDigit) || nombreText.getText().length()<1 || nombreText.getText().isEmpty()){
            errorNombre.setText("El nombre no debe contener números y debe ser mínimo de 2 caracteres");
            errorNombre.setVisible(true);
            error = false;
        }
        //si el apellido esta mal
        if(!apellidosText.getText().chars().noneMatch(Character::isDigit) || apellidosText.getText().length()<1 || apellidosText.getText().isEmpty()){
            errorApellidos.setText("El apellido no debe contener números y debe ser mínimo de 2 caracteres");
            errorApellidos.setVisible(true);
            error = false;
        }
        if(!Acount.getInstance().getLoggedUser().checkEmail(emailText.getText())) {
            errorEmail.setText("El correo no puede contener espacios.");
            errorEmail.setVisible(true);
            error = false;
        }
        
        if(error == true){
            //si no hay ningun error
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Diálogo de confirmación");
            alert.setHeaderText("Modificar cambios");
            alert.setContentText("¿Seguro que quieres continuar?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                Acount.getInstance().getLoggedUser().setName(nombreText.getText());       
                Acount.getInstance().getLoggedUser().setSurname(apellidosText.getText());
                Acount.getInstance().getLoggedUser().setEmail(emailText.getText());
            }
            else{
                nombreText.setText(Acount.getInstance().getLoggedUser().getName());
                apellidosText.setText(Acount.getInstance().getLoggedUser().getSurname());
                emailText.setText(Acount.getInstance().getLoggedUser().getEmail());
            }

            error=true; //al acabar que error se resetee a true
            guardar.setDisable(true);
            cancelar.setDisable(true);
        }
    }
    

    @FXML
    private void pulsarCancelar(ActionEvent event) throws Exception{      
        nombreText.setText(Acount.getInstance().getLoggedUser().getName());
        apellidosText.setText(Acount.getInstance().getLoggedUser().getSurname());
        emailText.setText(Acount.getInstance().getLoggedUser().getEmail());
        fotoImage.setImage(Acount.getInstance().getLoggedUser().getImage());
        
        guardar.setDisable(true);
        cancelar.setDisable(true);
    }

    @FXML
    private void escribirNombre(KeyEvent event) {
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }

    @FXML
    private void escribirApellidos(KeyEvent event) {
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }

    @FXML
    private void escribirEmail(KeyEvent event) {
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }  
    
}
