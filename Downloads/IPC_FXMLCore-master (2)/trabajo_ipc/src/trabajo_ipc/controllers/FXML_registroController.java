/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;

/**
 * FXML Controller class
 *
 * @author Dynabook
 */
public class FXML_registroController implements Initializable {

    @FXML
    private TextField field_name;
    @FXML
    private TextField field_nickname;
    @FXML
    private PasswordField field_password;
    @FXML
    private PasswordField field_repassword;
    @FXML
    private TextField field_mail;
    @FXML
    private ImageView image_perfil;
    @FXML
    private Button button_login;
    @FXML
    private Button button_cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void introducir_mail(ActionEvent event) {
    }

    @FXML
    private void pulsar_login(ActionEvent event) {
    }

    @FXML
    private void pulsar_cancelar(ActionEvent event) {
    }

    @FXML
    private void introducir_name(InputMethodEvent event) {
    }

    @FXML
    private void introducir_nickname(InputMethodEvent event) {
    }

    @FXML
    private void introducir_password(InputMethodEvent event) {
    }

    @FXML
    private void introducir_repassword(InputMethodEvent event) {
    }

    @FXML
    private void introducir_mail(InputMethodEvent event) {
    }
    
}