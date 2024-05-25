package trabajo_ipc.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private Button añadir_boton;
    @FXML
    private BorderPane fondocomparador;
    @FXML
    private ComboBox<String> boxmes1;
    @FXML
    private ComboBox<String> boxmes2;
    private int numeroMes1;
    private int numeroMes2;
    @FXML
    private TextField boton_año1;
    @FXML
    private TextField boton_año2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String css = this.getClass().getResource("/resources/css/perfilusuario.css").toExternalForm();
        fondocomparador.getStylesheets().add(css);
        
        boxmes1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            numeroMes1 = boxmes1.getItems().indexOf(newValue) + 1;   //en que pos del combobox se encuentra y sumarle 1 (empieza en 0)
            }
        });
        
        boxmes2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            numeroMes2 = boxmes2.getItems().indexOf(newValue) + 1;   //en que pos del combobox se encuentra y sumarle 1 (empieza en 0)
            }
        });
        
        boxmes1.getItems().addAll(  //añade meses al combo box 1(son strings por ahora)
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        boxmes2.getItems().addAll( //añade meses al combo box 2(son strings por ahora)
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        
    }    

    @FXML
    private void añadir_gasto(ActionEvent event) throws IOException {
        try {
            
            int year1 = Integer.parseInt(boton_año1.getText());
            int year2 = Integer.parseInt(boton_año2.getText());
            int month1 = numeroMes1;
            int month2 = numeroMes2;

            // Obtiene todos los gastos del usuario
            List<Charge> allCharges = Acount.getInstance().getUserCharges();

            // Filtra los gastos por el mes y año especificados
            List<Charge> filteredCharges1 = allCharges.stream()
                    .filter(charge -> charge.getDate().getYear() == year1 && charge.getDate().getMonthValue() == month1)
                    .collect(Collectors.toList());
            
            List<Charge> filteredCharges2 = allCharges.stream()
                    .filter(charge -> charge.getDate().getYear() == year2 && charge.getDate().getMonthValue() == month2)
                    .collect(Collectors.toList());

            // Suma los costos de los gastos filtrados
            double totalCost1 = filteredCharges1.stream()
                    .mapToDouble(Charge::getCost)
                    .sum();
            
            double totalCost2 = filteredCharges2.stream()
                    .mapToDouble(Charge::getCost)
                    .sum();

            // Añadir los datos al gráfico de barras
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            series1.setName(month1 + "/" + year1);
            series1.getData().add(new XYChart.Data<>(month1 + "/" + year1, totalCost1));
            grafico_barras.getData().add(series1);
            
            XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            series2.setName(month2 + "/" + year2);
            series2.getData().add(new XYChart.Data<>(month2 + "/" + year2, totalCost2));
            grafico_barras.getData().add(series2);

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