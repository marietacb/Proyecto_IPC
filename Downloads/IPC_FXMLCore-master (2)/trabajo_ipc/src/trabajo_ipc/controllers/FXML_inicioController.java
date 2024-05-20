/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAO;
import model.AcountDAOException;
import java.io.FileNotFoundException;


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
 
        //si el booleano está en true, abre nuestro perfil.
        if (aceptar) {
       FXMLLoader cargarRegistro= new FXMLLoader(getClass().getResource("/resources/fxml/FXMLDocument.fxml"));
       Parent root = cargarRegistro.load();

       Stage stage = new Stage();
       Stage stageinicial = (Stage) botonRegistrarse.getScene().getWindow();
       stage.setScene(new Scene(root));
       stage.show();
       stageinicial.close();
        } else {
            
        // si no es correcto lanza un mensaje
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al iniciar sesión");
        //distinguimos entre error de contraseña o de usuario no registrado
        if(Acount.getInstance().existsLogin(usuario) ){
        alert.setContentText("La contraseña es incorrecta");
        }else{
        alert.setContentText("El usuario no se encuentra registrado");
        }
        //continuamos definiendo el mensaje
        Exception excepción = new Exception("Detalles del error");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        excepción.printStackTrace(pw);
        String exceptionText = sw.toString();
        Label label = new Label("Excepción:");
        TextArea textArea =
        new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        textArea.setMaxWidth(Double.MAX_VALUE);textArea.setMaxHeight(Double.MAX_VALUE);GridPane.setVgrow(textArea,Priority.ALWAYS);
        GridPane.setHgrow(textArea,Priority.ALWAYS);
        GridPane expContent = new GridPane();expContent.setMaxWidth(Double.MAX_VALUE);expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);alert.showAndWait();
        
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
