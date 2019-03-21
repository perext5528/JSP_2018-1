package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

//MouseEvent ��ȸ�� ���
public class Main extends Application {
	Label label = new Label("Hello");

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new AnchorPane();
			root.setStyle("-fx-background-color:Yellow");
			//JavaFX���� CSS�� ����ϴ� ��� -fx-�� �׻� �����ؾ� ��

			label.setLayoutX(50);
			label.setLayoutY(50);
			root.getChildren().add(label);

			Scene scene = new Scene(root, 400, 400);
			scene.setOnMouseClicked(event -> {
				label.setLayoutX(event.getX());
				label.setLayoutY(event.getY());
			});
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
