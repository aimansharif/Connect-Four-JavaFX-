/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;
/**
 * Class creating the game and displaying the game board to play the game
 * @author Md Aiman Sharif
 */
public class ConnectFourTestClient {

   public static void main(String args[]) {
      ConnectFourGame game = new ConnectFourGame(ConnectFourEnum.BLACK); //instantiates a new ConnectFourGame object
      Scanner scanner = new Scanner(System.in); //creates a new scanner object
      
      do {
         System.out.println(game.toString()); //prints out the game board
         System.out.println(game.getTurn() + ": Where do you want to mark? Enter row column");
         int row = scanner.nextInt(); //takes in user input for row
         int column = scanner.nextInt(); //takes in user input for column
         scanner.nextLine(); 
         game.takeTurn(row, column); //takes the turn 

      } while(game.getGameState() == ConnectFourEnum.IN_PROGRESS);
      
      System.out.println(game.getGameState());
   }
}
