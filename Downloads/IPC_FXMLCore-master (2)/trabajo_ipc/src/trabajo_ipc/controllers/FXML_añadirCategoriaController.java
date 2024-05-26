/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAO;
import model.AcountDAOException;
import model.Category;
import model.Charge;

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
    @FXML
    private HBox hbox;
    @FXML
    private Text titulo;
    @FXML
    private Text nombre;
    @FXML
    private Text desc;
    private Stage stage;
    private Boolean edit = false;
    private List<Category> categorias;
    private ObservableList<Category> categoriasO;
    private Category miCategoria;
    @FXML
    private BorderPane borderpane;
    private FXMLDocumentController mainController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String css = this.getClass().getResource("/resources/css/fxml_anadircategoria.css").toExternalForm();
        borderpane.getStylesheets().add(css);
    } 
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
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
        if (!edit){
        Acount.getInstance().registerCategory(nuevaCategoria, descripcion);
        Stage stage = (Stage) boton_aceptar.getScene().getWindow();
        stage.close();
        }else{
            miCategoria.setName(nuevaCategoria);
            miCategoria.setDescription(descripcion);
                
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Añadir categoría");
            alert.setHeaderText("Categoría editada");
            alert.setContentText("La categoría " + nombre + " ha sido editada con éxito");
        
        }
           // Actualizar la tabla de gastos en FXMLDocumentController
            mainController.actualizarGastos();

            // Cerrar la ventana actual
            Stage stage = (Stage) boton_cancelar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelar(MouseEvent event) {
        Stage stage = (Stage) boton_cancelar.getScene().getWindow();
        stage.close();
    }
    
    public void editCategoria(Category category) throws AcountDAOException{
        edit = true;
        miCategoria = category;
        nombre_categoria.setText(miCategoria.getName());
        descripción_categoria.setText(miCategoria.getDescription());
        titulo.setText("Modificar categoría");
    }
    
    public void setStage(Stage sta) {
        stage = sta;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
    }


}
