package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			MenuBar mb = new MenuBar();
			Menu file = new Menu("_File");
			Menu edit = new Menu("_Edit");
			Menu project = new Menu("_Project");
			Menu help = new Menu("_Help");
			
			MenuItem newItem = new MenuItem("_New");
			newItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));
			MenuItem openItem = new MenuItem("_Open File");
			MenuItem exitItem = new MenuItem("E_xit");
			MenuItem copyItem = new MenuItem("_Copy");
			MenuItem pasteItem = new MenuItem("_Paste");
			
			CheckMenuItem buildItem = new CheckMenuItem("Build Automatically");
			buildItem.setSelected(true);
			
			newItem.setOnAction(event->{
				System.out.println("New menu item was selected.");
				
			});
			exitItem.setOnAction(event->System.exit(0));
			file.getItems().addAll(newItem, openItem, new SeparatorMenuItem(), exitItem);
			edit.getItems().addAll(copyItem, pasteItem);
			project.getItems().add(buildItem);
			mb.getMenus().addAll(file,edit,project,help);
			root.setTop(mb);
			
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
