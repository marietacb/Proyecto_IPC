package trabajo_ipc.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.sun.javafx.scene.control.skin.Utils;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Charge;


/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */
public class FXML_anadirGastoController implements Initializable {

     @FXML
    private Button boton_aceptar;   //acepta e introduce datos en la tabla
     @FXML
    private Button boton_Cancelar;  //cancela y vuelve a la pantalla de gastos
    
    @FXML
    private TextField nombre_gasto;   
    @FXML
    private DatePicker elegir_fecha;
    @FXML
    private TextField unidades_gasto;
    @FXML
    private TextField precio_gasto;
    @FXML
    private TextArea descripcion_gasto;
    @FXML
    private ImageView tiquet_gasto;
      
    
    //variables booleanas para comprobar que se introducen bien los datos
    private BooleanProperty nombreValido;
    private BooleanProperty fechaValida;
    private BooleanProperty precioValido;
    private BooleanProperty unidadesValidas;
    private BooleanProperty fotoValida;

   
    @FXML
    private Label error_fecha;  //texto de error en la fecha
    @FXML
    private Label error_unidades;   //texto de error en las unidades
    @FXML
    private Label error_precio; //texto de error en rel precio
    @FXML
    private Label error_nombre;     //texto de error en el nombre
    @FXML
    private Label error_foto;   //texto de error en la foto
    
    
    private Set<String> gastosAñadidos = new HashSet<>();    //lista con nombres añadidos
   
    private String nombreGasto; //nombre del gasto que sea el texto que obtengamos del textfield

    private FXMLDocumentController tablaController; //tabla vinculada a la pantalla principal
    @FXML
    private Label error_categoria;
    @FXML
    private MenuButton selector_categoria;
    @FXML
    private MenuItem categoria1;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    /*public void setExpenseTableController(ExpenseTableController controller) {
        this.expenseTableController = controller;
    }*/
    
    public void setTablaController(FXMLDocumentController tablaController){ //esta tabla que sea la del otro controller
        this.tablaController = tablaController;
    }
    


    @FXML
    private void escribirUnidades(InputMethodEvent event) {
        
    }

    @FXML
    private void escribirPrecio(InputMethodEvent event) {
        
    }
    
    @FXML
    private void introducirNombre(InputMethodEvent event) { //mirar buffer de nombres introducidos
   
    }

    @FXML
    private void escribirDescripcion(InputMethodEvent event) {

    }
    
    @FXML
    private void pulsar_seleccionarFecha(MouseEvent event) {
        elegir_fecha.setDayCellFactory((DatePicker picker) -> { 
        return new DateCell() { 
            @Override 
            public void updateItem(LocalDate date, boolean empty) { 
                super.updateItem(date, empty); 
                LocalDate today = LocalDate.now(); 
                setDisable(empty || date.compareTo(today) < 0 ); 
                //TODO: cambiar para que solo se vean los dia anteriores a hoy y hoy
            } 
        }; 
    });
    }

    @FXML
    private void pulsarCancelar(ActionEvent event) {    //cerrar ventana
        Stage stage = (Stage) boton_Cancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pulsarAceptar(ActionEvent event) { //guardar datos en la tabla y cerrar ventana
        
        //declaramos strings que contengan lo que se introduce en cada textfield
        String nombreGastos = nombre_gasto.getText();   //nombres
        String uni = unidades_gasto.getText();  //unidades
        String prec = precio_gasto.getText();   //precios
        String descripcion = descripcion_gasto.getText();
                
        //comprobar nombre
        //TODO: comprobar si el nombre esta o no en la tabla
        if (nombreGastos.isEmpty()) {   //si no se ha introducido ningun nombre
           error_nombre.visibleProperty().set(true);
        }   
        
        if (descripcion.isEmpty()) {   //si no se ha introducido ninguna descripcion
           error_nombre.visibleProperty().set(true);
        }  
        
        //comprobar unidades
        //si esta vacio el campo == error
        if (nombreGastos.isEmpty()) {error_unidades.setText("Introduzca las unidades");}
        
        try {   //que pruebe a convertir las unidades en integer
            Integer units = Integer.parseInt(uni);
            if (units <= 0) { //si se introducen valores negativos
                error_unidades.visibleProperty().set(true);
            }
        } catch (NumberFormatException e) { //si no es tipo integer = error
            error_unidades.visibleProperty().set(true);
        }
        
        
        //comprobar precio
        //TODO: hacer que descripcion y nombre gastos funcione
        //si no se han introducido unidaes == error
        if (nombreGastos.isEmpty()) {error_unidades.setText("Introduzca las unidades");}
        
        try {   //que pruebe a convertir las unidades en double
            Double precios = Double.parseDouble(prec);
            if (precios <= 0) { //si son precios negativos == error
                error_precio.visibleProperty().set(true);
            }    
        } catch (NumberFormatException e) { //si no es tipo double == error
            error_precio.visibleProperty().set(true);
        }
        
        
        
        //los datos añadidos == los introducimos en la tabla principal
        //nombreGastos esta declarado arriba
        //usamos clase Charge
        LocalDate fecha = elegir_fecha.getValue();
        int unidades = Integer.parseInt(unidades_gasto.getText());  //unidades a int
        double precio = Double.parseDouble(precio_gasto.getText()); //precio a double
        String descript = descripcion_gasto.getText();           //descripcion a texto
        Image factura = tiquet_gasto.getImage();
        
        //TODO: añadir categoria
        
        //QUITAR COMENTARIO CUANDO VAYA A PROGRAMAR
        //registerCharge(nombreGastos,descript,precio,unidades,factura,fecha,categoria);
        //ESTE METODO REGISTRA EN LA CUENTA DEL USUARIO EL GASTO QUE HA AÑADIDO 
        
        /*TableView<Gastos> tabla = tablaController.getTabla();
        ObservableList<Gastos> lista = FXCollections.observableArrayList();
        lista.add(nuevoGasto);    //añadimos a la tabla un gasto 
    
        tabla.setItems(lista); */
    }

    private void registerCharge() { //añade metodo para registrar gastos
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}