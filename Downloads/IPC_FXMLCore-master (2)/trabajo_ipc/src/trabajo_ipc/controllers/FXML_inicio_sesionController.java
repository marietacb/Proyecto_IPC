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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
        

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    

    @FXML
    private void click_Raton(MouseEvent event) throws IOException {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXML_registro.fxml"));
       Parent root = cargarRegistro.load();
       
       trabajo_ipc.setRoot(root);
    }
    
}

