package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Image File");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
					new FileChooser.ExtensionFilter("Jpeg Image", "*.jpg"),
					new FileChooser.ExtensionFilter("Png Image", "*.png"),
					new FileChooser.ExtensionFilter("Gif Image", "*.gif"));
			MenuBar menuBar = new MenuBar();
			Menu fileMenu = new Menu("File");
			MenuItem openItem = new MenuItem("Open");
			openItem.setOnAction(event -> {
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						Image img = new Image(new FileInputStream(file));
						ImageView imgView = new ImageView(img);
						imgView.fitWidthProperty().bind(primaryStage.widthProperty());
						imgView.setPreserveRatio(true);
						root.setCenter(new ScrollPane(imgView));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
			
			MenuItem exitItem = new MenuItem("Exit");
			exitItem.setOnAction(event->{System.exit(0);});
			fileMenu.getItems().addAll(openItem, exitItem);
			menuBar.getMenus().addAll(fileMenu);
			root.setTop(menuBar);
			
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
