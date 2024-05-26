/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Acount;
import model.AcountDAO;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */
public class PerfilUsuarioController implements Initializable {

    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidosText;
    @FXML
    private TextField emailText;
    @FXML
    private ImageView fotoImage;
    @FXML
    private Label nickname;
    @FXML
    private Button cancelar;
    @FXML
    private Label errorNombre;
    @FXML
    private Label errorApellidos;
    @FXML
    private Label errorEmail;
    @FXML
    private Label titulo;
    @FXML
    private VBox vbox;
    @FXML
    private Label fotolabel;
    @FXML
    private TextField textNick;
    @FXML
    private Label apell;
    @FXML
    private Label ema;
    @FXML
    private Label nom;
    @FXML
    private BorderPane borderpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            nombreText.setText(Acount.getInstance().getLoggedUser().getName());
            apellidosText.setText(Acount.getInstance().getLoggedUser().getSurname());
            emailText.setText(Acount.getInstance().getLoggedUser().getEmail());
            textNick.setText(Acount.getInstance().getLoggedUser().getNickName());
            fotoImage.setImage(Acount.getInstance().getLoggedUser().getImage());
            
            
        }
        catch(Exception e){}
        
        String css = this.getClass().getResource("/resources/css/perfilusuario.css").toExternalForm();
        borderpane.getStylesheets().add(css);
    }    

    @FXML
    private void cambiarFoto(MouseEvent event) {
        FileChooser ficheroSel = new FileChooser();    //seleccionador de archivos
        ficheroSel.setTitle("Abrir imagen");   
        ficheroSel.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg")); //formate de imagen
        
        File seleccionado = ficheroSel.showOpenDialog( 
        ((Node)event.getSource()).getScene().getWindow());
        if (ficheroSel != null) {
            Image imagen = new Image(seleccionado.toURI().toString());
            fotoImage.setImage(imagen);
        } 
    }
    

    @FXML
    private void pulsarCancelar(ActionEvent event) throws Exception{      
        nombreText.setText(Acount.getInstance().getLoggedUser().getName());
        apellidosText.setText(Acount.getInstance().getLoggedUser().getSurname());
        emailText.setText(Acount.getInstance().getLoggedUser().getEmail());
        fotoImage.setImage(Acount.getInstance().getLoggedUser().getImage());

        cancelar.setDisable(true);
    }


    
}
