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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */


public class FXMLDocumentController implements Initializable {

    @FXML
    private Button boton_gastos;
    @FXML
    private PieChart grafico;
    @FXML
    private BorderPane border_pane;
            
    private TableView<Gastos> tableView = new TableView<>();    //creamos tableview
    @FXML
    private Button boton_resumenGastos;
    
    private Scene scene;
    @FXML
    private MenuItem boton_añadirGasto;
            
    ObservableList<Gastos> lista = FXCollections.observableArrayList();



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creo el tableview

        TableColumn<Gastos, String> column1 = new TableColumn<>("Categoría");
        TableColumn<Gastos, String> column2 = new TableColumn<>("Producto");
        TableColumn<Gastos, LocalDate> column3 = new TableColumn<>("Fecha");
        TableColumn<Gastos, Integer> column4 = new TableColumn<>("Unidades");
        TableColumn<Gastos, Integer> column5 = new TableColumn<>("Precio");
        TableColumn<Gastos, String> column6 = new TableColumn<>("Foto");
        TableColumn<Gastos, String> column7 = new TableColumn<>(""); //eliminar
        
        column1.setPrefWidth(102);  //tamaños de cada columna
        column2.setPrefWidth(102);
        column3.setPrefWidth(102);
        column4.setPrefWidth(102);
        column5.setPrefWidth(102);
        column6.setPrefWidth(102);
        column7.setPrefWidth(50);   //columna eliminar mas pequeña
         
        tableView.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);    
        tableView.setItems(lista);
        border_pane.setCenter(tableView);
        
        //TODO: Inicializar el grafico aqui
                
    }    

    public TableView<Gastos> getTabla(){    //metodo que devuelve tabla
        return tableView;
    }   
    
    @FXML
    private void pulsarGastos(MouseEvent event) throws IOException {
        grafico.setVisible(false);
        tableView.setVisible(true);
        boton_gastos.disableProperty();
    }

    @FXML
    private void resumen_anual(MouseEvent event) {
        tableView.setVisible(false);
        grafico.setVisible(true);
        boton_resumenGastos.disableProperty();
    }

    @FXML
    private void añadirGasto(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_anadirGasto.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }
    
}
