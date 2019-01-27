/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Observer;
import java.util.Observable;
import java.util.Scanner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Class for the Connect four game application
 * Creates buttons and the GUI for the game 
 * 
 * @author Md Aiman Sharif
 */
public class ConnectFourApplication extends Application implements Observer{
   public static final int NUM_COLUMNS = 8; //sets the number of columns in the game
   public static final int NUM_ROWS  = 8; //sets the number of rows in the game
   public static final int BUTTON_SIZE = 20; //sets each of the button size
   private ConnectButton[][] buttons; //creates an array buttons of type ConnectButton 
   private ConnectFourGame gameEngine; //creates a reference of type ConnectFourGame to be initialized
   private int r; //holds the row 
   private int c; //holds the column
   
       public ConnectFourApplication() {
        this.gameEngine = new ConnectFourGame(ConnectFourEnum.BLACK);
        this.gameEngine.addObserver(this);
    }
   
   /**
    * start method which starts the application 
    * entry point for java application
    * @param primaryStage 
    */
   @Override
   public void start(Stage primaryStage) {
      BorderPane rootBorder = new BorderPane(); //creates a border pane  
      GridPane gridPane = new GridPane(); //creates new gridPane which stores the buttons in the game
      gameEngine = new ConnectFourGame(ConnectFourEnum.BLACK); //instantiates a new ConnectFourGame game object
      Scene scene = new Scene(rootBorder, 510, 380);
      this.buttons = new ConnectButton[NUM_ROWS][NUM_COLUMNS];
      
      gameEngine.addObserver(this); //adds observer 
      
      TextField topText = new TextField("Turn of " + gameEngine.getTurn()); //sets the text at the top to the turn of the player
      rootBorder.setTop(topText); //sets the top of the borderpane to topText

      /**
       * Inner class handling button clicks on the click of a button
       */
      class ButtonHandler implements EventHandler<ActionEvent> {

         private ConnectButton clickedButton;

         /**
          * Overloaded constructor initializing instance variable to the given input
          * @param clickedButton 
          */
         public ButtonHandler(ConnectButton clickedButton) {
            this.clickedButton = clickedButton;
         }
         
         /**
          * handle method which handles the event when a button is clicked
          * sets the text of the button to RED or BLACK depending on the turn of the player
          * @param event 
          */
         @Override
         public void handle(ActionEvent event) {
            r = clickedButton.getRow();
            c = clickedButton.getColumn();
            this.clickedButton.setText(((ConnectButton) event.getSource()).getText());
            System.out.println(r + "," + c);
         }
      }
      
      for(int i = 0; i < NUM_COLUMNS; i++){
         for(int j = 0; j < NUM_ROWS; j++){
            this.buttons[j][i] = new ConnectButton(ConnectFourEnum.EMPTY + "", j, i);
            buttons[j][i].setMinHeight(20); //sets the minimum height to 20
            buttons[j][i].setMaxWidth(Double.MAX_VALUE); //sets the maximum width to MAX_VALUE
            buttons[j][i].setPrefSize(70, 50); //sets the button size to a size to be easily clicked by the user and view
            
            gridPane.add(this.buttons[j][i], i, NUM_ROWS - j);
            EventHandler<ActionEvent> sharedHandler = new ButtonHandler(buttons[i][j]);
            this.buttons[j][i].setOnAction(sharedHandler);  
         }
      }
      
      rootBorder.setCenter(gridPane); //sets the centre of the borderpane with gridpane which holds the buttons
      
      Button takeTurn = new Button("Take my turn"); //creates a new button which lets the player confirm their move
      EventHandler<ActionEvent> turnHandler = new TakeTurn(); //shows a message when the take turn button is clicked
      takeTurn.setOnAction(turnHandler); //notifies that the take turn buttons is clicked
      
      rootBorder.setBottom(takeTurn); //sets the bottom of the borderpane to the take my turn button
      
      primaryStage.setTitle("ConnectFour"); //sets the title of the game 
      primaryStage.setScene(scene); //sets the scene to be displayed
      primaryStage.show(); //shows the stage
      
      takeTurn.setOnAction(new EventHandler<ActionEvent>() {
         
         @Override
         public void handle(ActionEvent event) {
            try {
               ConnectMove point = new ConnectMove();
               gameEngine.takeTurn(c, r); 
               buttons[r][c].setDisable(true);
               topText.setText("It's " + gameEngine.getTurn() + "'s turn.");
               
               if (gameEngine.getGameState() != ConnectFourEnum.IN_PROGRESS) {
                  Alert gameAlert = new Alert(Alert.AlertType.INFORMATION);
                  gameAlert.setTitle("ConnectFour");
                  gameAlert.setHeaderText("Game Over");

                  if (gameEngine.getGameState() == ConnectFourEnum.RED) {
                     gameAlert.setContentText("RED WINS THE GAME");
                  } else if (gameEngine.getGameState() == ConnectFourEnum.BLACK) {
                     gameAlert.setContentText("BLACK WINS THE GAME");
                  } else {
                     gameAlert.setContentText("Game draw. Press OK to play again");
                  }
               }
            } catch (Exception e) {
               Alert gameAlert = new Alert(Alert.AlertType.INFORMATION);
               
               gameAlert.setTitle("Information"); 
               gameAlert.setHeaderText("Error");
               gameAlert.setContentText(e.getMessage()); 

               gameAlert.showAndWait(); //waits till the user presses on a buton
            }
         }
      }
      );
   }
      
   /**
    * updates the observable class about the change that has been made
    * @param o
    * @param arg 
    */
   @Override
   public void update(Observable o, Object arg){
      ConnectFourGame game = (ConnectFourGame) o;
      ConnectMove cm = (ConnectMove) arg;
      
      this.buttons[cm.getRow()][cm.getColumn()].setText("" + cm.getColour());
      gameEngine.setState(game.getGameState());
      buttons[cm.getRow()][cm.getColumn()].setDisable(true);
   }
   
   /**
    * Main method to launch the application
    * @param args 
    */
   public static void main(String args[]){
      launch(args); //launches the application
   }
}

/**
 * Event handler for take turn button when it is clicked
 * @author Md Aiman Sharif
 */
class TakeTurn implements EventHandler<ActionEvent> {

   @Override
   public void handle(ActionEvent event) {
      Button onClickButton = (Button) event.getSource();

      System.out.println("Button clicked: " + event.getSource());
   }
}
