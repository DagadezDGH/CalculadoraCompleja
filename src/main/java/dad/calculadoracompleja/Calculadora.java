package dad.calculadoracompleja;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Calculadora extends Application {
	Complejos complejo1 = new Complejos();
	Complejos complejo2 = new Complejos();
	Complejos complejoRes = new Complejos();

	private ComboBox<String> operacionCombo;
	private TextField num11Text, num12Text, num21Text, num22Text, num31Text, num32Text;
	private Label num11Label, num12Label, num21Label, num22Label, num31Label, num32Label;
	private Separator separador;

	public void start(Stage primaryStage) throws Exception {

		HBox imaginariobox1 = new HBox();

		num11Text = new TextField("0");
		num11Text.setPrefColumnCount(3);
		num11Text.setAlignment(Pos.CENTER);
		num11Label = new Label("+");
		num12Text = new TextField("0");
		num12Text.setPrefColumnCount(3);
		num12Text.setAlignment(Pos.CENTER);
		num12Label = new Label("i");

		imaginariobox1.getChildren().addAll(num11Text, num11Label, num12Text, num12Label);
		imaginariobox1.setSpacing(5);
		imaginariobox1.setAlignment(Pos.CENTER);

		
		HBox imaginariobox2 = new HBox();

		num21Text = new TextField("0");
		num21Text.setPrefColumnCount(3);
		num21Text.setAlignment(Pos.CENTER);
		num21Label = new Label("+");
		num22Text = new TextField("0");
		num22Text.setPrefColumnCount(3);
		num22Text.setAlignment(Pos.CENTER);
		num22Label = new Label("i");

		imaginariobox2.getChildren().addAll(num21Text, num21Label, num22Text, num22Label);
		imaginariobox2.setAlignment(Pos.CENTER);
		imaginariobox2.setSpacing(5);

		
		separador = new Separator();

		
		HBox resultadoBox = new HBox(5);

		num31Text = new TextField("0");
		num31Text.setPrefColumnCount(3);
		num31Text.setDisable(true);
		num31Text.setAlignment(Pos.CENTER);
		num31Label = new Label("+");
		num32Text = new TextField("0");
		num32Text.setPrefColumnCount(3);
		num32Text.setDisable(true);
		num32Text.setAlignment(Pos.CENTER);
		num32Label = new Label("i");

		resultadoBox.getChildren().addAll(num31Text, num31Label, num32Text, num32Label);
		resultadoBox.setSpacing(5);
		resultadoBox.setAlignment(Pos.CENTER);


		VBox operacionBox = new VBox(5);
		operacionBox.getChildren().addAll(imaginariobox1, imaginariobox2, separador, resultadoBox);
		operacionBox.setAlignment(Pos.CENTER);

		
		operacionCombo = new ComboBox<>();
		operacionCombo.getItems().addAll("+", "-", "*", "/");
		operacionCombo.getSelectionModel().select(0);

		
		VBox seleccionBox = new VBox(5);
		seleccionBox.getChildren().add(operacionCombo);
		seleccionBox.setAlignment(Pos.CENTER);

		
		HBox cajaPrincipalBox = new HBox(5);
		cajaPrincipalBox.getChildren().addAll(seleccionBox, operacionBox);
		cajaPrincipalBox.setAlignment(Pos.CENTER);

		//Escena
		Scene escena = new Scene(cajaPrincipalBox, 320, 200);
		primaryStage.setScene(escena);
		primaryStage.setTitle("Calculadora de numeros Complejos");
		primaryStage.show();
		
		//Bindeos
		num11Text.textProperty().bindBidirectional(complejo1.realProperty(), new NumberStringConverter());
		num12Text.textProperty().bindBidirectional(complejo1.imaginarioProperty(), new NumberStringConverter());


		num21Text.textProperty().bindBidirectional(complejo2.realProperty(), new NumberStringConverter());
		num22Text.textProperty().bindBidirectional(complejo2.imaginarioProperty(), new NumberStringConverter());
		
		
		num31Text.textProperty().bind(complejoRes.realProperty().asString());
		num32Text.textProperty().bind(complejoRes.imaginarioProperty().asString());

		//Operaciones sin usar el boton igual
		operacionCombo.valueProperty().addListener(e -> {
			
			if (operacionCombo.valueProperty().getValue() == "+") {
				complejoRes.realProperty().bind(complejo1.realProperty().add(complejo2.realProperty()));
				complejoRes.imaginarioProperty()
						.bind(complejo1.imaginarioProperty().add(complejo2.imaginarioProperty()));
			}
			
			else if (operacionCombo.valueProperty().getValue() == "-") {
				complejoRes.realProperty().bind(complejo1.realProperty().subtract(complejo2.realProperty()));

				complejoRes.imaginarioProperty()
						.bind(complejo1.imaginarioProperty().subtract(complejo2.imaginarioProperty()));
			}
			
			else if (operacionCombo.valueProperty().getValue() == "*") {
				complejoRes.realProperty().bind(complejo1.realProperty().multiply(complejo2.realProperty())
						.subtract(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty())));

				complejoRes.imaginarioProperty().bind(complejo1.realProperty().multiply(complejo2.imaginarioProperty())
						.add(complejo2.realProperty().multiply(complejo1.imaginarioProperty())));
			}
			
			else if (operacionCombo.valueProperty().getValue() == "/") {

				complejoRes.realProperty()
						.bind(((complejo1.realProperty().multiply(complejo2.realProperty()))
								.add(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty())))
										.divide(complejo2.realProperty().multiply(complejo2.realProperty()
												.add(complejo2.realProperty().multiply(complejo2.realProperty())))));

				complejoRes.imaginarioProperty()
						.bind(((complejo2.realProperty().multiply(complejo1.imaginarioProperty()))
								.subtract(complejo1.realProperty().multiply(complejo2.imaginarioProperty())))
										.divide(complejo2.realProperty().multiply(complejo2.realProperty()
												.add(complejo2.realProperty().multiply(complejo2.realProperty())))));
			}
		});

	}
	

	public static void main(String[] args) {
		launch(args);

	}

}
