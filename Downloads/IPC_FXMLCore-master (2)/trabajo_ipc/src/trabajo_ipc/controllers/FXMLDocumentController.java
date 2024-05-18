/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Charge;
import model.User;
import model.AcountDAO;
import model.Category;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */


public class FXMLDocumentController implements Initializable {

    @FXML
    private Button boton_gastos;    //muestra la tabla gastoss en la pantalla principal
    @FXML
    private BorderPane border_pane;
            
    
    @FXML
    private Button boton_resumenGastos; //muestra grafico de gastos
    
    private Scene scene;
    @FXML
    private MenuItem boton_añadirGasto; //abre fxml añadir gasto
    @FXML
    private MenuItem anadirCategoria;

    
    private PieChart grafico;   //TODO añadir grafico con datos
    
    
    private ObservableList<Charge> lista = FXCollections.observableArrayList();   //lista con los gastos  
    @FXML
    private TableView<Charge> tableView;
    @FXML
    private TableColumn<Charge, String> nombre;
    @FXML
    private TableColumn<Charge, LocalDate> fecha;
    @FXML
    private TableColumn<Charge, Integer> unidades;
    @FXML
    private TableColumn<Charge, Double> precio;
    @FXML
    private TableColumn<Charge, Image> tiquet;
    @FXML
    private TableColumn<Charge, Image> papeleraYmodificar;
    @FXML
    private TableColumn<Charge, Category> categoria;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadData();
        
        //situamos y mostramos tabla en el centro del border pane
        tableView.getColumns().addAll(categoria, nombre, fecha, unidades, precio, tiquet, papeleraYmodificar);    
        tableView.setItems(lista);    //tabla de la lista de gastos existentes
        border_pane.setCenter(tableView);
        
        
        
        //TODO: Inicializar el grafico aqui (ponerle informacion gastos)
                
    }    
    
    //HECHO
    @FXML
    private void pulsarGastos(MouseEvent event) throws IOException { //boton gastos muestra tabla
        grafico.setVisible(false);
        tableView.setVisible(true);
        boton_gastos.disableProperty();
    }

    //HECHO
    @FXML
    private void resumen_anual(MouseEvent event) {  //boton resumen anual muestra grafico
        tableView.setVisible(false);
        grafico.setVisible(true);
        boton_resumenGastos.disableProperty();
    }

   
    //HECHO
    @FXML
    private void añadirGasto(ActionEvent event) throws IOException { //boton añadir gasto abre ventana
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_anadirGasto.fxml"));
        Parent root = loader.load();
        FXML_anadirGastoController controladorGasto = loader.getController();   //para poder añadir gasto a la tabla
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.APPLICATION_MODAL); //añade modalidad del escenario
        stage.showAndWait();    //espera a que se introduzac al información


        
    }

    @FXML
    private void añadircategoria(ActionEvent event) throws IOException {
        
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXML_añadir_categoria.fxml"));
       Parent root = cargarRegistro.load();
       
       Stage stage = new Stage();
       stage.setScene(new Scene(root));
       stage.showAndWait();
    }
    
    public void loadData(){
        categoria.setCellValueFactory(categoriaFila->new SimpleObjectProperty<Category>(categoriaFila.getValue().getCategory()));
        nombre.setCellValueFactory(nombreFila->new SimpleStringProperty(nombreFila.getValue().getName()));
        fecha.setCellValueFactory(fechaFila -> new SimpleObjectProperty<LocalDate>(fechaFila.getValue().getDate()));
        unidades.setCellValueFactory(unidadesFila -> new SimpleObjectProperty<Integer>(unidadesFila.getValue().getUnits()));
        precio.setCellValueFactory(precioFila -> new SimpleObjectProperty<Double>(precioFila.getValue().getCost()));
        tiquet.setCellValueFactory(tiquetFila -> new SimpleObjectProperty<Image>(tiquetFila.getValue().getImageScan()));
        papeleraYmodificar.setCellValueFactory(papeleraFila -> new SimpleObjectProperty<Image>(papeleraFila.getValue().getImageScan()));


    }
    
}
