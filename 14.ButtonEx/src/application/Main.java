package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = new HBox();
			root.setAlignment(Pos.CENTER);

			Button btn1 = new Button();
			btn1.setText("Hello");
			btn1.setOnAction(event -> {
				if (btn1.getText().equals("Hello"))
					btn1.setText("Accept");
				else
					btn1.setText("Hello");
			});

			Button btn2 = new Button("Accept");
			DropShadow shadow = new DropShadow();
			btn2.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btn2.setEffect(shadow));
			btn2.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btn2.setEffect(null));
			HBox.setMargin(btn2, new Insets(10));

			root.getChildren().addAll(btn1, btn2);

			Scene scene = new Scene(root, 400, 400);

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
