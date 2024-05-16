package trabajo_ipc.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.sun.javafx.scene.control.skin.Utils;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Charge;    
import model.User;
import model.Category;
import model.AcountDAO;
import model.Acount;
import model.AcountDAOException;
import model.Stowable;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */
public class FXML_anadirGastoController implements Initializable {

     @FXML
    private Button boton_aceptar;   //aceptar, revisa errores e introduce datos en la tabla
     @FXML
    private Button boton_Cancelar;  //cancela y vuelve a la pantalla de gastos (no guarda cambios)
    
     
     //textfields de cada parametro de una gasto
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
    private MenuItem categoria;    //categoria seleccionada

   
    @FXML
    private Label error_fecha;  //texto de error en la fecha
    @FXML
    private Label error_unidades;   //texto de error en las unidades
    @FXML
    private Label error_precio; //texto de error en el precio
    @FXML
    private Label error_nombre;     //texto de error en el nombre
    @FXML
    private Label error_foto;   //texto de error en la foto
     @FXML
    private Label error_categoria;
     
    private User usuario;
    
    
    private FXMLDocumentController tablaController; //tabla vinculada a la pantalla principal
    @FXML
    private Label error_descripcion;
    @FXML
    private TextField categoria_gasto;
    @FXML
    private HBox pantallaAñadirGasto;
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Charge> listaGastos = new ArrayList<Charge>();
       // listaGastos.getUserChargesDB(usuario.getNickName());
        
       //prueba para ver si al minimizar la pantalla, se adapata el fxml 
       pantallaAñadirGasto.widthProperty().addListener((observable,oldValue,newValue)->{
           vBox1.setPrefWidth((Double)newValue/2);
           vBox2.setPrefWidth((Double)newValue/2);
       });
       
       pantallaAñadirGasto.heightProperty().addListener((observable,oldValue,newValue)->{
           vBox1.setPrefHeight((Double)newValue/2);
           vBox2.setPrefHeight((Double)newValue/2);
       });
       
    }
    
    
    
    @FXML
    private void pulsar_seleccionarFecha(MouseEvent event) {
        elegir_fecha.setDayCellFactory((DatePicker picker) -> { 
        return new DateCell() { 
            @Override 
            public void updateItem(LocalDate date, boolean empty) { 
                super.updateItem(date, empty); 
                LocalDate today = LocalDate.now(); 
                setDisable(empty || date.compareTo(today) > 0 ); 
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
    private void pulsarAceptar(ActionEvent event) throws AcountDAOException, IOException { //guardar datos en la tabla y cerrar ventana
        
        //ERRORES MOSTRADOS == HECHO
        if(elegir_fecha.getDayCellFactory() == null){   //no se ha seleccionado fecha
            error_fecha.visibleProperty().set(true);
        }
        else{error_fecha.visibleProperty().set(false);}
        
        
        if((descripcion_gasto.getText()).isEmpty()){
            error_descripcion.visibleProperty().set(true);
        }
        else{error_descripcion.visibleProperty().set(false);}
        
        //TODO: comprobar tmb que no exista en los nombres creados por el usuario
        if(nombre_gasto.getText().isEmpty()){   
            error_nombre.visibleProperty().set(true);
        }
        else{error_nombre.visibleProperty().set(true);}
        
        
        if ((precio_gasto.getText()).isEmpty()) {               
            error_precio.setText("Introduzca el precio");
            error_precio.visibleProperty().set(true);                
        }
        
        else{
            try {   //que pruebe a convertir las unidades en double
                Double precios = Double.parseDouble(precio_gasto.getText());
            
                if (precios <= 0) { //si son precios negativos == error
                    error_precio.setText("El formato introducido no es correcto");
                    error_precio.visibleProperty().set(true);
                } 
                else {error_precio.visibleProperty().set(false);}
            
            } catch (NumberFormatException e) { //si no es tipo double == error
                error_precio.setText("El formato introducido no es correcto");
                error_precio.visibleProperty().set(true);
            }
        }
        
        
        //si no se introducen unidades
        if ((unidades_gasto.getText()).isEmpty()) { //si no se introducen las unidades
            error_unidades.setText("Introduzca las unidades");  //error mostrado
            error_unidades.visibleProperty().set(true); //visible = true
        }
        
        else{
            try {   //si se introducen unidades, que pruebe a convertir las unidades en integer            
                Integer units = Integer.parseInt(unidades_gasto.getText());
                if (units <= 0) { //si se introducen valores negativos
                    error_unidades.setText("El formato introducido no es correcto");
                    error_unidades.visibleProperty().set(true);
                }
                else {error_unidades.visibleProperty().set(false);}
            
            } catch (NumberFormatException e) { //si no es tipo integer = error
                error_unidades.setText("El formato introducido no es correcto");
                error_unidades.visibleProperty().set(true);
            }
        }
        
        
        
        
        String nombreGasto = nombre_gasto.getText();
        String descripcion = descripcion_gasto.getText();
        LocalDate fecha = elegir_fecha.getValue();
        int unidades = Integer.parseInt(unidades_gasto.getText());  //unidades a int
        double precio = Double.parseDouble(precio_gasto.getText()); //precio a double
        Image factura = tiquet_gasto.getImage();
        //Category categoria = (categoria_gasto.getText()); quiero coger una cotegoria
        
        //TODO: añadir categoria
     
        //ESTE METODO REGISTRA EN LA CUENTA DEL USUARIO EL GASTO QUE HA AÑADIDO 
        
        TableView<Charge> tabla = tablaController.getTabla();   //tabla controller document
            List<Category> list = Acount.getInstance().getUserCategories();
            //me falta añadir la categoria
           Acount.getInstance().registerCharge(nombreGasto,descripcion,precio,unidades,factura,fecha,categoria); 
           
    }
   
    }
    