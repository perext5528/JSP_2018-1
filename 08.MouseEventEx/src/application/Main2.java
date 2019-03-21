package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

//MouseEvent ���� ����
public class Main2 extends Application {
	Label label = new Label("Hello");

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new AnchorPane();
			root.setStyle("-fx-background-color:Yellow");
			
			label.setLayoutX(50);
			label.setLayoutY(50);
			root.getChildren().add(label);

			Scene scene = new Scene(root, 400, 400);
			
			//EventHandler�� ���� �ۼ�
			EventHandler<MouseEvent> mouseClickHandler = new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					label.setLayoutX(e.getX());
					label.setLayoutY(e.getY());
				}
			};
			
			//�ۼ��� EvnetHandler�� ��ü�� �߰�
			scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickHandler);
			
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
