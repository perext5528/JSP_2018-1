package application;

import java.util.Vector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Original extends Application{

   int num =0;
      
   Image img;
   ImageView[] snake = new ImageView[10];  
   double[] x = new double[9];         //뱀 위치 setLayout해주려고 존재
   double[] y = new double[9];
   
   double[] all_x = new double[10];       // 존재하는 모든 몸체의 위치 값
   double[] all_y = new double[10];
   
   int[] body = new int[10];         //몸통
   int get_body = 0;
   
   static final int DISTANCE = 25;
   
   class Move {
      
      public Move() {
      }
      
      public void Combind() {         // 그 전방
         for(int i=0;i<get_body;i++)
         {
            snake[body[i + 1]].setLayoutX(all_x[i]);      //1번 몸은 0번 all_x
            snake[body[i + 1]].setLayoutY(all_y[i]);
         }
      }
   }
   
   public static void main(String[] args) {

      launch(args);
   }

   
   
   
   public void start(Stage primaryStage) throws Exception {
      
      AnchorPane root = new AnchorPane();
      root.setPrefSize(400, 400);
      
      
      img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/down_1.png");      //머리
      snake[0] = new ImageView(img);
      snake[0].setImage(img);
      snake[0].setPreserveRatio(false);
      snake[0].setFitHeight(50);      //높이
      snake[0].setFitWidth(25);         //너비
      root.getChildren().add(snake[0]);
      
      for(int i=0;i<x.length;i++)
      {
         x[i] = Math.floor((Math.random() * 300));      //반올림 없애주면서 랜덤
         y[i] = Math.floor((Math.random() * 300));
         
         if(x[i] % 25 != 0)            // 나온 정수가 25의배수가 아닐경우 맞춰주기, 블록크기대로 움직여야하기때문
         {
            x[i] = x[i] + 25 - x[i] % 25;
         }
         if(y[i] % 25 != 0)
         {
            y[i] = y[i] + 25 - y[i] % 25;
         }
      }
      
 
         
      Image snake_body[] = {new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo1.png"), 
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo2.png"),
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo3.png"),
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo4.png"),
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo5.png"),
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo6.png"),
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo7.png"), 
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/photo8.png"),
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/up_21.png"),
            new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/up_21.png")
      };            
      
      
      
      
      Vector<Image> v = new Vector<Image>();
      for (int  i = 0; i < snake_body.length; i++)
      {
         v.add(snake_body[i]);
      }
      
      
      for(int i=1;i<snake.length;i++) {            // 몸통 겸 뿌려주기
         snake[i] = new ImageView(snake_body[i-1]);
         snake[i].setImage(snake_body[i-1]);
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
      
      scene.setOnKeyPressed((event) -> {
         
         // 마지막에 붙어있는 몸체의 위치
                
        double attach_x = snake[body[get_body]].getLayoutX();   ////
         double attach_y = snake[body[get_body]].getLayoutY();
         
         all_x[0] = snake[0].getLayoutX();      // 처음에 돌때 위치를 넣어줌, 몸체가 붙고 다음에 돌때 위치정보가 없으면 안되기 때문에
         all_y[0] = snake[0].getLayoutY();

//         double storageX = snake[0].getLayoutX();
//         double storageY = snake[0].getLayoutY();
         
         
         // 존재하는 모든 몸체의 위치 값
         for(int i=1;i<=get_body;i++)         // 다음값이 올 위치를 저장 해둠 >> 다시 돌아서 위로 올라갈때 [전에 돌때 저장된 정보]가 다음에 붙는 몸체위치에 사용됨
         {
            all_x[i] = snake[body[i]].getLayoutX();
            all_y[i] = snake[body[i]].getLayoutY();
       
         }
         
         if(event.getCode() == KeyCode.RIGHT)         // 머리통
         {
            img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/head_right.png");      //머리
            snake[0].setImage(img);
            snake[0].setFitHeight(25);      
            snake[0].setFitWidth(50); 
           
            snake[0].setLayoutX(snake[0].getLayoutX() + DISTANCE);
         }
         else if(event.getCode() == KeyCode.LEFT)
         {
            img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/head_left.png");      
            snake[0].setImage(img);
            snake[0].setFitHeight(25);      
            snake[0].setFitWidth(50); 
            
            snake[0].setLayoutX(snake[0].getLayoutX() - DISTANCE);
         }
         else if(event.getCode() == KeyCode.UP)
         {
            img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/head_up.png");      //머리
            snake[0].setImage(img);
            snake[0].setFitHeight(50);      //높이
            snake[0].setFitWidth(25); 
            
            snake[0].setLayoutY(snake[0].getLayoutY() - DISTANCE);
         }
         else if(event.getCode() == KeyCode.DOWN)
         {
            img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/head_down.png");      //머리
            snake[0].setImage(img);
            snake[0].setFitHeight(50);      //높이
            snake[0].setFitWidth(25); 
            

            snake[0].setLayoutY(snake[0].getLayoutY() + DISTANCE);
         }
         
         for(int i=1;i<snake.length;i++)      //몇번방의 몸통을 만났는지 알아보기 위해 포문 돌리기
         {
            
            boolean done =true;
            if(snake[0].getLayoutX() == snake[i].getLayoutX() &&       //머리가 몸통을 만나게 되는경우(겹쳐지면서 머리에 몸통이 붙는다),, 
               snake[0].getLayoutY() == snake[i].getLayoutY() )
               //num == snake[i].
            {
               while(done)
               {
                  if(snake[i].getImage() == v.get(num))
                  {
                     get_body++;            // 처음에 0으로 초기치를 주고(머리는 0, 몸통은 1부터 따라서 붙을때마다 숫자가 1씩증가) 
                        body[get_body] = i;      // [몸통 get_body번째 방]에는 몇번방의 몸통인지 대입한다.  ( (get)body는 1부터 순차적으로 증가 ) 몸통1에 3(스네이크할때의 3)이 넣어짐
                        snake[i].setLayoutX(attach_x);      //스네이크 3에는 
                        snake[i].setLayoutY(attach_y);
                        num ++;
                        
                        snake[i].setImage(new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/body.png"));
                  }
                  else { done = false; }
               }
            }
            
         }
         move.Combind();
         
      });
      
      primaryStage.setScene(scene);
      primaryStage.show();
      
   }

}