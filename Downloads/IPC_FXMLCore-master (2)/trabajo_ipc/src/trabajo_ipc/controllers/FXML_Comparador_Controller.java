/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajo_ipc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author siren
 */
public class FXML_Comparador_Controller {

    @FXML
    private BorderPane border_pane;
    @FXML
    private MenuItem boton_añadirGasto;
    @FXML
    private MenuItem boton_añadircategoria;
    @FXML
    private Button boton_resumenGastos;
    @FXML
    private Button boton_gastos;
    @FXML
    private BarChart<?, ?> gracigo_barras;
    @FXML
    private TextField año_actual;
    @FXML
    private TextField mes_actual;
    @FXML
    private Button comparar_actual;
    @FXML
    private TextField mes_otro;
    @FXML
    private Button comparar_otro;

    @FXML
    private void añadirGasto(ActionEvent event) {
    }

    @FXML
    private void añadircategoria(ActionEvent event) {
    }

    @FXML
    private void resumen_anual(MouseEvent event) {
    }

    @FXML
    private void pulsarGastos(MouseEvent event) {
    }

    @FXML
    private void comparando_actual(ActionEvent event) {
    }

    @FXML
    private void comparando_otro(ActionEvent event) {
    }
    
}
