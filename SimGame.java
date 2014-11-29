/*
 * File: SimGame.java
 * Purpose: This is a version of the Sim Pencil Game (see Wiki for details);
 *     it draws the game on  an 800x800 window and allows the user to 
 * Date: 4/21/14
 * Class: CS 112
 *
 * Author: Wayne Snyder
 */




import javax.swing.*; 
import java.util.*;
import java.awt.*;

public class SimGame{ 
   
   private static final int numCircles = 6; 
   private static Graph G = new Graph(numCircles); 
   
   private static JFrame frame; 
   private static Color bg;     
   private static JCanvas canvas;
   private static int centerX;
   private static int centerY;
   private static int radius;
   
   private static int diameter;
   
   private static int[] circlesX;    // location of vertices in camvas
   private static int[] circlesY; 
   private static int[] xl;         // location of vertex labels
   private static int[] yl;  
   
   private static int fontHeight = 14;
   private static int fontWidth = 10;
   
   public static void initDisplay() {
      
      frame=new JFrame("Sim Game"); 
      bg  = new Color(255,255,220);     
      canvas=new JCanvas();
      
      frame.setSize(600,600); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      canvas.setBackground(bg);
      frame.add(canvas); 
      frame.setVisible(true); 
      centerX = 300;
      centerY = 300;
      radius = 180;
      diameter = 20;
      
      circlesX = new int[numCircles];
      circlesY = new int[numCircles]; 
      xl = new int[numCircles];         // location of vertex labels
      yl = new int[numCircles]; 
      
      for(int i = 0; i < numCircles; ++i) {
         circlesX[i] = (int)(centerX - (diameter/2) + radius*Math.cos(i*2*3.1416/numCircles));
         circlesY[i] = (int)(centerY - (diameter/2) + radius*Math.sin(-i*2*3.1416/numCircles));
         xl[i] = (int)(centerX - (diameter/2.0) - (fontWidth/2) + (radius+30)*Math.cos(i*2*3.1416/numCircles));
         yl[i] = (int)(centerY - (diameter/2.0) + (fontHeight/2) + (radius+30)*Math.sin(-i*2*3.1416/numCircles));
      }
      
      canvas.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
      canvas.setStroke(new BasicStroke(2));
      
   }
   
   // update the display of the graph with whatever move has been made
   // edges with weight -1 (machine) will be drawn as blue, weight 1 (human)
   // as red
   
   public static void drawDisplay() {
      
      canvas.startBuffer(); 
      canvas.clear();
      
      canvas.setPaint(Color.BLACK);
      for(int i = 0; i < numCircles; ++i) {
         
         canvas.drawOval(circlesX[i]- (diameter/2) ,circlesY[i]- (diameter/2) ,diameter, diameter);
         canvas.drawString(i + "", xl[i], yl[i]);
      } 
      
      for(int r = 0; r < numCircles; ++r) {
         for(int c = 0; c < r; ++c) {
            int e = G.getEdge(r,c);
            if(e != 0) {
               if( e == 1 )
                  canvas.setPaint(Color.RED);
               else 
                  canvas.setPaint(Color.BLUE);
               
               canvas.drawLine(circlesX[r],circlesY[r],
                               circlesX[c],circlesY[c]  ); 
            }
         }
      }
      
      canvas.endBuffer(); 
      
   }
   
   // since users will not hit the mark precisely, find the closest
   // vertex to the point where they clicked or released the mouse
   
   private static int closestVertex(int x, int y) {
      double smallestDistance = 10000000.0; 
      int closestVertex = -1; 
      for(int i = 0; i < numCircles; ++i) {
         double distance = 
            Math.sqrt((circlesX[i]-x)*(circlesX[i]-x) 
                         +(circlesY[i]-y)*(circlesY[i]-y));
         if(distance < smallestDistance) {
            smallestDistance = distance; 
            closestVertex = i;
         }
      }
      return closestVertex;
   }
   
   private static boolean completeGraph(Graph G) {
      for(int r = 0; r < numCircles; ++r) {
         for(int c = 0; c < r; ++c) {
            if(!G.isEdge(r,c))
               return false;
         }
      }
      return true; 
            
         
      
   }
   
   
   
   public static void main(String args[]){ 
      
      initDisplay(); 
      drawDisplay(); 
      canvas.setPaint(Color.BLACK);
      canvas.drawString("You go first: drag your mouse between two vertices to move.", 100, 550);

      
      // comment-in the second line and comment-out the first to use your Player class
      
 //     RandomPlayer P = new RandomPlayer(); 
    Player P = new Player(); 
      
      JEventQueue events=new JEventQueue(); 
      events.listenTo(canvas,"canvas"); 
      
      int x0 = 0;
      int y0 = 0; 
      
      int round = 0; 
      while(!completeGraph(G)) {

         
         EventObject event=events.waitEvent(); 
         if(events.isMouseEvent(event)){ 
            int x=events.getMouseX(event); 
            int y=events.getMouseY(event); 
            if(events.isMousePressed(event)){
               x0=x;
               y0=y;
            } 
            

            if(events.isMouseReleased(event)) {
               // draw a red edge between vertices closest to
               //    begin and end of mouse drag
               
               int source = closestVertex(x0,y0);  
               int target = closestVertex(x,y);   

               if(source != target && !G.isEdge(source,target)) {
                  G.addEdge(source, target,1);


               }
               else if(source == target) {
                  canvas.setPaint(Color.BLACK);
                  canvas.drawString("You may not draw an edge from a vertex to itself... try again!", 100, 530);
                  continue; 
               }
               else {
                  canvas.setPaint(Color.BLACK);
                  canvas.drawString("You may not redraw a blue edge... try again!", 100, 550);
                  continue; 
               }

             drawDisplay(); 
               
               if(G.isCycleOfLength(3,1)) {
                  canvas.setPaint(Color.BLACK);
                  canvas.drawString("Blue Player Wins!", 100, 550);
                  break;
               }
               
               canvas.sleep(1000);

               // get move (edge) from player and make a move as blue
               
               Move m = P.chooseMove(G);
               System.out.println(m); 
               G.addEdge(m.source, m.target, -1); 

               drawDisplay(); 

               if(G.isCycleOfLength(3,-1)) {
                  canvas.setPaint(Color.BLACK);
                  canvas.drawString("Red Player Wins!", 100, 550);
                  break;
               }
  
               
            }
    
         } 
      } 
   } 
}

class Move {
   int source;
   int target;
   public Move(int s, int t) {
      source = s; target = t; 
   }
   public String toString() {
      return "(" + source + "," + target + ")";
   }
}