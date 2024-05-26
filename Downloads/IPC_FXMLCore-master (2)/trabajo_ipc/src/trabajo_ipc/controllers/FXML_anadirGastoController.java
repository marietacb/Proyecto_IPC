package trabajo_ipc.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.sun.javafx.scene.control.skin.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
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

    @FXML
    private Label error_fecha;  //texto de error en la fecha
    @FXML
    private Label error_unidades;   //texto de error en las unidades
    @FXML
    private Label error_precio; //texto de error en el precio
    @FXML
    private Label error_nombre;     //texto de error en el nombre
    
    
    private FXMLDocumentController tablaController; //tabla vinculada a la pantalla principal
    @FXML
    private Label error_descripcion;
    @FXML
    private HBox pantallaAñadirGasto;
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
    @FXML
    private Label error_categoria;
    @FXML
    private Label error_foto;
    @FXML
    private VBox vboxFactura;
    
    private Charge gasto;
    @FXML
    private SplitMenuButton categorias_boton;
    
    private List<Category> categorias;
    private ArrayList<Category> categorias2 = new ArrayList<>();

    private List<Charge> cargos;
    private ObservableList<Charge> cargosO;    //lista categorias
    
    int seleccionado;
    @FXML
    private Text nom;
    @FXML
    private Text fec;
    @FXML
    private Text un;
    @FXML
    private Text pre;
    @FXML
    private Text ca;
    @FXML
    private Text des;
    @FXML
    private Text ti;
    @FXML
    private Text titulo;
    
    private Boolean editGasto= false;
    
    private Stage stage;
    private Charge charge;
        
    private FXMLDocumentController fxmlDocumentController;
    private Image scanner;
    @FXML
    private BorderPane borderpane;
    @FXML
    private ScrollPane scrollpane;


    // Método para recibir la instancia actual de FXMLDocumentController
    public void setFXMLDocumentController(FXMLDocumentController fxmlDocumentController) {
        this.fxmlDocumentController = fxmlDocumentController;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
             String css = this.getClass().getResource("/resources/css/anadir_gastofxml.css").toExternalForm();
             borderpane.getStylesheets().add(css);
             
             //configurar datepicker
             elegir_fecha.setDayCellFactory((DatePicker picker) -> {
                 return new DateCell() {
                     @Override
                     public void updateItem(LocalDate date, boolean empty) {
                         super.updateItem(date, empty);
                         LocalDate today = LocalDate.now();
                         setDisable(empty || date.compareTo(today) > 0 );
                     }
                 };
             });
             
             //inicializar el selector de categoría
             categorias = Acount.getInstance().getUserCategories();
             categorias_boton.getItems().clear();    //limpiamos las categorias que pudiera haber cargadas
             // Agregamos nuevas categorías como elementos del menú recrriendo la lista
             for (int i = 0; i < categorias.size(); i++) {
                 Category categoria = categorias.get(i);
                 MenuItem menuItem = new MenuItem(categoria.getName());
                 categorias2.add(categoria);
                 
                 menuItem.setOnAction(evento -> {
                     categorias_boton.setText(categoria.getName());
                     seleccionado = categorias.indexOf(categoria);
                 });
                 
                 categorias_boton.getItems().add(menuItem);
             }} catch (AcountDAOException ex) {
             Logger.getLogger(FXML_anadirGastoController.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(FXML_anadirGastoController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void setTablaController(FXMLDocumentController tablaController){
        this.tablaController = tablaController;
    
    }
    
   
    @FXML
    private void pulsarCancelar(MouseEvent event) throws IOException {    //cerrar ventana
       Stage stage = (Stage) boton_Cancelar.getScene().getWindow();
       stage.close();
    }

    @FXML
    private void pulsarAceptar(ActionEvent event) throws AcountDAOException, IOException { //guardar datos en la tabla y cerrar ventana
                 
        //ERRORES MOSTRADOS
        
        //ERROR FECHA
        if(elegir_fecha.getDayCellFactory() == null){   //no se ha seleccionado fecha
            error_fecha.visibleProperty().set(true);
        }
        else{error_fecha.visibleProperty().set(false);}
        
        
        //ERROR DESCRIPCION
        if((descripcion_gasto.getText()).isEmpty()){
            error_descripcion.visibleProperty().set(true);
        }
        else{error_descripcion.visibleProperty().set(false);}
        
        
        //ERROR NOMBRE
        List<Charge> gastos = Acount.getInstance().getUserCharges();    //lista de gastos
        if(nombre_gasto.getText().isEmpty()){   
            error_nombre.setText("Introduzca un nombre");
            error_nombre.visibleProperty().set(true);
        }
        else{ 
            int i = 0;
            while(!gastos.isEmpty() && i < gastos.size()){  //mientras no recorra toda la lista
                if(nombre_gasto.equals(gastos.get(i))){
                    error_nombre.setText("El nombre introducido ya existe");
                    error_nombre.visibleProperty().set(true);
                    break;  //sale del bucle
                }
                else{i++;}  //sino que siga buscando
            }
            //si se sale sin encontrar nada
            error_nombre.visibleProperty().set(false);  //no hay error
        }

        
        //ERROR PRECIO
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
        
        
        //ERROR UNIDADES
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
        
        if(!error_nombre.isVisible() ){
        
        //CARGAR TABLA CON GASTOS
            
        if(!editGasto){
           String nombreGasto = nombre_gasto.getText();    //obtenemos nombre gasto añadido
           String descripcion = descripcion_gasto.getText();   //obtenemos descripcion gasto aññadido
           LocalDate fecha = elegir_fecha.getValue();  //obtenemos fecha añadida
           int unidades = Integer.parseInt(unidades_gasto.getText());  //obtenemos unidades gasto añadido
           double precio = Double.parseDouble(precio_gasto.getText()); //obtenemos precio gasto añadido
           Image factura = tiquet_gasto.getImage();    //obtenemos imagen gasto añadido
           Category cat = categorias.get(seleccionado);
     
           Acount.getInstance().registerCharge(nombreGasto,descripcion,precio,unidades,factura,fecha,cat); 
        }
        else {
            
                charge.setName(nombre_gasto.getText());
                charge.setDescription(descripcion_gasto.getText());
                charge.setCost(Double.parseDouble(precio_gasto.getText()));
                charge.setUnits(Integer.parseInt(unidades_gasto.getText()));
                charge.setDate(elegir_fecha.getValue());
                charge.setCategory(categorias.get(seleccionado));
                charge.setImageScan(tiquet_gasto.getImage());

        }
           //actualizar tabla
           // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXMLDocument.fxml"));
            Parent root = loader.load();
        
        // Obtener el controlador asociado
           fxmlDocumentController = loader.getController();
           fxmlDocumentController.actualizarGastos();
           
           //volvemos a la pantalla principal LA DE LA TABLA
           FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXMLDocument.fxml"));
           Parent raiz = cargarRegistro.load();
     
           Stage stage = (Stage) boton_aceptar.getScene().getWindow();
           stage.close();
            
        }
    }

    @FXML   //seleccionar foto gasto desde archivos, HECHO
    private void añadirImagen(MouseEvent event) {
        FileChooser ficheroSel = new FileChooser();    //seleccionador de archivos
        ficheroSel.setTitle("Abrir imagen");   
        ficheroSel.getExtensionFilters().addAll(
            new ExtensionFilter("Imagenes", "*.png", "*.jpg")); //formate de imagen
        
        File seleccionado = ficheroSel.showOpenDialog( 
        ((Node)event.getSource()).getScene().getWindow());
        if (ficheroSel != null) {
            tiquet_gasto.setFitHeight(50); //adaptamos tamaño foto
            tiquet_gasto.setFitWidth(50);  
            Image imagen = new Image(seleccionado.toURI().toString());
            tiquet_gasto.setImage(imagen);
        } 
    }    

    @FXML
    private void seleccionar_categoria(MouseEvent event) throws AcountDAOException, IOException {
       /* categorias = Acount.getInstance().getUserCategories();
        categorias_boton.getItems().clear();    //limpiamos las categorias que pudiera haber cargadas
            // Agregamos nuevas categorías como elementos del menú recrriendo la lista
        for (int i = 0; i < categorias.size(); i++) {
            Category categoria = categorias.get(i);
            MenuItem menuItem = new MenuItem(categoria.getName());

            menuItem.setOnAction(evento -> {
                categorias_boton.setText(categoria.getName());  // Opcional
                seleccionado = categorias.indexOf(categoria);
            });
            
            categorias_boton.getItems().add(menuItem);
        } */
    }

    @FXML
    private void comprobar_unidades(KeyEvent event) {
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
                unidades_gasto.setText("");
            }

    }

    @FXML
    private void comprobar_precio(KeyEvent event) {
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
                precio_gasto.setText("");
            }

    }

    @FXML
    private void seleccionar_categoria1(ContextMenuEvent event) throws AcountDAOException, IOException {

    }
    
    public void editGasto(Integer indice) throws AcountDAOException, IOException{
        editGasto = true;
        cargos = Acount.getInstance().getUserCharges();
        cargosO = FXCollections.observableList(cargos);
        charge = cargosO.get(indice);
        
        nombre_gasto.setText(charge.getName());
        unidades_gasto.setText(String.valueOf(charge.getUnits()));
        precio_gasto.setText(String.valueOf(charge.getCost()));
        descripcion_gasto.setText(charge.getDescription());
        tiquet_gasto.setImage(charge.getImageScan());
        elegir_fecha.setValue(charge.getDate());
        scanner = charge.getImageScan();
        tiquet_gasto.setImage(scanner);
        titulo.setText("Modificar gasto");

        
    }
     public void editCategoria(Category cat) throws AcountDAOException, IOException{
        if(editGasto && categorias != null){ 
            categorias_boton.setText(cat.getName()); 
            seleccionado = categorias2.indexOf(cat);
        }
     }
    public void setStage(Stage sta) {
        stage = sta;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
    }
    
    public void setScene(Stage sta){
        stage = sta;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
    
    
    
    }

}