/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAO;
import model.AcountDAOException;


/**
 * FXML Controller class
 *
 * @author siren
 */
public class FXML_inicioController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox myHBox;
    @FXML
    private MenuBar miMenu;
    @FXML
    private VBox miVbox;
    @FXML
    private TextField nickField;
  
    @FXML
    private Button botonAceptar;
    @FXML
    private Button botonRegistrarse;
    @FXML
    private PasswordField passwordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void aceptar(MouseEvent event) throws AcountDAOException, IOException {
        //obtenemos el usuario
        String usuario = nickField.getText();
        //obtenemos contraseña
        String contraseña = passwordField.getText();
        boolean aceptar = Acount.getInstance().logInUserByCredentials(usuario, contraseña);
        if (aceptar) {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXMLDocument.fxml"));
       Parent root = cargarRegistro.load();
       
       Stage stage = new Stage();
       Stage stageinicial = (Stage) botonRegistrarse.getScene().getWindow();
       stage.setScene(new Scene(root));
       stage.show();
       stageinicial.close();
        
        
        }
    }

    @FXML
    private void registrarse(MouseEvent event) throws IOException {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXML_registro.fxml"));
       Parent root = cargarRegistro.load();
       
       Stage stage = new Stage();
       Stage stageinicial = (Stage) botonRegistrarse.getScene().getWindow();
       stage.setScene(new Scene(root));
       stage.show();
       stageinicial.close();

    }
    
}
