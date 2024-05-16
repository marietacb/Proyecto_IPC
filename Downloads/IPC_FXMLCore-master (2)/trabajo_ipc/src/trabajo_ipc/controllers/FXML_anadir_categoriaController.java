/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAO;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import model.Stowable;
import model.User;

/**
 * FXML Controller class
 *
 * @author siren
 */
public class FXML_anadir_categoriaController implements Initializable {
    //elementos del Scene
    @FXML
    private HBox pantallaAñadirGasto;
    @FXML
    private VBox vBox1;
    @FXML
    private TextField nombre_categoria;
    @FXML
    private Label error_precio;
    @FXML
    private TextArea descripcion_categoria;
    @FXML
    private VBox vBox2;
    @FXML
    private Button boton_aceptar;
    @FXML
    private Button boton_Cancelar;
    
    //variables asociadas a los elementos del scene
    private String nuevaCategoria = nombre_categoria.getText();
    private String descripcion = descripcion_categoria.getText();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pulsarAceptar(ActionEvent event) throws AcountDAOException, IOException {
         if((nombre_categoria.getText()).isEmpty()){
            error_precio.visibleProperty().set(true);
        }
    Acount.getInstance().registerCategory(nuevaCategoria, descripcion);
    }

    @FXML
    private void pulsarCancelar(ActionEvent event) {
        Stage stage = (Stage) boton_Cancelar.getScene().getWindow();
        stage.close();
    }
    
}
