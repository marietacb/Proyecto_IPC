package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Acount;
import model.AcountDAOException;
import model.Charge;

/**
 * FXML Controller class
 *
 */
public class ComparadorController implements Initializable {

    @FXML
    private BarChart<String, Number> grafico_barras;
    @FXML
    private TextField boton_año;
    @FXML
    private TextField boton_mes;
    @FXML
    private Button añadir_boton;
    @FXML
    private BorderPane fondocomparador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String css = this.getClass().getResource("/resources/css/perfilusuario.css").toExternalForm();
        fondocomparador.getStylesheets().add(css);
    }    

    @FXML
    private void añadir_gasto(ActionEvent event) throws IOException {
        try {
            int year = Integer.parseInt(boton_año.getText());
            int month = Integer.parseInt(boton_mes.getText());

            // Obtiene todos los gastos del usuario
            List<Charge> allCharges = Acount.getInstance().getUserCharges();

            // Filtra los gastos por el mes y año especificados
            List<Charge> filteredCharges = allCharges.stream()
                    .filter(charge -> charge.getDate().getYear() == year && charge.getDate().getMonthValue() == month)
                    .collect(Collectors.toList());

            // Suma los costos de los gastos filtrados
            double totalCost = filteredCharges.stream()
                    .mapToDouble(Charge::getCost)
                    .sum();

            // Añadir los datos al gráfico de barras
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(month + "/" + year);
            series.getData().add(new XYChart.Data<>(month + "/" + year, totalCost));
            grafico_barras.getData().add(series);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de formato");
            alert.setHeaderText("Formato de fecha inválido");
            alert.setContentText("Por favor, introduce un mes y un año válidos.");
            alert.showAndWait();
        } catch (AcountDAOException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de carga");
            alert.setHeaderText("Error al cargar los datos");
            alert.setContentText("No se pudo cargar los gastos del usuario.");
            alert.showAndWait();
        }
    }
}