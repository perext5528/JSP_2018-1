package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Report extends Application {
   
   File file = new File("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\score.txt");
   FileInputStream fis;
   BufferedReader br;
   BufferedWriter bw;
   PrintWriter pw;
   String[] info = new String[3];
   String buf;
   int i, j;
      TableView<Person> table = new TableView<Person>();
      ObservableList<Person> data = FXCollections.observableArrayList();
      
      
      
      public void start(Stage primaryStage) throws Exception {
         
         try
         {
            br = new BufferedReader(new FileReader("C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\score.txt"));
            while((buf = br.readLine()) != null)
            {
               info = buf.split(" ");
               data.add(new Person(info[0], info[1], info[2]));
            }
         }
         catch(Exception e)
         {
            e.printStackTrace();
         }
         i = 0;
         j = 0;
         
         primaryStage.setTitle("RANKING");
         VBox root  = new VBox();
         root.setPadding(new Insets(10));
         root.setSpacing(5);
         
         Label label = new Label("Rank");
         label.setFont(new Font("Arial", 20));
         
         table.setEditable(true);
         TableColumn NameCol = new TableColumn("Name");
         NameCol.setCellValueFactory(
               new PropertyValueFactory<Person, String>("name"));
         NameCol.setPrefWidth(110);
         
         TableColumn TimeCol = new TableColumn("time");
         TimeCol.setCellValueFactory(
               new PropertyValueFactory<Person, String>("Time"));
         TimeCol.setPrefWidth(65);
         
         TableColumn rankCol = new TableColumn("rank");
         rankCol.setCellValueFactory(
               new PropertyValueFactory<Person, String>("Rank"));
         rankCol.setPrefWidth(65);
         
         table.getColumns().addAll(NameCol, TimeCol, rankCol);
         table.setItems(data);
         ////////////////////////////////////////////////////////////////////////////////
         
         
         HBox hb = new HBox();
            hb.setSpacing(3);
            
            TextField addName = new TextField();
            addName.setPromptText("Name");
            addName.setMaxWidth(NameCol.getPrefWidth());
            
            
            TextField addTime = new TextField();
            addTime.setPromptText("Time");
            addTime.setMaxWidth(TimeCol.getPrefWidth());
            
            TextField addRank = new TextField();
            addRank.setPromptText("Time");
            addRank.setMaxWidth(rankCol.getPrefWidth());
            
            Button addButton = new Button("Add");
            addButton.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(addButton, Priority.ALWAYS);
                          
            addButton.setOnAction( new EventHandler<ActionEvent>() {
               public void handle(ActionEvent e) {
                  data.add( new Person(addName.getText(),
                                 addTime.getText(),
                                 addRank.getText()) 
                  );
                  
                  try
                  {
                     bw = new BufferedWriter(new FileWriter(file, true));
                     pw = new PrintWriter(bw, true);
                     while((buf = br.readLine()) != null)
                     {
                        pw.write(buf);
                     }
                     pw.write("\n" + addName.getText() + " " + addTime.getText() + " " + addRank.getText());
                     pw.flush();
                     pw.close();
                  }
                  catch(Exception e1)
                  {
                     e1.printStackTrace();
                  }
                  
                  addName.clear();
                  addTime.clear();
                  addRank.clear();
               }
               
            });
            
            hb.getChildren().addAll(addName, addTime, addRank, addButton);
            root.getChildren().addAll(label, table, hb);

         
         ///////////////////////////////////////////////////////////////////////////////////
         Scene scene = new Scene(root,320,200);
         primaryStage.setScene(scene);
         primaryStage.show();   
      }
      
      
      public class Person
      {
         private SimpleStringProperty Name;
         private SimpleStringProperty time;
         private SimpleStringProperty rank;
         
         public Person(String Name, String time_, String rank_) {
            this.Name = new SimpleStringProperty(Name);
            this.time = new SimpleStringProperty(time_);
            this.rank = new SimpleStringProperty(rank_);
         }
         
         public String getName()  {   return this.Name.get();  }
         public void setName(String Name_)   {   this.Name.set(Name_);   }
         
         public String getTime()  {   return this.time.get(); }
         public void setTime(String time_)   { this.time.set(time_); }
         
         public String getRank()  {   return this.rank.get(); }
         public void setRank(String rank_)   { this.rank.set(rank_); }
         
      }
      
      
      public static void main(String[] args) {
         launch(args);
      }

   }