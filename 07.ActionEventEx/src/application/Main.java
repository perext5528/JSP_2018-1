package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FlowPane root = new FlowPane();
			root.setPrefSize(250, 120);
			root.setAlignment(Pos.CENTER);

			Button btn = new Button("Action");
			btn.setPrefWidth(80);
			btn.setOnAction(event -> {
				if (btn.getText().equals("Action"))
					btn.setText("¾×¼Ç");
				else
					btn.setText("Action");
			});
			root.getChildren().add(btn);

			primaryStage.setTitle("ActionEventEx");
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
