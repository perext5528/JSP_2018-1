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
   double[] x = new double[9];         //�� ��ġ setLayout���ַ��� ����
   double[] y = new double[9];
   
   double[] all_x = new double[10];       // �����ϴ� ��� ��ü�� ��ġ ��
   double[] all_y = new double[10];
   
   int[] body = new int[10];         //����
   int get_body = 0;
   
   static final int DISTANCE = 25;
   
   class Move {
      
      public Move() {
      }
      
      public void Combind() {         // �� ����
         for(int i=0;i<get_body;i++)
         {
            snake[body[i + 1]].setLayoutX(all_x[i]);      //1�� ���� 0�� all_x
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
      
      
      img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/down_1.png");      //�Ӹ�
      snake[0] = new ImageView(img);
      snake[0].setImage(img);
      snake[0].setPreserveRatio(false);
      snake[0].setFitHeight(50);      //����
      snake[0].setFitWidth(25);         //�ʺ�
      root.getChildren().add(snake[0]);
      
      for(int i=0;i<x.length;i++)
      {
         x[i] = Math.floor((Math.random() * 300));      //�ݿø� �����ָ鼭 ����
         y[i] = Math.floor((Math.random() * 300));
         
         if(x[i] % 25 != 0)            // ���� ������ 25�ǹ���� �ƴҰ�� �����ֱ�, ���ũ���� ���������ϱ⶧��
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
      
      
      for(int i=1;i<snake.length;i++) {            // ���� �� �ѷ��ֱ�
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
         
         // �������� �پ��ִ� ��ü�� ��ġ
                
        double attach_x = snake[body[get_body]].getLayoutX();   ////
         double attach_y = snake[body[get_body]].getLayoutY();
         
         all_x[0] = snake[0].getLayoutX();      // ó���� ���� ��ġ�� �־���, ��ü�� �ٰ� ������ ���� ��ġ������ ������ �ȵǱ� ������
         all_y[0] = snake[0].getLayoutY();

//         double storageX = snake[0].getLayoutX();
//         double storageY = snake[0].getLayoutY();
         
         
         // �����ϴ� ��� ��ü�� ��ġ ��
         for(int i=1;i<=get_body;i++)         // �������� �� ��ġ�� ���� �ص� >> �ٽ� ���Ƽ� ���� �ö󰥶� [���� ���� ����� ����]�� ������ �ٴ� ��ü��ġ�� ����
         {
            all_x[i] = snake[body[i]].getLayoutX();
            all_y[i] = snake[body[i]].getLayoutY();
       
         }
         
         if(event.getCode() == KeyCode.RIGHT)         // �Ӹ���
         {
            img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/head_right.png");      //�Ӹ�
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
            img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/head_up.png");      //�Ӹ�
            snake[0].setImage(img);
            snake[0].setFitHeight(50);      //����
            snake[0].setFitWidth(25); 
            
            snake[0].setLayoutY(snake[0].getLayoutY() - DISTANCE);
         }
         else if(event.getCode() == KeyCode.DOWN)
         {
            img = new Image("file:///C:/Users/user/eclipse-workspace/Original/src/application/head_down.png");      //�Ӹ�
            snake[0].setImage(img);
            snake[0].setFitHeight(50);      //����
            snake[0].setFitWidth(25); 
            

            snake[0].setLayoutY(snake[0].getLayoutY() + DISTANCE);
         }
         
         for(int i=1;i<snake.length;i++)      //������� ������ �������� �˾ƺ��� ���� ���� ������
         {
            
            boolean done =true;
            if(snake[0].getLayoutX() == snake[i].getLayoutX() &&       //�Ӹ��� ������ ������ �Ǵ°��(�������鼭 �Ӹ��� ������ �ٴ´�),, 
               snake[0].getLayoutY() == snake[i].getLayoutY() )
               //num == snake[i].
            {
               while(done)
               {
                  if(snake[i].getImage() == v.get(num))
                  {
                     get_body++;            // ó���� 0���� �ʱ�ġ�� �ְ�(�Ӹ��� 0, ������ 1���� ���� ���������� ���ڰ� 1������) 
                        body[get_body] = i;      // [���� get_body��° ��]���� ������� �������� �����Ѵ�.  ( (get)body�� 1���� ���������� ���� ) ����1�� 3(������ũ�Ҷ��� 3)�� �־���
                        snake[i].setLayoutX(attach_x);      //������ũ 3���� 
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