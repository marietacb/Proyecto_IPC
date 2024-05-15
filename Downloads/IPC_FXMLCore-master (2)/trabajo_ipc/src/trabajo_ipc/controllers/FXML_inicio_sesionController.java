/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
        

/**
 * FXML Controller class
 *
 * @author siren
 */
public class FXML_inicio_sesionController implements Initializable {

    @FXML
    private HBox myHBox;
    @FXML
    private Button botonRegistro;
    @FXML
    private TextField huecoEmail;
    @FXML
    private TextField huecoContraseña;
    @FXML
    private Label errorEmail;
    @FXML
    private Label errorContraseña;
    @FXML
    private BooleanProperty validEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        validEmail = new SimpleBooleanProperty();
        validEmail.setValue(Boolean.FALSE); 
      
        huecoEmail.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
            //checkEditEmail();
 }
 });
    }    

    @FXML
    private void click_Raton(MouseEvent event) throws IOException {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXML_registro.fxml"));
       Parent root = cargarRegistro.load();
       
       Stage stage = new Stage();
       stage.setScene(new Scene(root));
       stage.show();
    }

    @FXML
    private void escribirUsuario(KeyEvent event) {
    }

    @FXML
    private void escribirContraseña(KeyEvent event) {
    }
    
}

