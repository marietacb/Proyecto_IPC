/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Acount;
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
    private TextField nickname;
    @FXML
    private Button guardar;
    @FXML
    private Button cancelar;

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
            nickname.setText(Acount.getInstance().getLoggedUser().getNickName());
            fotoImage.setImage(Acount.getInstance().getLoggedUser().getImage());
            
            guardar.setDisable(true);
            cancelar.setDisable(true);
            
        }
        catch(Exception e){}
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
        
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }

    @FXML
    private void pulsarGuardar(ActionEvent event) {
        
    }

    @FXML
    private void pulsarCancelar(ActionEvent event) {
        
    }

    @FXML
    private void escribirNombre(KeyEvent event) {
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }

    @FXML
    private void escribirApellidos(KeyEvent event) {
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }

    @FXML
    private void escribirEmail(KeyEvent event) {
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }

    @FXML
    private void escribirNickName(KeyEvent event) {
        guardar.setDisable(false);
        cancelar.setDisable(false);
    }
    
    
}
