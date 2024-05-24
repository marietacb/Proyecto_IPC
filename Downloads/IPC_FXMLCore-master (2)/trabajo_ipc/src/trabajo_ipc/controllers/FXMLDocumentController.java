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
import java.util.Optional;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private TableColumn<Charge, String> categoria;
    private ObservableList<Charge> listaGastos;
    @FXML
    private MenuItem boton_añadircategoria;
    @FXML
    private Button bPerfil;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private Button bAyuda;
    @FXML
    private Button bAjustes;
    @FXML
    private TableColumn<Charge, String> Descripción;
    @FXML
    private HBox bordeSuperior;
    @FXML
    private ImageView imagenAjustes;
    @FXML
    private ToolBar toolbar;
    @FXML
    private Button comparar_boton;
    @FXML
    private MenuButton bSalir;
    @FXML
    private MenuItem cPerfil;
    @FXML
    private MenuItem boton_salir;
    @FXML
    private MenuButton eliminar_boton;
    @FXML
    private MenuItem categoria_eliminar;
    @FXML
    private MenuItem gasto_eliminar;
    @FXML
    private MenuButton modificar_boton;
    @FXML
    private MenuItem modificar_categoria;
    @FXML
    private MenuItem modificar_Gasto;
    @FXML
    private HBox prueba;
    
    
    /**
     * Initializes the controller class.
     * @throws model.AcountDAOException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        String css = this.getClass().getResource("/resources/css/document.css").toExternalForm();
        border_pane.getStylesheets().add(css);

        try {
            listaGastos = FXCollections.observableArrayList(Acount.getInstance().getUserCharges());
        } catch (AcountDAOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.setItems(listaGastos);

        nombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        Descripción.setCellValueFactory(new PropertyValueFactory<>("description"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("date"));
        unidades.setCellValueFactory(new PropertyValueFactory<>("units"));
        precio.setCellValueFactory(new PropertyValueFactory<>("cost"));
        tiquet.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getImageScan()));
        tiquet.setCellFactory(c-> new ImagenTabCell());
        categoria.setCellValueFactory(cellData -> {Charge charge = cellData.getValue();
                    return new SimpleStringProperty(charge.getCategory().getName());
        });
        
        tableView.setFixedCellSize(50); // Establece una altura fija para todas las celdas
        //border_pane.setCenter(tableView);
        
        grafico = new PieChart();
        grafico.setVisible(true);

        
        //TODO: Inicializar el grafico aqui (ponerle informacion gastos)
        PieChart.Data slice1 = new PieChart.Data("Categoría A", 30);
        PieChart.Data slice2 = new PieChart.Data("Categoría B", 25);
        PieChart.Data slice3 = new PieChart.Data("Categoría C", 45);

        // Crear el gráfico de sectores y añadir los datos
        grafico.getData().add(slice1);
        grafico.getData().add(slice2);
        grafico.getData().add(slice3);
        grafico.setTitle("Gráfico de Sectores de Ejemplo"); 
        
        
        border_pane.setCenter(grafico);
        boton_resumenGastos.setDisable(true);
       
        try{
        imagenPerfil.setImage(Acount.getInstance().getLoggedUser().getImage());
        }
        catch(Exception e){}
        
        //inicializar gridpane
        
    }    
    
    
    //HECHO
    @FXML
    private void pulsarGastos(MouseEvent event) throws IOException { //boton gastos muestra tabla
        grafico.setVisible(false);  
        tableView.setVisible(true); 
        //border_pane.setCenter(tableView);
        border_pane.setCenter(prueba);
        boton_gastos.setDisable(true);
        boton_resumenGastos.setDisable(false);
        
        bPerfil.setDisable(false);
    }

    //HECHO
    @FXML
    private void resumen_anual(MouseEvent event) {  //boton resumen anual muestra grafico
        tableView.setVisible(false);
        grafico.setVisible(true);
        border_pane.setCenter(grafico);
        boton_resumenGastos.setDisable(true);
        boton_gastos.setDisable(false);
        
        bPerfil.setDisable(false);

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
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
        
            textArea.setMaxWidth(Double.MAX_VALUE);textArea.setMaxHeight(Double.MAX_VALUE);GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            GridPane expContent = new GridPane();expContent.setMaxWidth(Double.MAX_VALUE);expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
        }

        
        //boton añadir gasto abre ventana, cuando se añada el gasto, esta debe quedarse abierta
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_anadirGasto.fxml"));
            Parent root = loader.load();
        
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
        
            stage.initModality(Modality.APPLICATION_MODAL); //añade modalidad del escenario
            stage.showAndWait();    //espera a que se introduzac al información
            Stage estestage = (Stage) bAyuda.getScene().getWindow();
            estestage.close();
        }
        
        bPerfil.setDisable(false);
        
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
        
        bPerfil.setDisable(false);

    }
    
    public void addCharge(Charge g){
        tableView.getItems().add(g);
    }
    

    @FXML
    private void pulsarMiPerfil(ActionEvent event) throws Exception{
        
        bPerfil.setDisable(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/PerfilUsuario.fxml"));
            Parent userProfilePane = loader.load();
            border_pane.setCenter(userProfilePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boton_gastos.setDisable(false);
        boton_añadirGasto.setDisable(false);
        boton_añadircategoria.setDisable(false);
        boton_resumenGastos.setDisable(false);

    }

    @FXML
    private void comparar(ActionEvent event) throws IOException {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXML_Comparador_gastos.fxml"));
       Parent root = cargarRegistro.load();
       
       Stage stage = new Stage();
       stage.setScene(new Scene(root));
       stage.showAndWait();
    }

    @FXML
    private void cambiarperfil(ActionEvent event) throws IOException, AcountDAOException {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("confirmación");
      alert.setHeaderText("");
      alert.setContentText("¿Seguro que quieres abandonar sesión?");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK){System.out.println("OK");
      Acount.getInstance().logOutUser();
      FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/inicio.fxml"));
       Parent root = cargarRegistro.load();
       Stage stage = new Stage();
       Stage stageinicial = (Stage) bPerfil.getScene().getWindow();
       stage.setScene(new Scene(root));
       stage.show();
       stageinicial.close();
      } else {
     System.out.println("CANCEL");
     }
    }

    @FXML
    private void sailr(ActionEvent event) throws IOException {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("confirmación");
      alert.setHeaderText("");
      alert.setContentText("¿Seguro que quieres salir?");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK){System.out.println("OK");
       Stage stageinicial = (Stage) bPerfil.getScene().getWindow();
       stageinicial.close();
      } else {
     System.out.println("CANCEL");
     }
    }

    @FXML
    private void eliminarCategoria(ActionEvent event) throws IOException,AcountDAOException {
        Category selectedCategory = tableView.getSelectionModel().getSelectedItem().getCategory();
        Charge selectedCharge = tableView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Categoría");
            alert.setHeaderText("¿Estás seguro que quieres eliminar la Categoría");
            alert.setContentText("Se borrarán todos los gastos de esa categoría");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                        boolean success;
                    try {
                        success = Acount.getInstance().removeCategory(selectedCategory);
                        if (success) {
                            listaGastos.remove(selectedCharge);
                        } else {
                            Alert errorAlert = new Alert(AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText("Error al eliminar la categoría");
                            errorAlert.setContentText("No se pudo eliminar la categoría. Inténtalo de nuevo.");
                            errorAlert.showAndWait();
                        }
                    } catch (AcountDAOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }       
                }
            });
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Eliminar Categoría");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un categoría.");
            alert.showAndWait();
        }
        
        
    }

    @FXML
    private void eliminarGasto(ActionEvent event) {
        Charge selectedCategory = tableView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Gasto");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás seguro que quieres eliminar el gasto");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                        boolean success;
                    try {
                        success = Acount.getInstance().removeCharge(selectedCategory);
                        if (success) {
                            listaGastos.remove(selectedCategory);
                        } else {
                            Alert errorAlert = new Alert(AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText("Error al eliminar el gasto");
                            errorAlert.setContentText("No se pudo eliminar el gasto. Inténtalo de nuevo.");
                            errorAlert.showAndWait();
                        }
                    } catch (AcountDAOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }       
                }
            });
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Eliminar Categoría");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un gasto.");
            alert.showAndWait();
        }
    }

    @FXML
    private void modificarCategoria(ActionEvent event) {
    }

    @FXML
    private void modificarGasto(ActionEvent event) throws IOException, AcountDAOException {
        

        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_anadirGasto.fxml"));
        Parent root = loader.load();
        
        // Obtener el controlador asociado
        FXML_anadirGastoController controlerGasto = loader.getController();
        controlerGasto.editGasto(tableView.getSelectionModel().getSelectedItem());
        Stage newStage = new Stage();
        
        // Crear una nueva ventana
        
        newStage.setTitle("Modificar Gasto");
        newStage.setScene(new Scene(root));
        controlerGasto.setStage(newStage);
        newStage.show();
        
        
    }
    
    class ImagenTabCell extends TableCell<Charge, Image> {
            private ImageView view = new ImageView();
            @Override
            protected void updateItem(Image t, boolean bln) {
                super.updateItem(t, bln);
                if (t == null || bln) {
                    setText(null);
                    setGraphic(null);
                } else {
                     try {
                // Puedes ajustar el tamaño de la imagen aquí según tus necesidades
                view.setFitWidth(40);
                view.setFitHeight(40);
                view.setImage(t);
                setGraphic(view);
            } catch (Exception e) {
                // Maneja cualquier excepción que ocurra al cargar la imagen
                System.err.println("Error al cargar la imagen: " + e.getMessage());
                setText("Error");
                setGraphic(null);
            }
            }
    }   
   }   
}
    
    