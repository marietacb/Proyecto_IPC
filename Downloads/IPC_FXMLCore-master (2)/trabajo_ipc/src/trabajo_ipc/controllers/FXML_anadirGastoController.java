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
    private Button boton_aceptar;
     @FXML
    private Button boton_Cancelar;
    
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
    
    
    private BooleanProperty nombre_valido;
    private BooleanProperty fecha_valida;
    private BooleanProperty unidades_validas;
    private BooleanProperty precio_valido;
    private BooleanProperty tiquet_valido;

    
    
    
    @FXML
    private Label error_fecha;
    @FXML
    private Label error_unidades;
    @FXML
    private Label error_precio;
    @FXML
    private Label error_nombre;
    @FXML
    private Label error_foto;
    
    private Set<String> gastosAñadidos = new HashSet<>();    //lista con nombres añadidos
       
    private String nombreGasto = nombre_gasto.getText().trim(); //nombre del gasto que sea el texto que obtengamos del textfield

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // boton_aceptar.disableProperty().bind(nombre_valido.not().or(fecha_valida.not().
     //           or(precio_valido.not().or(unidades_validas.not().or(tiquet_valido.not())))));

    }
    

   
    private void escribirFecha(InputMethodEvent event) {
        fecha_valida = new SimpleBooleanProperty();
        fecha_valida.setValue(Boolean.FALSE);
        
        if(elegir_fecha.getValue() == null ){   //si no se introduce ningún valor
            error_fecha.setVisible(true);   //dejar ver el error
            
        }
        else{   //si se introduce fecha, debe ser de este formato
            fecha_valida.setValue(Boolean.TRUE);
            elegir_fecha.getValue().format(DateTimeFormatter.ofPattern("dd / MM / yyyy"));
        }
    }

    @FXML
    private void escribirUnidades(InputMethodEvent event) {
        unidades_validas = new SimpleBooleanProperty();
        unidades_validas.setValue(Boolean.FALSE);
        try{
            String unidades = unidades_gasto.getText();
            int numero_unidades = Integer.parseInt(unidades);
        }
        catch(NumberFormatException e){
            unidades_validas.setValue(Boolean.TRUE);
            error_unidades.setVisible(true);
        }
    }

    @FXML
    private void escribirPrecio(InputMethodEvent event) {
        precio_valido = new SimpleBooleanProperty();
        precio_valido.setValue(Boolean.FALSE);
        
        try{
            String precio = unidades_gasto.getText();
            int numero_precio = Integer.parseInt(precio);
        }
        catch(NumberFormatException e){
            precio_valido.setValue(Boolean.TRUE);
            error_precio.setVisible(true);
        }
    }

    @FXML
    private void introducirNombre(InputMethodEvent event) { //mirar buffer de nombres introducidos
   
        if (nombreGasto.isEmpty()) {    //si esta vacio == error
            error_nombre.setText("Introduzca un nombre por favor");
        }

        if (gastosAñadidos.contains(nombreGasto)) {
            error_nombre.setText("El nombre del gasto ya existe.");
        }

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
    private void pulsarCancelar(ActionEvent event) {

    }

    @FXML
    private void pulsarAceptar(ActionEvent event) {
        gastosAñadidos.add(nombreGasto);    //añadimos gasto a la lista despues de pulsar aceptar
        nombre_gasto.setText("");           //reseteamos el textfield a ""
        
        //elegir_fecha.setChronology();
        //fechasAñadidas.add(fecha);
        
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
}
