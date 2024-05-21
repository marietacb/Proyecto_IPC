/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author siren
 */
public class FXML_añadirCategoriaController implements Initializable {

    @FXML
    private TextField nombre_categoria;
    @FXML
    private TextArea descripción_categoria;
    @FXML
    private Button boton_aceptar;
    @FXML
    private Button boton_cancelar;
    @FXML
    private Label error_label;
    
    //variables asociadas a los elementos del scene
    private String nuevaCategoria;
    private String descripcion;
    
    @FXML
    private Label error_descripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void aceptar(MouseEvent event) throws AcountDAOException, IOException {
        //Indicadores de error
        if((nombre_categoria.getText()).isEmpty()){
               error_label.visibleProperty().set(true);
        } else if((descripción_categoria.getText()).isEmpty()){
                error_descripcion.visibleProperty().set(true);
        } else {
        nuevaCategoria = nombre_categoria.getText();
        descripcion = descripción_categoria.getText();
        Acount.getInstance().registerCategory(nuevaCategoria, descripcion);
        Stage stage = (Stage) boton_aceptar.getScene().getWindow();
        stage.close();
        }
    }

    @FXML
    private void cancelar(MouseEvent event) {
        Stage stage = (Stage) boton_cancelar.getScene().getWindow();
        stage.close();
    }


}
