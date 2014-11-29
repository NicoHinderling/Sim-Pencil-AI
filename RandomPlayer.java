/*
 * File: Player.java
 * Purpose: This implements the minMax strategy as presented in lecture
 *      in the last few lectures. Input is a graph, and the player chooses
 *      the next edge to draw. Player is the machine playing as the max player,
 *      the weight of the its edges are -1, shown as blue. 
 * Date: 4/21/14
 * Class: CS 112
 *
 * Author: Wayne Snyder
 */

import java.util.Random;

public class RandomPlayer {
  
   Random R = new Random(); 
   
   public Move chooseMove(Graph G) {
      boolean done = false; 
      
      while(!done) {
         int s = R.nextInt(6);
         int t = R.nextInt(6);
         if(s != t && G.getEdge(s,t) == 0)
            return new Move(s,t);
      }
      return null; 
   }   
}