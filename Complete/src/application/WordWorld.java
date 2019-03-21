package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WordWorld extends Application {

   Image img, title;
   ImageView view, TitleView;
   AnchorPane root;
   Scene scene;
   Group group;
   BufferedReader br;
   Button[] btn = new Button[26];
   Label[] label;
   Button check;
   Button reset;
   Button[] hit;
   int stage = 0;
   double[] alph_x = new double[26];
   double[] alph_y = new double[26];
   String message = "";
   
   File file;
   String[] word1;
   String[] word2;
   String[] word3;
   int i = 0;
    int batch_x = 60;
    int batch_y = 70;
    int LeftPadding = 60;
    int LeftPaddingWord = 75;
    int GapOfWord = 60;
    int controll = 0;
    int CenterOfWord = 440;
   
   double x, y;
   
   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      
      try
      {
         br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\result.txt"));
         String s;
         while((s = br.readLine()) != null)
         {
            i++;
         }
         
         word1 = new String[i];
         i = 0;
         
         br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\result.txt"));
         while((s = br.readLine()) != null)
         {
            word1[i] = new String(s);
            i++;
         }
         
         i = 0;
         br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\alpha.txt"));
         while((s = br.readLine()) != null)
         {
            i++;
         }
         
         word2 = new String[i];
         i = 0;
         
         br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\alpha.txt"));
         while((s = br.readLine()) != null)
         {
            word2[i] = new String(s);
            i++;
         }
         
         i = 0;
         br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\alpha.txt"));
         while((s = br.readLine()) != null)
         {
            btn[i] = new Button(s);
            i++;
         }
         
         i = 0;
         br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\word.txt"));
         while((s = br.readLine()) != null)
         {
            i++;
         }
         
         word3 = new String[i];
         i = 0;
         
         br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\word.txt"));
         while((s = br.readLine()) != null)
         {
            word3[i] = new String(s);
            i++;
         }
         i = 0;
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }

      root = new AnchorPane();
      root.setPrefSize(880, 495);
        root.getStyleClass().clear();
        root.getStyleClass().add("thebackground");
      
      check = new Button("CHECK");
       check.getStyleClass().clear();
       check.getStyleClass().add("CheckReset");
      reset = new Button("RESET");
       reset.getStyleClass().clear();
       reset.getStyleClass().add("CheckReset");
       
       title = new Image("file:///C:/Users/User/eclipse-workspace/GameProject/src/application/logo.png");
       TitleView = new ImageView(title);
       TitleView.setImage(title);
       TitleView.setLayoutX(260);
       TitleView.setLayoutY(20);
      
      label = new Label[3];
      for(int k=0;k<label.length;k++)
      {
          label[k] = new Label(word3[k]);
            label[k].setLayoutX(170);
            label[k].setLayoutY(240 + k * 30);
          root.getChildren().add(label[k]);
            label[k].getStyleClass().clear();
            label[k].getStyleClass().add("setence");
      }
      hit = new Button[word1[stage].length()];
            
      for(int j=0;j<word1[stage].length();j++)
      {
            hit[j] = new Button();
            hit[j].setPrefSize(50, 50);
            
            hit[j].setLayoutX((CenterOfWord - (word1[stage].length()+1) * 50 / 2) + GapOfWord * j);
            hit[j].setLayoutY(360);
            root.getChildren().add(hit[j]);

            hit[j].getStyleClass().clear();
            hit[j].getStyleClass().add("word");
      }
      
      for(int j=0;j<btn.length;j++)
      {
         final int index = j;
            btn[j].setLayoutX(batch_x * controll + LeftPadding);
            btn[j].setLayoutY(batch_y + 20);
            btn[j].setPrefSize(30, 30);

            alph_x[j] = btn[j].getLayoutX();
            alph_y[j] = btn[j].getLayoutY();
         
            btn[j].setOnMouseDragged(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                   x = event.getSceneX();
                   y = event.getSceneY();
                   btn[index].setLayoutX(x - 25);
                   btn[index].setLayoutY(y - 25);
                   for (int k = 0; k < hit.length; k++) {
                      if (btn[index].getLayoutX() > hit[k].getLayoutX() -25
                            && btn[index].getLayoutX() < (hit[k].getLayoutX() + 25)
                            && btn[index].getLayoutY() > hit[k].getLayoutY() -25
                            && btn[index].getLayoutY() < (hit[k].getLayoutY() + 25)) {
                         hit[k].setText(btn[index].getText());
                         btn[index].setLayoutX(alph_x[index]);
                         btn[index].setLayoutY(alph_y[index]);
                         root.requestFocus();
                      }
                   }
                }
             });
         
         controll++;
         
         if((j+1) % 13 == 0)
         {
            batch_y = batch_y + 70;
            controll = 0;
         }
           btn[j].setMinWidth(50);
           btn[j].setMinHeight(50);
           btn[j].getStyleClass().clear();
           btn[j].getStyleClass().add("alphabet");
         
         root.getChildren().add(btn[j]);
      }
      
      check.setOnAction(new EventHandler<ActionEvent>() {

         @Override
         public void handle(ActionEvent event) {
            for(int k=0;k<hit.length;k++)
            {
               message += hit[k].getText();
            }
            if(word1[stage].equals(message))
            {
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setContentText("정답입니다!");
               alert.show();
               message = "";
               stage++;
               newScene(stage);
            }
            else
            {
               message = "";
               Alert alert = new Alert(AlertType.ERROR);
               alert.setContentText("틀렸습니다.");
               alert.show();
            }
         }
      });
      
      reset.setOnAction(new EventHandler<ActionEvent>() {

         @Override
         public void handle(ActionEvent event) {
            for(int k=0;k<btn.length;k++)
            {
               btn[k].setLayoutX(alph_x[k]);
               btn[k].setLayoutY(alph_y[k]);
            }
         }
         
      });
      
         check.setLayoutX(CenterOfWord - 100 - 10);
         check.setLayoutY(440);
         check.setPrefSize(100, 30);
         
         reset.setLayoutX(CenterOfWord + 10);
         reset.setLayoutY(440);
         reset.setPrefSize(100, 30);
      root.getChildren().addAll(check, reset, TitleView);
      
      scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      
      primaryStage.setTitle("단어 완성 게임");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   
   public void newScene(int s)
   {
      int newS = s;
      
      for(int k=0;k<label.length;k++)
      {
         label[k].setVisible(false);
         label[k] = new Label(word3[newS*3 + k]);
      }
      
      for(int k=0;k<label.length;k++)
      {
            label[k].setLayoutX(170);
            label[k].setLayoutY(240 + k * 30);
            label[k].getStyleClass().clear();
            label[k].getStyleClass().add("setence");
            root.getChildren().add(label[k]);
      }
      
      for(int i=0;i<hit.length;i++)
      {
         hit[i].setVisible(false);
      }
      
      hit = new Button[word1[newS].length()];
      
      for(int j=0;j<word1[newS].length();j++)
      {
            hit[j] = new Button();
            hit[j].setPrefSize(50, 50);
            hit[j].setLayoutX((CenterOfWord - (word1[stage].length()+1) * 50 / 2) + GapOfWord * j);
            hit[j].setLayoutY(360);
            root.getChildren().add(hit[j]);

            hit[j].getStyleClass().clear();
            hit[j].getStyleClass().add("word");
      }
      
   }

}