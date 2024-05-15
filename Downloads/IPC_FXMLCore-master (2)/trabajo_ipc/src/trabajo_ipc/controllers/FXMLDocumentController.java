/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */


public class FXMLDocumentController implements Initializable {

    @FXML
    private Button boton_gastos;    //muestra la tabla gastoss en la pantalla principal
    @FXML
    private PieChart grafico;   //TODO: añadir informacion al grafico
    @FXML
    private BorderPane border_pane;
            
    private TableView<Charge> tableView = new TableView<>();    //creamos tableview
    @FXML
    private Button boton_resumenGastos; //muestra grafico de gastos
    
    private Scene scene;
    @FXML
    private MenuItem boton_añadirGasto; //abre fxml añadir gasto
            
    ObservableList<Charge> lista = FXCollections.observableArrayList(); //una lista de gastos
    
    public User user;  //usuario publico



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Creo el tableview
        TableColumn<Charge, String> column1 = new TableColumn<>("Categoría");
        TableColumn<Charge, String> column2 = new TableColumn<>("Producto");
        TableColumn<Charge, LocalDate> column3 = new TableColumn<>("Fecha");
        TableColumn<Charge, Integer> column4 = new TableColumn<>("Unidades");
        TableColumn<Charge, Integer> column5 = new TableColumn<>("Precio");
        TableColumn<Charge, String> column6 = new TableColumn<>("Tiquet");
        TableColumn<Charge, Image> column7 = new TableColumn<>(); //imagen papelera eliminar
        
        column1.setPrefWidth(102);  //tamaños de cada columna
        column2.setPrefWidth(102);
        column3.setPrefWidth(102);
        column4.setPrefWidth(102);
        column5.setPrefWidth(102);
        column6.setPrefWidth(102);
        column7.setPrefWidth(50);   //columna eliminar mas pequeña
         
        //situamos y mostramos tabla en el centro del border pane
        tableView.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);    
        tableView.setItems(lista);
        border_pane.setCenter(tableView);
        
        //TODO: Inicializar el grafico aqui (ponerle informacion gastos)
                
    }    

    public TableView<Charge> getTabla(){    //metodo que devuelve tabla
        return tableView;
    }   
    
    @FXML
    private void pulsarGastos(MouseEvent event) throws IOException { //boton gastos muestra tabla
        grafico.setVisible(false);
        tableView.setVisible(true);
        boton_gastos.disableProperty();
    }

    @FXML
    private void resumen_anual(MouseEvent event) {  //boton resumen anual muestra grafico
        tableView.setVisible(false);
        grafico.setVisible(true);
        boton_resumenGastos.disableProperty();
    }

    
    @FXML
    private void añadirGasto(ActionEvent event) throws IOException { //boton añadir gasto abre ventana
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_anadirGasto.fxml"));
        Parent root = loader.load();

        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();    //espera a que se introduzac al información

    }
    
}
