/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.Charge;
import model.User;
import model.AcountDAO;
import model.Category;
import model.AcountDAOException;

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

    
    private PieChart grafico;   //TODO añadir grafico con datos
    
    
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
    private ObservableList<Charge> listaGastos;
    @FXML
    private MenuItem boton_añadircategoria;
    
    
    /**
     * Initializes the controller class.
     * @throws model.AcountDAOException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
              
        try{
            //User usuario = Acount.getInstance().getLoggedUser();
            List<Charge> lista = Acount.getInstance().getUserCharges(); //lista con los gastos del usuario
            listaGastos = (ObservableList)lista;    //convertir lit a observable y meterla en la lista
            this.tableView.setItems(listaGastos);
        }
        catch(Exception e){}  
              
        //situamos y mostramos tabla en el centro del border pane
        tableView.getColumns().addAll(categoria, nombre, fecha, unidades, precio, tiquet, papeleraYmodificar);    
        tableView.setItems(listaGastos);    //tabla de la lista de gastos existentes
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
    private void añadirGasto(ActionEvent event) throws IOException, AcountDAOException { 
        //Si no hay categorias creadas nos lanza un mensaje de error
        if(Acount.getInstance().getUserCategories().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("No existen categorías de gasto");
            alert.setContentText("Por favor introduce primero una categoría");
        
        //continuamos definiendo el mensaje
            Exception excepción = new Exception("Detalles del error");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            excepción.printStackTrace(pw);
            String exceptionText = sw.toString();
            Label label = new Label("Excepción:");
            TextArea textArea =
            new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
        
            textArea.setMaxWidth(Double.MAX_VALUE);textArea.setMaxHeight(Double.MAX_VALUE);GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            GridPane expContent = new GridPane();expContent.setMaxWidth(Double.MAX_VALUE);expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);alert.showAndWait();
        }

        
        //boton añadir gasto abre ventana
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_anadirGasto.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.APPLICATION_MODAL); //añade modalidad del escenario
        stage.showAndWait();    //espera a que se introduzac al información


        
    }

    @FXML
    private void añadircategoria(ActionEvent event) throws IOException { //abre la ventana de añadir categoria
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_añadirCategoria.fxml"));
        Parent root = loader.load();
        
        // Crear una nueva ventana
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        
        // Configurar la ventana como modal
        stage.initModality(Modality.APPLICATION_MODAL);
        
        // Mostrar la ventana y esperar a que se cierre
        stage.show();
    }
    
    public void initTabla(Charge g){
        this.categoria.setCellValueFactory(categoriaFila->new SimpleObjectProperty<Category>(categoriaFila.getValue().getCategory()));
        this.nombre.setCellValueFactory(nombreFila->new SimpleStringProperty(nombreFila.getValue().getName()));
        this.fecha.setCellValueFactory(fechaFila -> new SimpleObjectProperty<LocalDate>(fechaFila.getValue().getDate()));
        this.unidades.setCellValueFactory(unidadesFila -> new SimpleObjectProperty<Integer>(unidadesFila.getValue().getUnits()));
        this.precio.setCellValueFactory(precioFila -> new SimpleObjectProperty<Double>(precioFila.getValue().getCost()));
        this.tiquet.setCellValueFactory(tiquetFila -> new SimpleObjectProperty<Image>(tiquetFila.getValue().getImageScan()));
        this.papeleraYmodificar.setCellValueFactory(papeleraFila -> new SimpleObjectProperty<Image>(papeleraFila.getValue().getImageScan()));      
        
    }
    
}
    
    