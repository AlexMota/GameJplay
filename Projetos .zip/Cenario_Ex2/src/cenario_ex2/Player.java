/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cenario_ex2;

/**
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */

import jplay.Keyboard;
import jplay.Sprite;

 public class Player extends Sprite
 {
         private boolean horizontalMoveKeyPressed;

         public Player()
         {
                 super("player.png",8);
         }

         public boolean moveuNaHorizontal()
         {
                 return horizontalMoveKeyPressed;
         }

         public void move(Keyboard keyboard)
         {
                 horizontalMoveKeyPressed = true;
                 if(keyboard.keyDown(Keyboard.LEFT_KEY) == true)
                 {
                     this.x -= 1;
                     setCurrFrame(2);
                 }
                 else
                     if(keyboard.keyDown(Keyboard.RIGHT_KEY) == true)
                     {
                         this.x += 1;
                         setCurrFrame(3);
                     }
                     else
                         horizontalMoveKeyPressed = false;

                 if (!horizontalMoveKeyPressed)
                 {
                     if(keyboard.keyDown(Keyboard.UP_KEY) == true)
                     {
                         this.y -= 1;
                         setCurrFrame(0);
                     }
                     else
                         if(keyboard.keyDown(Keyboard.DOWN_KEY) == true)
                         {
                             this.y += 1;
                             setCurrFrame(1);
                         }
                 }
         }
 }
