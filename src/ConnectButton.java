/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * A class holding the buttons
 * @author Md Aiman Sharif
 */
public class ConnectButton extends javafx.scene.control.Button {
   private int row; 
   private int column;
   private String label;
   
   /**
    * Overloaded constructor initializing instance variables to given inputs
    * @param label of the button
    * @param row of the button
    * @param column of the button
    */
   public ConnectButton(String label, int row, int column){
      super(label);
      this.row = row; 
      this.column = column;
   }
   
   /**
    * @return int type:  row of the button
    */
   public int getRow(){
      return this.row;
   }
   
   /**
    * @return int type: column of the button
    */
   public int getColumn(){
      return this.column;
   }
   
   /**
    * @return String representation of the rows and columns
    */
   public String toString(){
      return this.row + "," + this.column;
   }
}
