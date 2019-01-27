/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Md Aiman Sharif
 */
/**
 * Class holding the row, column and the colour of the move to be made
 * @author Md Aiman Sharif
 */
public class ConnectMove{
   private int row; 
   private int column;
   private ConnectFourEnum colour;
   
   /**
    * Default constructor initializing instance variables using constructor chaining
    */
   public ConnectMove(){
      this(0, 0, ConnectFourEnum.BLACK);
   }
   
   /**
    * Overloaded constructor initializing instance variables to given inputs
    * @param row
    * @param column
    * @param colour 
    */
   public ConnectMove(int row, int column, ConnectFourEnum colour){
      this.row = row;
      this.column = column;
      this.colour = colour;
   }
   
   /**
    * Method which gets the row number
    * @return int row 
    */
   public int getRow(){
      return this.row;
   }
   
   /**
    * Method which gets the column number
    * @return int column
    */
   public int getColumn(){
      return this.column;
   }
   
   /**
    * Method gets the colour of the checker
    * @return ConnectFourEnum colour
    */
   public ConnectFourEnum getColour(){
      return this.colour;
   }
}
