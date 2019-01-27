
import java.util.Observable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class creating the game and the game play
 * @author Md Aiman Sharif
 */
public class ConnectFourGame extends Observable{
   private int nColumns; //number of columns in the game
   private int nRows; //number of rows in the game
   private int numToWin; //number required to win the game
   private int nMarks; // Number of plays made.  Used to detect full grid (i.e. a draw)
   private ConnectFourEnum[][] grid; //EMPTY, RED, BLACK
   private ConnectFourEnum gameState; //IN_PROGRESS, RED(won), BLACK(won), DRAW
   private ConnectFourEnum turn; //RED, BLACK
   
   /**
    * Overloaded constructor initializing instance variables
    * uses constructor chaining
    * @param initialTurn 
    */
   public ConnectFourGame(ConnectFourEnum initialTurn){
      this(8, 8, 4, initialTurn); //uses constructor chaining to initialize the instance variables
      
      this.gameState = ConnectFourEnum.IN_PROGRESS; //sets the current game state to IN_PROGRESS state initially
      this.grid = new ConnectFourEnum[nRows][nColumns]; //creates a new grid of nRows and nColumns
      
      for(int i = 0; i < this.nRows; i++){
         for(int j = 0; j < this.nColumns; j++){
            grid[i][j] = ConnectFourEnum.EMPTY;
         }
      }
   }
   
   /**
    * Overloaded constructors initializing instance variables to given inputs
    * @param nRows number of rows
    * @param nColumns number of columns
    * @param numToWin number to win the game
    * @param initialTurn initial turn of the player
    */
   public ConnectFourGame(int nRows, int nColumns, int numToWin, ConnectFourEnum initialTurn){
      this.nColumns = nColumns; //initializes nColumns to the given input parameter
      this.nRows = nRows; //initializes nRows to input parameter
      this.numToWin = numToWin; //initializes numToWin to input parameter
      this.turn = initialTurn; //initializes turn to input parameter
      this.gameState = ConnectFourEnum.IN_PROGRESS; //initializes gameState to input parameter
      
      //reset(initialTurn);
      
      if(nRows < 0 || nColumns < 0 || numToWin <= 0)
         throw new IllegalArgumentException ("Parameters cannot be negative values.");
      if(numToWin > this.nRows && numToWin > this.nColumns){
         throw new IllegalArgumentException("numToWin cannot be greater than the number of rows or columns");
      }
      if(initialTurn != ConnectFourEnum.RED && initialTurn != ConnectFourEnum.BLACK){
         throw new IllegalArgumentException("initial has to either Black or Red");
      }
      
      grid = new ConnectFourEnum[nRows][nColumns];
      for(int i = 0; i < this.nRows; i++){
         for(int j = 0; j < this.nColumns; j++){
            grid[i][j] = ConnectFourEnum.EMPTY; //sets the array of grid to EMPTY
         }
      }
   }
   
   /**
    * Resets the game board to empty blocks
    * @param initialTurn 
    */
   public void reset(ConnectFourEnum initialTurn){
      
      for(int i = 0; i < this.nRows; i++){
         for(int j = 0; j < this.nColumns; j++){
            ConnectMove cmMove = new ConnectMove(i, j, ConnectFourEnum.EMPTY);
            this.grid[i][j] = ConnectFourEnum.EMPTY;
            this.setChanged();
            this.notifyObservers(cmMove);
         }
      }
      
      this.turn = initialTurn; //sets turn to initial turn
      this.gameState = ConnectFourEnum.IN_PROGRESS; //sets the game state to IN_PROGRESS
      this.numToWin = 0; //sets numToWin to 0
   }
   
   /**
    * Takes the turn of the player 
    * @param row of the board
    * @param column of the board
    * @return ConnectFourEnum status of the game
    */
   public ConnectFourEnum takeTurn(int row, int column){
      if(this.nRows < row || row < 0){
         throw new IllegalArgumentException("row is out of range");
      }
      //checks if the column answer entered in is out of range
      if(this.nColumns < column || column < 0){
         throw new IllegalArgumentException("column is out of range");
      }
      
      //checks if the game is over or not
      if(this.gameState != ConnectFourEnum.IN_PROGRESS){
         throw new IllegalArgumentException("Game over");
      }
      
      //checks if the checker is placed at the bottom or not
      if (row > 0) {
         if (grid[row - 1][column] == ConnectFourEnum.EMPTY) {
            throw new IllegalArgumentException("Checker must be placed above another checker");
         }
      }
      
      ConnectMove move = new ConnectMove(row, column, getTurn()); //instantiates a new ConnectMove object
      
      if(this.grid[row][column] == ConnectFourEnum.EMPTY){
         this.grid[row][column] = turn; //sets the turn to either Black or Red
         this.setChanged(); //changes the state of the object
         this.notifyObservers(move); //notifies the observer that a move is made
      }
      else{
         throw new IllegalArgumentException("The space is already filled");
      }
      
      if(this.turn == ConnectFourEnum.RED){
         this.turn = ConnectFourEnum.BLACK; //sets the turn to black 
      }
      else{
         this.turn = ConnectFourEnum.RED; //sets the turn to red
      }
      
      this.nMarks += 1;
      
      gameState = this.findWinner();
      return gameState;
   }
   
   public ConnectFourEnum findWinner(){
      ConnectFourEnum newGameState;
        for (int r = 0; r < nRows; r++) {
          for (int c = 0; c < nColumns; c++) {
             if (grid[r][c] != ConnectFourEnum.EMPTY) {
                    newGameState = findWinnerFrom(r,c);
                    if ( newGameState != ConnectFourEnum.IN_PROGRESS) 
                        return newGameState;
                }
            }
        }
        if (nMarks == this.nRows * this.nColumns) {
            return ConnectFourEnum.DRAW;
        }  
        return ConnectFourEnum.IN_PROGRESS;
   }
   
   /**
    * Checks each row and column to find a winner 
    * @param row
    * @param column
    * @return 
    */
   private ConnectFourEnum findWinnerFrom(int row, int column) {

      int count;

      count = 1;
      for (int c = column - 1; c > 0; c--) {
         if (this.grid[row][column] == this.grid[row][c]) {
            count++;
            if (count == this.numToWin) {
               gameState = (grid[row][column]);
               return gameState;
            }
         } // else, look in another direction
      }

      count = 1;
      for (int c = column + 1; c < this.nColumns; c++) {
         if (this.grid[row][column] == this.grid[row][c]) {
            count++;
            if (count == this.numToWin) {
               gameState = (grid[row][column]);
               return gameState;
            }
         } // else, look in another direction
      }

      // Look vertically - up then down
      count = 1;
      for (int r = row - 1; r > 0; r--) {
         if (this.grid[r][column] == this.grid[row][column]) {
            count++;
            if (count == this.numToWin) {
               gameState = (grid[row][column]);
               return gameState;
            }
         } // else, look in another direction
      }

      count = 1;
      for (int r = row + 1; r < this.nRows; r++) {
         if (this.grid[row][column] == this.grid[r][column]) {
            count++;
            if (count == this.numToWin) {
               gameState = (grid[row][column]);
               return gameState;
            }
         } // else, look in another direction
      }

      // Look diagonally - Left-down - TBD
      // Look diagonally - Right-down - TBD
      return ConnectFourEnum.IN_PROGRESS;
   }
   
   /**
    * Method which gets the game state
    * @return ConnectFourEnum variable game state returning the state of the game
    */
   public ConnectFourEnum getGameState(){
      return this.gameState;
   }
   
   /**
    * Sets the state of the game to the gameState
    */
   public void setState(ConnectFourEnum game){
      this.gameState = game;
   }
   
   /**
    * method which gets the current turn of the player
    * @return ConnectFourEnum turn 
    */
   public ConnectFourEnum getTurn(){
      return this.turn;
   }
   
   /**
    * returns a string representation of the board of the game 
    * @return String which stores the game board to be displayed
    */
   public String toString(){
      String s = "";
      
      for (int i = 0; i < this.nRows; i++) {
         for (int j = 0; j < this.nColumns; j++) {
            s += grid[i][j] + " | ";
         }
         s += "\n"; //new line after every row
      }
      return s;
   }
}