package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SnakeGame extends Application{

   Image img;
   ImageView[] snake = new ImageView[10];
   //머리를 제외한 몸
   double[] x = new double[9];	
   double[] y = new double[9];
   
   //머리를 포함한 몸
   double[] all_x = new double[10];
   double[] all_y = new double[10];
   
   
   int[] body = new int[10];
   int get_body = 0;
   
   static final int DISTANCE = 25;	//움직이는 길이
   
   class Move {
      
      public Move() {
      }
      
      public void Combind() {
         for(int i=0;i<get_body;i++)
         {
            snake[body[i + 1]].setLayoutX(all_x[i]);
            snake[body[i + 1]].setLayoutY(all_y[i]);
         }
      }
   }
   
   public static void main(String[] args) {

      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      
      AnchorPane root = new AnchorPane();	//게임 창
      root.setPrefSize(400, 400);	//게임 창 크기
      //머리 이미지 세팅
      img = new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProject/src/application/head.png");
      snake[0] = new ImageView(img);
      snake[0].setImage(img);
      snake[0].setPreserveRatio(false);
      snake[0].setFitHeight(25);
      snake[0].setFitWidth(25);
      root.getChildren().add(snake[0]);
      
      //랜덤으로 아이템 흩뿌리기
      for(int i=0;i<x.length;i++)
      {
         x[i] = Math.floor((Math.random() * 300));
         y[i] = Math.floor((Math.random() * 300));
         if(x[i] % 25 != 0)
         {
            x[i] = x[i] + 25 - x[i] % 25;
         }
         if(y[i] % 25 != 0)
         {
            y[i] = y[i] + 25 - y[i] % 25;
         }
      }
      
      //몸통 이미지 세팅
      img = new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProject/src/application/head.png");
      for(int i=1;i<snake.length;i++) {
         snake[i] = new ImageView(img);
         snake[i].setImage(img);
         snake[i].setPreserveRatio(false);
         snake[i].setFitHeight(25);
         snake[i].setFitWidth(25);
         snake[i].setLayoutX(x[i-1]);
         snake[i].setLayoutY(y[i-1]);
         //snake[i].setLayoutX(i*25);
         root.getChildren().add(snake[i]);
      }
      
      Scene scene = new Scene(root);
      
      Move move = new Move();
      
      //이동 - 키이벤트
      scene.setOnKeyPressed((event) -> {
         
         // 끝에 붙어있는 몸체의 위치
         double attach_x = snake[body[get_body]].getLayoutX();
         double attach_y = snake[body[get_body]].getLayoutY();
         
         all_x[0] = snake[0].getLayoutX();
         all_y[0] = snake[0].getLayoutY();

         // 존재하는 모든 몸체의 위치 값
         for(int i=1;i<=get_body;i++)
         {
            all_x[i] = snake[body[i]].getLayoutX();
            all_y[i] = snake[body[i]].getLayoutY();
         }
         
         //키 이벤트 설정
         if(event.getCode() == KeyCode.RIGHT)
         {
            snake[0].setLayoutX(snake[0].getLayoutX() + DISTANCE);
         }
         else if(event.getCode() == KeyCode.LEFT)
         {
            snake[0].setLayoutX(snake[0].getLayoutX() - DISTANCE);
         }
         else if(event.getCode() == KeyCode.UP)
         {
            snake[0].setLayoutY(snake[0].getLayoutY() - DISTANCE);
         }
         else if(event.getCode() == KeyCode.DOWN)
         {
            snake[0].setLayoutY(snake[0].getLayoutY() + DISTANCE);
         }
         
         for(int i=1;i<snake.length;i++)
         {
            if(snake[0].getLayoutX() == snake[i].getLayoutX() && 
               snake[0].getLayoutY() == snake[i].getLayoutY())
            {
               get_body++;
               body[get_body] = i;
               snake[i].setLayoutX(attach_x);
               snake[i].setLayoutY(attach_y);
            }
         }
         
         move.Combind();
         
      });
      
      primaryStage.setScene(scene);
      primaryStage.show();
   }

}