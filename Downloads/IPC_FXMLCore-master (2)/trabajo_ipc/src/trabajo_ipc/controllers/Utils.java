/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author Belén Rodríguez
 */
public class Utils {
    
    public void sendEmail(String to, String username, String tituloEmail, String emailMessage) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");  // El servidor SMTP de Google
        props.put("mail.smtp.auth", "true");    // Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

        // Crear una sesión con las propiedades
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ipctrabajo0@gmail.com", "psnoprdbhidxvung");
            }
        });

        try {
            // Crear un mensaje MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ipctrabajo0@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(tituloEmail);
            message.setText(emailMessage);

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado exitosamente");

        } catch (MessagingException me) {
            me.printStackTrace();   // Si se produce un error
        }
    }
}
