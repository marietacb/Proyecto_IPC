 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;



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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert.AlertType;
import javax.imageio.ImageIO;

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
    public TableView<Charge> tableView;
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
    private Button bAyuda;
    @FXML
    private HBox bordeSuperior;
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
    @FXML
    private TableColumn<Charge, String> descripcion;
    @FXML
    private MenuItem miPerfil;
    @FXML
    private ImageView imagenPerf;
    @FXML
    private MenuButton botonCrear;
    @FXML
    private Button imprimir_boton;
    private Stage EscenarioPrincipal;
    @FXML
    private MenuItem mPerfil;
    @FXML
    private ImageView boton_ayuda;
    
    /**
     * Initializes the controller class.
     * @throws model.AcountDAOException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        String css = this.getClass().getResource("/resources/css/document.css").toExternalForm();
        border_pane.getStylesheets().add(css);
        
        actualizarGastos();

        nombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("description"));
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
        try {
            iniciarGrafico();
        } catch (AcountDAOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        border_pane.setCenter(grafico);
        boton_resumenGastos.setDisable(true);
       
        try{
        imagenPerf.setImage(Acount.getInstance().getLoggedUser().getImage());
        }
        catch(Exception e){}
        
                //inicializar gridpane

    }

    private void iniciarGrafico() throws AcountDAOException, IOException {
        grafico = new PieChart();
        grafico.setVisible(true);   
            Acount acount = Acount.getInstance();
            List<Charge> userCharges = acount.getUserCharges();

            LocalDate now = LocalDate.now();
            Map<String, Double> categoriaGastos = new HashMap<>();

        for (Charge charge : userCharges) {
            if (charge.getDate().getMonth() == now.getMonth() && charge.getDate().getYear() == now.getYear()) {
                String categoriaNombre = charge.getCategory().getName();
                categoriaGastos.put(categoriaNombre, categoriaGastos.getOrDefault(categoriaNombre, 0.0) + charge.getCost());
            }
        }

        for (Map.Entry<String, Double> entry : categoriaGastos.entrySet()) {
                PieChart.Data slice = new PieChart.Data(entry.getKey() + " €" + String.format("%.2f", entry.getValue()), entry.getValue());
                slice.pieValueProperty().setValue(entry.getValue());
                grafico.getData().add(slice);
        }

        grafico.setTitle("Gastos del Mes Actual");

         border_pane.setCenter(grafico);
        }
         
    
    public void actualizarGastos(){
        try {
                listaGastos = FXCollections.observableArrayList(Acount.getInstance().getUserCharges());
            } catch (AcountDAOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        tableView.setItems(listaGastos);
        
         try {
            iniciarGrafico();
        } catch (AcountDAOException | IOException e) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
        }
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
        comparar_boton.setDisable(false);
    }

    //HECHO
    @FXML
    private void resumen_anual(MouseEvent event) {  //boton resumen anual muestra grafico
        tableView.setVisible(false);
        grafico.setVisible(true);
        border_pane.setCenter(grafico);
        boton_resumenGastos.setDisable(true);
        boton_gastos.setDisable(false);
        comparar_boton.setDisable(false);
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

        // Obtener el controlador del archivo FXML cargado
        FXML_anadirGastoController añadirGastoController = loader.getController();

        // Pasar la instancia actual de FXMLDocumentController a AñadirGastoController
            añadirGastoController.setMainController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Añade modalidad del escenario
            stage.show(); // Espera a que se introduzca la información
        }
    }

    @FXML
    private void añadircategoria(ActionEvent event) throws IOException { //abre la ventana de añadir categoria
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_añadirCategoria.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de añadir/editar categoría
        FXML_añadirCategoriaController controller = loader.getController();
        controller.setMainController(this); // Pasar referencia del controlador principal

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        controller.setStage(stage); // Configurar el stage en el controlador de añadir/editar categoría

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    public void addCharge(Charge g){
        tableView.getItems().add(g);
    }
    

    @FXML
    private void comparar(ActionEvent event) throws IOException {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/Comparador.fxml"));
       Parent root = cargarRegistro.load();
       border_pane.setCenter(root);
       
       comparar_boton.setDisable(true);
       botonCrear.setDisable(false);
       boton_resumenGastos.setDisable(false);
       boton_gastos.setDisable(false);
      
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
       Stage stageinicial = (Stage) bSalir.getScene().getWindow();
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
      Stage stageinicial = (Stage) bSalir.getScene().getWindow();
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
    private void modificarCategoria(ActionEvent event) throws IOException, AcountDAOException {
        if(tableView.getSelectionModel().getSelectedItem() != null)   {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_añadirCategoria.fxml"));
        Parent root = loader.load();
        
        // Obtener el controlador asociado
        FXML_añadirCategoriaController controlerCategoria = loader.getController();
        controlerCategoria.editCategoria(tableView.getSelectionModel().getSelectedItem().getCategory());
        controlerCategoria.setMainController(this);
        Stage newStage = new Stage();
        
        // Crear una nueva ventana
        newStage.setTitle("Modificar Categoría");
        newStage.setScene(new Scene(root));
        controlerCategoria.setStage(newStage);
        newStage.show();
        
    }else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error modificar");
          alert.setHeaderText(null);
          alert.setContentText("Selecciona Primero un Gasto");
          alert.showAndWait();
    
    
    }
    }

    @FXML
    private void modificarGasto(ActionEvent event) throws IOException, AcountDAOException {
        Charge gasto = tableView.getSelectionModel().getSelectedItem();
        
        if(gasto != null)   { 
        Category cat = gasto.getCategory();
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_anadirGasto.fxml"));
        Parent root = loader.load();
        
        // Obtener el controlador asociado
        FXML_anadirGastoController controlerGasto = loader.getController();
        controlerGasto.editCategoria(cat);
        controlerGasto.editGasto(tableView.getSelectionModel().getSelectedIndex());
        controlerGasto.setMainController(this);
        Stage newStage = new Stage();
        
        // Crear una nueva ventana
        
        newStage.setTitle("Modificar Gasto");
        newStage.setScene(new Scene(root));
        controlerGasto.setStage(newStage);
        newStage.show();
        
    }else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error modificar");
          alert.setHeaderText(null);
          alert.setContentText("Selecciona Primero un Gasto");
          alert.showAndWait();
    
    
    }
        
        
    }



    @FXML
    private void pulsarMiperfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/PerfilUsuario.fxml"));
        Parent userProfilePane = loader.load();
        border_pane.setCenter(userProfilePane);
        
        boton_gastos.setDisable(false);
        boton_añadirGasto.setDisable(false);
        boton_añadircategoria.setDisable(false);
        boton_resumenGastos.setDisable(false);
    }

    
    @FXML
    private void imprimir(ActionEvent event) throws AcountDAOException {
        EscenarioPrincipal = (Stage) boton_gastos.getScene().getWindow();
        // El usuario elige donde guardará el PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar el PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(EscenarioPrincipal);
        BaseColor colorVerde = new BaseColor(66, 70, 50);
        if (file != null) {
            try {
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                
                 // Añadir el título
                Font titleFont = FontFactory.getFont("Arial", 18, Font.BOLD, colorVerde);
                Paragraph title = new Paragraph("Gastos Totales", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add( new Paragraph("\n") );


                // Crear una tabla en el PDF con el mismo número de columnas
                PdfPTable table = new PdfPTable(7);

                // Fuente para los encabezados
                Font headerFont = FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.BLACK);
                BaseColor colorFondo = new BaseColor(189, 236, 182);

                // Añadir encabezados de columnas
                PdfPCell header;

                header = new PdfPCell(new Phrase("Categoría", headerFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setBackgroundColor(colorFondo);
                table.addCell(header);

                header = new PdfPCell(new Phrase("Nombre", headerFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setBackgroundColor(colorFondo);
                table.addCell(header);

                header = new PdfPCell(new Phrase("Fecha", headerFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setBackgroundColor(colorFondo);
                table.addCell(header);

                header = new PdfPCell(new Phrase("Unidades", headerFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setBackgroundColor(colorFondo);
                table.addCell(header);

                header = new PdfPCell(new Phrase("Precio", headerFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setBackgroundColor(colorFondo);
                table.addCell(header);

                header = new PdfPCell(new Phrase("Descripción", headerFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setBackgroundColor(colorFondo);
                table.addCell(header);

                header = new PdfPCell(new Phrase("Ticket", headerFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setBackgroundColor(colorFondo);
                table.addCell(header);
                
                //declaraciones que necesitamos posteriormente
                DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Font fuente = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.NORMAL, BaseColor.BLACK);


                // Añadir datos de las filas
                listaGastos = FXCollections.observableList(Acount.getInstance().getUserCharges());
                for (int i = 0; i < listaGastos.size(); i++) {
                    Charge charge = listaGastos.get(i);
                    Image imagenCelda = charge.getImageScan();

                // Añadir los datos a las celdas de la tabla y decorarlas
                PdfPCell celda1 = new PdfPCell();
                celda1.setFixedHeight(30);
                celda1.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda1.setBorderColor(colorVerde);
                celda1.setPhrase(new Phrase(charge.getCategory().getName(), fuente));
                
                PdfPCell celda2 = new PdfPCell();
                celda2.setFixedHeight(30);
                celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda2.setBorderColor(colorVerde);
                celda2.setPhrase(new Phrase(charge.getName(), fuente));
                
                PdfPCell celda3 = new PdfPCell();
                celda3.setFixedHeight(30);
                celda3.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda3.setBorderColor(colorVerde);
                celda3.setPhrase(new Phrase(charge.getDate().format(formatoDia), fuente));
                
                PdfPCell celda4 = new PdfPCell();
                celda4.setFixedHeight(30);
                celda4.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda4.setBorderColor(colorVerde);
                celda4.setPhrase(new Phrase(String.valueOf(charge.getUnits()), fuente));
                
                PdfPCell celda5 = new PdfPCell();
                String coste = (charge.getCost() + " €");
                celda5.setFixedHeight(30);
                celda5.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda5.setBorderColor(colorVerde);
                celda5.setPhrase(new Phrase(coste, fuente));
                
                PdfPCell celda6 = new PdfPCell();
                celda6.setFixedHeight(30);
                celda6.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda6.setBorderColor(colorVerde);
                celda6.setPhrase(new Phrase(charge.getDescription(), fuente));
                
                PdfPCell celda7 = new PdfPCell();
                celda7.setFixedHeight(30);
                celda7.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda7.setBorderColor(colorVerde);
                if (imagenCelda != null) {
                    try {
                        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imagenCelda, null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "png", baos);
                        baos.flush();
                        com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(baos.toByteArray());
                        img.scaleToFit(20, 20); // Ajusta el tamaño de la imagen
                        celda7.addElement(img);
                        baos.close();
                    } catch (IOException e) {
                        celda7.setPhrase(new Phrase("Imagen no disponible", fuente));
                    }
                } else {
                    celda7.setPhrase(new Phrase("No hay imagen", fuente));
                }

             //añadimos cada una de las celdas a la tabla   
                
                table.addCell(celda1);
                table.addCell(celda2);
                table.addCell(celda3);
                table.addCell(celda4);
                table.addCell(celda5);
                table.addCell(celda6);
                table.addCell(celda7);
                        
                }

                document.add(table);
                document.close();

                // Mostrar un mensaje de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF guardado");
                alert.setHeaderText(null);
                alert.setContentText("El PDF se ha guardado correctamente.");
                alert.showAndWait();

            } catch (IOException | DocumentException e) {
                // Mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al guardar PDF");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo guardar el PDF: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void modificarPerfil(ActionEvent event) throws IOException {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/FXML_registro.fxml"));
        Parent root = loader.load();
        
        // Obtener el controlador asociado
        FXML_registroController controlerRegistro = loader.getController();
        controlerRegistro.editRegister();
        Stage newStage = new Stage();
        
        // Crear una nueva ventana
        newStage.setTitle("Modificar Perfil");
        newStage.setScene(new Scene(root));
        controlerRegistro.setStage(newStage);
        Stage stageinicial = (Stage) bAyuda.getScene().getWindow();
        newStage.show();
        stageinicial.close();
        
    }

    @FXML
    private void ayudar(ActionEvent event) {
        // Crear el contenido expandible con el mensaje de ayuda
        GridPane helpContent = new GridPane();
        Label helpLabel = new Label("Ayuda:");
        TextArea helpTextArea = new TextArea("Para visualizar todos los gastos realizados debe hacer click en el botón mis gastos. \nPara imprimir todos sus gastos, presione la impresora al lado de la tabla. \nPara más información, acuda al correo: ipctrabajo0@gmail.com");
        helpTextArea.setEditable(false);
        helpTextArea.setWrapText(true);
        helpTextArea.setMaxWidth(Double.MAX_VALUE);
        helpTextArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(helpTextArea, Priority.ALWAYS);
        GridPane.setHgrow(helpTextArea, Priority.ALWAYS);
        helpContent.add(helpLabel, 0, 0);
        helpContent.add(helpTextArea, 0, 1);

        // Crear el diálogo de información
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ayuda");
        alert.setHeaderText(null);
        alert.setContentText("Para obtener ayuda, expanda esta sección.");

        // Establecer el contenido expandible
        alert.getDialogPane().setExpandableContent(helpContent);

        // Mostrar el diálogo
        alert.showAndWait();
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
    
    