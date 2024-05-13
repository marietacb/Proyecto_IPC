package trabajo_ipc.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
      
    @FXML
    private Label error_fecha;  //texto de error en la fecha
    @FXML
    private Label error_unidades;   //texto de error en las unidades
    @FXML
    private Label error_precio; //texto de error en la fecha
    @FXML
    private Label error_nombre;     //texto de error en la fecha
    @FXML
    private Label error_foto;   //texto de error en la fecha
    
    private Set<String> gastosAñadidos = new HashSet<>();    //lista con nombres añadidos
    private String nombreGasto; //nombre del gasto que sea el texto que obtengamos del textfield

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       boton_aceptar.setDisable(true);  //desactivar boton al principio
    }
    


    @FXML
    private void escribirUnidades(InputMethodEvent event) {
        precio_gasto.textProperty().addListener((observable, oldValue, newValue) -> {
            if(esUnidades(newValue)){
                error_unidades.setVisible(false); //ocultamos mensaje de error
                //guardar precio y mostrar en gastos
            }
             else{
                error_unidades.setVisible(true);
            }
      
        });
    }

    @FXML
    private void escribirPrecio(InputMethodEvent event) {
        precio_gasto.textProperty().addListener((observable, oldValue, newValue) -> {
            if(esPrecio(newValue)){
                error_precio.setVisible(false); //ocultamos mensaje de error
                //guardar precio y mostrar en gastos
            }
             else{
                error_precio.setVisible(true);
            }
      
        });
    }
    
    private static boolean esPrecio (String cadena){ //metodo que comprueba si o no un numero
        try{
            Double.parseDouble(cadena);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    
    private static boolean esUnidades(String cadena){ //metodo que comprueba si o no un numero
        try{
            Integer.parseInt(cadena);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    
    @FXML
    private void introducirNombre(InputMethodEvent event) { //mirar buffer de nombres introducidos
   
        nombreGasto = nombre_gasto.getText().trim();    //string nombre es el texto que obtenemos del textfield
        
        if (nombreGasto.isEmpty()) {    //si esta vacio == error (no hay texto introducido)
            error_nombre.setText("Introduzca un nombre por favor");
        }

        if (gastosAñadidos.contains(nombreGasto)) { //si el nombre ya esta en el buffer
            error_nombre.setText("El nombre del gasto ya existe."); //error
        }
        
        gastosAñadidos.add(nombreGasto);    //añadimos el nombre del gasto a la lista
    }

    @FXML
    private void pulsarSeleccionarCategoria(ActionEvent event) {

    }

    @FXML
    private void escribirDescripcion(InputMethodEvent event) {

    }

    @FXML
    private void pulsarAñadirCategoria(ActionEvent event) {

        
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
    private void pulsarAceptar(ActionEvent event) { //guardar datos y cerrar ventana
        gastosAñadidos.add(nombreGasto);    //añadimos gasto a la lista despues de pulsar aceptar
        nombre_gasto.setText("");           //reseteamos el textfield a ""
        
        //elegir_fecha.setChronology();
        //fechasAñadidas.add(fecha);
        //set la fecha a 0 == elegir_fecha.;
        
        //unidades
        //unidadesAñadidas.add();
        unidades_gasto.setText("");
        
        //precios
        //preciosAñadidos.add();
        precio_gasto.setText("");
        
        //descripciones
        //descripcionesAñadidas.add();
        descripcion_gasto.setText("");
        
        //foto tiquet
        //tiquetsAñadidos.add();
        tiquet_gasto.setImage(null);    
        
        Stage stage = (Stage) boton_aceptar.getScene().getWindow();
        stage.close();
    }
}
