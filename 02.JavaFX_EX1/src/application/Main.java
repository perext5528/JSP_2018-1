package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox root = new VBox();
		root.setPrefSize(400, 150);
		root.setAlignment(Pos.CENTER);
		
		Button btn = new Button("����");
		btn.setPrefWidth(80);
		btn.setOnAction(event->Platform.exit());	//�̺�Ʈ �߻� �� �������α׷� ����
		root.getChildren().add(btn);
		Scene scene = new Scene(root);
		
		//��� ����
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//FXML ����
		Parent root1 = FXMLLoader.load(getClass().getResource("FXMLTest1.fxml"));
		Scene scene1 = new Scene(root1);
		primaryStage.setTitle("JavaFxTest");
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
