package application;
   
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
   
   Image img1, img2;
   ImageView view1, view2;
   Button game1, game2;
   
   public void start(Stage primaryStage) {
      try {
         VBox root = new VBox();
         Scene scene = new Scene(root,600,400);
         
         img1 = new Image("file:///C:/Users/User/eclipse-workspace/GameProject/images/wordcompletegame.png");
         img2 = new Image("file:///C:/Users/User/eclipse-workspace/GameProject/images/snakegame.png");
         view1 = new ImageView(img1);
         view2 = new ImageView(img2);
         
         game1 = new Button("1p", view1);
         game2 = new Button("2p", view2);
         
         view1.setImage(img1);
         view1.setPreserveRatio(false);
         view1.setFitWidth(563);
         view1.setFitHeight(200);
         
         view2.setImage(img2);
         view2.setPreserveRatio(false);
         view2.setFitWidth(563);
         view2.setFitHeight(200);
         
         //Alert alert = new Alert(AlertType.CONFIRMATION);
         
         game1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
               // TODO Auto-generated method stub
               try {
                  new WordWorld().start(new Stage());
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }
            
         });
         
         
         game2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
               
               try {
                  new SnakeGame().start(new Stage());
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }
            
         });
         
         root.getChildren().addAll(game1, game2);
         
         primaryStage.setTitle("메인 화면");
         primaryStage.setScene(scene);
         primaryStage.setResizable(false);
         primaryStage.show();
         
         
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void main(String[] args) {
      launch(args);
   }
}