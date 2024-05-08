/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package resources.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button boton_gastos;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pulsarGastos(ActionEvent event) throws IOException {
        FXMLLoader cargadorGastofxml = new FXMLLoader(getClass().getResource("/resources.fxml/añadir_gastoFXML.fxml"));
        Parent root = cargadorGastofxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
    }
    
}

